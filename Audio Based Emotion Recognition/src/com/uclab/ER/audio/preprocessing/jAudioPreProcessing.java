package com.uclab.ER.audio.preprocessing;

import jAudioFeatureExtractor.AudioFeatures.*;
import jAudioFeatureExtractor.jAudioTools.AudioSamples;
import jAudioFeatureExtractor.jAudioTools.FFT;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

/***
 * This Class is to preprocess the audio data using filter-bank algorithm
 * @author BangGae
 *
 */

public class jAudioPreProcessing {
	private double sampling_rate;
	
	private double[][] other_feature_value;
	public jAudioPreProcessing() {
		
	}
	
	public jAudioPreProcessing(double sampling_rate) {
		this.sampling_rate = sampling_rate;
		
	}
	
	
	public double[] getPowerSpectrum(double[] samples) throws Exception {
		PowerSpectrum ps = new PowerSpectrum ();
		double[] result = ps.extractFeature(samples, sampling_rate, null);
		return result;
	}
	
	

	public double[] getLPC(double[] samples) throws Exception {
		LPC lpc = new LPC();
		double[] result = lpc.extractFeature(samples, sampling_rate, null);
		return result;
	}

	public double[] getMFCC(double[] samples) throws Exception {
		MFCC mfcc = new MFCC();
		double[] ms = getMagnitudeSpectrum(samples);
		double[][] temp = new double[1][];
		temp[0] = ms;
		double[] result = mfcc.extractFeature(samples, sampling_rate, temp);
		return result;
	}

	
	public double[] getPeak(double[] samples) throws Exception{
		PeakFinder bh = new PeakFinder();
		double[][] mag = new double[1][];
		mag[0] = getMagnitudeSpectrum(samples);
		double[] result = bh.extractFeature(samples, sampling_rate, mag);
		return result;
	}
	
	public double[] getSpectralCentroid(double[] samples) throws Exception{
		SpectralCentroid sc = new SpectralCentroid();
		double[][] ps = new double[1][];
		ps[0] = getPowerSpectrum(samples);
		double[] result = sc.extractFeature(samples, sampling_rate, ps);
		return result;
	}
	
	public double[] getSpectralFlux(double[] samples) throws Exception{
		SpectralFlux sf = new SpectralFlux();
		double[] result = sf.extractFeature(samples, sampling_rate, other_feature_value);
		return result;
	}
	
	public double[] getSpectralRolloffPoint(double[] samples) throws Exception{
		SpectralRolloffPoint srp = new SpectralRolloffPoint();
		double[] result = srp.extractFeature(samples, sampling_rate, other_feature_value);
		return result;
	}
	
	public double[] getStrongestBeat(double[] samples) throws Exception {
		StrongestBeat bh = new StrongestBeat();
		double[] result = bh.extractFeature(samples, sampling_rate, other_feature_value);
		return result;
	}
	public double[] getStrengthofStrongestBeat(double[] samples) throws Exception {
		StrengthOfStrongestBeat bh = new StrengthOfStrongestBeat();
		double[] result = bh.extractFeature(samples, sampling_rate, other_feature_value);
		return result;
	}
	
	
	public double[] getLogConstantQ(double[] samples) throws Exception{
		LogConstantQ lcq = new LogConstantQ();
		double[] result = lcq.extractFeature(samples, sampling_rate, other_feature_value);
		return result;
	}
	
	public double[] getSpectralVariability(double[] samples) throws Exception{
		SpectralVariability sv = new SpectralVariability();
		double[][] ms = new double[1][];
		ms[0] = getMagnitudeSpectrum(samples);
		double[] result = sv.extractFeature(samples, sampling_rate, ms);
		return result;
	}
	
	
	
	public double[] StrongestBeat(double[] samples) throws Exception{
		StrongestBeat sb = new StrongestBeat();
		double[] result = sb.extractFeature(samples, sampling_rate, other_feature_value);
		return result;
	}
	
	public double[] getZeroCrossings(double[] samples) throws Exception{
		ZeroCrossings sfvzc = new ZeroCrossings();
		double[] result = sfvzc.extractFeature(samples, sampling_rate, null);
		return result;
	}
	
