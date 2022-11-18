package com.uclab.leanuxplatform.services.couch;

import java.util.HashMap;
import java.util.List;

import org.lightcouch.CouchDbClient;
import com.google.gson.JsonObject;

public class Fetcher {
    
    private CouchDbClient db;
    HashMap<String, String> meta;

    public Fetcher(CouchDbClient dbClient, HashMap<String, String> metadata) {
        this.db = dbClient;
        this.meta = metadata;
    }

    public List<JsonObject> fetchFinalEmotionsByTimeRange(String viewId, int project, int participant, int taskId, String startTime, String endTime) {
        JsonObject startKey = new JsonObject();
        JsonObject endKey = new JsonObject();

        startKey.addProperty(meta.get("PROJECT_ID"), project);
        startKey.addProperty(meta.get("PARTICIPANT_ID"), participant);
        startKey.addProperty(meta.get("TASK_ID"), taskId);
        startKey.addProperty(meta.get("TIMESTAMP"), startTime);

        endKey.addProperty(meta.get("PROJECT_ID"), project);
        endKey.addProperty(meta.get("PARTICIPANT_ID"), participant);
        endKey.addProperty(meta.get("TASK_ID"), taskId);
        endKey.addProperty(meta.get("TIMESTAMP"), endTime);

        return db.view(viewId).startKey(startKey).endKey(endKey).query(JsonObject.class);
    }

    List<JsonObject> fetchFinalMetricsByTimeRange(String finalMetricsViewId, int selectedProject, int activeParticipant, String evaluationStartTime, String evaluationEndTime) {
        JsonObject startKey = new JsonObject();
        JsonObject endKey = new JsonObject();

        startKey.addProperty(meta.get("PROJECT_ID"), selectedProject);
        startKey.addProperty(meta.get("PARTICIPANT_ID"), activeParticipant);
        startKey.addProperty(meta.get("TIMESTAMP"), evaluationStartTime);

        endKey.addProperty(meta.get("PROJECT_ID"), selectedProject);
        endKey.addProperty(meta.get("PARTICIPANT_ID"), activeParticipant);
        endKey.addProperty(meta.get("TIMESTAMP"), evaluationEndTime);

        return db.view(finalMetricsViewId).startKey(startKey).endKey(endKey).query(JsonObject.class);
    }
}
