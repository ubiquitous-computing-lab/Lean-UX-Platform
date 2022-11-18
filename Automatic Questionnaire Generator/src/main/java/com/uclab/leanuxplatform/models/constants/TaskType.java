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
public enum TaskType {
    @JsonProperty("pre")
    PRE("pre","Before Usage"),
    @JsonProperty("during")
    DURING("during", "During Usage"),
    @JsonProperty("post")
    POST("post", "After Usage");
    
    private final String key;
    private final String value;

    private TaskType(String key, String value) {
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
