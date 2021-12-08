/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.gsr.gsr_fx;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author Fahad Ahmed Satti
 */
public class GsrData implements Serializable,Cloneable{
    private long timeStamp;
    private Map<String,BigDecimal> _allMetrics;
    private String userAssignedLabel;
    private boolean outlier = false;
    private double distance = Double.POSITIVE_INFINITY;

    @Override
    public String toString() {
        return "GsrData{" + "timeStamp=" + timeStamp + ", _allMetrics=" + _allMetrics + ", userAssignedLabel=" + userAssignedLabel + ", outlier=" + outlier + ", distance=" + distance + '}';
    }
    
   
    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Map<String,BigDecimal> getAllMetrics() {
        return _allMetrics;
    }

    public void setAllMetrics(Map<String,BigDecimal> _allMetrics) {
        this._allMetrics = _allMetrics;
    }

    public GsrData() {
        _allMetrics = new HashMap();
    }
    
    public void addItem(String s, double d){
        this._allMetrics.put(s, BigDecimal.valueOf(d));
    }

    public String getUserAssignedLabel() {
        return userAssignedLabel;
    }

    public void setUserAssignedLabel(String userAssignedLabel) {
        this.userAssignedLabel = userAssignedLabel;
    }
    
    public BigDecimal getValByLabel(String label){
        return this._allMetrics.get(label);
    }

    public boolean isOutlier() {
        return outlier;
    }

    public void setOutlier(boolean outlier) {
        this.outlier = outlier;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
    
}
