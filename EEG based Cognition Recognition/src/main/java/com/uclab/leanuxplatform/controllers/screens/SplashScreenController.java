
package com.uclab.leanuxplatform.controllers.screens;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import org.springframework.stereotype.Component;


/**
 *
 * @author Jamil Hussain
 */
@Component
public class SplashScreenController implements Initializable {
    
    @FXML
    private Label progress;
    
    public static Label label;
    
    
    @FXML
    private ProgressBar progressBar;
    
    public static ProgressBar statProgressBar;
    
    /*@FXML 
    private ImageView imageView; */
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
       
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       label = progress ;
       statProgressBar = progressBar;

    }    
    
}
