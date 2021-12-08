/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.controllers.screens.utils;

import com.jfoenix.controls.JFXTextField;
import com.uclab.leanuxplatform.models.Question;
import com.uclab.leanuxplatform.models.Survey;
import com.uclab.leanuxplatform.models.constants.QuestionType;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author uclab351
 */
@Component
public class DynamicSurvey {
    private Survey currentSurvey;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private boolean hasQuestions = false;
    private Map<Integer, String> userResponse = Collections.synchronizedMap(new HashMap());
    
    public Node displaySurveyQuestions(Survey currentSurvey) {
        if(currentSurvey==null || currentSurvey.getQuestions()==null ||
                currentSurvey.getQuestions().size()<1){
            Label message = new Label("There are no questions in this survey. You can press next, when ready!");
            return message;
        }else{
            this.currentSurvey = currentSurvey;
            this.hasQuestions = true;
            VBox surveyHolder = new VBox();
            Iterator<Question> questionIterator = this.currentSurvey.getQuestions().iterator();
            while(questionIterator.hasNext()){
                log.debug("Got a question");
                // Got a new question
                Question quest = questionIterator.next();
                log.debug(quest.toString());
                //Creating an empty HBox holder
                VBox questionHolder = new VBox();
                questionHolder.setSpacing(10); 
                Label questionText;
               
                String baseStyle = "-fx-padding: 10;" + 
                  "-fx-border-style: solid inside;" + 
                  "-fx-border-width: 1;" +
                  "-fx-border-insets: 2;" + 
                  "-fx-border-radius: 2;";
                if(quest.getRequired()){
                    questionHolder.setStyle(baseStyle+"-fx-border-color: #FFFF8D;");
                     questionText = new Label("* "+quest.getTitle());
                     questionText.setTextFill(Color.WHITE);
                }else{
                    questionHolder.setStyle(baseStyle+"-fx-border-color: #FFFF8D;");
                    questionText = new Label(quest.getTitle());
                     questionText.setTextFill(Color.WHITE);
                }
                questionText.getStyleClass().add(".label");
                Label questionId = new Label(quest.getId()+"");
                questionId.setVisible(false);
                questionHolder.getChildren().add(questionId);
                questionHolder.getChildren().add(questionText);
                log.debug("question type:"+quest.getQuestion_type());
                if(quest.getQuestion_type().equals(QuestionType.TEXT)){
                    
                    JFXTextField answer = new JFXTextField();
                    answer.setStyle("-fx-border-color:white;");
//                    answer.setStyle("-fx-text-inner-color: #000000;");
//                    answer.getStyleClass().add(".quest-text-field");
                    //answer.setId("a_"+quest.getId());
                    answer.focusedProperty().addListener(new ChangeListener<Boolean>(){
                        @Override
                        public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
                            if (!newPropertyValue && answer.getText() != null)
                            {
                                //int id = Integer.parseInt(answer.getId().substring(answer.getId().indexOf("a_")+2));
                                log.debug("Adding User Response:"+answer.getText());
                                userResponse.put(quest.getId(), answer.getText());
                            }
                        }
                    });
                    questionHolder.getChildren().add(answer);
                }else if(quest.getQuestion_type().equals(QuestionType.ESSAY)){
                    TextArea answer = new TextArea();
                    answer.setStyle("-fx-text-inner-color: #000000;");
                   
                    //answer.setId("a_"+quest.getId());
                    answer.focusedProperty().addListener(new ChangeListener<Boolean>(){
                        @Override
                        public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
                            if (!newPropertyValue && answer.getText() != null && !answer.getText().isEmpty())
                            {
                                //int id = Integer.parseInt(answer.getId().substring(answer.getId().indexOf("a_")+2));
                                log.debug("Adding User Response:"+answer.getText());
                                userResponse.put(quest.getId(), answer.getText());
                            }
                        }
                    });
                    questionHolder.getChildren().add(answer);
                }
                else if(quest.getQuestion_type().equals(QuestionType.RADIO_ONE)){
                    
                    HBox choiceHolder = new HBox();
                    choiceHolder.setSpacing(10);
                    
                    ToggleGroup answerGroup = new ToggleGroup();
                    String[] options = quest.getChoices().split(",");
                    for(int i=0; i<options.length;i++){
                        RadioButton rb = new RadioButton(options[i]);
                        //rb.setId("a_"+quest.getId());
                        rb.setToggleGroup(answerGroup);
                        rb.setTextFill(Color.WHITE);
                        choiceHolder.getChildren().add(rb);
                    }
                    answerGroup.selectedToggleProperty().addListener(
                    (ObservableValue<? extends Toggle> ob, Toggle o, Toggle n) -> {
                        RadioButton rb1 = (RadioButton) answerGroup.getSelectedToggle();
                        if (rb1 != null) {
                            //int id = Integer.parseInt(rb1.getId().substring(rb1.getId().indexOf("a_")+2));
                            //log.debug("Adding User Response:"+id+"->"+rb1.getText());
                            log.debug("Adding User Response:"+rb1.getText());
                                userResponse.put(quest.getId(), rb1.getText());
                        } else {
                            
                        }
                    });
                    
                    questionHolder.getChildren().add(choiceHolder);
                }else if(quest.getQuestion_type().equals(QuestionType.RADIO_MULTIPLE)){
                    
                    HBox choiceHolder = new HBox();
                    String[] options = quest.getChoices().split(",");
                    for(int i=0; i<options.length;i++){
                        CheckBox cb = new CheckBox (options[i]);
                        cb.setTextFill(Color.WHITE);
                        cb.selectedProperty().addListener((observable,oldValue, selectedNow)->{
                            if(selectedNow){
                                if(userResponse.containsKey(quest.getId())){
                                    log.debug("Updating User Response[radioMultiple]:"+cb.getText());
                                    userResponse.put(quest.getId(), userResponse.get(quest.getId())+","+cb.getText());
                                }else{
                                    log.debug("Adding User Response[radioMultiple]:"+cb.getText());
                                    userResponse.put(quest.getId(), cb.getText());
                                }
                            }else{
                                if(userResponse.containsKey(quest.getId())){
                                    log.debug("Removing User Response[radioMultiple]:"+cb.getText());
                                    userResponse.put(quest.getId(), userResponse.get(quest.getId()).replace(","+cb.getText(), ""));
                                }
                            }
                        });
                        choiceHolder.getChildren().add(cb);
                    }
                    questionHolder.getChildren().add(choiceHolder);
                }
                log.debug("Adding questionholder in the question box");
                surveyHolder.getChildren().add(questionHolder);
            }
            return surveyHolder;
        }

    }
    
    public String getUserResponse(Integer id) {
        if(userResponse.containsKey(id)){
            return userResponse.get(id);
        }else{
            return null;
        }
    }
    
    public void setUserResponse(Map<Integer, String> userResponse) {
        this.userResponse = userResponse;
    }

    public Survey getCurrentSurvey() {
        return currentSurvey;
    }

    public void setCurrentSurvey(Survey currentSurvey) {
        this.currentSurvey = currentSurvey;
    }

    public boolean hasQuestions() {
        return hasQuestions;
    }

    public void setHasQuestions(boolean hasQuestions) {
        this.hasQuestions = hasQuestions;
    }

    public Boolean hasUserResponse(int id) {
        System.out.println(userResponse);
        return userResponse.containsKey(id);
    }
}
