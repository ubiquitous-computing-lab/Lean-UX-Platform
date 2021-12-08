/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.controllers.screens;

import com.uclab.leanuxplatform.controllers.screens.utils.FxmlUtils;
import com.uclab.leanuxplatform.scope.ScreenScope;
import com.uclab.leanuxplatform.scope.ViewScreen;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 *
 * @author Fahad Ahmed Satti
 */
@Service
public class ScreensController implements ApplicationContextAware {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private ScreenScope screenscope;

    private ApplicationContext app_context;
    private Stage currentStage;
    private String currentScreenId;
    private ArrayList<Stage> openStages = new ArrayList();
    private final Map<String, BaseScreenController> screens = Collections.synchronizedMap(new HashMap<>());

    public void init(Stage stage) {
        this.currentStage = stage;
       
        this.currentStage.initStyle(StageStyle.DECORATED);
        Group root = new Group();
        this.currentStage.setScene(new Scene(root));
//        this.currentStage.setResizable(false);
        openStages.add(stage);

    }
    
    public Object load(String url){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
            loader.setControllerFactory(new Callback<Class<?>, Object>() {
                @Override
                public Object call(Class<?> aClass) {
                    return app_context.getBean(aClass);
                }
            });
            return loader.load();
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("Failed to load FXML file '%s'", url));
        }
    }
    
    public static Object loadWindow(URL loc, String title, Stage parentStage) {
        Object controller = null;
        try {
            FXMLLoader loader = new FXMLLoader(loc);
            Parent parent = loader.load();
            controller = loader.getController();
            Stage stage = null;
            if (parentStage != null) {
                stage = parentStage;
            } else {
                stage = new Stage(StageStyle.DECORATED);
            }
            
            stage.setScene(new Scene(parent));
            stage.setTitle(title);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return controller;
    }

    public void loadScreen(String fxml) {
        BaseScreenController oldScreenController = this.getCurrentController();
        try {

            Class controllerClass = FxmlUtils.getControllerClass(fxml);
            final BaseScreenController fxmlController = (BaseScreenController) app_context.getBean(controllerClass);

            if (this.screens.get(fxmlController.getScreenId()) == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
                loader.setControllerFactory(new Callback<Class<?>, Object>() {
                    @Override
                    public Object call(Class<?> aClass) {
                        return fxmlController;
                    }
                });
                Parent root = loader.load();
                fxmlController.setRoot(root);
                this.screens.put(fxmlController.getScreenId(), fxmlController);
            }
            log.debug("Old screen id:"+this.currentScreenId);
            this.currentScreenId = fxmlController.getScreenId();
            log.debug("New screen id:"+this.currentScreenId);
            swapScreen(getCurrentController().getRoot());
            if (oldScreenController != null) {
                if (oldScreenController.getClass().isAnnotationPresent(ViewScreen.class)) {
                    this.screens.remove(oldScreenController.getScreenId());
                    this.screenscope.remove(oldScreenController.getScreenId());
                }
            }

        this.openStages.add(this.getCurrentStage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private boolean swapScreen(final Parent root) {
        final Group rootGroup = getScreenRoot();
        final DoubleProperty opacity = rootGroup.opacityProperty();
        if (!isScreenEmpty()) {

            Timeline fade = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                    new KeyFrame(new Duration(250), new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent t) {
                            rootGroup.getChildren().remove(0);

                            rootGroup.getChildren().add(0, root);
                            Timeline fadeIn = new Timeline(
                                    new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                    new KeyFrame(new Duration(350), new KeyValue(opacity, 1.0)));
                            fadeIn.play();
                        }
                    }, new KeyValue(opacity, 0.0)));
            fade.play();
            this.currentStage.setTitle(this.currentScreenId);
            return true;
        } else {
            opacity.set(0.0);
            rootGroup.getChildren().add(0, root);
            Timeline fadeIn = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                    new KeyFrame(new Duration(350), new KeyValue(opacity, 1.0)));
            fadeIn.play();
        }
//        this.currentStage.close();
//        this.currentStage = new Stage(StageStyle.DECORATED);
        log.debug("New Title should be:"+currentScreenId);
        this.currentStage.setTitle(this.currentScreenId);
        if (!this.currentStage.isShowing()) {
            this.currentStage.show();
        }
        return true;
    }
    
    public ScreensController setScreenSize(int width, int height){
        this.currentStage.setMinHeight(height);
        this.currentStage.setMinWidth(width);
        return this;
    }
    
    public BaseScreenController getCurrentController() {
        return screens.get(this.getCurrentScreenId());
    }

    private Group getScreenRoot() {
        return (Group) this.currentStage.getScene().getRoot();
    }

    private boolean isScreenEmpty() {
        return getScreenRoot().getChildren().isEmpty();
    }

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        this.app_context = ac;
    }

    public ScreenScope getScreenscope() {
        return screenscope;
    }

    public Stage getCurrentStage() {
        return currentStage;
    }

    public String getCurrentScreenId() {
        return currentScreenId;
    }

    void close() {
        this.currentStage.close();
        this.openStages.forEach((stage)->{stage.close();});
        this.screens.remove(this.getCurrentController().screenId);
    }

}
