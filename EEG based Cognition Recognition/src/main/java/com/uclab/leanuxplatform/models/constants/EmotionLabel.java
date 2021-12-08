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
public enum EmotionLabel {

    ANGER("Anger",1),
    SAD("Sadness",2),
    JOYFUL("Joyful",3),
    NEUTRAL("Neutral",0);
    
    private final String key;
    private final Integer value;

    private EmotionLabel(String key, Integer value) {
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
        return "EmotionEnum{" + "key=" + key + ", value=" + value + '}';
    }
    
    
}
