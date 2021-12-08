/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.services;

import com.uclab.leanuxplatform.controllers.screens.alerts.AlertController;
import com.uclab.leanuxplatform.services.util.RemoteLeanUxServer;
import com.uclab.leanuxplatform.models.User;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Fahad Ahmed Satti
 */
@Component
public class LoginService extends RemoteLeanUxServer{
    private User thisUser = new User();
    @Autowired
    RestTemplate restTemplate;
    private String errorMessage = "";
    
    
    public String loginEndPoint = "api/users/login/";
    public boolean getLoginResult(User u){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<User> request = new HttpEntity<>(u, headers);
        ResponseEntity<User> response = null;
        try{
            response = restTemplate.exchange(REMOTE_SERVER_HOST + "/" + loginEndPoint,
                    HttpMethod.POST,
                    request,
                    User.class);
        }catch(HttpClientErrorException ex){
            //AlertController.error("Unable to login. Your username or password is not correct");
            log.error(ex.getMessage(),ex);
            errorMessage = ex.getMessage();
            return false;
        }catch(ResourceAccessException ex){
            log.error(ex.getMessage(),ex);
            errorMessage = ex.getMessage()+":"+" Please check your network connection.";
            return false;
        }
        if(response!=null){
            log.debug(response.getBody().toString());
            thisUser = response.getBody();
            log.info(thisUser.toString());
            errorMessage = "";
            return thisUser.getLogin_status().equalsIgnoreCase("loggedin");
        }
        return false;
       
    }

    public User getThisUser() {
        return thisUser;
    }

    public void logout() {
        thisUser = null;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}