	public double[] getStrongestFrequencyViaZeroCrossings(double[] samples) throws Exception{
		StrongestFrequencyViaZeroCrossings sfvz = new StrongestFrequencyViaZeroCrossings();
		double[][] temp = new double[1][];
		temp[0] = getZeroCrossings(samples);
		double[] result = sfvz.extractFeature(samples, sampling_rate, temp);
		return result; 
		
	}
	
	public double[] getStrongestFrequencyViaSpectralCentroid(double[] samples) throws Exception{
		StrongestFrequencyViaSpectralCentroid sfvsc = new StrongestFrequencyViaSpectralCentroid();
		double[] result = sfvsc.extractFeature(samples, sampling_rate, other_feature_value);
		return result;
	}
	
	public double[] getStrongestFrequencyViaFFTMax(double[] samples) throws Exception{
		StrongestFrequencyViaFFTMax sfvf = new StrongestFrequencyViaFFTMax();
		double[] result = sfvf.extractFeature(samples, sampling_rate, other_feature_value);
		return result;
	}
	
	public double[] getStrongestFrequencyVariability(double[] samples) throws Exception{
		StrongestFrequencyVariability sfv = new StrongestFrequencyVariability();
		double[] result = sfv.extractFeature(samples, sampling_rate, other_feature_value);
		return result;
	}
	
	public double[] getRMS(double[] samples) throws Exception{
		RMS rms = new RMS();
		double[] result= rms.extractFeature(samples, sampling_rate, null);
		return result;
	}
	
	
	
	
	
	
	public double[] getMagnitudeSpectrum(double[] samples) throws Exception {
		MagnitudeSpectrum ms = new MagnitudeSpectrum();
		double[] result = ms.extractFeature(samples, sampling_rate, null);
		return result;
	}
	
	public double[] getRealValue(double[] samples) throws Exception {
		FFT fft = new FFT(samples, null, false, true);
		double[] result = fft.getRealValues();
		return result;
	}
	
	
	
	public double[][] getSamples (AudioInputStream ais, int window_size) throws Exception{
		AudioSamples audio_data = new AudioSamples(ais, "unique", true);
		audio_data.normalize();
		sampling_rate = ais.getFormat().getSampleRate();
		return audio_data.getSampleWindowsMixedDown(window_size);
	}
	
	public double[] getSamples (AudioInputStream ais) throws Exception {
		// Get the original audio and its tformat
				AudioInputStream original_stream = ais;
				AudioFormat original_format = original_stream.getFormat();
				sampling_rate = original_format.getSampleRate();

				// Set the bit depth
				int bit_depth = original_format.getSampleSizeInBits();
				if (bit_depth != 8 && bit_depth != 16)
					bit_depth = 16;

				// If the audio is not PCM signed big endian, then convert it to PCM
				// signed
				// This is particularly necessary when dealing with MP3s
				AudioInputStream second_stream = original_stream;
				if (original_format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED
						|| original_format.isBigEndian() == false) {
					AudioFormat new_format = new AudioFormat(
							AudioFormat.Encoding.PCM_SIGNED,
							original_format.getSampleRate(), bit_depth,
							original_format.getChannels(),
							original_format.getChannels() * (bit_depth / 8),
							original_format.getSampleRate(), true);
					second_stream = AudioSystem.getAudioInputStream(new_format,
							original_stream);
				}

				// Convert to the set sampling rate, if it is not already at this
				// sampling rate.
				// Also, convert to an appropriate bit depth if necessary.
				AudioInputStream new_stream = second_stream;
				if (original_format.getSampleRate() != (float) sampling_rate
						|| bit_depth != original_format.getSampleSizeInBits()) {
					AudioFormat new_format = new AudioFormat(
							AudioFormat.Encoding.PCM_SIGNED,
							original_format.getSampleRate(), bit_depth,
							original_format.getChannels(),
							original_format.getChannels() * (bit_depth / 8),
							original_format.getSampleRate(), true);
					new_stream = AudioSystem.getAudioInputStream(new_format,
							second_stream);
				}

				// Extract data from the AudioInputStream
				AudioSamples audio_data = new AudioSamples(new_stream,
						"unique", false);
				
				
				
				// Normalise samples if this option has been requested
				/*if (normalise)
					audio_data.normalizeMixedDownSamples();*/

				// Return all channels compressed into one
				return audio_data.getSamplesMixedDown();
				//return audio_data.normalizeMixedDownSamples();
		
	}

	public double[][] getOtherSamples(){
		return other_feature_value;
	}


}
