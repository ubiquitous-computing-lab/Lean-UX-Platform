package com.uclab.ER.audio.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

/***
 * 
 * @author Jaehun Bang
 *
 */

public class ConvertAudioData {
	

	public AudioInputStream convertAudioInputStream(byte[] data,
			AudioFormat audioFM) {
		AudioInputStream ais = null;
		if (data != null && audioFM != null) {
			InputStream is = convertInputStream(data);
			ais = new AudioInputStream(is, audioFM,
					(long) ((data.length) / audioFM.getFrameSize()));
		}
		return ais;
	}

	public InputStream convertInputStream(byte[] data) {
		InputStream is = null;
		if (data != null) {
			is = new ByteArrayInputStream(data);
		}
		return is;
	}

	public byte[] convertByteBuffer(AudioInputStream input) {
		byte[] temp = null;
		if (input != null) {
			temp = new byte[(int) (input.getFrameLength() * input
					.getFormat().getFrameSize())];
			try {
				input.read(temp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return temp;
	}
	public byte[] convertByteBuffer(Vector<byte[]> input){
		byte[] temp = new byte[input.size()*input.get(0).length];
		int cnt = 0;
		for(int i=0; i<input.size(); i++){
			for(int j=0; j<input.get(i).length; j++){
				temp[cnt++] = input.get(i)[j];
			}
		}
		
		return temp;
	}

}
