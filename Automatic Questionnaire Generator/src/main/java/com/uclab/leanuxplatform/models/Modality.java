/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.models;

import com.uclab.leanuxplatform.models.constants.ModalityEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

/**
 *
 * @author uclab351
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Modality  implements Serializable{
    private String name;
//    private String fullName;
//    private String iconName;

    public Modality() {
    }

    public Modality(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconName() {
        if(name.equals(ModalityEnum.EEG.name())){
            return "USER";
        }else if(name.equals(ModalityEnum.FER.name())){
            return "CAMERA";
        }else if(name.equals(ModalityEnum.MIC.name())){
            return "MICROPHONE";
        }else if(name.equals(ModalityEnum.BL.name())){
            return "HDD_ALT";
        }else if(name.equals(ModalityEnum.ET.name())){
            return "EYE";
        }else if(name.equals(ModalityEnum.IT.name())){
            return "GEAR";
        }else if(name.equals(ModalityEnum.GSR.name())){
            return "GEAR";
        }else{
            return "GEAR";
        }

    }
    public String getFullName() {
        if(name.equals(ModalityEnum.EEG.name())){
            return "Electroencephalography";
        }else if(name.equals(ModalityEnum.FER.name())){
            return "Facial Recognition";
        }else if(name.equals(ModalityEnum.MIC.name())){
            return "Emo Voice";
        }else if(name.equals(ModalityEnum.BL.name())){
            return "Body Language Recognition";
        }else if(name.equals(ModalityEnum.ET.name())){
            return "Eye Tracking";
        }else if(name.equals(ModalityEnum.SUR.name())){
            return "Survey";
        }else if(name.equals(ModalityEnum.IT.name())){
            return "Interaction Tracking";
        }else if(name.equals(ModalityEnum.GSR.name())){
            return "GSR";
        }else{
            return "GEAR";
        }

    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Modality{" + "name=" + name + '}';
    }
    
    
}
