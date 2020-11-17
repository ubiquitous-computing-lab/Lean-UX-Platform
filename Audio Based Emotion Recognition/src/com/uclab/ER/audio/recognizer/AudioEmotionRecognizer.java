package com.uclab.ER.audio.recognizer;

import com.uclab.ER.audio.classification.Classification;
import com.uclab.ER.datatype.Emotion;
import com.uclab.ER.datatype.SensoryData;

import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.RandomForest;

/**
 * 
 * @author Jaehun Bang
 *
 */
public class AudioEmotionRecognizer {

	static String answer = "";
	
	RecognizerProcessing aer = null;
	
	
	public AudioEmotionRecognizer(String[] labels, String path, String pos_path, String nega_path){
		
		
		Classification recognizer = new Classification(path, 100, labels,new RandomForest());
		Classification positvie = new Classification(pos_path, 100, labels,new SMO());
		Classification negative = new Classification(nega_path, 100, labels,new SMO());
		
		aer = new RecognizerProcessing(recognizer,positvie, negative, labels, path);
		
	}
	
	
	public int RecognizeEmotion(SensoryData sd) {
		
		Emotion emotion = aer.recognize(sd);
		System.err.println(emotion.getEmotion());
			
		System.err.println(aer.toString(emotion));
		System.out.println("-------------------------------------------");
	
		
		return emotion.getEmotion();
	}
}
