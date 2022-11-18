/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.services.reasoner;

/**
 *
 * @author Devspot-AI1
 */
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
//import org.uclab.mm.kcl.edket.krf.model.knowledgebase.KRFKnowledgeBase;
//import org.uclab.mm.kcl.edket.krf.model.knowledgebase.KRFRule;


public class RecommendationBuilder implements IRecommendationBuilder {
    private static Logger log = LogManager.getLogger(RecommendationBuilder.class);
    private PatternMatcher patternMatcher;
    
    public RecommendationBuilder(PatternMatcher patternMatcher){
        this.patternMatcher = patternMatcher;
    }
    
    @Override
    public KRFResult buildRecommendation(Map<String, List<String>> conditionsValue, KRFKnowledgeBase krfKnowledgeBase) {
        log.debug("Building recommendation...");
        long matcherStartTime = System.currentTimeMillis();
        List<KRFRule> firedRules = patternMatcher.fireRule(conditionsValue, krfKnowledgeBase);
        long matcherTimeTaken = System.currentTimeMillis() - matcherStartTime;
        long resolverStartTime = System.currentTimeMillis();
        List<KRFRule> finalResolvedRules = ConflictResolver.resolveConflict(firedRules);
        long resolverTimeTaken = System.currentTimeMillis() - resolverStartTime;
        
        KRFResult krfResult = new KRFResult();
        
        krfResult.setInputCase(conditionsValue);
        krfResult.setMatchedRules(firedRules);
        krfResult.setFinalResolvedRules(finalResolvedRules);
        krfResult.setTimeTakenByMatcher(matcherTimeTaken);
        krfResult.setTimeTakenByConflictResolver(resolverTimeTaken);
        
        return krfResult;
    }

    @Override
    public void generateResults(KRFResult krfResult) { 
        log.debug("Generating Results...");
        try {
            RecommendationsResultGenerator.generateResults(krfResult);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        }
    }

}
