/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.controllers.screens;

//reseaoner 
import com.uclab.leanuxplatform.controllers.screens.alerts.AlertController;
import com.uclab.leanuxplatform.controllers.screens.utils.DynamicSurvey;
import com.uclab.leanuxplatform.controllers.screens.utils.LeanUxScreens;
import com.uclab.leanuxplatform.models.Answer;

import com.uclab.leanuxplatform.models.Participant;
import com.uclab.leanuxplatform.models.Project;
import com.uclab.leanuxplatform.models.Question;
import com.uclab.leanuxplatform.models.Survey;
import com.uclab.leanuxplatform.models.Task;
import com.uclab.leanuxplatform.models.constants.TaskType;
import com.uclab.leanuxplatform.services.LoginService;

import com.uclab.leanuxplatform.services.ProjectService;
import com.uclab.leanuxplatform.services.util.TimeHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jamil
 */
@Component
public class PreSurveyController extends BaseScreenController implements Initializable {

    @Autowired
    LoginService ls;
    @Autowired
    ProjectService ps;
    @Autowired
    DynamicSurvey ds;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    String taskStartTime;
    Task currentTask = null;
    Survey currentSurvey = null;

    private Project selectedProject;
    private Participant activeParticipant;

    @FXML
    private Label taskLabel;
    @FXML
    private VBox surveyQuestions;

    private String projectStartTime;

    public PreSurveyController() {

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //multiTaskService = new  MultiTaskService();
            selectedProject = ls.getThisUser().getSelectedProjectObject();
            // Identify the active participant
            this.activeParticipant = selectedProject.getParticipantById(ls.getThisUser().getActiveParticipantId());
            if (this.activeParticipant == null) {
                AlertController.error("No user logged in.");
                //System.out.println("No active participant for id:"+ls.getThisUser().getActiveParticipantId());
                return;
            }
            this.screenId = "Pre Survey of " + selectedProject.getTitle();
            projectStartTime = TimeHandler.getCurrentTime();

            currentTask = selectedProject.getTask(TaskType.PRE);
            if (currentTask == null) {
                AlertController.error("No pre tasks were found. This window should not have opened.");
                return;
            }

            log.debug("curr Task:::::" + currentTask);
            if (!ps.getSurveyListByTask(currentTask)) {
                AlertController.error(ps.getErrorMessage());
                return;
            }

            currentSurvey = currentTask.getNextSurvey();
            log.debug("current Survey:" + currentSurvey);
            //System.out.println(currentTask.getTitle());
            taskLabel.setText(currentTask.getTitle());

            surveyQuestions.getChildren().clear();
            //Display the questions in the survey
            Node surveyNode = ds.displaySurveyQuestions(currentSurvey);
            if (surveyNode != null) {
                this.surveyQuestions.getChildren().clear();
                this.surveyQuestions.getChildren().add(surveyNode);
            } else {
                log.debug("There was an error building the dynamic survey.");
                log.debug("Null node was retruned by Dynamic Survey Builder.");
                AlertController.info("There was an error building the dynamic survey.");
                this.sc.getCurrentStage().close();
            }

            //Get the current time
            taskStartTime = TimeHandler.getCurrentTime();

            this.sc.getCurrentStage().setOnCloseRequest(event -> {
                log.debug("Attempting to close Project");
                AlertController.info("User forced shutdown of the evaluation");
                this.sc.getCurrentStage().close();
            });

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @FXML
    protected void handleNextTaskButtonAction(ActionEvent event) throws JSONException, Exception {

        this.sc.getCurrentStage().close();
        //Alright no more surveys in the pre task. So let's move to the during tasks.
        Stage stage = new Stage();
        this.sc.init(stage);
        this.sc.loadScreen(LeanUxScreens.EVALUATE_PROJECT);

        //Reset Task Start Time
        taskStartTime = TimeHandler.getCurrentTime();

    }

    public void resetFields() {
        taskStartTime = TimeHandler.getCurrentTime();
    }

    

}
