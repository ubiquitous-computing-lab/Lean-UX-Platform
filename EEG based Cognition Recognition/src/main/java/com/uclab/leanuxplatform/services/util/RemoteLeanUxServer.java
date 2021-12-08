/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.services.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class RemoteLeanUxServer {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    
    public static final String REMOTE_SERVER_HOST = "http://127.0.0.1/en/";
    //public static final String REMOTE_KNOWLEDGE_BASE_HOST = "http://163.180.116.194:8081";
    public static final String REMOTE_KNOWLEDGE_BASE_HOST = "http://127.0.0.1:8080";
    
    public Object getCall(String endPoint, Object data){
        RestTemplate rt = new RestTemplate();
        Object result = rt.getForObject(REMOTE_SERVER_HOST+"/"+endPoint, data.getClass());
        log.info(result.toString());
        return result;
    }
    
    
    
}
