package BLClassification;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import weka.classifiers.functions.SMO;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;


/**
 * 
 * @author Asif
 * THis class classfies user context based on the 25 skeleton coordinates data 
 * A Software to get Video-based Body Language
 * Dated Aug 29, 2019
 *
 **
 */
public class KinectBLClassification extends Classification<double[]>{

	String[] labelString = new String[] {"Joyful","Anger","Neutral", "Sadness"}; //, "Surprised", "Disgust"
	
	private SMO smo;
	Instances dummyDataset;

	public KinectBLClassification(String modelFile) {
		smo = null;
		loadModel(modelFile);
	}

	public boolean loadModel(String filename) {
		try {
			System.out.println("loading model now... ");
			smo = (SMO) weka.core.SerializationHelper.read(filename);
			dummyDataset = createDummyDataset(281);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	

	public boolean saveModel(String filename) {
		try {
			weka.core.SerializationHelper.write(filename, smo);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	

	protected Instance createInstance(double[] features) {  //280
		
		int numAttributes = features.length;
		Instance instance = new Instance(numAttributes + 1);
		for (int i = 0; i < numAttributes; i++)
			instance.setValue(i, features[i]);

		try { 
			 BufferedWriter outM = new BufferedWriter(new FileWriter("D:\\BodyLanguageAsif\\CommunicationFiles\\SkeletalDataMain.txt", true));
			 outM.write(instance.toString());
				outM.newLine();
				outM.close();
		} catch (IOException e) {
			e.printStackTrace();
		}   
		
		
		return instance;
	}
	

	protected Instances createDummyDataset(int numAttributes) {

		FastVector fvWekaAttributes = new FastVector(numAttributes);
		

		for (int i = 0; i < numAttributes - 1; i++) {
			Attribute attribute = new Attribute("f" + i);
			fvWekaAttributes.addElement(attribute);
		}
		

		FastVector fvClassVal = new FastVector(6);
		fvClassVal.addElement("c1");
		fvClassVal.addElement("c2");
		fvClassVal.addElement("c3");
		fvClassVal.addElement("c4");
		Attribute classAttribute = new Attribute("class", fvClassVal);
		fvWekaAttributes.addElement(classAttribute);
		

		Instances dataset = new Instances("Rel", fvWekaAttributes, 1);

		dataset.setClassIndex(numAttributes - 1);
		
		return dataset;
	}

	
	
	@Override
	public String Classify(double[] data) { //// 140 for sum + 140 for sumsq = 280 features data.length

		try {
			if (smo == null)

				System.out.println(" SMO is null");
			
			Instance instance = createInstance(data);
			instance.setDataset(dummyDataset);

			// Classify
			int labelIndex =  Double.valueOf(smo.classifyInstance(instance)).intValue();

			return labelString[labelIndex];

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public double[] PredictedRanges(double[] data) {
		try {
			if (smo == null)
				System.out.println(" Trained Model Not Found");
			

			Instance instance = createInstance(data);
			instance.setDataset(dummyDataset);

		
			double[] prediction=smo.distributionForInstance(instance);

			return prediction; //labelString[labelIndex];

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	}