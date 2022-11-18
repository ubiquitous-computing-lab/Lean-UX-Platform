package com.uclab.leanuxplatform.services.reasoner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.uclab.mm.kcl.edket.krf.model.knowledgebase.KRFCondition;
//import org.uclab.mm.kcl.edket.krf.model.knowledgebase.KRFKnowledgeBase;
//import org.uclab.mm.kcl.edket.krf.model.knowledgebase.KRFRule;
//import org.uclab.mm.kcl.edket.krf.util.KRFConditionType;
//import org.uclab.mm.kcl.edket.krf.util.KRFConditionValueOperator;
//import org.uclab.mm.kcl.edket.krf.util.KRFUtil;

public class PatternMatcher {
    private static Logger log = LogManager.getLogger(PatternMatcher.class);
    
    /**
     * returns list of matched rules
     * 
     * @param conditionsValue
     * @param knowledgeBase
     * @return Matched Rules
     */
    public List<KRFRule> fireRule(Map<String, List<String>> conditionsValue, KRFKnowledgeBase knowledgeBase) {
        List<KRFRule> firedRules = new ArrayList<KRFRule>();

        for (KRFRule rule : knowledgeBase.getRules()) {
            if (matchedRule(conditionsValue, rule.getConditions())) {
                firedRules.add(rule);
            }
        }
        return firedRules;
    }
    
    /**
     * returns true if all conditionKeys of the rule matched against condition values false otherwise
     * 
     * @param conditionsValue
     * @param conditionsKeyList
     * @return boolean
     */
    public boolean matchedRule(Map<String, List<String>> conditionsValue, List<KRFCondition> conditionsKeyList) {
        for (KRFCondition conditionsKey : conditionsKeyList) {
            String conditionKey = conditionsKey.getAttributeKey();
            if (conditionsValue.containsKey(conditionKey)) {
                if (!matchedCondition(conditionsValue.get(conditionKey), conditionsKey)) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * returns TRUE if FactValue and RuleValue matched according to the operator
     * defined in the rule, FALSE otherwise.
     * 
     * @param conditionsValue
     * @param conditionsKey
     * @return Boolean
     */
    public boolean matchedCondition(List<String> conditionsValue, KRFCondition conditionsKey) {
        Boolean matched = false;
        try {
            KRFConditionType krfConditionType = KRFConditionType.valueOf(fetchConditionType(conditionsKey.getConditionType()));
            KRFConditionValueOperator cvo = KRFConditionValueOperator.getValueOperator(conditionsKey.getOperator());
            
            switch (krfConditionType) {
                case String:
                    matched = TypeComparator.compare(conditionsValue.get(0), conditionsKey.getAttributeValue(), cvo);
                break;
                case Integer:
                    matched = TypeComparator.compare(Integer.parseInt(conditionsValue.get(0)), Integer.parseInt(conditionsKey.getAttributeValue()), cvo);
                break;
                case Boolean:
                    matched = TypeComparator.compare(conditionsValue.get(0).equalsIgnoreCase("yes"), conditionsKey.getAttributeValue().equalsIgnoreCase("yes"), cvo);
                break;
                case Time:
                    int factTimeSec = KRFUtil.timeToSec(conditionsValue.get(0));
                    int ruleTimeSec = KRFUtil.timeToSec(conditionsKey.getAttributeValue());
                    matched = TypeComparator.compare(factTimeSec, ruleTimeSec, cvo);
                break;
                default:
                    log.error("Type: {}  is not defined! ", krfConditionType);
            }

        } catch (Exception ex) {
            matched = false;
            log.error("Invalid type conversion {}", ex.getMessage());
        }
        return matched;
    }

    public String fetchConditionType(String conditionType) {
        if (conditionType == null) {
            conditionType = "String";
        }
        return conditionType.trim();
    }
}
