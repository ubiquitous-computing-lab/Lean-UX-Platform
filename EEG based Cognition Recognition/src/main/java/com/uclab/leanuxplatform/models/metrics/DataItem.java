/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.models.metrics;

import java.math.BigDecimal;

/**
 *
 * @author uclab351
 */
public class DataItem {
    
    protected BigDecimal data;
    protected long timeStamp;
    //to be used for managing the states of this data item at a later stage
    protected int status;

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BigDecimal getData() {
        return data;
    }

    public void setData(BigDecimal data) {
        this.data = data;
    }
    
    public void setData(float f) {
        this.data = new BigDecimal(Float.toString(f));
    }
    
    public void setData(double d) {
        this.data = new BigDecimal(Double.toString(d));
    }

    @Override
    public String toString() {
        return "DataItem{" + "data=" + data + ", timeStamp=" + timeStamp + ", status=" + status + '}';
    }
    
}
