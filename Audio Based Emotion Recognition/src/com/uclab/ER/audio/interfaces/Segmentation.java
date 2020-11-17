/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.ER.audio.interfaces;

/**
 * This class is data segmentation
 * @author Jaehun Bang
 * @param <InputType> Input data type for segmentation
 * @param <OutputType> segmented output data
 */
public abstract class Segmentation <InputType, OutputType> {
    
    /**
     * 
     * @param input input data to segment
     * @return segmented data
     */
    abstract public OutputType segmentation(InputType input);
}
