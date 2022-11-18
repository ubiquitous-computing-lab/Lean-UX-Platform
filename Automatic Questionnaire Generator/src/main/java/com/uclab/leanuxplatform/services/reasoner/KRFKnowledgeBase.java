package com.uclab.leanuxplatform.services.reasoner;

import java.io.Serializable;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class KRFKnowledgeBase implements Serializable {

    private static final long serialVersionUID = 2577947434339517656L;

    List<KRFRule> rules;

    public List<KRFRule> getRules() {
        return rules;
    }

    public void setRules(List<KRFRule> rules) {
        this.rules = rules;
    }

    @Override
    public String toString() {
        return "KRFKnowledgeBase [rules=" + rules + "]";
    }
}
