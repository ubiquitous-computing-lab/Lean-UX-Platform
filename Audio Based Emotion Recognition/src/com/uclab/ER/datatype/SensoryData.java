/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.ER.datatype;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author Jaehun Bang
 */
public class SensoryData implements Serializable {
    private static final long serialVersionUID = 1L;
    Long userID;
    byte[] data;
    File file;
    double[] samples;

    /**
     *
     * @return user ID who generated this sensor data
     */
    public Long getUserID() {
        return userID;
    }


    /**
     *
     * @param userID user ID who generated this sensor data 
     */
    public void setUserID(Long userID) {
        this.userID = userID;
    }


 
    public void setAudioSensoryData(byte[] data){
        this.data = data;
    }
   public void setFile(File file){
       this.file = file;
   }
    public void setSamples(double[] samples){
        this.samples = samples;
    }

    public byte[] getAudioSensoryData(){
        return data;
    }
    public File getFile(){
        return file;
    }
    public double[] getSamples(){
        return samples;
    }


}
