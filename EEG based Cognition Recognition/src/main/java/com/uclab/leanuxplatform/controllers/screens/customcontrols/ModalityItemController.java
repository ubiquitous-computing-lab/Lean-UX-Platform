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
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;

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
public class ModalityItemController extends VBox {
    private static final Logger logger = Logger.getLogger(ProjectItemController.class.getName());
    
    @Autowired
    LoginService ls;
    
    @Autowired
    protected ScreensController sc;
    
    @FXML
    private Label modalityName;
    
    @FXML
    private Label modalityStatus;
   
    @FXML
    private FontAwesomeIcon iconName;
    
    private boolean markRemoved=true;
    private String modalityShortName;
    
    
    public ModalityItemController() {
        super();
        FXMLLoader loader = new FXMLLoader( getClass().getResource(LeanUxScreens.MODALITY_ITEM) );

        loader.setRoot( this );
        loader.setController( this );

        try {
            loader.load();
        } catch ( IOException e ) {
            throw new RuntimeException( e );
        }
    }

    public Label getModalityName() {
        return modalityName;
    }

    public void setModalityName(Label modalityName) {
        this.modalityName = modalityName;
    }
    
    public void setModalityName(String modalityName) {
        this.modalityName.setText(modalityName);
    }
    
    public Label getModalityStatus() {
        return modalityStatus;
    }

    public void setModalityStatus(Label modalityStatus) {
        this.modalityStatus = modalityStatus;
    }
    
    public void setModalityStatus(String modalityStatus) {
        this.modalityStatus.setText(modalityStatus);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.modalityName);
        hash = 71 * hash + Objects.hashCode(this.modalityStatus);
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
        final ModalityItemController other = (ModalityItemController) obj;
        if (!Objects.equals(this.modalityName, other.modalityName)) {
            return false;
        }
        if (!Objects.equals(this.modalityStatus, other.modalityStatus)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ModalityItemController{" + "modalityName=" + modalityName + ", modalityStatus=" + modalityStatus + '}';
    }

    public FontAwesomeIcon getIconName() {
        return iconName;
    }

    public void setIconName(FontAwesomeIcon iconName) {
        this.iconName.setIconName(iconName.getIconName());
        //this.iconName.setText(iconName.getText());
    }

    public boolean isMarkRemoved() {
        return markRemoved;
    }

    public void setMarkRemoved(boolean markRemoved) {
        this.markRemoved = markRemoved;
    }

    public String getModalityShortName() {
        return modalityShortName;
    }

    public void setModalityShortName(String modalityShortName) {
        this.modalityShortName = modalityShortName;
    }

    

    
}
