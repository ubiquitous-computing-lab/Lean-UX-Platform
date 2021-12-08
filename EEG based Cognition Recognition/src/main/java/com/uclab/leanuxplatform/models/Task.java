/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uclab.leanuxplatform.models.constants.TaskType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Fahad Ahmed Satti
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {
    private int id;
    private String title;
    private String description;
    private TaskType type;
    private Project project;
    private CopyOnWriteArrayList<Survey> surveys;
    private ListIterator<Survey> surveyIterator;
    
    public Task(int task_id) {
        this();
        this.id = task_id;
    }
    
    public Task(String name) {
        this();
        this.title = name;
    }
    
    public Task(){
        this.surveys = new CopyOnWriteArrayList<>(new ArrayList());
        surveyIterator = null;
    }
    
    public void initSurveyIterator(){
        surveyIterator = this.surveys.listIterator();
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Task{" + "id=" + id + ", title=" + title + ", description=" + description + ", type=" + type + ", project=" + project + ", surveys=" + surveys + '}';
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    
    public void setProjects(int projectId){
        this.project = new Project(projectId);
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public List<Survey> getSurveys() {
        return surveys;
    }

    public void setSurveys(ArrayList<Survey> surveys) {
        this.surveys.addAll(surveys);
    }

    public Survey getNextSurvey() {
        if(surveyIterator == null){
            this.initSurveyIterator();
        }
//        System.out.println(surveyIterator.hasNext());
        if(surveyIterator.hasNext()){
            return surveyIterator.next();
        }
        return null;
    }
    public Survey getPreviousSurvey() {
        if(surveyIterator == null){
            this.initSurveyIterator();
        }
//        System.out.println(surveyIterator.hasNext());
        if(surveyIterator.hasPrevious()){
            return surveyIterator.previous();
        }
        return null;
    }
    public boolean hasNextSurvey() {
        if(surveyIterator == null){
            this.initSurveyIterator();
        }
        return surveyIterator.hasNext();
    }
    
}
