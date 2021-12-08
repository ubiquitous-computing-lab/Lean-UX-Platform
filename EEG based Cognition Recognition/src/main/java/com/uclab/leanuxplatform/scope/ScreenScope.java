/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.scope;

import com.uclab.leanuxplatform.controllers.screens.BaseScreenController;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class ScreenScope implements Scope {
    private static final Map<String, BaseScreenController> screens = Collections.synchronizedMap(new HashMap<String, BaseScreenController>());
    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        if (!screens.containsKey(name)) {
            screens.put(name, (BaseScreenController)objectFactory.getObject());
        }
        return screens.get(name);

    }

    @Override
    public Object remove(String name) {
        return screens.remove(name);
    }

    @Override
    public void registerDestructionCallback(String string, Runnable r) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object resolveContextualObject(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getConversationId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
