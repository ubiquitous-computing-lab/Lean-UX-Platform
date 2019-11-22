package org.uiux.maven.fer;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.tensorflow.DataType;
import org.tensorflow.Graph;
import org.tensorflow.Output;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.types.UInt8;

/** Sample use of the TensorFlow Java API to label images using a pre-trained model. */
public final class ClassifyFacialEmotions_4labels {

  public static void main(String[] args) {
    //String modelDir = args[0];
	String modelDir = "resources";  
    //String imageFile = args[1];
    String imageFile = "resources/face_0_225.png";
    
    byte[] graphDef = readAllBytesOrExit(Paths.get(modelDir, "model_r50_4all_4lab_2620_features.pb"));
    List<String> labels =
        readAllLinesOrExit(Paths.get(modelDir, "facial_emotion_label_strings.txt"));
    byte[] imageBytes = readAllBytesOrExit(Paths.get(imageFile));

    try (Tensor<Float> image = constructAndExecuteGraphToNormalizeImage(imageBytes)) {
      Object[] outputs = executeFERGraph(graphDef, image);
      float[] prediction = (float[]) outputs[0];
      float[] featureVector = (float[]) outputs[1];
     
      //System.out.println(Arrays.toString(featureVector));
      System.out.println(
    	  String.format("The first entry of Feature vector: %s", featureVector[0]));
      int bestLabelIdx = maxIndex(prediction);
      System.out.println(
          String.format("%s (%.2f%% likely)",
              labels.get(bestLabelIdx),
              prediction[bestLabelIdx] * 100f));
    }
  }
	
/**
 * construct And Execute Graph To Normalize Image
 * @param imageBytes
 *
 */
  static Tensor<Float> constructAndExecuteGraphToNormalizeImage(byte[] imageBytes) {
    try (Graph g = new Graph()) {
      GraphBuilder b = new GraphBuilder(g);
      // Some constants specific to the pre-trained model at:
      // https://storage.googleapis.com/download.tensorflow.org/models/inception5h.zip
      //
      // - The model was trained with images scaled to 224x224 pixels.
      // - The colors, represented as R, G, B in 1-byte each were converted to
      //   float using (value - Mean)/Scale.
      final int H = 256;
      final int W = 256;
      final float mean = 117f;
      final float scale = 1f;

      // Since the graph is being constructed once per execution here, we can use a constant for the
      // input image. If the graph were to be re-used for multiple input images, a placeholder would
      // have been more appropriate.
      final Output<String> input = b.constant("input", imageBytes);
      final Output<Float> output =
          b.div(
              b.sub(
                  b.resizeBilinear(
                      b.expandDims(
                          b.cast(b.decodeJpeg(input, 3), Float.class),
                          b.constant("make_batch", 0)),
                      b.constant("size", new int[] {H, W})),
                  b.constant("mean", mean)),
              b.constant("scale", scale));
      try (Session s = new Session(g)) {
        // Generally, there may be multiple output tensors, all of them must be closed to prevent resource leaks.
        return s.runner().fetch(output.op().name()).run().get(0).expect(Float.class);
      }
    }
  }
  
/**
 * execute FER Graph
 * @param graphDef
 * @param image
 *
 * @return object
 */
  static Object[] executeFERGraph(byte[] graphDef, Tensor<Float> image) {
	    try (Graph g = new Graph()) {
	      g.importGraphDef(graphDef);
	      try (Session s = new Session(g)) {
	        List<Tensor<?>> results = s.runner().feed("IteratorGetNext", image).fetch("resnet_v1_50/predictions/Softmax", 0).fetch("resnet_v1_50/block2/unit_4/bottleneck_v1/Relu", 0).run();
	        
	        Tensor<?> pred = results.get(0);
	        final long[] pshape = pred.shape();
	        int nlabels = (int) pshape[1];
	        final float[] prediction = pred.copyTo(new float[1][nlabels])[0];
	        
	        Tensor<?> feature = results.get(1);
	        final long[] fshape = feature.shape();
	        int fheight = (int) fshape[1];
	        int fwidth = (int) fshape[2];
	        int fchannel = (int) fshape[3];
	        final float[][][] featureArray = feature.copyTo(new float[1][fheight][fwidth][fchannel])[0];
	        
	        float [] featureVector = new float[fchannel];
	        for (int c = 0; c < fchannel; ++c) {
	        	float sum = 0;
	        	for (int h = 0; h < fheight; ++h) {
	        		for (int w = 1; w < fwidth; ++w) {
	        			sum += featureArray[h][w][c];
	        		}
	        	}
	        	float average = sum / (fheight * fwidth);
	        	featureVector[c] = average;
	        }
	        
	        return new Object[]{prediction, featureVector};
	      }
	    }
  }
  

