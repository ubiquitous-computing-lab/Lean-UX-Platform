/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.ER.audio.util;

import java.util.Vector;

/***
 * 
 * @author Jaehun Bang
 *
 */

public class UtilityFunctions {
    
	/**
	 * Concatenate two vector into one vector
	 * @param a Input vector
	 * @param b Input vector
	 * @return Concatenated vector of a and b
	 */
	public static double[] concat(double[] a, double[] b) {
	   int aLen = a.length;
	   int bLen = b.length;
	   double[] c = new double[aLen + bLen];
	   System.arraycopy(a, 0, c, 0, aLen);
	   System.arraycopy(b, 0, c, aLen, bLen);
	   return c;
	}
	
	/**
	 * Convert Single Array 
	 * @param data
	 * @return double[][]
	 */
	public double[] getSigleArray(double[][] data){
		double[]result = new double[data.length*data[0].length];
		int cnt = 0;
		for(int i=0; i<data.length; i++){
			for(int j=0;j<data[i].length; j++){
				result[cnt] = data[i][j];
				cnt++;
			}
		}
		return result;
	}
	public double[] getSingleArray(Vector<Double> data){
		double []result = new double[data.size()];
		for(int i=0; i<data.size(); i++){
			result[i] = data.get(i);
		}
		return result;
	}
}
