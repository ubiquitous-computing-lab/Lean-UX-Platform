/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.models.metrics;

import com.uclab.leanuxplatform.models.constants.DataStatus;
import java.math.BigDecimal;
import org.slf4j.LoggerFactory;

/**
 *
 * @author uclab351
 */
public class CognitionDataItem  extends DataItem{
    protected final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());
    private String cognitionLabel;

    public CognitionDataItem(String cognitionLabel, BigDecimal data, long timeStamp, int status) {
        this.cognitionLabel = cognitionLabel;
        this.data = data;
        this.timeStamp = timeStamp;
        this.status = status;
    }

    public CognitionDataItem(String cognitionLabel, BigDecimal data, long timeStamp) {
        this.timeStamp = timeStamp;
        this.cognitionLabel = cognitionLabel;
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
    public CognitionDataItem(String cognitionLabel, double d, long timeStamp) {
        this.timeStamp = timeStamp;
        this.cognitionLabel = cognitionLabel;
        try{
            this.data = new BigDecimal(Double.toString(d));
        }catch(NumberFormatException nfe){
            this.data = new BigDecimal(0);
            log.debug("Data for this cognition label("+cognitionLabel+" is errorneous:"+d);
        }
        this.status=DataStatus.LOCAL_SAVE.getValue();
    }
    
    public CognitionDataItem(String cognitionLabel, float f, long timeStamp) {
        this.timeStamp = timeStamp;
        this.cognitionLabel = cognitionLabel;
        try{
            this.data = new BigDecimal(Float.toString(f));
        }catch(NumberFormatException nfe){
            this.data = new BigDecimal(0);
            log.debug("Data for this cognition label("+cognitionLabel+" is errorneous:"+f);
        }
        this.status=DataStatus.LOCAL_SAVE.getValue();
    }

    public String getCognitionLabel() {
        return cognitionLabel;
    }

    public void setCognitionLabel(String cognitionLabel) {
        this.cognitionLabel = cognitionLabel;
    }

    @Override
    public String toString() {
        return "CognitionDataItem{" + "cognitionLabel=" + cognitionLabel + "data=" + this.data + '}';
    }


    
    
}
