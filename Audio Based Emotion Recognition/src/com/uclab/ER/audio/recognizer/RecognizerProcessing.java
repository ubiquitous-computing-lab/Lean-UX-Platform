/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.ER.audio.recognizer;

import java.io.IOException;
import javax.sound.sampled.AudioInputStream;

import com.uclab.ER.audio.classification.Classification;
import com.uclab.ER.audio.featureExtraction.jAudioFeatureExtraction;
import com.uclab.ER.audio.preprocessing.SilentRemoval;
import com.uclab.ER.audio.preprocessing.jAudioPreProcessing;
import com.uclab.ER.audio.segmentation.WindowBasedSegmentation;
import com.uclab.ER.audio.textER.Speech2TextERapi;
import com.uclab.ER.audio.util.AudioBufferManagement;
import com.uclab.ER.audio.util.ConvertAudioData;
import com.uclab.ER.datatype.Emotion;
import com.uclab.ER.datatype.SensoryData;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;

/**
 *
 * @author Jaehun Bang
 */
public class RecognizerProcessing {

	String[] labels;
	
	AudioInputStream buffer = null; // Audio Buffer
	AudioInputStream origin_buffer=null;
	AudioBufferManagement abm = null; // Supporting Class for accumulating Audio Buffer
	AudioBufferManagement kakao_abm = null;
	SilentRemoval silentRemover = new SilentRemoval(Encoding.PCM_SIGNED, 48000, 16, 1, 2,
			48000, false, 5); // set Silent Remover Option
	
	WindowBasedSegmentation aers = new WindowBasedSegmentation(3, 48000, 1);
	Classification aerc = null;
	Classification positive = null;
	Classification negative = null;
	String path;
	
	ConvertAudioData cad = new ConvertAudioData();
	  AudioFormat audioFM = new AudioFormat(
	  48000, // Sample Rate
		16, // Size of SampleBits
		1, // Number of Channels
		true, // Is Signed?
		false // Is Big Endian?
		);


	byte[] data; // Never Mind

	public RecognizerProcessing(Classification classifier, String[] labels, String filepath) {
		aerc = classifier;
		this.labels = labels;
		this.path = filepath;
	}
	public RecognizerProcessing(Classification classifier,Classification positive, Classification negative, String[] labels, String filepath) {
		aerc = classifier;
		this.positive = positive;
		this.negative = negative;
		this.labels = labels;
		this.path = filepath;
	}
	/***
	 * recognize emotion
	 * @param input
	 * @return
	 */

	
	public Emotion recognize(SensoryData input) {
		kakao_abm = new AudioBufferManagement(audioFM);
		origin_buffer = kakao_abm.accumulateData(origin_buffer, cad.convertAudioInputStream(input.getAudioSensoryData(), audioFM));

		AudioInputStream preprocessed = silentRemover.preprocessing(input); // Audio Input Stream for removing silent
		
		Emotion emotion = new Emotion();
		emotion.setEmotion(labels.length);

		if (preprocessed != null) {
			abm = new AudioBufferManagement(preprocessed.getFormat()); // AudioBufferManagement for accumulating Audio
																		// Buffer
			buffer = abm.accumulateData(buffer, preprocessed);
			try {
				while (buffer.available() > 0) {
					if (buffer != null) {
						AudioInputStream segmented = aers.segmentation(buffer);
						
						if (segmented != null) {
											
						
							String text = Speech2TextERapi.STT(origin_buffer);
							
						    //double predict = Speech2TextERapi.predict("http://127.0.0.1:10000/post", text);
							double predict = 0.5;
							buffer = aers.returnRemainedData(buffer);
							System.out.println("Data Size:" + buffer.getFrameLength()+ "," + buffer.getFormat());
							segmented.reset();
							origin_buffer = null;
							
							
							try {
								double[] featurevt = featureExtraction(segmented);
								if(text != "No result") {
									if(predict < 0.3) {
										emotion.setEmotion(negative.classify(featurevt)); 
									}else if (predict > 0.7) {
										emotion.setEmotion(positive.classify(featurevt));
									}else {
										emotion.setEmotion(aerc.classify(featurevt));
									}
								}else {
									System.err.println(text);
									emotion.setEmotion(aerc.classify(featurevt));
								}
								
								
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							

						} else {
							break;
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//emotion.setEmotion(decisionEmotion(ev));
		} else {
			emotion.setEmotion(labels.length);
		}
		


		return emotion;

	}
	
	/***
	 * convert Emotion data to String
	 * @param emotion
	 * @return
	 */

	public String toString(Emotion emotion) {
		if (emotion.getEmotion() >= labels.length) {
			return "NoEmotion";
		}
		return labels[emotion.getEmotion()];
	}
	
	

	public RecognizerProcessing(Object obj) {
		data = (byte[]) obj;
	}

	/**
	 * feature extraction for real-time audio signal based emotion recognition
	 * @param preprocessed
	 * @return
	 * @throws Exception
	 */
	
	static public double[] featureExtraction(AudioInputStream preprocessed) throws Exception {
		jAudioFeatureExtraction jafe = new jAudioFeatureExtraction();
		jAudioPreProcessing japp = new jAudioPreProcessing();
		double[][] win_samples = japp.getSamples(preprocessed, 512);
		preprocessed.reset();
		double[] featurevt = jafe.extractFeatures(win_samples,preprocessed);
		return featurevt;
	}

}
