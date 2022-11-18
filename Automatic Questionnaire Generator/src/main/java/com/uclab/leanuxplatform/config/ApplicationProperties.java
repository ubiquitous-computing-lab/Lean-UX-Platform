/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author uclab351
 */
@Component
public final class ApplicationProperties {

    @Value("${path.BL.model}")
    private String path_model_BL;
    @Value("${path.BL.output}")
    private String path_output_BL;
    @Value("${path.Audio.model}")
    private String path_model_Audio;
    @Value("${path.Audio.modelPos}")
    private String path_model_AudioPos;
    @Value("${path.Audio.modelNeg}")
    private String path_model_AudioNeg;
    @Value("${path.Audio.output}")
    private String path_output_Audio;
    @Value("${url.eyetracking.connect}")
    private String url_connect_eyetracking;
    

    public String getPath_model_BL() {
        return path_model_BL;
    }

    public void setPath_model_BL(String path_model_BL) {
        this.path_model_BL = path_model_BL;
    }

    public String getPath_output_BL() {
        return path_output_BL;
    }

    public void setPath_output_BL(String path_output_BL) {
        this.path_output_BL = path_output_BL;
    }

    public String getPath_model_Audio() {
        return path_model_Audio;
    }

    public void setPath_model_Audio(String path_model_Audio) {
        this.path_model_Audio = path_model_Audio;
    }

    public String getPath_model_AudioPos() {
        return path_model_AudioPos;
    }

    public void setPath_model_AudioPos(String path_model_AudioPos) {
        this.path_model_AudioPos = path_model_AudioPos;
    }

    public String getPath_model_AudioNeg() {
        return path_model_AudioNeg;
    }

    public void setPath_model_AudioNeg(String path_model_AudioNeg) {
        this.path_model_AudioNeg = path_model_AudioNeg;
    }

    public String getPath_output_Audio() {
        return path_output_Audio;
    }

    public void setPath_output_Audio(String path_output_Audio) {
        this.path_output_Audio = path_output_Audio;
    }

    public String getUrl_connect_eyetracking() {
        return url_connect_eyetracking;
    }

    public void setUrl_connect_eyetracking(String url_connect_eyetracking) {
        this.url_connect_eyetracking = url_connect_eyetracking;
    }

   
    
    
    
}
