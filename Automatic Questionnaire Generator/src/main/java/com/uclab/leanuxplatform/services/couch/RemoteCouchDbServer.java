package com.uclab.leanuxplatform.services.couch;

import com.uclab.leanuxplatform.services.util.TimeHandler;
import com.google.gson.JsonObject;
import com.uclab.leanuxplatform.models.BLData;
import com.uclab.leanuxplatform.models.EegData;
import com.uclab.leanuxplatform.models.FerData;
import static com.uclab.leanuxplatform.services.couch.RemoteCouchDbServer.log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lightcouch.CouchDbClient;
import java.util.Map;
import org.lightcouch.CouchDbException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("couchController")
@DependsOn({"properties"})
@Scope(value = "singleton")
public class RemoteCouchDbServer {
    static final Logger log = LoggerFactory.getLogger(RemoteCouchDbServer.class);
    private String dbHost;// = "dcl.leanuxplatform.com";
    private String dbName;// = "leanux_emotion_archive_2.7_test";
    private int dbPort;// = 5984;
    private String dbUser;// = "leanux";
    private String dbPass;// = "l3@nux";
    //Convert this to use https
    private String dbProtocol;// = "http";
    // CouchDB view configurations
    String finalEmotionViewId = "emotionData/final_emotion";
    String finalMetricsViewId = "globalstats/project_user_states";
    // CouchDB metadata configurations
    private final HashMap<String, String> metadata;

    CouchDbClient dbClient = null;
    private Fetcher fetcher = null;
    private Uploader uploader = null;
    
    @Autowired
    public RemoteCouchDbServer(@Value("${remote.db.url}") String dbHost,
            @Value("${remote.db.name}") String dbName,
            @Value("${remote.db.port}") String dbPort,
            @Value("${remote.db.protocol}") String dbProtocol,
            @Value("${hidden.remote.db.user}") String dbUser,
            @Value("${hidden.remote.db.password}") String dbPass) {
        
        this.metadata = new HashMap<String, String>() {
            {
                // Column names
                put("MODALITY", "datatype");
                put("PROJECT_ID", "project");
                put("PARTICIPANT_ID", "participant");
                put("TASK_ID", "taskid");
                put("TIMESTAMP", "streamdatatime");
                put("EMOTION", "emotion");
                put("SCORE", "score");

                // Modality names
                put("MODALITY_FACIAL", "Face");
                put("MODALITY_VOICE", "Audio");
                put("MODALITY_BODY_LANGUAGE", "BLData");
                put("MODALITY_FUSION", "Fusion");	// Totally final emotion

                // Emotion names
                put("EMOTION_JOY", "Joyful");
                put("EMOTION_ANGRY", "Anger");
                put("EMOTION_NEUTRAL", "Neutral");
                put("EMOTION_SAD", "Sadness");
                
                put("FEATURE", "features");
                put("ALL_EMOTIONS", "deepdata");
                
                
            }
        };
        
        this.dbHost = dbHost;
        this.dbName = dbName;
        this.dbPort = Integer.parseInt(dbPort);
        this.dbUser = dbUser;
        this.dbPass = dbPass;
        this.dbProtocol = dbProtocol;

        this.init();
    }

    private boolean connect() {
        // CouchDB connection
        try{
            dbClient = new CouchDbClient(dbName, true, dbProtocol, dbHost, dbPort, dbUser, dbPass);
        }catch(CouchDbException cde){
            //AlertController.error("Unable to connect to Couch DB:"+cde.getMessage());
            log.error(cde.getMessage(),cde);
            dbClient = null;
        }

        return dbClient != null;
    }

    public boolean init() {
        if (this.connect()) {
            //log.debug("Fetcher status:"+this.fetcher);
            //log.debug("Uploader status:"+this.uploader.getDb());
            if (this.fetcher == null) {
                this.fetcher = new Fetcher(dbClient, metadata);
            }
            if (this.uploader == null) {
                this.uploader = new Uploader(dbClient, metadata);
            }

            return true;
        } else {
            log.debug("unable to connect to remote couch at " + dbHost);
            return false;
        }
        
    }
    
