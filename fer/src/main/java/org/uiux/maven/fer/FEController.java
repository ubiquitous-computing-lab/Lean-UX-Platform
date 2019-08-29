package org.uiux.maven.fer;

import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.opencv.videoio.VideoCapture;
import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;

/**
 * The controller handles the button for starting/stopping the camera, the acquired video stream, 
 * and the face detection + tracking.
 */
@SuppressWarnings("restriction")
public class FEController
{
			
	// FXML buttons
	@FXML
	private Button cameraButton;
	// the FXML area for showing the current frame
	@FXML
	private ImageView originalFrame;
	
	// a timer for acquiring the video stream
	private ScheduledExecutorService timer;
	
	// the OpenCV object that performs the video capture
	private VideoCapture capture;
	
	// a flag to change the button behavior
	private boolean cameraActive;
	
	// face cascade classifier
	private CascadeClassifier faceCascade;
	private int absoluteFaceSize;
	
	// period (ms) of capturing 1 frame for the detection
	private static int period = 1000;
	
	/**
	 * Initialize the controller, at start time
	 */
	protected void init()
	{
		this.capture = new VideoCapture();
		this.faceCascade = new CascadeClassifier();
		this.absoluteFaceSize = 0;
		
		// set a fixed width for the frame
		originalFrame.setFitWidth(600);
		
		// preserve image ratio
		originalFrame.setPreserveRatio(true);
	}
	
	/**
	 * The action triggered by pushing the button on the GUI
	 */
	@FXML
	protected void startCamera()
	{	
		if (!this.cameraActive)
		{			
			// start the video capture
			this.capture.open(0);
			
			// is the video stream available?
			if (this.capture.isOpened())
			{
				this.cameraActive = true;
				
				// grab a frame with predefined period
				Runnable frameGrabber = new Runnable() {
					
					@Override
					public void run()
					{
						// effectively grab and process a single frame
						Mat frame = grabFrame();
						
						// convert and show the frame
						Image imageToShow = Utils.mat2Image(frame);
						updateImageView(originalFrame, imageToShow);
						
					}
					
				};
				
				this.timer = Executors.newSingleThreadScheduledExecutor();
				this.timer.scheduleAtFixedRate(frameGrabber, 0, period, TimeUnit.MILLISECONDS);
				
				// update the button content
				this.cameraButton.setText("Stop Camera");
			}
			else
			{
				// log the error
				System.err.println("Failed to open the camera connection...");
			}
		}
		else
		{
			// the camera is not active at this point
			this.cameraActive = false;
			
			// update again the button content
			this.cameraButton.setText("Start Camera");
			
			// stop the timer
			this.stopAcquisition();
		}
	}
	
	/**
	 * Get a frame from the opened video stream (if any)
	 * 
	 * @return the {@link Image} to show
	 */

	private Mat grabFrame()
	{
		Mat frame = new Mat();
		
		// check if the capture is open
		if (this.capture.isOpened())
		{
			try
			{
				
				// read the current frame
				this.capture.read(frame);
				
				// if the frame is not empty, process it
				if (!frame.empty())
				{
					// detect face
					this.detectAndDisplay(frame);
					
					// recognize facial emotion
					this.classifyEmotion("outputs/face_0.png");
				}
				
			}
			catch (Exception e)
			{
				// log the (full) error
				System.err.println("Exception during the image elaboration: " + e);
			}
			
			
		}
		
		return frame;
	}
	

	/**
	 * Method for face detection and tracking
	 * 
	 * @param frame
	 *            it looks for faces in this frame
	 */
	protected void detectAndDisplay(Mat frame)
	{
		MatOfRect faces = new MatOfRect();
		Mat grayFrame = new Mat();
		Mat faceImg = new Mat();
		
		// clone original frame
		Mat oriFrame = frame.clone();
		
		// convert the frame in gray scale
		Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
		
		// equalize the frame histogram to improve the result
		Imgproc.equalizeHist(grayFrame, grayFrame);
		
		// compute minimum face size (20% of the frame height)
		if (this.absoluteFaceSize == 0)
		{
			int height = grayFrame.rows();
			if (Math.round(height * 0.2f) > 0)
			{
				this.absoluteFaceSize = Math.round(height * 0.2f);
			}
		}
		
		// load trained model
		this.faceCascade.load("resources/haarcascade_frontalface_alt.xml");
						
		// detect faces
		this.faceCascade.detectMultiScale(grayFrame, faces, 1.1, 2, 0 | Objdetect.CASCADE_SCALE_IMAGE,
				new Size(this.absoluteFaceSize, this.absoluteFaceSize), new Size());
		
		// each rectangle in faces is a face
		Rect[] facesArray = faces.toArray();
		for (int i = 0; i < facesArray.length; i++)
		{
			// draw each rectangle of detected face on frame
			Imgproc.rectangle(frame, facesArray[i].tl(), facesArray[i].br(), new Scalar(0, 255, 0), 3);
			
			// crop the original RGB frame around the detected face
			Mat faceCrop = oriFrame.submat(facesArray[i]);
			
			// resize the cropped frame to resolution of 256x256 (down-scaling:INTER_AREA; Up-scaling:INTER_CUBIC)
			Imgproc.resize(faceCrop, faceImg, new Size(256,256), 0, 0, Imgproc.INTER_AREA);
			
			// write face image to "outputs" folder ("face_0_50.png" = 1st person's face of the 50th detection time)
			//Imgcodecs.imwrite("outputs/face_" + i + "_" + count + ".png", faceImg);
			Imgcodecs.imwrite("outputs/face_0.png", faceImg);
		}
		
	}
		
