package com.uclab.leanuxplatform.services.couch;

import com.google.gson.JsonObject;
import com.uclab.leanuxplatform.models.metrics.EmotionDataItem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.lightcouch.CouchDbClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Uploader {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    private CouchDbClient db;
    private HashMap<String, String> meta;
    private boolean allowUpload = true;
    
    public Uploader(CouchDbClient dbClient, HashMap<String, String> metadata) {
        //log.debug(dbClient.toString());
        this.db = dbClient;
        this.meta = metadata;
    }

    public CouchDbClient getDb() {
        return db;
    }
    
    public void saveMap(Map<String, Object> data){
        if(allowUpload){
            //log.debug(this.db.toString());
            this.db.save(data);
        }else{
            log.info("Preventing upload");
        }
    }
    
    /*
     * DO NOT USE THIS METHOD!! (Except to upload final feedback). 
     * This method does not employ any safety checks and should be used for 1 time uploads only.
    */
    public void uploadData(JsonObject data) {
            db.save(data);
    }
    
    public void uploadFusionedData(int project, int participant, int taskId, String time, String emotion) {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put(meta.get("PROJECT_ID"), project);
        map.put(meta.get("PARTICIPANT_ID"), participant);
        map.put(meta.get("TASK_ID"), taskId);
        map.put(meta.get("TIMESTAMP"), time);
        map.put(meta.get("MODALITY"), meta.get("MODALITY_FUSION"));
        map.put(meta.get("EMOTION"), emotion);
        
        this.saveMap(map);
    }

    public void uploadSingleModalityData(int project, int participant, int taskId, String time, String emotion, String datatype, float score) {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put(meta.get("PROJECT_ID"), project);
        map.put(meta.get("PARTICIPANT_ID"), participant);
        map.put(meta.get("TASK_ID"), taskId);
        map.put(meta.get("TIMESTAMP"), time);
        map.put(meta.get("MODALITY"), datatype);
        map.put(meta.get("EMOTION"), emotion);
        map.put(meta.get("SCORE"), score);

        this.saveMap(map);
    }

    public boolean isAllowUpload() {
        return allowUpload;
    }

    public void setAllowUpload(boolean allowUpload) {
        //log.debug("Current upload status 1:"+this.allowUpload);
        this.allowUpload = allowUpload;
        //log.debug("Current upload status 2:"+this.allowUpload);
    }

    void uploadSingleModalityData(int project, int participant, int taskId, String currentTime, String emotion, String blData, float score, List<EmotionDataItem> allEmotions) {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put(meta.get("PROJECT_ID"), project);
        map.put(meta.get("PARTICIPANT_ID"), participant);
        map.put(meta.get("TASK_ID"), taskId);
        map.put(meta.get("TIMESTAMP"), currentTime);
        map.put(meta.get("MODALITY"), blData);
        map.put(meta.get("EMOTION"), emotion);
        map.put(meta.get("SCORE"), score);
        map.put(meta.get("ALL_EMOTIONS"), allEmotions);

        this.saveMap(map);
    }

}
