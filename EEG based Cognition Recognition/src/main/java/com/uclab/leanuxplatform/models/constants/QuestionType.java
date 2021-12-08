/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.models.constants;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Fahad Ahmed Satti
 */
public enum QuestionType {
    @JsonProperty("text")
    TEXT("text","Short Text"),
    @JsonProperty("radioOne")
    RADIO_ONE("radioOne", "Multiple Choice - Select one option"),
    @JsonProperty("radioMultiple")
    RADIO_MULTIPLE("radioMultiple", "Multiple Choice - Select multiple options"),
    @JsonProperty("essay")
    ESSAY("essay", "Long Text");
    
    private final String key;
    private final String value;

    private QuestionType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "QuestionTypes{" + "key=" + key + ", value=" + value + '}';
    }
    
    
}
