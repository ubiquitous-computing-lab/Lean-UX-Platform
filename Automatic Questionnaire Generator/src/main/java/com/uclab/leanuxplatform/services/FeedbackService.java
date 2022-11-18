/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.services;

import com.uclab.leanuxplatform.services.util.RemoteLeanUxServer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.uclab.leanuxplatform.models.FeedbackResponse;
import static com.uclab.leanuxplatform.services.util.RemoteLeanUxServer.REMOTE_SERVER_HOST;
import com.uclab.leanuxplatform.services.couch.RemoteCouchDbServer;
import com.uclab.leanuxplatform.services.reasoner.InputCaseBase;
import com.uclab.leanuxplatform.services.reasoner.KRFKnowledgeBase;
import com.uclab.leanuxplatform.services.reasoner.KRFResult;
import com.uclab.leanuxplatform.services.reasoner.KRFRule;
import com.uclab.leanuxplatform.services.reasoner.KRFUtil;
import com.uclab.leanuxplatform.services.reasoner.PatternMatcher;
import com.uclab.leanuxplatform.services.reasoner.RecommendationBuilder;
import com.uclab.leanuxplatform.services.util.CommonUtils;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author Fahad Ahmed Satti
 */
@Service
@DependsOn({"restTemplate"})
@Scope(value = "prototype")
public class FeedbackService extends RemoteLeanUxServer {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RemoteCouchDbServer couchController;

    @Autowired
    private KRFKnowledgeBase knowledgeBase;
    
    @Autowired
    LoginService ls;

    @Autowired
    ProjectService ps;    
    //automatic Question Generator
    private InputCaseBase inputCaseBase;
    private String questionText = "";
    private String emotionalState = "";
    private String cognitionState = "";
    //file paths
    private static final String KRF_INPUT_CASES_LOCAL = "krf_input_cases.json";
    private static final String KRF_KNOWLEDGE_BASE_LOCAL = "krf_knowledge_base.json";
    private static final String REMOTE_KNOWLEDGE_BASE = REMOTE_KNOWLEDGE_BASE_HOST + "/AuthoringEnvironmentLeanUX/rules/api/list/";
    private static final String POST_FEEDBACK = REMOTE_SERVER_HOST + "/api/projects/question/response/create/";

    public FeedbackService() {
        inputCaseBase = new InputCaseBase();
    }

    public String getTriangulationQuestion() {

        boolean hasFacts = this.loadFacts();
        //boolean hasFacts = this.loadLocalFacts();
        boolean hasRules = this.loadRules();

        if (!hasFacts && !hasRules) {
            log.debug("facts loaded:" + hasFacts);
            log.debug("Rules loaded:" + hasRules);
            log.error("ERROR!!!");
        } else {
            try {
                RecommendationBuilder recommendationBuilder = new RecommendationBuilder(new PatternMatcher());
                boolean batchInputCase = inputCaseBase.getInputCaseBase().size() > 1;
                for (Map<String, List<String>> conditionsValue : inputCaseBase.getInputCaseBase()) {
                    KRFResult krfResult = recommendationBuilder.buildRecommendation(conditionsValue, knowledgeBase);
                    recommendationBuilder.generateResults(krfResult);
                    if (!batchInputCase) {
                        if (krfResult.getFinalResolvedRules().isEmpty()) {
                            questionText = "No Rules Matched!";
                        } else {
                            Map<Integer, String> ruleMap = new HashMap<>();
                            krfResult.getFinalResolvedRules().forEach((r) -> {
                                ruleMap.put(r.getId(), r.getConclusion());
                            });
                            questionText = KRFUtil.objectMapper
                                    .writerWithDefaultPrettyPrinter()
                                    .writeValueAsString(ruleMap);
                            questionText = questionText.replaceAll("[^a-zA-Z ]", "");
                            questionText = questionText.trim();
                        }
                        log.info(questionText);
                    }else{
                        log.debug("InputCaseBase size is 0");
                    }
                }
                if (batchInputCase) {
                    String batchResult = "No. of input cases[" + inputCaseBase.getInputCaseBase().size() + "], "
                            + "No. of rules[" + knowledgeBase.getRules().size() + "]";
                    //resultArea.setText(batchResult);
                    log.debug("RESULT");
                    log.debug(batchResult);
                }

            } catch (IOException ex) {
                //message.setText("Cannot load data!!!");
                log.error("Cant generate Recommendations!");
                log.error(ex.getMessage(), ex);
            }
        }
        return questionText;
    }

    /* 
    * get facts from JSON file
     */
    private boolean loadLocalFacts() {
        try {
            String loadedFacts = CommonUtils.loadData(KRF_INPUT_CASES_LOCAL);
            inputCaseBase = KRFUtil.objectMapper.readValue(loadedFacts, InputCaseBase.class);
            String prettyFacts = KRFUtil.objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(inputCaseBase);

            log.info("Conditions loaded successfully");
            log.debug("------Conditions----------");
            log.debug(prettyFacts);
            log.debug("_____________________");
        } catch (Exception ex) {
            log.error("Unable to load Facts");
            log.error(ex.getMessage(), ex);
            return false;
        }
        return true;

    }
    
