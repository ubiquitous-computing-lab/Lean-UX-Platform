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
public abstract class BaseEvent extends Event{
    
    public static final EventType<BaseEvent> CUSTOM_EVENT_TYPE = new EventType(ANY);
    
    public BaseEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
    
    public abstract void invokeHandler(LogoutEventHandler handler);

}
