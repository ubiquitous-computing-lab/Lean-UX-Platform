/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.controllers.screens;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class CustomDialog {
    private ScreensConfiguration screens;
    private FXMLDialog dialog;

    public void setDialog(FXMLDialog dialog) {
        this.dialog = dialog;
    }
    
    public CustomDialog(ScreensConfiguration screens){
        this.screens = screens;
    }
}
