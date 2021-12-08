/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.controllers.screens;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.springframework.stereotype.Component;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.uclab.leanuxplatform.controllers.screens.alerts.AlertController;
import com.uclab.leanuxplatform.models.Participant;
import com.uclab.leanuxplatform.services.LoginService;
import com.uclab.leanuxplatform.services.ProjectService;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * FXML Controller class
 *
 * @author Jamil
 */
@Component
public class AddParticipantController extends BaseScreenController  implements Initializable  {
    
    @Autowired
    LoginService ls;
    @Autowired
    ProjectService ps;
    
    @FXML
    private StackPane rootPane;

    @FXML
    private AnchorPane mainContainer;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField age;




    @FXML
    private JFXButton cancelButton;
    
    
      @FXML
    private JFXComboBox<Label> gendercombo;
      

    @FXML
    void addParticipantHandler(ActionEvent event) {
        String mName = name.getText();
        String mAge = age.getText();
        
        
        String mGender= gendercombo.getValue().getText().toString();
       
        //System.out.println(mGender);
        
        Boolean flag = mName.isEmpty() || mAge.isEmpty() ;
        if (flag) {
            AlertController.showMaterialDialog(rootPane, mainContainer, new ArrayList<>(), "Insufficient Data", "Please enter data in all fields.");
            return;
        } else
        {
            // add participant code goes here....
            Participant p = new Participant();
            p.setProject(ls.getThisUser().getSelectedProject());
            p.setName(mName);
            p.setAge(Integer.parseInt(mAge));
            p.setGender(mGender);
            if(!ps.postNewParticipant(p)){
                AlertController.error(ps.getErrorMessage());
                return;
            }
            ls.getThisUser().getSelectedProjectObject().getParticipants().add(p);
            //System.out.println("new participant:"+p);
            
            resetFields();
            Stage stage = (Stage) name.getScene().getWindow();
            stage.close();
        }
        

    }
   
    @FXML
    private void cancelHandler(ActionEvent event) {
        Stage stage = (Stage) name.getScene().getWindow();
        stage.close();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        gendercombo.getItems().add(new Label("male"));
        gendercombo.getItems().add(new Label("female"));
        gendercombo.setConverter(new StringConverter<Label>() {
            @Override
            public String toString(Label object) {
                return object==null? "" : object.getText();
            }

            @Override
            public Label fromString(String string) {
                return new Label(string);
            }
        });
         
       
    }    
    
    
    
    public void resetFields() {
        name.setText("");
        age.setText("");       
    }
    
    
    
}
