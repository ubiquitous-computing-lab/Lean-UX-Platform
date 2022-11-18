package com.uclab.leanuxplatform.services.reasoner;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

//import org.uclab.mm.kcl.edket.krf.model.knowledgebase.KRFRule;

public class KRFResult implements Serializable{
    
    private static final long serialVersionUID = 4864501782957542841L;
    
    private Map<String, List<String>> inputCase;
    private List<KRFRule> matchedRules;
    private List<KRFRule> finalResolvedRules;
    private long timeTakenByMatcher;
    private long timeTakenByConflictResolver;
    public Map<String, List<String>> getInputCase() {
        return inputCase;
    }
    public void setInputCase(Map<String, List<String>> inputCase) {
        this.inputCase = inputCase;
    }
    public List<KRFRule> getMatchedRules() {
        return matchedRules;
    }
    public void setMatchedRules(List<KRFRule> matchedRules) {
        this.matchedRules = matchedRules;
    }
    public List<KRFRule> getFinalResolvedRules() {
        return finalResolvedRules;
    }
    public void setFinalResolvedRules(List<KRFRule> finalResolvedRules) {
        this.finalResolvedRules = finalResolvedRules;
    }
    public long getTimeTakenByMatcher() {
        return timeTakenByMatcher;
    }
    public void setTimeTakenByMatcher(long timeTakenByMatcher) {
        this.timeTakenByMatcher = timeTakenByMatcher;
    }
    public long getTimeTakenByConflictResolver() {
        return timeTakenByConflictResolver;
    }
    public void setTimeTakenByConflictResolver(long timeTakenByConflictResolver) {
        this.timeTakenByConflictResolver = timeTakenByConflictResolver;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    
    @Override
    public String toString() {
        return "KRFResult [inputCase=" + inputCase + ", matchedRules=" + matchedRules + ", finalResolvedRules="
                + finalResolvedRules + ", timeTakenByMatcher=" + timeTakenByMatcher + ", timeTakenByConflictResolver="
                + timeTakenByConflictResolver + "]";
    }
}
