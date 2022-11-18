/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author Fahad Ahmed Satti
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedbackResponse {
    private int project;
    private int answer;
    private String free_respone;
    private String question_text;
    private String emotional_state;
    private String cognitive_state;
    private String datatype = "FeedbackAutomaticQuestionnaire";

    public FeedbackResponse() {
    }

    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
    }

    public String getFree_respone() {
        return free_respone;
    }

    public void setFree_respone(String free_respone) {
        this.free_respone = free_respone;
    }

    @Override
    public String toString() {
        return "FeedbackResponse{" + "project=" + project + ", answer=" + answer + ", free_respone=" + free_respone + ", question_text=" + question_text + ", emotional_state=" + emotional_state + ", cognitive_state=" + cognitive_state + ", datatype=" + datatype + '}';
    }


    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public String getEmotional_state() {
        return emotional_state;
    }

    public void setEmotional_state(String emotional_state) {
        this.emotional_state = emotional_state;
    }

    public String getCognitive_state() {
        return cognitive_state;
    }

    public void setCognitive_state(String cognitive_state) {
        this.cognitive_state = cognitive_state;
    }

    
}