    public void uploadAudio(int project, int participant, int taskId, String emotion, float score, String speechText){
        if(this.uploader==null){
            log.debug("No uploader. Skipping this record. Project:"+project+",participant:"+participant+", task:"+taskId+", Modality:Audio, Emotion:"+emotion);
            return;
        }
        //log.debug("Uploading Audio");
        Map<String, Object> map = new HashMap<>();

        map.put(this.metadata.get("PROJECT_ID"), project);
        map.put(this.metadata.get("PARTICIPANT_ID"), participant);
        map.put(this.metadata.get("TASK_ID"), taskId);
        map.put(this.metadata.get("TIMESTAMP"), TimeHandler.getCurrentTime());
        map.put(this.metadata.get("MODALITY"), "Audio");
        map.put(this.metadata.get("EMOTION"), emotion);
        map.put(this.metadata.get("SCORE"), score);
        map.put("text", speechText);
        
        this.uploader.saveMap(map);
        //this.getUploader().uploadSingleModalityData(project, participant, taskId, TimeHandler.getCurrentTime(), emotion, "Audio", score);
    }
    public void uploadBodyLanguage(int project, int participant, int taskId, String emotion, float score, BLData newBlData){
        if(this.uploader==null){
            log.debug("No uploader. Skipping this record. Project:"+project+",participant:"+participant+", task:"+taskId+", Modality:BL, Emotion:"+emotion);
            return;
        }
        //log.debug("Uploading Body Language");
        this.uploader.uploadSingleModalityData(project, participant, taskId, TimeHandler.getCurrentTime(), emotion, "BLData", score, newBlData.getAllEmotions());
    }
    public void uploadFace(int project, int participant, int taskId, String emotion, float score,float[] featureVector,FerData newFerData){
        if(this.uploader==null){
            log.debug("No uploader. Skipping this record. Project:"+project+",participant:"+participant+", task:"+taskId+", Modality:FER, Emotion:"+emotion);
            return;
        }
        
        //log.debug("Uploading Facial Recognition");
        Map<String, Object> map = new HashMap<>();
        map.put(this.metadata.get("PROJECT_ID"), project);
        map.put(this.metadata.get("PARTICIPANT_ID"), participant);
        map.put(this.metadata.get("TASK_ID"), taskId);
        map.put(this.metadata.get("TIMESTAMP"), TimeHandler.getCurrentTime());
        map.put(this.metadata.get("MODALITY"), "Face");
        map.put(this.metadata.get("EMOTION"), emotion);
        map.put(this.metadata.get("SCORE"), score);
        map.put(this.metadata.get("FEATURE"), featureVector);
        map.put(this.metadata.get("ALL_EMOTIONS"), newFerData.getAllEmotions());
        this.uploader.saveMap(map);
        //this.getUploader().uploadSingleModalityData(project, participant, taskId, TimeHandler.getCurrentTime(), emotion, "Face", score);
    }
    public void uploadEeg(int project, int participant, int taskId, double interest, double stress, double relaxation, double excitement, double focus, EegData cdi){
        if(this.uploader==null){
            log.debug("No uploader. Skipping this record. Project:"+project+",participant:"+participant+", task:"+taskId+", Modality:EEG");
            return;
        }
        
        //log.debug("Uploading EEG");
        Map<String, Object> map = new HashMap<>();

        map.put(this.metadata.get("PROJECT_ID"), project);
        map.put(this.metadata.get("PARTICIPANT_ID"), participant);
        map.put(this.metadata.get("TASK_ID"), taskId);
        map.put(this.metadata.get("TIMESTAMP"), TimeHandler.getCurrentTime());
        map.put(this.metadata.get("MODALITY"), "EEG");
        map.put("interest", interest);
        map.put("stress", stress);
        map.put("relaxation", relaxation);
        map.put("excitement", excitement);
        map.put("focus", focus);
        map.put(this.metadata.get("ALL_EMOTIONS"), cdi);

        this.uploader.saveMap(map);
        //this.getUploader().uploadSingleModalityData(project, participant, taskId, TimeHandler.getCurrentTime(), congnition, "EEG", score);
    }
    public void uploadET(int project, int participant, int taskId, double y_coord, double x_coord){
        if(this.uploader==null){
            log.debug("No uploader. Skipping this record. Project:"+project+",participant:"+participant+", task:"+taskId+", Modality:ET");
            return;
        }
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(this.metadata.get("PROJECT_ID"), project);
        map.put(this.metadata.get("PARTICIPANT_ID"), participant);
        map.put(this.metadata.get("TASK_ID"), taskId);
        map.put(this.metadata.get("TIMESTAMP"), TimeHandler.getCurrentTime());
        map.put(this.metadata.get("MODALITY"), "EyeTracking");
        map.put("x", x_coord);
        map.put("y", y_coord);
  
        this.uploader.saveMap(map);
        //this.getUploader().uploadSingleModalityData(project, participant, taskId, TimeHandler.getCurrentTime(), emotion, "EyeTracking", score);
    }
    
