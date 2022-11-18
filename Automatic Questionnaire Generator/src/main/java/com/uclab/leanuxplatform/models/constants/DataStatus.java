/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.models.constants;

/**
 *
 * @author uclab351
 */
public enum DataStatus {
    LOCAL_SAVE("save",1),
    LOCAL_READ("read",2),
    REMOTE_SAVE("remote",3),
    ERROR("error",0);
    
    private final String key;
    private final Integer value;

    private DataStatus(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Integer getValue() {
        return value;
    }
    
}
