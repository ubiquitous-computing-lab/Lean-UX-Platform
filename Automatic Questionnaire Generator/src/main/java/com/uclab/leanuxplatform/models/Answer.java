/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Objects;

/**
 *
 * @author uclab351
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Answer {
    private int id;
    private int question;
    private String response;
    private int participant;
//    private String projectstarttime;
//    private int taskid;
//    private String taskstarttime;
//    private String taskendtime;
//    private int taskquestionanswer;
//    private String taskfree_respone;
//    private String istaskcomplete;
//    private String datatype;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + this.id;
        hash = 61 * hash + this.question;
        hash = 61 * hash + Objects.hashCode(this.response);
        hash = 61 * hash + this.participant;
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
        final Answer other = (Answer) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.question != other.question) {
            return false;
        }
        if (this.participant != other.participant) {
            return false;
        }
        if (!Objects.equals(this.response, other.response)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Answer{" + "id=" + id + ", question=" + question + ", response=" + response + ", participant=" + participant + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestion() {
        return question;
    }

    public void setQuestion(int question) {
        this.question = question;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getParticipant() {
        return participant;
    }

    public void setParticipant(int participant) {
        this.participant = participant;
    }

    
    
}
