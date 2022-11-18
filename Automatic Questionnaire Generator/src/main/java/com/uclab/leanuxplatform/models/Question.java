/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uclab.leanuxplatform.models.constants.QuestionType;
import java.util.Objects;

/**
 *
 * @author Fahad Ahmed Satti
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Question {
    private int id;
    private String title;
    private Boolean required;
    private Dimension dimensions;
    private QuestionType question_type;
    private String choices;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + this.id;
        hash = 41 * hash + Objects.hashCode(this.title);
        hash = 41 * hash + Objects.hashCode(this.required);
        hash = 41 * hash + Objects.hashCode(this.dimensions);
        hash = 41 * hash + Objects.hashCode(this.question_type);
        hash = 41 * hash + Objects.hashCode(this.choices);
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
        final Question other = (Question) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.choices, other.choices)) {
            return false;
        }
        if (!Objects.equals(this.required, other.required)) {
            return false;
        }
        if (!Objects.equals(this.dimensions, other.dimensions)) {
            return false;
        }
        if (this.question_type != other.question_type) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Question{" + "id=" + id + ", title=" + title + ", required=" + required + ", dimensions=" + dimensions + ", question_type=" + question_type + ", choices=" + choices + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Dimension getDimensions() {
        return dimensions;
    }

    public void setDimensions(Dimension dimensions) {
        this.dimensions = dimensions;
    }

    public QuestionType getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(QuestionType question_type) {
        this.question_type = question_type;
    }

    public String getChoices() {
        return choices;
    }

    public void setChoices(String choices) {
        this.choices = choices;
    }
    
    
}
