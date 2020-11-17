package com.uclab.ER.audio.featureExtraction;

import java.util.Vector;
import javax.sound.sampled.AudioInputStream;
import com.uclab.ER.audio.preprocessing.jAudioPreProcessing;

/**
 * This class is feature extraction component by jAudio Library.
 * @author Jaehun Bang
 *
 */

public class jAudioFeatureExtraction {
	StatisticalFunction sfe = new StatisticalFunction();
	
	/**
	 * Extract the feature set of audio data
	 * @param win_samples
	 * @param ais
	 * @return featureVector
	 */
	
	public double[] extractFeatures(double[][] win_samples, AudioInputStream ais){
		Vector<double[]> peakVector = new Vector<double[]>();
		Vector<double[]> psVector = new Vector<double[]>();
		Vector<double[]> lpcVector = new Vector<double[]>();
		Vector<double[]> mfccVector = new Vector<double[]>();
				
		Vector<Vector<double[]>> filterbank_windowing = new Vector<Vector<double[]>>();
		
		jAudioPreProcessing japp = new jAudioPreProcessing(ais.getFormat().getSampleRate());
		for(int i=0; i<win_samples.length; i++){
			try {
				double[] peak = japp.getPeak(win_samples[i]);
				double[] ps = japp.getPowerSpectrum(win_samples[i]);
				double[] lpc = japp.getLPC(win_samples[i]);
				double[] mfcc = japp.getMFCC(win_samples[i]);
				
				
				peakVector.add(peak);
				psVector.add(ps);
				lpcVector.add(lpc);
				mfccVector.add(mfcc);
								
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		filterbank_windowing.add(mfccVector);
		filterbank_windowing.add(lpcVector);
		
		
		double[] featureVector = statisticalFeatureExtraction(peakVector, psVector, filterbank_windowing );
		
		return featureVector;
	}
	/**
	 * Extract statistical feature set 
	 * @param peak
	 * @param ps
	 * @param filterbank_windowing
	 * @return
	 */
	private double[] statisticalFeatureExtraction(Vector<double[]> peak, Vector<double[]> ps,  Vector<Vector<double[]>> filterbank_windowing){
		
		
		Vector<double[]> featurevt = new Vector<double[]>();
		double[] peakFeatures = windowingExtractFeatures(peak);
		double[] psFeatures = windowingExtractFeatures(ps);
		
		for(int i =0; i<filterbank_windowing.size(); i++){
			double[] filterBankFeature = getFilterBankFeatures(filterbank_windowing.get(i));
			featurevt.add(filterBankFeature);
		}
	
		featurevt.add(peakFeatures);
		featurevt.add(psFeatures);

		
		double[] statisticalFeatures = getSigleArray(featurevt);
		return statisticalFeatures;
		
	}
	private double[] windowingExtractFeatures(Vector<double[]> data){
		double[] temp = vector2double(data);
		double[] featurevt = new double[4];
		
		featurevt[0] = sfe.getMean(temp);
		featurevt[1] = sfe.getStdDev(temp);
		featurevt[2] = sfe.getMax(temp);
		featurevt[3] = sfe.getMin(temp);
		
		
		return featurevt;
	}
	
	private double[][] reverse2ArraysData(Vector<double[]> data){
		double[][] result = new double[data.get(0).length][data.size()];
		for(int i =0; i<data.size(); i++){
			for(int j=0; j<data.get(i).length; j++){
				result[j][i] = data.get(i)[j];
			}
		}
		return result;
	}
	
	private double[] getFilterBankFeatures(Vector<double[]> data){
		double[][] reverseData = reverse2ArraysData(data);
		StatisticalFunction sfe = new StatisticalFunction();
		double[] means = new double[data.get(0).length];
		double[] stdDev = new double[data.get(0).length];
		double[] max = new double[data.get(0).length];
		double[] min = new double[data.get(0).length];
		
		double[] featurevt = new double[means.length*4];
	
		for(int i =0; i< reverseData.length; i++){
			means[i] = sfe.getMean(reverseData[i]);
			stdDev[i] = sfe.getStdDev(reverseData[i]);
			max[i] = sfe.getMax(reverseData[i]);
			min[i] = sfe.getMin(reverseData[i]);
			
			featurevt[i] = means[i];
			featurevt[means.length + i] = stdDev[i];
			featurevt[means.length*2 + i] = max[i];
			featurevt[means.length*3 + i] = min[i];	
		}
		
		
		
		return featurevt;
	}
	
	private double[] vector2double(Vector<double[]> doubles) {
		
		Vector<Double> temp = new Vector<Double>();
		
		for (int i = 0; i < doubles.size(); i++) {
			for (int j = 0; j < doubles.get(i).length; j++) {
				temp.add(doubles.get(i)[j]);
			}
		}
		double[] result = transformSingleArray (temp);
	
		return result;
	}
	
	private double[] getSigleArray(Vector<double[]> data){
		Vector<Double> temp = new Vector<Double>();
		for(int i=0; i<data.size(); i++){
			for(int j=0;j<data.get(i).length; j++){
				temp.add(data.get(i)[j]);
			}
		}
		double[]result = transformSingleArray(temp);
		return result;
	}
	
	private double[] transformSingleArray(Vector<Double> data){
		double[] result = new double[data.size()];
		for(int i=0; i<data.size(); i++){
			result[i] = data.get(i);
		}
		return result;
	}
	
}
