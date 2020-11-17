package BLMain;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;

import BLClassification.KinectBLClassification;
import BLFeatureExtraction.KinectBLFeatureCalculation;
import weka.classifiers.functions.SMO;
/**
 * 
 * @author Asif
 * THis class classfies user context based on the 25 skeleton coordinates data 
 * A Software to get Video-based Body Language
 * Dated Aug 29, 2019
 *
 **
 */

public class KinectBLNotifier {

	private static Scanner scan;
	private SMO smo;
	
	public void BLClassifier() throws InterruptedException, IOException, ParseException {
				

		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS"); //yyyy-MM-dd'T'HH:mm:ssZ
		Date date = new Date();
		
	long userID = 10;
	String score = null;
	String label = null;
	long systemTS = 0;
	Date BL_emo_timestamp = null;

		
	String modelFile = "D:\\BodyLanguageAsif\\CommunicationFiles\\BLEmotionModel.model";//"BLEmotionModel
	KinectBLClassification classifier = new KinectBLClassification(modelFile);
	KinectBLFeatureCalculation fr = new KinectBLFeatureCalculation();
	
	Thread.sleep(1000);
		
		double[][] ary = new double[90][75];
		scan = new Scanner(new File("D:\\BodyLanguageAsif\\CommunicationFiles\\SkeletalData.txt"));//test2 SkeletalData
		scan.useDelimiter(",|\r\n");
		
	

	
		for(int i = 0; i<90; i++){

			for(int j = 0; j<75; j++) 
			
			{	ary[i][j] = scan.nextDouble();

			}
			
 
		}
		
		fr.setInputData(ary);   
		
		double[] inputFeatures = fr.extractFeatures(); //inputFeatures.length = 280

		label = classifier.Classify(inputFeatures);
		

		double[] prediction= classifier.PredictedRanges(inputFeatures);
		String[] labelString = new String[] {"Joyful","Anger","Neutral", "Sadness"}; //, "Surprised", "Disgust"
		System.out.printf("BL Emotion: "+ label );
		
		try (BufferedWriter out = new BufferedWriter(new FileWriter("D:\\BodyLanguageAsif\\CommunicationFiles\\FinalLabels.txt", true))) //false no append
		{


			systemTS = System.currentTimeMillis();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");  //yyyy-MM-dd'T'HH:mm:ssZ 
					
			BL_emo_timestamp = new Date(systemTS);
					
			out.write(userID + " " + label + " "+  sdf.format(BL_emo_timestamp)); //sdf.format(BL_emo_timestamp)
			out.newLine();
			
			
			if (label == labelString[0] ) 
				{ score = Double.toString(prediction[0]); } 
			else if (label == labelString[1] )
				{ score = Double.toString(prediction[1]); } 
			else if(label == labelString[2] )
				{ score = Double.toString(prediction[2]);} 
			else
				{ score = Double.toString(prediction[3]); }
			
						
			Map<String, Object> map = new HashMap<>();

//				map.put("User_id", userID);
				map.put("project" , 1 );
				map.put("participant" , 1 );
				map.put("projectstarttime" ,  dateFormat.format(date)+'Z' );
				map.put("datatype" , "BLData" );
				map.put("taskid" , 1 );
				map.put("streamdatatime", sdf.format(BL_emo_timestamp)+'Z');
				map.put("emotion", label);
				map.put("score", score);

		}
		scan.close();

		
	}
}