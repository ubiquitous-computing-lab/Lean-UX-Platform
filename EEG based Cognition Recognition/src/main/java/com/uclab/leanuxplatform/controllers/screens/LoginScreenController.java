package com.uclab.leanuxplatform.controllers.screens;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.uclab.leanuxplatform.controllers.screens.alerts.AlertController;
import com.uclab.leanuxplatform.controllers.screens.utils.LeanUxScreens;
import com.uclab.leanuxplatform.models.User;
import com.uclab.leanuxplatform.services.LoginService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


@Component
public class LoginScreenController extends BaseScreenController implements Initializable {

    @Autowired
    private LoginService ls;
    
    @FXML
    private AnchorPane rootPane;

    @FXML
    private Pane mainContainer;
    
 
    @FXML
    private Label lblErrors;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    private Button btnSignin;

    @FXML
    private Button btnCancel;

    @FXML
    void handleButtonAction(ActionEvent event) throws IOException {

        if (event.getSource() == btnSignin) {
            User guest = new User(txtUsername.getText(), txtPassword.getText());
            if(ls.getLoginResult(guest)){
                AlertController.info("Welcome to LeanUX ("+guest.getEmail()+"). Let's begin!");
                this.sc.setScreenSize(1010, 720).loadScreen(LeanUxScreens.MAIN);
            }else{
                AlertController.showError("Login Error", ls.getErrorMessage());
            }
        }
        if (event.getSource() == btnCancel) {
            this.sc.close();
            Platform.exit();
            System.exit(0);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.screenId = "Login";
    }
}
