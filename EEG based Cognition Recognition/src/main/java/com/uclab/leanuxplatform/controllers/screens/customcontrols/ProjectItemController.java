/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.controllers.screens.customcontrols;

import com.uclab.leanuxplatform.controllers.screens.ScreensController;
import com.uclab.leanuxplatform.controllers.screens.utils.LeanUxScreens;
import com.uclab.leanuxplatform.services.LoginService;
import java.io.IOException;
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
 * @author Fahad Ahmed Satti
 */
@Component
@Configurable(preConstruction = true, autowire = Autowire.BY_TYPE, dependencyCheck = false)
public class ProjectItemController extends HBox {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    LoginService ls;
    
    @Autowired
    protected ScreensController sc;
    
    @FXML
    private Label projectTitle;
    private int projectId; 
    @FXML
    private Button btnEvaluate;
    // The thread updating the project list. This should be stopped after a project
    // has been selected to reduce load.
    private ScheduledExecutorService projectListUpdateService;
    
    
    @FXML
    void handleProjectEvent(ActionEvent event) throws IOException {

        if (event.getSource() == btnEvaluate) {
            //Stop updating the project list now.
            projectListUpdateService.shutdown();
            
//            log.debug("user:"+ls.getThisUser());
            ls.getThisUser().setSelectedProject(projectId);
//            log.debug("project ID:"+projectId);

            Stage stage = new Stage();
            this.sc.init(stage);
            sc.loadScreen(LeanUxScreens.PROJECT_DETAILS);
        }

    }

    public ProjectItemController() {
        super();
        FXMLLoader loader = new FXMLLoader( getClass().getResource(LeanUxScreens.PROJECT_ITEM) );

        loader.setRoot( this );
        loader.setController( this );

        try {
            loader.load();
        } catch ( IOException e ) {
            throw new RuntimeException( e );
        }
    }


    public Label getProjectTitle() {
        return this.projectTitle;
    }

    public void setProjectTitle(Label projectTitle) {
        this.projectTitle = projectTitle;
    }
    
    public void setProjectTitle(String projectTitle) {
        this.projectTitle.setText(projectTitle);
    }
    
    public int getProjectId() {
        return this.projectId;
    }

    public void setProjectId(int pId) {
        this.projectId = pId;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.projectTitle);
        hash = 17 * hash + this.projectId;
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
        final ProjectItemController other = (ProjectItemController) obj;
        if (this.projectId != other.projectId) {
            return false;
        }
        if (!Objects.equals(this.projectTitle, other.projectTitle)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProjectItemController{" + "projectTitle=" + projectTitle + ", projectId=" + projectId + '}';
    }

    public void setProjectListUpdateService(ScheduledExecutorService projectListUpdateService) {
        this.projectListUpdateService = projectListUpdateService;
    }


    
}
