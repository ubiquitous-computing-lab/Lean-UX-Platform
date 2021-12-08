/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fahad Ahmed Satti
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable{
    private int id;
    private String email;
    private String password;
    private String login_status;
    private List<Project> ownedProjects = new ArrayList();
    private int selectedProject;
    private int activeParticipantId = 1;        //default participant Jamil Hussain
    
    public User() {
        
    }
    public User(int id){
        //System.out.println(id);
        this.id = id;
    }
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin_status() {
        return login_status;
    }

    public void setLogin_status(String login_status) {
        this.login_status = login_status;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", email=" + email + ", password=" + password + ", login_status=" + login_status + '}';
    }

    public List<Project> getOwnedProjects() {
        return ownedProjects;
    }

    public void setOwnedProjects(List<Project> ownedProjects) {
        this.ownedProjects = ownedProjects;
    }

    public int getSelectedProject() {
        return selectedProject;
    }
    
    public Project getSelectedProjectObject() {
        for(int i=0; i<this.ownedProjects.size();i++){
            if(this.ownedProjects.get(i).getId()==this.selectedProject){
                return this.ownedProjects.get(i);
            }
        }
        System.out.println("Selected project not found");
        return null;
        
        //return this.ownedProjects.get(this.selectedProject);
    }
    
    public void setSelectedProject(int selectedProject) {
        this.selectedProject = selectedProject;
    }

    public int getActiveParticipantId() {
        return activeParticipantId;
    }

    public void setActiveParticipantId(int activeParticipantId) {
        this.activeParticipantId = activeParticipantId;
    }

   
    
}
