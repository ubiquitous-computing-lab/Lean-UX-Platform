/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.controllers.screens.customcontrols;

import com.uclab.leanuxplatform.controllers.screens.MainController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import com.jfoenix.controls.JFXButton;
import com.uclab.leanuxplatform.controllers.screens.BaseScreenController;
import com.uclab.leanuxplatform.controllers.screens.utils.LeanUxScreens;
import com.uclab.leanuxplatform.services.LoginService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;


/**
 * FXML Controller class
 *
 * @author Jamil
 */
@Component
public class ToolbarController extends BaseScreenController implements Initializable {
    
    @Autowired
    private LoginService ls;
    
    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnsetting;

    @FXML
    private JFXButton btnabout;

    @FXML
    private JFXButton btnlogout;

    @FXML
    void handleButtonAction(ActionEvent event) {
        
        if (event.getSource() == btnabout) {

            this.sc.loadWindow(getClass().getResource(LeanUxScreens.ABOUT), "About Me", null);
        }else if (event.getSource() == btnlogout) {
           
           //this.sc.getCurrentStage().close();
           ls.logout();
           System.out.println("Loading login screen");
           this.sc.getCurrentStage().close();
           Stage stage = new Stage();
           this.sc.init(stage);
           this.sc.loadScreen(LeanUxScreens.LOGIN);
//            System.out.println("Close the main screen");
//            ls.logout();
//            this.sc.getCurrentStage().fireEvent(new WindowEvent(this.sc.getCurrentStage(), WindowEvent.WINDOW_CLOSE_REQUEST));
        }

    }

    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setCallback(MainController aThis) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    
}
