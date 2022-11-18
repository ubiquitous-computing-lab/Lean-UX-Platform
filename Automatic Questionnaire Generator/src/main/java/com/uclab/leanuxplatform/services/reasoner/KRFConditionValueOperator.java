package com.uclab.leanuxplatform.services.reasoner;

public enum KRFConditionValueOperator {
    EQ("="), NOT_EQ("!="), LT("<"), GT(">"), LT_EQ("<="), GT_EQ(">=");
    
    private final String operator;
    
    KRFConditionValueOperator(String operator){
        this.operator = operator;
    }
    
    public String getOperator(){
        return this.operator;
    }
    
    public static KRFConditionValueOperator getValueOperator(String operator) {
        if (operator != null) {
          for (KRFConditionValueOperator cvo : KRFConditionValueOperator.values()) {
            if (operator.equals(cvo.operator)) {
              return cvo;
            }
          }
        }
        return null;
      }
}
