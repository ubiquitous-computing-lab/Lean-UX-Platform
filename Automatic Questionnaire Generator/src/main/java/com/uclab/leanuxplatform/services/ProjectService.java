/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.services;

import com.uclab.leanuxplatform.services.util.RemoteLeanUxServer;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.uclab.leanuxplatform.models.EvaluationResponse;
import com.uclab.leanuxplatform.models.Modality;
import com.uclab.leanuxplatform.models.Participant;
import com.uclab.leanuxplatform.models.Project;
import com.uclab.leanuxplatform.models.ScreenRecorderData;
import com.uclab.leanuxplatform.models.Survey;
import com.uclab.leanuxplatform.models.Task;
import com.uclab.leanuxplatform.models.User;
import com.uclab.leanuxplatform.services.couch.RemoteCouchDbServer;
import static com.uclab.leanuxplatform.services.util.RemoteLeanUxServer.REMOTE_SERVER_HOST;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.DependsOn;

/**
 *
 * @author Fahad Ahmed Satti
 */
@Service
@DependsOn({"restTemplate", "couchController"})
public class ProjectService extends RemoteLeanUxServer {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    RemoteCouchDbServer couchController;
        
    public String getProjectList = "api/projects/list/";
    public String getTaskList = "api/projects/task/list/";
    public String getQuestionList = "api/projects/question/list/";
    public String getSurveyList = "api/projects/task/survey/list/";
    public String postEvaluationResponse = "api/projects/task/survey/response/create/";
    public String getModalityList = "api/projects/modality/list/";
    public String getParticipantList = "api/projects/participant/list/";
    public String postNewParticipant = "api/projects/participant/create/";
    public String postNewScreenVideo = "api/projects/screenrecording/create/";
    private String errorMessage;
    private String evaluationStartTime;
    private String evaluationEndTime;
    
