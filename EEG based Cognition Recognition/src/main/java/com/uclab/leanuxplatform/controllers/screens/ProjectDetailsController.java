/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.controllers.screens;

import com.jfoenix.controls.JFXButton;
import com.uclab.leanuxplatform.controllers.screens.alerts.AlertController;
import com.uclab.leanuxplatform.controllers.screens.customcontrols.ModalityItemController;
import com.uclab.leanuxplatform.controllers.screens.customcontrols.ParticipantItemController;
import com.uclab.leanuxplatform.controllers.screens.utils.LeanUxScreens;
import com.uclab.leanuxplatform.models.Modality;
import com.uclab.leanuxplatform.models.Participant;
import com.uclab.leanuxplatform.models.Project;
import com.uclab.leanuxplatform.models.constants.ModalityEnum;
import com.uclab.leanuxplatform.services.EegService;
import com.uclab.leanuxplatform.services.LoginService;
import com.uclab.leanuxplatform.services.ProjectService;
import com.uclab.leanuxplatform.services.util.RemoteLeanUxServer;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author Jamil
 */
@Component
public class ProjectDetailsController extends BaseScreenController implements Initializable {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    LoginService ls;
    @Autowired
    ProjectService ps;
    @Autowired
    ApplicationContext context;
    @Autowired
    AutowireCapableBeanFactory factory;

    @Autowired
    private EegService eegService;
    
    private Project selectedProject;
    private boolean autoUpdateModalityList = false;
    private boolean autoUpdateParticipantList = false;
    // Scheduled thread for updating the project list
    private ScheduledExecutorService selectedProjectUpdateService;
    private Thread modalityUpdater;

    @FXML
    private Text projectDescription;

    @FXML
    private Label projectTitle;

    @FXML
    private VBox participantList;

    @FXML
    private Label participantCount;

    @FXML
    private Label taskCount;

    @FXML
    private ProgressIndicator updateProgress;
    @FXML
    private JFXButton addParticipantButton;

    @FXML
    private HBox modalityList;

