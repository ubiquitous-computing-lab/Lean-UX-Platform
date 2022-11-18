package com.uclab.leanuxplatform.services.reasoner;

import java.io.Serializable;
import java.util.List;

public class KRFRule implements Serializable {

    private static final long serialVersionUID = -3608483411293578402L;

    private String conclusion;
    private String conclusionType;
    private List<KRFConclusion> conclusionList;
    private List<KRFCondition> conditions;
    private Integer id;

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public List<KRFConclusion> getConclusionList() {
        return conclusionList;
    }

    public void setConclusionList(List<KRFConclusion> conclusionList) {
        this.conclusionList = conclusionList;
    }

    public List<KRFCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<KRFCondition> conditions) {
        this.conditions = conditions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "KRFRule{" + "conclusion=" + conclusion + ", conclusionType=" + conclusionType + ", conclusionList=" + conclusionList + ", conditions=" + conditions + ", ruleID=" + id + '}';
    }
    
    public String getConclusionType() {
        return conclusionType;
    }

    public void setConclusionType(String conclusionType) {
        this.conclusionType = conclusionType;
    }
}