    /*
     * Get all projects for the user. This generic method should take User object
     * as input, so that this can be used for picking projects for another user
     * as well.
     */
    public boolean getAllForUser(User u) {

        List<Project> allProjects;
        try {
            ResponseEntity<List<Project>> response = restTemplate.exchange(REMOTE_SERVER_HOST + "/" + getProjectList + "?created_by=" + u.getId(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Project>>() {
            });
            /*
             * Clear any pre-existing projects to keep the data current
             */
            u.getOwnedProjects().clear();
            allProjects = response.getBody();
        } catch(HttpClientErrorException ex){
            log.error(ex.getMessage(),ex);
            errorMessage = ex.getMessage();
            return false;
        }catch (ResourceAccessException ex) {
            errorMessage = ex.getMessage()+":Please check your network connection.";
            log.error(ex.getMessage(), ex);
            return false;
        }
        log.debug("projects:"+allProjects.size());
        if (allProjects != null) {
            allProjects.forEach((project) -> {
                if (project.getCreated_by().getId() == u.getId()) {
                    log.debug("Found a project:" + project);
                    u.getOwnedProjects().add(project);
                }
            });
        } else {
            log.info("No projects were found for the user:" + u.getId());
            errorMessage = "No projects were found for the user:" + u.getId();
            return false;
        }
        errorMessage = "";        
        return true;
    }

    /*
    * Update the project instance from db.
     */
    public boolean getUpdatedProjectInstance(Project p) {
        try {
            ResponseEntity<List<Project>> response = restTemplate.exchange(REMOTE_SERVER_HOST + "/" + getProjectList + "?id=" + p.getId(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Project>>() {
            });
            // Only select the first project. There should be only 1 project returned
            // but the REST service returns it wrapped in an array
            Project updatedProject = response.getBody().get(0);
            if (!p.equals(updatedProject)) {
                p.getParticipants().clear();
                updatedProject.getParticipants().forEach((participant) -> {
                    //log.debug("[Participant] new:" + participant);
                    p.getParticipants().add(participant);
                });
                p.getModalities().clear();
                updatedProject.getModalities().forEach((modality) -> {
                    //log.debug("[Participant] new:" + participant);
                    p.getModalities().add(modality);
                });
                //p = updatedProject;
                log.debug("[Project] updated:" + p);
            }
        } catch(HttpClientErrorException ex){
            log.error(ex.getMessage(),ex);
            errorMessage = ex.getMessage();
            return false;
        }catch (ResourceAccessException ex) {
            errorMessage = ex.getMessage()+":Please check your network connection.";
            log.error(ex.getMessage(), ex);
            return false;
        }
        errorMessage = "";
        return true;
    }

    /*
    * Get all participants for a project and replace in the selectedProject.
     */
    public boolean getParticipantListByProject(Project p) {
        try {
            ResponseEntity<List<Participant>> response = restTemplate.exchange(REMOTE_SERVER_HOST + "/" + getParticipantList + "?project=" + p.getId(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Participant>>() {
            });
            //System.out.println(response.getBody());
            List<Participant> allParticipants = response.getBody();
            if (allParticipants != null) {

                p.getParticipants().clear();
                allParticipants.forEach((participant) -> {
                    log.debug("[Participant] new:" + participant);
                    p.getParticipants().add(participant);
                });
            } else {
                log.info("No participants were found for the project:" + p.getId());
            }
        }catch(HttpClientErrorException ex){
            log.error(ex.getMessage(),ex);
            errorMessage = ex.getMessage();
            return false;
        } catch (ResourceAccessException ex) {
            errorMessage = ex.getMessage()+":Please check your network connection.";
            log.error(ex.getMessage(), ex);
            return false;
        }
        errorMessage = "";
        return true;
    }
    
    /*
    * Get the survey(s) associated with this task.
     */
    public boolean getSurveyListByTask(Task t) {
        try {
            ResponseEntity<List<Survey>> response = restTemplate.exchange(REMOTE_SERVER_HOST + "/" + getSurveyList + "?task=" + t.getId(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Survey>>() {
            });
            List<Survey> allSurveys = response.getBody();
            if (allSurveys != null) {
                t.getSurveys().clear();
                allSurveys.forEach((survey) -> {
                    log.debug("[Project][Task][Survey]:" + survey);
                    t.getSurveys().add(survey);
                });
                
                //Initialize he iterator, now that all the items have been added in the list
                t.initSurveyIterator();
            } else {
                log.info("No Qeustions were found for the task:" + t.getId());
            }
        } catch(HttpClientErrorException ex){
            log.error(ex.getMessage(),ex);
            errorMessage = ex.getMessage();
            return false;
        } catch (ResourceAccessException ex) {
            errorMessage = ex.getMessage()+":Please check your network connection.";
            log.error(ex.getMessage(), ex);
            return false;
        }
        errorMessage = "";
        return true;
    }
        

    /*
    * Get all modalities for a project and replace in the selectedProject.
     */
    public boolean getModalityListByProject(Project p) {
        try {
            ResponseEntity<List<Modality>> response = restTemplate.exchange(REMOTE_SERVER_HOST + "/" + getModalityList + "?id=" + p.getId(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Modality>>() {
            });
            //System.out.println(response.getBody());
            List<Modality> allModalities = response.getBody();
            if (allModalities != null) {

                p.getModalities().clear();
                allModalities.forEach((modality) -> {
                    log.debug("[Modality] new:" + modality);
                    p.getModalities().add(modality);
                });
            } else {
                log.info("No modalities were found for the project:" + p.getId());
            }
        }catch(HttpClientErrorException ex){
            log.error(ex.getMessage(),ex);
            errorMessage = ex.getMessage();
            return false;
        } catch (ResourceAccessException ex) {
errorMessage = ex.getMessage()+":Please check your network connection.";
            log.error(ex.getMessage(), ex);
            return false;
        }
        errorMessage = "";
        return true;
    }

    /*
    * Get all tasks for a project. The new tasks should replace any older tasks.
     */
    public boolean getTaskListByProject(Project p) {
        try {
            ResponseEntity<List<Task>> response = restTemplate.exchange(REMOTE_SERVER_HOST + "/" + getTaskList + "?project=" + p.getId(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Task>>() {
            });
            //System.out.println(response.getBody());
            List<Task> allTasks = response.getBody();
            if (allTasks != null) {

                /*
             * For all tasks returned, only update the ones which exist in the
             * project object. There should be no reason for any new Task
             * to be created, which was not in the project object.
                 */
                p.getTasks().clear();
                allTasks.forEach((task) -> {
                    log.debug("[Task] new:" + task);
                    p.getTasks().add(task);
                });
                //Initialize he iterator, now that all the items have been added in the list
                p.initTaskIterator();
                log.debug("Found Tasks:"+p.getTasks().size());
            } else {
                log.info("No tasks were found for the project:" + p.getId());
                errorMessage = "No tasks were found for the project:" + p.getId();
                return false;
            }
        } catch(HttpClientErrorException ex){
            log.error(ex.getMessage(),ex);
            errorMessage = ex.getMessage();
            return false;
        } catch (ResourceAccessException ex) {
            errorMessage = ex.getMessage()+":Please check your network connection.";
            log.error(ex.getMessage(), ex);
            return false;
        }
        errorMessage = "";
        log.debug("returning true");
        return true;
    }
    
    /**
     * Saves the response provided by the user and additional parameters
     * in Django server and CouchDb
     * @param userResponse
     * @return
     */
    public boolean postResponse(EvaluationResponse userResponse) {
        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<EvaluationResponse> request = new HttpEntity<>(userResponse, headers);
            ResponseEntity<EvaluationResponse> result = restTemplate.exchange(
                    REMOTE_SERVER_HOST + "/" + postEvaluationResponse,
                    HttpMethod.POST,
                    request,
                    EvaluationResponse.class);
            log.debug("return status code:" + result.getStatusCodeValue());
            if(result.hasBody()){
                log.debug(result.getBody().toString());
            }
            
            //Also save the feedback in CouchDb;
            Gson gsonBuilder = new GsonBuilder().create();
            JsonElement element = gsonBuilder.toJsonTree(userResponse);
            if (couchController.init()) {
                couchController.uploadData(element.getAsJsonObject());
            }
            couchController.close();
            
        } catch(HttpClientErrorException ex){
            log.error(ex.getMessage(),ex);
            errorMessage = ex.getMessage();
            return false;
        } catch (ResourceAccessException ex) {
            errorMessage = ex.getMessage()+":Please check your network connection.";
            log.error(ex.getMessage(), ex);
            return false;
        }
        errorMessage = "";
        return true;
    }

    public boolean postEvaluationResponse(EvaluationResponse userResponse) {
        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<EvaluationResponse> request = new HttpEntity<>(userResponse, headers);
            ResponseEntity<EvaluationResponse> result = restTemplate.exchange(
                    REMOTE_SERVER_HOST + "/" + postEvaluationResponse,
                    HttpMethod.POST,
                    request,
                    EvaluationResponse.class);
            log.debug("return status code:" + result.getStatusCodeValue());
            if(result.hasBody()){
                log.debug(result.getBody().toString());
            }
            
            //Also save the feedback in CouchDb;
        Gson gsonBuilder = new GsonBuilder().create();
        JsonElement element = gsonBuilder.toJsonTree(userResponse);
        if (couchController.init()) {
            couchController.uploadData(element.getAsJsonObject());
        }
        couchController.close();
            
        } catch(HttpClientErrorException ex){
            log.error(ex.getMessage(),ex);
            errorMessage = ex.getMessage();
            return false;
        } catch (ResourceAccessException ex) {
            errorMessage = ex.getMessage()+":Please check your network connection.";
            log.error(ex.getMessage(), ex);
            return false;
        }
        errorMessage = "";
        return true;
    }

    public boolean postNewParticipant(Participant participant) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Participant> request = new HttpEntity<>(participant, headers);
            ResponseEntity<Participant> response = restTemplate.exchange(REMOTE_SERVER_HOST + "/" + postNewParticipant,
                    HttpMethod.POST,
                    request,
                    Participant.class);
            Participant temp = response.getBody();
            if (temp != null) {
                participant.setId(temp.getId());
            }
        } catch(HttpClientErrorException ex){
            log.error(ex.getMessage(),ex);
            errorMessage = ex.getMessage();
            return false;
        } catch (ResourceAccessException ex) {
            errorMessage = ex.getMessage()+":Please check your network connection.";
            log.error(ex.getMessage(), ex);
            return false;
        }
        errorMessage = "";
        return true;

    }

    public boolean postScreenRecordedVideo(ScreenRecorderData scd) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // This nested HttpEntiy is important to create the correct
            // Content-Disposition entry with metadata "name" and "filename"
            MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
            ContentDisposition contentDisposition = ContentDisposition
                    .builder("form-data")
                    .name("screenrecording")
                    .filename(scd.getFileInstance().getName())
                    .build();
            fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
            HttpEntity<byte[]> fileEntity = new HttpEntity<>(Files.toByteArray(scd.getFileInstance()), fileMap);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("screenrecording", fileEntity);
            body.add("participant", scd.getParticipant());
            body.add("project", scd.getProject());
            body.add("recordstarttime", scd.getRecordstarttime());
            body.add("recordendtime", scd.getRecordendtime());
            body.add("screenwidth", scd.getScreenwidth());
            body.add("screenheight", scd.getScreenheight());

            HttpEntity<MultiValueMap<String, Object>> requestEntity
                    = new HttpEntity<>(body, headers);
            String result = restTemplate.postForObject(REMOTE_SERVER_HOST + "/" + postNewScreenVideo, requestEntity, String.class);

        } catch (IOException ex) {
            errorMessage = ex.getMessage();
            log.error(ex.getMessage(),ex);
            return false;
        } catch(HttpClientErrorException ex){
            errorMessage = ex.getMessage()+":Please check your network connection.";
            log.error(ex.getMessage(),ex);
            errorMessage = ex.getMessage();
            return false;
        } catch (ResourceAccessException ex) {
            log.error(ex.getMessage(), ex);
            return false;
        }
        errorMessage = "";
        return true;

    }