    @FXML
    void addParticipantHandler(ActionEvent event) throws IOException {
        Stage s = new Stage();
        s.setTitle("Add Participant");
        this.sc.init(s);
        this.sc.loadScreen(LeanUxScreens.ADD_PARTICIPANT);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.screenId = "Project Details";

        selectedProject = ls.getThisUser().getSelectedProjectObject();
        this.projectTitle.setText(selectedProject.getTitle());
        this.projectDescription.setText(selectedProject.getDescription());
        //this.updateParticipantList();

        //System.out.println(selectedProject);
        // setup a scheduled executor to periodically update the project list
        selectedProjectUpdateService = Executors.newSingleThreadScheduledExecutor();

        //Start modalities
        javafx.concurrent.Task updateModalities = new javafx.concurrent.Task<Void>() {
            @Override
            public Void call() {
                log.debug("**********************Updating Modalities*****************************");
                fillModalities();
                autoUpdateModalityList = true;
                fillParticipants();
                autoUpdateParticipantList = true;
                log.debug("*********************************************************************");
                return null;
            }
        };
        modalityUpdater = new Thread(updateModalities);
        modalityUpdater.setName("Project-Details-Modality-Updater");
        modalityUpdater.start();
        
        selectedProjectUpdateService.scheduleWithFixedDelay(() -> {
                    updateProject();
                }, 0, 5, TimeUnit.SECONDS);
        
        //Start the timeline and modalities, only when the window is shown
        this.sc.getCurrentStage().setOnShowing((WindowEvent event) -> {
            log.debug("Processing screen items");
        });

//        this.sc.getCurrentStage().focusedProperty().addListener((listener) -> {
//            log.info("Focus gained:" + this.sc.getCurrentStage().focusedProperty());
//            if (this.sc.getCurrentStage().focusedProperty().getValue()) {
//
//                //AlertController.info("Starting autoupdate");
//                
//            } else {
//                //AlertController.info("Shutdown autoupdate");
//                selectedProjectUpdateService.shutdown();
//            }
//        });
        this.sc.getCurrentStage().setOnCloseRequest(event -> {
            log.debug("Attempting to close ProjectDetails");
            try {
                modalityList.getChildren().forEach((mic) -> {
                    closeModality((ModalityItemController) mic);
                });
                Platform.runLater(() -> {
                    modalityList.getChildren().clear();
                });
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            //Stop updating the charts
            if (!selectedProjectUpdateService.isShutdown()) {
                selectedProjectUpdateService.shutdown();
            }
            AlertController.info("User forced shutdown of project details:" + this.sc.getCurrentStage().getTitle());
            log.debug("User forced shutdown of project details:" + this.sc.getCurrentStage().getTitle());
            this.sc.getCurrentStage().close();
            //this.sc.close();

        });
    }

    /**
     * This method does not bring any new information from the server and should
     * be used only to update the view with current contents.
     */
    private void fillModalities() {
        //modalityList.getChildren().clear();
        log.debug("Processing Modalities:"+selectedProject.getModalities().size());
        
        selectedProject.getModalities().forEach(new Consumer<Modality>() {
            @Override
            public void accept(Modality modality) {
                log.debug("modality:"+modality);
                if (isNotPresent(modality)) {
                    ModalityItemController mic = (ModalityItemController) context.getBean("modalityItemController");
                    factory.autowireBean(mic);
                    mic.setModalityName(modality.getFullName());
                    mic.setModalityShortName(modality.getName());

                    FontAwesomeIcon fai = FontAwesomeIcon.create();
                    //initialize the modality
    //                    if (initModality(modality)) {
    //                        log.debug(modality.getName()+" Initialized");
    //                        fai.setIconName(modality.getIconName());
    //                        fai.style("-icons-color: blue;");
    //                        fai.setFill(Color.BLUE);
    //                        mic.setModalityStatus("Active");
    //                    } else {
                    fai.setIconName(modality.getIconName());
                    mic.setModalityStatus("Not Active");
                    //}
                    mic.setIconName(fai);

                    //Magic status check method
                    Platform.runLater(()->{
                        modalityList.getChildren().add(mic);
                    });
                    
                }

            }
            private boolean isNotPresent(Modality modality) {
                for (int i = 0; i < modalityList.getChildren().size(); i++) {
                    if (((ModalityItemController) modalityList.getChildren().get(i)).getModalityName().getText().equalsIgnoreCase(modality.getFullName())) {
                        return false;
                    }
                }
                return true;
            }

        });
        log.debug("Modalities done");
    }

    public void fillParticipants(){
        
        int participants = (selectedProject.getParticipants() == null) ? 0 : selectedProject.getParticipants().size();
        //log.debug("current participants:"+participants+"");
        //log.debug(this.updateProgress.getProgress()+"-");
        Platform.runLater(() -> {
            participantCount.setText(participants + "/" + selectedProject.getNumber_participants() + " Participants");
            if (participants >= selectedProject.getNumber_participants()) {
                addParticipantButton.setDisable(true);
            }
        });
        
        if (participants > 0) {
            selectedProject.getParticipants().forEach(new Consumer<Participant>() {
                @Override
                public void accept(Participant participant) {
                    try {
                        if (!ps.getParticipantById(participant)) {
                            AlertController.error("Unable to get Participant info for id:"+participant);
                            log.error(ps.getErrorMessage());
                            return;
                        }
                        if (participant.getId() != 0 && isNotPresent(participant)) {

                            log.debug("New Participant:" + participant);
                            ParticipantItemController pic = (ParticipantItemController) context.getBean("participantItemController");
                            //Autowiring the beans inside the ProjectItemController
                            factory.autowireBean(pic);

                            pic.setParticipantId(participant.getId());
                            pic.setParticipantName(participant.getName());
                            pic.setParticipantAge(participant.getAge());
                            pic.setSelectedProjectUpdateService(selectedProjectUpdateService);
                            if (selectedProject.getTasks() == null || selectedProject.getTasks().size() < 1) {
                                pic.setDisableEvaluation(true);
                            }
                            Platform.runLater(() -> {
                                participantList.getChildren().add(pic);
                            });
                            //System.out.println(pic);
                        }
                    } catch (IllegalArgumentException | BeansException e) {
                        log.error("Error creating participantItemController Bean", e);
                    }
                }

                private boolean isNotPresent(Participant participant) {
                    for (int i = 0; i < participantList.getChildren().size(); i++) {
                        if (participant.getId() == ((ParticipantItemController) participantList.getChildren().get(i)).getParticipantId()) {
                            //System.out.println("Participant is present:"+participant.getId());
                            return false;
                        }
                    }
                    //System.out.println("Participant is not present:"+participant.getId());
                    return true;
                }
            });
        } else {

        }
    
    }
    /**
     * Initialize each modality.
     *
     * @param Modality the model object containing only the name of the modality
     * @return boolean the result of the initialization process.
     */
//    private boolean initModality(Modality modality) {
//        if (modality.getName().equals(ModalityEnum.EEG.name())) {
//            log.debug("Initializing EEG service");
//            return eegService.init();
//        } else if (modality.getName().equals(ModalityEnum.GSR.name())) {
//            log.debug("Initializing GSR service");
//            return gsrService.init();
//        } else if (modality.getName().equals(ModalityEnum.ET.name())) {
//            log.debug("Initializing ET service");
//            return eyeTrackingService.init();
//        } else if (modality.getName().equals(ModalityEnum.BL.name())) {
//            log.debug("Not implemented yet!");
//        } else if (modality.getName().equals(ModalityEnum.MIC.name())) {
//            log.debug("Not implemented yet!");
//        } else if (modality.getName().equals(ModalityEnum.FER.name())) {
//            log.debug("Not implemented yet!");
//        } else if (modality.getName().equals(ModalityEnum.IT.name())) {
//            log.debug("Not implemented yet!");
//        }
//        return false;
//    }
    /**
     * Cleanly close the modality execution.
     *
     * @param ModalityItemController the custom controller object used for
     * identifying the correct service
     */
    private void closeModality(ModalityItemController modality) {
        if (modality.getModalityShortName().equals(ModalityEnum.EEG.name())) {
            log.debug("Stopping EEG service");
            eegService.stopProcessing();
        } 

    }

    private void updateProject() {
        this.updateProgress.setProgress(10);
        //Don't update till the first process is compelte
        if (!autoUpdateModalityList) {
            log.debug("Auto Update modality List is false. Returning");
            Platform.runLater(() -> {
                this.updateProgress.setProgress(0);
            });
            return;
        }
        /*
         * Get modality list from the server.
         */
        if (!ps.getUpdatedProjectInstance(selectedProject)) {
            AlertController.error(ps.getErrorMessage());
        }
        Platform.runLater(() -> {
            this.updateProgress.setProgress(20);
        });
        log.debug("auto updating modality and participant list now..." + selectedProject);
        this.fillModalities();
        
        
//        selectedProject.getModalities().forEach(new Consumer<Modality>() {
//            @Override
//            public void accept(Modality modality) {
//                if (isNotPresent(modality)) {
//                    log.debug("Adding new modality:" + modality);
//                    ModalityItemController mic = (ModalityItemController) context.getBean("modalityItemController");
//                    factory.autowireBean(mic);
//                    mic.setModalityName(modality.getFullName());
//                    mic.setModalityShortName(modality.getName());
//                    FontAwesomeIcon fai = FontAwesomeIcon.create();
//                    //initialize the modality
////                    if (initModality(modality)) {
////                        //log.debug("EEG Initialized");
////                        fai.setIconName(modality.getIconName());
////                        //fai.style("-fx-background-color: blue;");
////                        fai.setFill(Color.BLUE);
////                        mic.setModalityStatus("Active");
////                    } else {
//                    fai.setIconName(modality.getIconName());
//                    mic.setModalityStatus("Not Active");
//                    //}
//                    mic.setIconName(fai);
//                    log.debug("adding modality:" + mic.getModalityName());
//                    try {
//                        Platform.runLater(() -> modalityList.getChildren().add(mic));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            private boolean isNotPresent(Modality modality) {
//                for (int i = 0; i < modalityList.getChildren().size(); i++) {
//                    if (((ModalityItemController) modalityList.getChildren().get(i)).getModalityName().getText().equalsIgnoreCase(modality.getFullName())) {
//                        return false;
//                    }
//                }
//                return true;
//            }
//        });

        Platform.runLater(() -> {
            this.updateProgress.setProgress(50);
        });
        //Remove deleted modalities
        for (int i = 0; i < modalityList.getChildren().size(); i++) {
            boolean found = false;
            for (int j = 0; j < selectedProject.getModalities().size(); j++) {
                if (((ModalityItemController) modalityList.getChildren().get(i)).getModalityName().getText().equalsIgnoreCase(selectedProject.getModalities().get(j).getFullName())) {
                    //System.out.println("[DEL]Present Modality:"+((ModalityItemController) modalityList.getChildren().get(i)).getModalityName().getText());
                    //System.out.println("[DEL]New Modality:"+selectedProject.getModalities().get(j).getFullName());
                    found = true;
                }
            }
            //System.out.println("found:"+found);
            if (!found) {
                //System.out.println("Modality is NOT present:"+((ModalityItemController) modalityList.getChildren().get(i)).getModalityName().getText());
                //log.debug("Removing modality:" + i);
                try {
                    ModalityItemController mic = (ModalityItemController) modalityList.getChildren().get(i);
                    closeModality(mic);
                    Platform.runLater(() -> {
                        modalityList.getChildren().remove(mic);
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        Platform.runLater(() -> {
            this.updateProgress.setProgress(60);
        });
        
        //Fill participants now
        this.fillParticipants();
        
        //log.debug("current participants:"+selectedProject.getParticipants()+"");
//        int participants = (selectedProject.getParticipants() == null) ? 0 : selectedProject.getParticipants().size();
//        //log.debug("current participants:"+participants+"");
//        //log.debug(this.updateProgress.getProgress()+"-");
//        Platform.runLater(() -> {
//            participantCount.setText(participants + "/" + selectedProject.getNumber_participants() + " Participants");
//            if (participants >= selectedProject.getNumber_participants()) {
//                addParticipantButton.setDisable(true);
//            }
//        });
        Platform.runLater(() -> {
            this.updateProgress.setProgress(80);
        });
        //log.debug("processing participant list now...");
        

        Platform.runLater(() -> {
            if (selectedProject.getTasks() != null && selectedProject.getTasks().size() > 0) {
                taskCount.setText(selectedProject.getTasks().size() + " Tasks found");
            } else {
                taskCount.setText("No tasks created yet");
            }
        });

        Platform.runLater(() -> {
            this.updateProgress.setProgress(100);
        });
    }

    @FXML
    void openNewTaskWindow(ActionEvent event) {
        final Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;

        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            //Have to run this in a separate thread to avoid blocking the UI
            final Task<Void> task = new Task<Void>() {
                @Override
                public Void call() throws Exception {
                    try {
                        desktop.browse(new URI(RemoteLeanUxServer.REMOTE_SERVER_HOST + "/projects/project_details/" + selectedProject.getId() + "/"));
                    } catch (URISyntaxException | IOException ex) {
                        log.error(ex.getMessage(), ex);
                    }
                    return null;
                }
            };

            final Thread thread = new Thread(task);
            thread.setName("NewTaskWindow");
            thread.setDaemon(true);
            thread.start();

        } else {
            log.error("Browse action not supported");
        }
    }
}
