package com.uclab.leanuxplatform.services.reasoner;

import java.util.List;
import java.util.Map;

//import org.uclab.mm.kcl.edket.krf.model.knowledgebase.KRFKnowledgeBase;

public interface IRecommendationBuilder {
    /**
     * build recommendations
     * @param conditionsValue
     * @param krfKnowledgeBase
     * @return KRFResult
     */
    public KRFResult buildRecommendation(Map<String, List<String>> conditionsValue, KRFKnowledgeBase krfKnowledgeBase);
    
    /**
     * writes KRFResults to the output csv file
     * @param krfResult
     */
    public void generateResults(KRFResult krfResult);
}
