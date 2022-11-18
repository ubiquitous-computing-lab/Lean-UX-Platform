package com.uclab.leanuxplatform.services.reasoner;

import java.io.Serializable;

public class KRFConclusion implements Serializable {

    private static final long serialVersionUID = -2403438459005621858L;

    private String conclusionKey;
    private String conclusionValue;
    private String conclusionOperator;
    private Integer id;
    private Integer conclusionID;

    public String getConclusionKey() {
        return conclusionKey;
    }

    public void setConclusionKey(String conclusionKey) {
        this.conclusionKey = conclusionKey;
    }

    public String getConclusionValue() {
        return conclusionValue;
    }

    public void setConclusionValue(String conclusionValue) {
        this.conclusionValue = conclusionValue;
    }

    public String getConclusionOperator() {
        return conclusionOperator;
    }

    public void setConclusionOperator(String conclusionOperator) {
        this.conclusionOperator = conclusionOperator;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getConclusionID() {
        return conclusionID;
    }

    public void setConclusionID(Integer conclusionID) {
        this.conclusionID = conclusionID;
    }

    @Override
    public String toString() {
        return "KRFConclusion [conclusionKey=" + conclusionKey
                + ", conclusionValue=" + conclusionValue
                + ", conclusionOperator=" + conclusionOperator + ", ruleID="
                + id + ", conclusionID=" + conclusionID + "]";
    }
}
