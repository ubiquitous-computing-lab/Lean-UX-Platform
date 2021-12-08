/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.models;

import com.uclab.leanuxplatform.models.constants.CognitionLabel;
import com.uclab.leanuxplatform.models.metrics.CognitionDataItem;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class EegData implements Serializable,Cloneable {
    
    private CognitionDataItem finalCognition;
    private long timeStamp;
    private List<CognitionDataItem> _allMetrics;

    public CognitionDataItem getFinalCognition() {
        return finalCognition;
    }

    public void setFinalCognition(CognitionDataItem finalCognition) {
        this.finalCognition = finalCognition;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public List<CognitionDataItem> getAllMetrics() {
        return _allMetrics;
    }

    public void setAllMetrics(List<CognitionDataItem> _allMetrics) {
        this._allMetrics = _allMetrics;
    }

    @Override
    public String toString() {
        return "EegData{" + "finalCognition=" + finalCognition + ", timeStamp=" + timeStamp + ", _allMetrics=" + _allMetrics + '}';
    }

    public EegData() {
        timeStamp = System.currentTimeMillis();
        _allMetrics = new ArrayList();
    }
    
    public void addCognitionDataItem(CognitionDataItem cdi){
        if(this.getTimeStamp()<=0){
            this.timeStamp = System.currentTimeMillis();
        }
        this._allMetrics.add(cdi);
    }
    
    public void addCognitionDataItem(CognitionLabel label, double d){
        if(Double.isNaN(d)){
            d = 0;
        }
        this._allMetrics.add(new CognitionDataItem(label.getKey(), d, this.timeStamp));
    }
    
    public void addCognitionDataItem(CognitionLabel label, float f){
        if(Float.isNaN(f)){
            f = 0f;
        }
        try{
            this._allMetrics.add(new CognitionDataItem(label.getKey(), f, this.timeStamp));
        }catch(NumberFormatException nfe){
            //System.out.println("float:"+f);
            this._allMetrics.add(new CognitionDataItem(label.getKey(), 0, this.timeStamp));
        }
    }
    
    public void addCognitionDataItem(String label, double d){
        this._allMetrics.add(new CognitionDataItem(label, d, this.timeStamp));
    }
    
    public void addCognitionDataItem(String label, float f){
        this._allMetrics.add(new CognitionDataItem(label, f, this.timeStamp));
    }
    
    /**
     * Checks all the stored emotions to find one matching the param.
     * @param emotionLabel the enum for which to return the value
     * @return the first CognitionDataItem with matching label
     */
    public CognitionDataItem getCognitionByCognitionLabel(CognitionLabel emotionLabel) {
        for( int i=0; i<_allMetrics.size(); i++){
            if(_allMetrics.get(i).getCognitionLabel().equals(emotionLabel.getKey())){
                return _allMetrics.get(i);
            }
        }
        return null;
    }
    
    /**
     * Checks all the stored emotions to find one matching the param.
     * @param emotionLabel the String name matching the CognitionEnum entry
     * @return the first CognitionDataItem with matching label
     */
    public CognitionDataItem getCognitionByStringLabel(String emotionLabel) {
        for( int i=0; i<_allMetrics.size(); i++){
            if(_allMetrics.get(i).getCognitionLabel().equals(emotionLabel)){
                return _allMetrics.get(i);
            }
        }
        return null;
    }
    
    /**
     * Checks all the stored emotions to find the one with max score
     * @return the CognitionDataItem with the max score or -1 if for some reason nothing is found
     */
    public CognitionDataItem getMaxCognitionByScore() {
        BigDecimal maxScore = new BigDecimal(0);
        int index=-1;
        for( int i=0; i<_allMetrics.size(); i++){
            if(_allMetrics.get(i).getData().compareTo(maxScore)>0){
                maxScore = _allMetrics.get(i).getData();
                index = i;
            }
        }
        if(index!=-1){
            return _allMetrics.get(index);
        }else{
            return null;
        }
    }
    
        public void setFinalCognition(String finalCognition) {
        this.finalCognition = getCognitionByStringLabel(finalCognition);
    }
    

}