    /* 
    * get facts from Couch
     */
    private boolean loadFacts() {
        try {
            
            int selectedProject = ls.getThisUser().getSelectedProject();
            int activeParticipant = ls.getThisUser().getActiveParticipantId();
            HashMap<String, Integer> cognitionMetricCount = new HashMap();
            HashMap<String, Integer> emotionMetricCount = new HashMap();
            System.out.println("Loading Facts Start Time:"+ps.getEvaluationStartTime());
            System.out.println("Loading Facts End Time:"+ps.getEvaluationEndTime());
            List<JsonObject> projectUserStatesJson = couchController.getConsolidatedData(selectedProject, activeParticipant,ps.getEvaluationStartTime(),ps.getEvaluationEndTime());
            
            for (JsonObject row : projectUserStatesJson) {
            JsonObject value = row.get("value").getAsJsonObject();
            //log.debug("value:"+value);
            //log.debug(meta.get("MODALITY"));
                if(value.get("metric").getAsString().equals("cognition_state")){
                    if(cognitionMetricCount.containsKey(value.get("labelType").getAsString())){
                        cognitionMetricCount.put(value.get("labelType").getAsString(),(cognitionMetricCount.get(value.get("labelType").getAsString()))+1);
                    }else{
                        cognitionMetricCount.put(value.get("labelType").getAsString(),1);
                    }
                }
                if(value.get("metric").getAsString().equals("emotional_state")){
                    if(emotionMetricCount.containsKey(value.get("labelType").getAsString())){
                        emotionMetricCount.put(value.get("labelType").getAsString(),(emotionMetricCount.get(value.get("labelType").getAsString()))+1);
                    }else{
                        emotionMetricCount.put(value.get("labelType").getAsString(),1);
                    }
                }
            
            }
            if(cognitionMetricCount.size()>0){
                cognitionState = Collections.max(cognitionMetricCount.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
                log.debug(cognitionState);
                inputCaseBase.addInputFact("cognition_state", new String[]{cognitionState});
            }
            if(emotionMetricCount.size()>0){
                emotionalState = Collections.max(emotionMetricCount.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
                log.debug(emotionalState);
                inputCaseBase.addInputFact("emotional_state", new String[]{emotionalState});
            }
   
            //String loadedFacts = CommonUtils.loadData(KRF_INPUT_CASES_LOCAL);
            //inputCaseBase = KRFUtil.objectMapper.readValue(loadedFacts, InputCaseBase.class);
            String prettyFacts = KRFUtil.objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(inputCaseBase);

            log.info("Conditions loaded successfully");
            log.debug("------Conditions----------");
            log.debug(prettyFacts);
            log.debug("_____________________");
        } catch (Exception ex) {
            log.error("Unable to load Facts");
            log.error(ex.getMessage(), ex);
            return false;
        }
        return true;

    }

    /* 
    * get facts from JSON file
     */
    private boolean loadRules() {
        try {
            log.info(REMOTE_KNOWLEDGE_BASE);
            ResponseEntity<List<KRFRule>> response = restTemplate.exchange(REMOTE_KNOWLEDGE_BASE,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<KRFRule>>() {
            });
            //log.info(response.toString());
            //knowledgeBase = new KRFKnowledgeBase();
            knowledgeBase.setRules(response.getBody());
            if (knowledgeBase.getRules().size() > 0) {
                log.info("Rules loaded successfully");
                log.debug("------Rules----------");
                log.debug(knowledgeBase.toString());
                log.debug("_____________________");
            } else {
                log.info("No Rules were found!");
            }
        } catch (RestClientException ex) {
            log.error("Unable to load Rules");
            log.error(ex.getMessage(), ex);
            return false;
        }
        return true;

    }

    public void postEvaluationResponse(FeedbackResponse userResponse) {
        if(userResponse==null) userResponse = new FeedbackResponse();
        userResponse.setQuestion_text(questionText);
        userResponse.setCognitive_state(cognitionState);
        userResponse.setEmotional_state(emotionalState);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<FeedbackResponse> request = new HttpEntity<>(userResponse, headers);
        ResponseEntity<FeedbackResponse> result = restTemplate.exchange(
                POST_FEEDBACK,
                HttpMethod.POST,
                request,
                FeedbackResponse.class);
        log.debug("return status code:" + result.getStatusCodeValue());
        log.debug(result.getBody().toString());
        
        //Also save the feedback in CouchDb;
        Gson gsonBuilder = new GsonBuilder().create();
        JsonElement element = gsonBuilder.toJsonTree(userResponse);
        if (couchController.init()) {
            couchController.uploadData(element.getAsJsonObject());
        }
        couchController.close();

    }
}