  static int maxIndex(float[] probabilities) {
    int best = 0;
    for (int i = 1; i < probabilities.length; ++i) {
      if (probabilities[i] > probabilities[best]) {
        best = i;
      }
    }
    return best;
  }

  static byte[] readAllBytesOrExit(Path path) {
    try {
      return Files.readAllBytes(path);
    } catch (IOException e) {
      System.err.println("Failed to read [" + path + "]: " + e.getMessage());
      System.exit(1);
    }
    return null;
  }

  static List<String> readAllLinesOrExit(Path path) {
    try {
      return Files.readAllLines(path, Charset.forName("UTF-8"));
    } catch (IOException e) {
      System.err.println("Failed to read [" + path + "]: " + e.getMessage());
      System.exit(0);
    }
    return null;
  }

  // In the fullness of time, equivalents of the methods of this class should be auto-generated from
  // the OpDefs linked into libtensorflow_jni.so. That would match what is done in other languages
  // like Python, C++ and Go.
  static class GraphBuilder {
    GraphBuilder(Graph g) {
      this.g = g;
    }

    Output<Float> div(Output<Float> x, Output<Float> y) {
      return binaryOp("Div", x, y);
    }

    <T> Output<T> sub(Output<T> x, Output<T> y) {
      return binaryOp("Sub", x, y);
    }

    <T> Output<Float> resizeBilinear(Output<T> images, Output<Integer> size) {
      return binaryOp3("ResizeBilinear", images, size);
    }

    <T> Output<T> expandDims(Output<T> input, Output<Integer> dim) {
      return binaryOp3("ExpandDims", input, dim);
    }

    <T, U> Output<U> cast(Output<T> value, Class<U> type) {
      DataType dtype = DataType.fromClass(type);
      return g.opBuilder("Cast", "Cast")
          .addInput(value)
          .setAttr("DstT", dtype)
          .build()
          .<U>output(0);
    }

    Output<UInt8> decodeJpeg(Output<String> contents, long channels) {
      return g.opBuilder("DecodeJpeg", "DecodeJpeg")
          .addInput(contents)
          .setAttr("channels", channels)
          .build()
          .<UInt8>output(0);
    }

    <T> Output<T> constant(String name, Object value, Class<T> type) {
      try (Tensor<T> t = Tensor.<T>create(value, type)) {
        return g.opBuilder("Const", name)
            .setAttr("dtype", DataType.fromClass(type))
            .setAttr("value", t)
            .build()
            .<T>output(0);
      }
    }
    Output<String> constant(String name, byte[] value) {
      return this.constant(name, value, String.class);
    }

    Output<Integer> constant(String name, int value) {
      return this.constant(name, value, Integer.class);
    }

    Output<Integer> constant(String name, int[] value) {
      return this.constant(name, value, Integer.class);
    }

    Output<Float> constant(String name, float value) {
      return this.constant(name, value, Float.class);
    }

    private <T> Output<T> binaryOp(String type, Output<T> in1, Output<T> in2) {
      return g.opBuilder(type, type).addInput(in1).addInput(in2).build().<T>output(0);
    }

    private <T, U, V> Output<T> binaryOp3(String type, Output<U> in1, Output<V> in2) {
      return g.opBuilder(type, type).addInput(in1).addInput(in2).build().<T>output(0);
    }
    private Graph g;
  }
}
