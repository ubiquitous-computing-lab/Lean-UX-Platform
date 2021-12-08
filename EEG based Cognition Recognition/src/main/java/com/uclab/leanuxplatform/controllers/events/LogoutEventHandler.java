/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.controllers.events;

import javafx.event.EventHandler;

/**
 *
 * @author uclab351
 */
public abstract class LogoutEventHandler implements EventHandler<BaseEvent>{
    public abstract void onLogout(String param);
    
    @Override
    public void handle(BaseEvent event) {
        event.invokeHandler(this);
    }
}
