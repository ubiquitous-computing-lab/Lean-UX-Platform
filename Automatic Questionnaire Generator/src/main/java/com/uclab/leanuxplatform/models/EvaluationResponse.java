/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Fahad Ahmed Satti
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EvaluationResponse {

    private int participant;
    private int project;
    private int task;
    private String project_start_time;
    private String task_start_time;
    private String task_end_time;
    private Boolean task_completion;
    private ArrayList<Answer> collect_response = new ArrayList();

    @Override
    public String toString() {
        return "EvaluationResponse{" + "participant=" + participant + ", project=" + project + ", task=" + task + ", project_start_time=" + project_start_time + ", task_start_time=" + task_start_time + ", task_end_time=" + task_end_time + ", is_task_complete=" + task_completion + ", collect_response=" + collect_response + '}';
    }
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.participant;
        hash = 97 * hash + this.project;
        hash = 97 * hash + this.task;
        hash = 97 * hash + Objects.hashCode(this.project_start_time);
        hash = 97 * hash + Objects.hashCode(this.task_start_time);
        hash = 97 * hash + Objects.hashCode(this.task_end_time);
        hash = 97 * hash + Objects.hashCode(this.task_completion);
        hash = 97 * hash + Objects.hashCode(this.collect_response);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EvaluationResponse other = (EvaluationResponse) obj;
        if (this.participant != other.participant) {
            return false;
        }
        if (this.project != other.project) {
            return false;
        }
        if (this.task != other.task) {
            return false;
        }
        if (!Objects.equals(this.project_start_time, other.project_start_time)) {
            return false;
        }
        if (!Objects.equals(this.task_start_time, other.task_start_time)) {
            return false;
        }
        if (!Objects.equals(this.task_end_time, other.task_end_time)) {
            return false;
        }
        if (!Objects.equals(this.task_completion, other.task_completion)) {
            return false;
        }
        if (!Objects.equals(this.collect_response, other.collect_response)) {
            return false;
        }
        return true;
    }

    public int getParticipant() {
        return participant;
    }

    public void setParticipant(int participant) {
        this.participant = participant;
    }

    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
    }

    public int getTask() {
        return task;
    }

    public void setTask(int task) {
        this.task = task;
    }

    public String getProject_start_time() {
        return project_start_time;
    }

    public void setProject_start_time(String project_start_time) {
        this.project_start_time = project_start_time;
    }

    public String getTask_start_time() {
        return task_start_time;
    }

    public void setTask_start_time(String task_start_time) {
        this.task_start_time = task_start_time;
    }

    public String getTask_end_time() {
        return task_end_time;
    }

    public void setTask_end_time(String task_end_time) {
        this.task_end_time = task_end_time;
    }

    public Boolean getTask_completion() {
        return task_completion;
    }

    public void setTask_completion(Boolean task_completion) {
        this.task_completion = task_completion;
    }

    public ArrayList<Answer> getCollect_response() {
        return collect_response;
    }

    public void setCollect_response(ArrayList<Answer> collect_response) {
        this.collect_response = collect_response;
    }
    
    


    
}
