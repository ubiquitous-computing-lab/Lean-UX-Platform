/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.controllers.screens.customcontrols;

import com.uclab.leanuxplatform.controllers.screens.ScreensController;
import com.uclab.leanuxplatform.controllers.screens.alerts.AlertController;
import com.uclab.leanuxplatform.controllers.screens.utils.LeanUxScreens;
import com.uclab.leanuxplatform.models.constants.ModalityEnum;
import com.uclab.leanuxplatform.models.constants.TaskType;
import com.uclab.leanuxplatform.services.LoginService;
import com.uclab.leanuxplatform.services.ProjectService;
import com.uclab.leanuxplatform.services.util.TimeHandler;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import javafx.stage.Stage;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

/**
 *
 * @author uclab351
 */
@Component
@Configurable(preConstruction = true, autowire = Autowire.BY_TYPE, dependencyCheck = false)
public class ParticipantItemController extends HBox {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    LoginService ls;
    @Autowired
    ProjectService ps;
    
    @Autowired
    protected ScreensController sc;
    
    @FXML
    private Label participantName;
    @FXML
    private Label participantAge;
    
    private int participantId; 
    private boolean disableEvaluation;
    
    @FXML
    private Button btnActivateParticipant;
    
    private ScheduledExecutorService selectedProjectUpdateService;
    
    @FXML
    void activateParticipant(ActionEvent event) throws IOException {
        log.debug("New Participant selected:"+participantId);
        selectedProjectUpdateService.shutdown();
            ls.getThisUser().setActiveParticipantId(participantId);
            
            //Check which windows to open next (Pre Survey || Evaluate)
            log.debug("Getting tasks for the selected project");
            if (!ps.getTaskListByProject(ls.getThisUser().getSelectedProjectObject())) {
                log.debug("No Tasks found for some reason");
                AlertController.error(ps.getErrorMessage());
                log.debug(ps.getErrorMessage());
            }else{
                ps.setEvaluationStartTime(TimeHandler.getCurrentTime());
                if(ls.getThisUser().getSelectedProjectObject().hasModality(ModalityEnum.SUR) && ls.getThisUser().getSelectedProjectObject().hasTask(TaskType.PRE)){
                    Stage stage = new Stage();
                    this.sc.init(stage);
                    sc.loadScreen(LeanUxScreens.PRE_SURVEY);
                }else if(ls.getThisUser().getSelectedProjectObject().hasTask(TaskType.DURING)){
                    log.debug("Normal logic flow with During tasks.");
                    Stage stage = new Stage();
                    this.sc.init(stage);
                    sc.loadScreen(LeanUxScreens.EVALUATE_PROJECT);
                }else if(!ls.getThisUser().getSelectedProjectObject().hasModality(ModalityEnum.SUR)){
                    log.debug("Modality Survey is not enabled so moving on to evaluate project.");
                    Stage stage = new Stage();
                    this.sc.init(stage);
                    sc.loadScreen(LeanUxScreens.EVALUATE_PROJECT);
                }else{
                    AlertController.error("No valid Task type was found.");
                    log.debug(Arrays.deepToString(ls.getThisUser().getSelectedProjectObject().getTasks().toArray()));
                }
            }
//            Stage stage = new Stage();
//            this.sc.init(stage);
//            sc.loadScreen(LeanUxScreens.EVALUATE_PROJECT);
    }

    public ParticipantItemController() {
        super();
        FXMLLoader loader = new FXMLLoader( getClass().getResource(LeanUxScreens.PARTICIPANT_ITEM) );

        loader.setRoot( this );
        loader.setController( this );

        try {
            loader.load();
        } catch ( IOException e ) {
            throw new RuntimeException( e );
        }
    }

    public Label getParticipantName() {
        return participantName;
    }

    public void setParticipantName(Label participantName) {
        this.participantName = participantName;
    }
    
    public void setParticipantName(String participantName) {
        this.participantName.setText(participantName);
    }
    
    public Label getParticipantAge() {
        return participantAge;
    }

    public void setParticipantAge(Label participantAge) {
        this.participantAge = participantAge;
    }
    public void setParticipantAge(int participantAge) {
        this.participantAge.setText(participantAge+"");
    }
    public void setParticipantAge(String participantAge) {
        this.participantAge.setText(participantAge);
    }

    public int getParticipantId() {
        return participantId;
    }

    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.participantName.getText());
        hash = 19 * hash + Objects.hashCode(this.participantAge.getText());
        hash = 19 * hash + this.participantId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ParticipantItemController other = (ParticipantItemController) obj;
        if (this.participantId != other.participantId) {
            return false;
        }
        if (!Objects.equals(this.participantName.getText(), other.participantName.getText())) {
            return false;
        }
        if (!Objects.equals(this.participantAge.getText(), other.participantAge.getText())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ParticipantItemController{" + "participantName=" + participantName.getText() + ", participantAge=" + participantAge.getText() + ", participantId=" + participantId + '}';
    }

    public void setSelectedProjectUpdateService(ScheduledExecutorService selectedProjectUpdateService) {
        this.selectedProjectUpdateService = selectedProjectUpdateService;
    }

    public boolean isDisableEvaluation() {
        return disableEvaluation;
    }

    public void setDisableEvaluation(boolean disableEvaluation) {
        this.disableEvaluation = disableEvaluation;
        btnActivateParticipant.setDisable(disableEvaluation);
    }


    
}
