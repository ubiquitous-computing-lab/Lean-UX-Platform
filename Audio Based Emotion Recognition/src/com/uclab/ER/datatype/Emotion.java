/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.ER.datatype;

/**
 *
 * @author 
 */
public class Emotion{
	int emotion;

    
   
    public String toString() {
        //need to be implemented
        return "";
    }

    public boolean equals(Object other) {
        if(other instanceof Emotion){
            //need to be implemented
            
            return true;
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	public int getEmotion() {
		return emotion;
	}

	public void setEmotion(int emotion) {
		this.emotion = emotion;
	}
    
}