	/**
	 * Stop the acquisition from the camera and release all the resources
	 */
	private void stopAcquisition()
	{
		if (this.timer!=null && !this.timer.isShutdown())
		{
			try
			{
				// stop the timer
				this.timer.shutdown();
				this.timer.awaitTermination(period, TimeUnit.MILLISECONDS);
			}
			catch (InterruptedException e)
			{
				// log any exception
				System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
			}
		}
		
		if (this.capture.isOpened())
		{
			// release the camera
			this.capture.release();
		}
	}
	
	/**
	 * Update the {@link ImageView} in the JavaFX main thread
	 * 
	 * @param view
	 *            the {@link ImageView} to update
	 * @param image
	 *            the {@link Image} to show
	 */
	private void updateImageView(ImageView view, Image image)
	{
		Utils.onFXThread(view.imageProperty(), image);
	}
	
	/**
	 * On application close, stop the acquisition from the camera
	 */
	protected void setClosed()
	{
		this.stopAcquisition();
	}
	
	private void classifyEmotion(String imageFile)
	{
			// Load FER model
			byte[] graphDef = ClassifyFacialEmotions_4labels.readAllBytesOrExit(Paths.get("resources", "model_r50_4all_4lab_2620_features.pb"));
			List<String> labels = ClassifyFacialEmotions_4labels.readAllLinesOrExit(Paths.get("resources", "facial_emotion_label_strings.txt"));
			
			// Execute face image and then infer emotion label
		    byte[] imageBytes = ClassifyFacialEmotions_4labels.readAllBytesOrExit(Paths.get(imageFile));
	        //byte[] imageBytes = new byte[(int) (faceImg.total() * faceImg.elemSize())];
	        //faceImg.get(0, 0, imageBytes);
	        //Mat faceImgProperties = new Mat(faceImg.size(), faceImg.type());
	        //faceImgProperties.put(0, 0, imageBytes);
	        
		    try (Tensor<Float> image = ClassifyFacialEmotions_4labels.constructAndExecuteGraphToNormalizeImage(imageBytes)) {
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
		  	        
		  	        //System.out.println(Arrays.toString(featureVector));
		  	        int bestLabelIdx = ClassifyFacialEmotions_4labels.maxIndex(prediction);
		  	        float predictionScore = prediction[bestLabelIdx];
		  	        String label = labels.get(bestLabelIdx);
		  	        System.out.println(String.format("%s (%.2f%% likely)", label, predictionScore*100f));
		  	        
		  	        // upload to CouchDB
		  	        this.uploadToCouchDB(featureVector, predictionScore, label);
		  	        
		  	      }
		    	}
		    }
	}
	
	/** https://dcl.leanuxplatform.com:6984/_utils/#/login 
	 * username: leanux
	 * password: l3@nux
	 */	 
	private void uploadToCouchDB(float[] featureVector, float predictionScore, String label)
	{
		// define CouchDB client
		CouchDbProperties properties = new CouchDbProperties()
				  .setDbName("facial_expression_recognition") //bl_emotions leanux_archive  leanux_emotion_archive
				  .setCreateDbIfNotExist(true)
				  .setProtocol("https")
				  .setHost("dcl.leanuxplatform.com") 
				  .setPort(6984)
				  .setUsername("leanux")
				  .setPassword("l3@nux")
				  .setMaxConnections(100)
				  .setConnectionTimeout(0);
		CouchDbClient dbClient = new CouchDbClient(properties);
		
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA);
		long current = System.currentTimeMillis();
	    String timeStamp = timeFormat.format(current);  
			
		Map<String, Object> map = new HashMap<>();
		map.put("score", predictionScore);
		map.put("emotion", label);
		map.put("datatype" , "Face" );
		map.put("streamdatatime", timeStamp);
		map.put("project" , 21 );
		map.put("participant" , 90 );
		map.put("taskid" , 1 );
		map.put("feature", featureVector);
		
		dbClient.save(map);

		// shutdown the client
		dbClient.shutdown(); 
	}
	 
		    
}

