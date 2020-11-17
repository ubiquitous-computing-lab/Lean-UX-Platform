/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.ER.audio.service;

import com.uclab.ER.audio.preprocessing.jAudioPreProcessing;
import com.uclab.ER.audio.recognizer.AudioEmotionRecognizer;
import com.uclab.ER.audio.visualizer.AudioVisualizerFrame;
import com.uclab.ER.audio.visualizer.VDataType;
import com.uclab.ER.datatype.AudioData;
import com.uclab.ER.datatype.SensoryData;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

/**
 * Audio based Emotion Recognition Service
 * @author Jaehun Bang & Fahad Ahmed Satti
 */
public class AudioERService {
	
	AudioEmotionRecognizer aer;
    TargetDataLine _mic;
    jAudioPreProcessing jap = new jAudioPreProcessing();
   
    AudioVisualizerFrame av;
    

    private boolean _running = false;
    private String label = "";
    AudioFormat _format = new AudioFormat(48000, // Sample Rate
            16, // Size of SampleBits
            1, // Number of Channels
            true, // Is Signed?
            false // Is Big Endian?
    );
    String dir = "c:/AudioDataCollection/";

    Thread _clientThread = null;

    public AudioERService(String[] labels, String path, String pos_path, String nega_path) {
    	 aer = new AudioEmotionRecognizer(labels, path, pos_path, nega_path);
        _clientThread = new Thread(_audioClientRunnable);
    	
        try {
			av = new AudioVisualizerFrame();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    

    private void initMic() throws LineUnavailableException {

        //  getting the source line i.e, mic
        _mic = AudioSystem.getTargetDataLine(_format);

        //  causes the line to acquire any required system resources and become operational
        _mic.open(_format);

    }

    // Should be synchronized
    //List<AudioData> audioQueue = Collections.synchronizedList(new ArrayList<>());
    private final Runnable _audioClientRunnable = new Runnable() {
        @Override
        public void run() {
            try {
            	 Vector<byte[]> audio_buffer = new Vector<>();
                System.out.println("Mic open.");
                initMic();
                _mic.start();
                System.out.println(_running);
                while (_running) {
                    // returns the length of data copied in buffer
                    byte _buffer[] = new byte[(int) (_mic.getFormat().getSampleRate() * 0.4)];
                    int count = _mic.read(_buffer, 0, _buffer.length);
                    //if data is available
                    if (count > 0) {
                        AudioData ad = new AudioData();

                        ad.setBuffer(_buffer);
                        ad.setReadCount(count);
                        ad.setLabel(label);
                        audio_buffer.add(ad.getBuffer());
                        
                        InputStream v_bais = new ByteArrayInputStream(_buffer);
                        AudioInputStream v_ais = new AudioInputStream(v_bais, _format, AudioSystem.NOT_SPECIFIED);
                        double[] v_data = jap.getSamples(v_ais);
                        int cnt = 0;
                        for(int i =0; i<v_data.length; i=i+100) {
                        	VDataType.data[cnt] = (int)((v_data[i]*(double)VDataType.height)+(double)VDataType.align);
                        	cnt++;
                        }
                        
                        av.repaint();
                                               
                        // File Save //
                        if (audio_buffer.size() > 14) {
                            try {
                                System.out.println(dir);
                                File file_dir = new File(dir);
                                file_dir.mkdirs();
                                File wavFile = new File(dir + "Log_" + getTime() + "_" + ad.getLabel() + ".wav");
                               
                                System.out.println(wavFile.toString());

                                byte[] data = mergeBuffer(audio_buffer);
                                SensoryData sd = new SensoryData();
                                sd.setAudioSensoryData(data);
                                System.out.println(sd.getAudioSensoryData().length);
                                
                                
                                int emotion = aer.RecognizeEmotion(sd);
                                                                                 
                                InputStream bais = new ByteArrayInputStream(data);
                                AudioInputStream ais = new AudioInputStream(bais, _format, AudioSystem.NOT_SPECIFIED);
                                AudioSystem.write(ais, AudioFileFormat.Type.WAVE, wavFile);
                                audio_buffer.removeAllElements();
                                
                            } catch (IOException ex) {
                                
                            }
                        }
                     
                    }
                }
                //  honestly.... program never reaches here
                //  drain() causes the mixer's remaining data to get delivered to the target data line's buffer
                _mic.drain();
                _mic.close();
            } catch (LineUnavailableException e) {
                System.out.println("Error!!!");
                e.printStackTrace();
            } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    };
    static public String getTime() {
        Date today = new Date();

        SimpleDateFormat date = new SimpleDateFormat("yyyy;MM;dd");
        SimpleDateFormat time = new SimpleDateFormat("hh;mm;ss a");

        String result = date.format(today) + "_" + time.format(today);
        return result;
    }
    private byte[] mergeBuffer(Vector<byte[]> audio_buffer) {
        byte[] result = null;
        int cnt = 0;
        int size = 0;

        for (int i = 0; i < audio_buffer.size(); i++) {
            size = size + audio_buffer.get(i).length;

        }

        if (audio_buffer.size() > 0) {
            result = new byte[size];
            for (int i = 0; i < audio_buffer.size(); i++) {
                for (int j = 0; j < audio_buffer.get(i).length; j++) {
                    result[cnt] = audio_buffer.get(i)[j];
                    cnt++;
                }
            }

        }
        return result;
    }


    public void beginProcessing() {
        //Stop the threads, if they are already running.
        //Separate conditions to resolve any errors due to only one of these threads running
        if (_clientThread != null) {
            try {
                _running = false;
                _clientThread.interrupt();
            } catch (Exception ex) {
            }
            _clientThread = null;
        }

        _running = true;
        //Start the client thread before the server thread
        _clientThread = new Thread(_audioClientRunnable);
        _clientThread.start();
    }
    public void stopProcessing() {
    	_clientThread.stop();
    }

}