    public boolean getParticipantById(Participant participant) {
        try {
            ResponseEntity<List<Participant>> response = restTemplate.exchange(REMOTE_SERVER_HOST + "/" + getParticipantList + "?id=" + participant.getId(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Participant>>() {
            });

            Participant updatedParticipant = response.getBody().get(0);
            if (!participant.equals(updatedParticipant)) {
                participant.update(updatedParticipant);

                log.debug("[Participant] new:" + participant);
            } else {
                log.info("No participants were found for the project:" + participant.getId());
            }
        } catch(HttpClientErrorException ex){
            log.error(ex.getMessage(),ex);
            errorMessage = ex.getMessage();
            return false;
        } catch (ResourceAccessException ex) {
            errorMessage = ex.getMessage()+":Please check your network connection.";
            log.error(ex.getMessage(), ex);
            return false;
        }
        errorMessage = "";
        return true;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getEvaluationStartTime() {
        return evaluationStartTime;
    }

    public void setEvaluationStartTime(String evaluationStartTime) {
        this.evaluationStartTime = evaluationStartTime;
    }

    public String getEvaluationEndTime() {
        return evaluationEndTime;
    }

    public void setEvaluationEndTime(String evaluationEndTime) {
        this.evaluationEndTime = evaluationEndTime;
    }

}
