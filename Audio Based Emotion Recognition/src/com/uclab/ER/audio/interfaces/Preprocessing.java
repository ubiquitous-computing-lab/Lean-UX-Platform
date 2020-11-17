/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.ER.audio.interfaces;

/**
 *This class preprocess the data
 * @author Jaehun Bang
 * This class preprocess the data
 * @OutputType preprocessed output
 * @InputType Data which will be preprocessed
 */


public abstract class Preprocessing<InputType, OutputType> {
    
    /**
     * 
     * @param input input data to preprocess
     * @return processed daa
     */
    abstract public OutputType preprocessing(InputType input);
}
