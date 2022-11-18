/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 *
 * @author Fahad Ahmed Satti
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Survey {
    private int id;
    private Task task;
    private String name;
    private List<Question> questions;

    @Override
    public String toString() {
        return "Survey{" + "id=" + id + ", task=" + task + ", name=" + name + ", questions=" + questions + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
    
    
}
