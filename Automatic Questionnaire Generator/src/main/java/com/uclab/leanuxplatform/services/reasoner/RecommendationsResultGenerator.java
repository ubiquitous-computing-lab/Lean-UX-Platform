package com.uclab.leanuxplatform.services.reasoner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
//import org.uclab.mm.kcl.edket.krf.model.knowledgebase.KRFRule;
//import org.uclab.mm.kcl.edket.krf.util.KRFUtil;


public class RecommendationsResultGenerator {
    private static Logger log = LogManager.getLogger(PatternMatcher.class);
    public static final String CSV_FILE_NAME = "recommendationResults.csv";
    public static final String DELIMITER = ", ";
    public static final String NEW_LINE = "\n";
    public static final String TITLE_ROW = "Input Case, Matched Rules, Time Taken for Rules Matching, Conflict Resolved Rules, Time Taken for Conflict Resolution, Total Time Taken";
    
    
    /**
     * Generate Results of the recommended rules
     * 
     * @param finalResolvedRules
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static void generateResults(KRFResult krfResult) throws JsonGenerationException, JsonMappingException{
        File csvFile = new File(CSV_FILE_NAME);
        boolean addTitle = false;
        if(!csvFile.exists()){
            addTitle = true;
        }
        
        FileWriter writer = null;
        try{
            log.debug("Appending generated results to {}", CSV_FILE_NAME);
            writer = new FileWriter(csvFile, true);
            if(addTitle){
                writer.append(TITLE_ROW);
            } 
            writer.append(NEW_LINE);
            
            //.1 add inputCase
            String ic = KRFUtil.objectMapper.writeValueAsString(krfResult.getInputCase());
            ic = ic.replaceAll("[\\[\\]{}\"]", "");
            ic = ic.replaceAll(":", "=")
                   .replaceAll(",", ". ");
            writer.append(ic);
            writer.append(DELIMITER);
            
            //.2 add matcher ids
            StringBuilder mids = new StringBuilder(" ");
            String idAppender  = "";
            for(KRFRule matchedRule : krfResult.getMatchedRules()){
                mids.append(idAppender).append(matchedRule.getId());
                idAppender  = " - ";
            }
            writer.append(mids.toString()).append(DELIMITER);
            
            //.3 add time taken by matcher
            String mtime = TimeUnit.MILLISECONDS.toSeconds(krfResult.getTimeTakenByMatcher())+" sec/" + krfResult.getTimeTakenByMatcher() + " ms";
            writer.append(mtime).append(DELIMITER);
            
            //.4 add finalResolved rule IDs
            StringBuilder crids = new StringBuilder(" ");
            idAppender  = "";
            for(KRFRule resolvedRule : krfResult.getFinalResolvedRules()){
                crids.append(idAppender).append(resolvedRule.getId());
                idAppender  = " - ";
            }
            writer.append(crids.toString()).append(DELIMITER);
            
            //.5 add time taken by conflictResolver
            String crtime = TimeUnit.MILLISECONDS.toSeconds(krfResult.getTimeTakenByConflictResolver())+" sec/" + krfResult.getTimeTakenByConflictResolver() + " ms";
            writer.append(crtime).append(DELIMITER);
            
            //.6 add total time taken e.g. matcherTime + conflictResolverTime
            long totalTime = krfResult.getTimeTakenByMatcher() + krfResult.getTimeTakenByConflictResolver();
            String totalTimeStr = TimeUnit.MILLISECONDS.toSeconds(totalTime) + " sec/" + totalTime + " ms";
            writer.append(totalTimeStr);
            
            writer.flush();
            writer.close();
        }catch(IOException e){
            log.error("Cannot write to file: {}", CSV_FILE_NAME);
            try{writer.close();}catch(IOException ex){}
        }
    }
}
