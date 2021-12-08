package com.uclab.leanuxplatform.controllers.screens;

import com.uclab.leanuxplatform.controllers.screens.customcontrols.ProjectItemController;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import com.uclab.leanuxplatform.controllers.screens.alerts.AlertController;
import com.uclab.leanuxplatform.controllers.screens.utils.LeanUxScreens;
import com.uclab.leanuxplatform.models.Project;
//import com.uclab.leanuxplatform.services.FaceDetectionService;
import com.uclab.leanuxplatform.services.LoginService;
import com.uclab.leanuxplatform.services.ProjectService;
import com.uclab.leanuxplatform.services.util.RemoteLeanUxServer;
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
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class MainController extends BaseScreenController implements Initializable {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProjectService ps;
    @Autowired
    private LoginService ls;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private VBox listProjects;

    @FXML
    private Label projectCount;

    private boolean isNewProjectBoxPrinted = false;

    @FXML
    void handleAboutMenu(ActionEvent event) {
        this.sc.loadWindow(getClass().getResource(LeanUxScreens.ABOUT), "About Me", null);
    }

    @FXML
    void handleAddParticipantMenu(ActionEvent event) {

    }

    @FXML
    void handleMenuClose(ActionEvent event) {

    }

    @FXML
    void handleSettingMenu(ActionEvent event) {

    }

    @Autowired
    ApplicationContext context;
    @Autowired
    AutowireCapableBeanFactory factory;
    // Scheduled thread for updating the project list
    private ScheduledExecutorService projectListUpdateService;
    private boolean autoUpdateProjectList = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.screenId = "Dashboard";
        initDrawer();

        //ps.getAllForUser(ls.getThisUser());
        // Maybe this should be a user controlled setting?
        autoUpdateProjectList = true;

        // setup a scheduled executor to periodically update the project list
        projectListUpdateService = Executors.newSingleThreadScheduledExecutor();

        projectListUpdateService.scheduleAtFixedRate(() -> {

            updateProjectList();
        }, 0, 5, TimeUnit.SECONDS);
        
        
        this.sc.getCurrentStage().setOnCloseRequest((WindowEvent event) -> {
            System.out.println("Close the main screen");
            Alert closeConfirmation = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Would you like to exit?"
            );
            Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(
                    ButtonType.OK
            );
            
            exitButton.setText("Exit");
            closeConfirmation.setHeaderText("Confirm Exit");
            closeConfirmation.initModality(Modality.APPLICATION_MODAL);
            closeConfirmation.initOwner(this.sc.getCurrentStage());
  
            Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
            if (!ButtonType.OK.equals(closeResponse.get())) {
                log.debug("Button Clicked");
                event.consume();
            } else {
                this.sc.getCurrentStage().close();
            }
        });

    }
    
    private void logout(){
        try {
            Platform.runLater(() -> {
                listProjects.getChildren().clear();
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }   //Stop updating the charts
        if (!projectListUpdateService.isShutdown()) {
            projectListUpdateService.shutdown();
        }
        //this.sc.getCurrentStage().close();
        ls.logout();
        System.out.println("Loading login screen");
        this.sc.loadScreen(LeanUxScreens.LOGIN);
        
    }
    
    
    private void initDrawer() {
        VBox toolbar = (VBox) this.sc.load(LeanUxScreens.TOOLBAR);
        drawer.setSidePane(toolbar);
        HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
        task.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
            drawer.toggle();
        });
        drawer.setOnDrawerOpening((event) -> {
            task.setRate(task.getRate() * -1);
            task.play();
            drawer.toFront();
        });
        drawer.setOnDrawerClosed((event) -> {
            drawer.toBack();
            task.setRate(task.getRate() * -1);
            task.play();
        });

    }

    private void updateProjectList() {
        //System.out.println(ls.getThisUser());
        //log.debug("User not logged in...");
        if (ls.getThisUser() == null) {
            Platform.runLater(() -> {
                listProjects.getChildren().clear();
                //this.sc.getCurrentStage().fireEvent(new WindowEvent(this.sc.getCurrentStage(), WindowEvent.WINDOW_CLOSE_REQUEST));
            });
            projectListUpdateService.shutdown();
            return;
        }
        //Don't update till the first process is compelte
        if (!autoUpdateProjectList) {
            log.debug("initializing project list...");
            return;
        }

        if(!ps.getAllForUser(ls.getThisUser())){
            AlertController.error(ps.getErrorMessage());
        }

        if (ls.getThisUser().getOwnedProjects() == null || ls.getThisUser().getOwnedProjects().size() < 1) {
            // Clear the list of projects first.
            if (listProjects.getChildren().size() > 0) {
                Platform.runLater(() -> listProjects.getChildren().clear());
            }
            Platform.runLater(() -> {
                projectCount.setText("You don't have any projects yet." + ls.getThisUser().getOwnedProjects().size());
            });

            //Now the let the user know that there is no project
        } else {
            ls.getThisUser().getOwnedProjects().forEach(new Consumer<Project>() {
                @Override
                public void accept(Project project) {
                    try {
                        if (project.getId() != 0 && isNotPresent(project)) {
                            ProjectItemController pic = (ProjectItemController) context.getBean("projectItemController");
                            //Autowiring the beans inside the ProjectItemController
                            factory.autowireBean(pic);

                            pic.setProjectId(project.getId());
                            pic.setProjectTitle(project.getTitle());
                            pic.setProjectListUpdateService(projectListUpdateService);
                            log.debug("updating project list with:" + project.getTitle());
                            Platform.runLater(() -> {
                                listProjects.getChildren().add(pic);
                                projectCount.setText("Found " + ls.getThisUser().getOwnedProjects().size() + " projects.");
                            });
                        }
                    } catch (BeansException e) {
                        log.error(e.getMessage(), e);
                    }
                }

                private boolean isNotPresent(Project project) {
                    for (int i = 0; i < listProjects.getChildren().size(); i++) {
                        if (((ProjectItemController) listProjects.getChildren().get(i)).getProjectId() == project.getId()) {
                            return false;
                        }
                    }
                    return true;
                }
            });
        }

    }

    @FXML
    void openNewProjectWindow(ActionEvent event) {

        final Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;

        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            //Have to run this in a separate thread to avoid blocking the UI
            final Task<Void> task = new Task<Void>() {
                @Override
                public Void call() throws Exception {
                    try {
                        desktop.browse(new URI(RemoteLeanUxServer.REMOTE_SERVER_HOST + "/projects/new_project/"));
                    } catch (URISyntaxException | IOException ex) {
                        log.error(ex.getMessage(), ex);
                    }
                    return null;
                }
            };

            final Thread thread = new Thread(task);
            thread.setName("new_project_window");
            thread.setDaemon(true);
            thread.start();

        } else {
            log.error("Browse action not supported");
        }
    }

}
