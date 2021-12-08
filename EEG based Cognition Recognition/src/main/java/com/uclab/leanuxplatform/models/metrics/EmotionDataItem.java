/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.models.metrics;

import com.uclab.leanuxplatform.models.constants.DataStatus;
import java.math.BigDecimal;

/**
 *
 * @author uclab351
 */
public class EmotionDataItem extends DataItem{
    private String emotionLabel;

    public EmotionDataItem(String emotionLabel, BigDecimal data, long timeStamp, int status) {
        this.emotionLabel = emotionLabel;
        this.data = data;
        this.timeStamp = timeStamp;
        this.status = status;
    }

    public EmotionDataItem(String emotionLabel, BigDecimal data, long timeStamp) {
        this.timeStamp = timeStamp;
        this.emotionLabel = emotionLabel;
        this.data = data;
        this.status=DataStatus.LOCAL_SAVE.getValue();
    }
    
    /*
     * Note: For values other float and double NaN and ±Infinity, 
     * this constructor is compatible with the values returned by 
     * Float.toString(float) and Double.toString(double).
     * This is generally the preferred way to convert a float or double into a
     * BigDecimal, as it doesn't suffer from the unpredictability of the
     * BigDecimal(double) constructor.
     * 
    */
    public EmotionDataItem(String emotionLabel, double d, long timeStamp) {
        this.timeStamp = timeStamp;
        this.emotionLabel = emotionLabel;
        this.data = new BigDecimal(Double.toString(d));
        this.status=DataStatus.LOCAL_SAVE.getValue();
    }
    
    public EmotionDataItem(String emotionLabel, float f, long timeStamp) {
        this.timeStamp = timeStamp;
        this.emotionLabel = emotionLabel;
        this.data = new BigDecimal(Float.toString(f));
        this.status=DataStatus.LOCAL_SAVE.getValue();
    }
    

    @Override
    public String toString() {
        return "EmotionDataItem{" + "emotionLabel=" + emotionLabel + ", data=" + data + ", timeStamp=" + timeStamp + ", status=" + status + '}';
    }



    public String getEmotionLabel() {
        return emotionLabel;
    }

    public void setEmotionLabel(String emotionLabel) {
        this.emotionLabel = emotionLabel;
    }


    
    
}
