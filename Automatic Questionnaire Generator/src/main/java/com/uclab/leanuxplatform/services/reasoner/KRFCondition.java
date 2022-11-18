package com.uclab.leanuxplatform.services.reasoner;

import java.io.Serializable;

public class KRFCondition implements Serializable {

    private static final long serialVersionUID = 4672043610791173466L;

    private String attributeKey;
    private String attributeValue;
    private String conditionType;
    private String operator;
    private Boolean isItSituation;
    private Integer id;

    public String getAttributeKey() {
        return attributeKey;
    }

    public void setAttributeKey(String attributeKey) {
        this.attributeKey = attributeKey;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Boolean getIsItSituation() {
        return isItSituation;
    }

    public void setIsItSituation(Boolean isItSituation) {
        this.isItSituation = isItSituation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "KRFCondition [conditionKey=" + attributeKey
                + ", conditionValue=" + attributeValue + ", conditionType="
                + conditionType + ", conditionValueOperator="
                + operator + ", isItSituation=" + isItSituation
                + ", id=" + id + "]";
    }

}
