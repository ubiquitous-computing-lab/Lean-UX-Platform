/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.models.constants;

/**
 *
 * @author Fahad Ahmed Satti
 */
public enum CognitionLabel {

    INTEREST("Interest",1),
    STRESS("Stress",2),
    EXCITEMENT("Excitement",3),
    FOCUS("Focus",4),
    RELAXATION("Relaxation",0);
    
    private final String key;
    private final Integer value;

    private CognitionLabel(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {    
        return key;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "CognitionLabel{" + "key=" + key + ", value=" + value + '}';
    }

    
    
}