    public void close(){
        dbClient.shutdown();
    }
    
//    public Fetcher getFetcher() {
//        if (this.fetcher == null) {
//            this.fetcher = new Fetcher(dbClient, metadata);
//        }
//        return fetcher;
//    }

    public void setFetcher(Fetcher fetcher) {
        this.fetcher = fetcher;
    }

//    public Uploader getUploader() {
//        if(!this.connect()){
//            return null;
//        }
//        if (this.uploader == null) {
//            this.uploader = new Uploader(dbClient, metadata);
//        }
//        //log.debug("Uploader DB:"+this.uploader.getDb());
//        return uploader;
//    }

    public void setUploader(Uploader uploader) {
        this.uploader = uploader;
    }

    public String getDbHost() {
        return dbHost;
    }

    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public int getDbPort() {
        return dbPort;
    }

    public void setDbPort(int dbPort) {
        this.dbPort = dbPort;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPass() {
        return dbPass;
    }

    public void setDbPass(String dbPass) {
        this.dbPass = dbPass;
    }

    public String getDbProtocol() {
        return dbProtocol;
    }

    public void setDbProtocol(String dbProtocol) {
        this.dbProtocol = dbProtocol;
    }

    public void preventFurtherUpload(boolean b) {
        //log.debug("Prevent further upload:"+b);
        this.uploader.setAllowUpload(!b);
    }

    public HashMap<String, String> getMetadata() {
        return metadata;
    }

    public List<JsonObject> getEmotionData(int project, int participant, int taskId, String startTime, String endTime) {
        if(this.fetcher==null){
            log.debug("No fetcher defined. Will return an empty arraylist.");
            return new ArrayList<>();
        }
        return this.fetcher.fetchFinalEmotionsByTimeRange(finalEmotionViewId, project, participant, taskId, startTime, endTime);
    }

    public void uploadFusionedData(int project, int participant, int task, String currentTime, String fusionedEmotion) {
        if(this.uploader==null){
            log.debug("No uploader. Skipping this record. Project:"+project+",participant:"+participant+", task:"+task+", Modality:FusionedData, Emotion:"+fusionedEmotion);
            return;
        }
        this.uploader.uploadFusionedData(project, participant, task, currentTime, fusionedEmotion);
    }

    public void uploadData(JsonObject asJsonObject) {
        if(this.uploader==null){
            log.debug("No uploader. Skipping this record. Record:"+asJsonObject.getAsString());
            return;
        }
        this.uploader.uploadData(asJsonObject);
    }

    public List<JsonObject> getConsolidatedData(int selectedProject, int activeParticipant, String evaluationStartTime, String evaluationEndTime) {
        if(this.fetcher==null){
            log.debug("No fetcher defined. Will return an empty arraylist.");
            return new ArrayList<>();
        }
        return this.fetcher.fetchFinalMetricsByTimeRange(finalMetricsViewId, selectedProject, activeParticipant, evaluationStartTime, evaluationEndTime);
    }
}
