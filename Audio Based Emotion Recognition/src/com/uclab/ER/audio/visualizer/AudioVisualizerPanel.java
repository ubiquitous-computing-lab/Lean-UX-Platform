package com.uclab.ER.audio.visualizer;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class AudioVisualizerPanel extends JPanel {

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.blue);

		g.drawPolyline(VDataType.index, VDataType.data, VDataType.data.length);

	}

}
