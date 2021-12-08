/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.controllers.events;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

/**
 *
 * @author uclab351
 */
public class LogoutEvent extends BaseEvent{

    public static final EventType<BaseEvent> LOGOUT_EVENT = new EventType(CUSTOM_EVENT_TYPE, "LogoutEvent");

    private final String param;

    public LogoutEvent(String param) {
        super(LOGOUT_EVENT);
        this.param = param;
    }
    
    @Override
    public void invokeHandler(LogoutEventHandler handler) {
        handler.onLogout(param);
    }
    
}
