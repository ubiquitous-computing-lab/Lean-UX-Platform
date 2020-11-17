package com.uclab.ER.audio.example;

import com.uclab.ER.audio.service.AudioERService;

/***
 * This class show the example of the audio based emotion recognition modules.
 * @author Jaehun Bang
 *
 */

public class AudioSeviceExample {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String[] labels = { "Anger", "Neutral", "Joyful", "Sadness"};
		String path = "model/training_v3.txt";
		String pos_path = "model/positive_v3.txt";
		String nega_path = "model/negative_v3.txt";
					
		AudioERService as = new AudioERService(labels, path, pos_path, nega_path);
		as.beginProcessing();
	
	}

}

