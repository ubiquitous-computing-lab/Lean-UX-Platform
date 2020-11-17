package com.uclab.ER.audio.visualizer;

import java.awt.*;
import javax.swing.*;

public class AudioVisualizerFrame extends JFrame{
	
	public static void main(String[] args) {
	        try {
				new AudioVisualizerFrame();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
    
	public AudioVisualizerFrame() throws Exception{
		
        this.setTitle("Audio data Visualization");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        AudioVisualizerPanel panel = new AudioVisualizerPanel();
        this.add(panel, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
        this.setSize(300,200);
        this.setVisible(true);
    }
  
   
}