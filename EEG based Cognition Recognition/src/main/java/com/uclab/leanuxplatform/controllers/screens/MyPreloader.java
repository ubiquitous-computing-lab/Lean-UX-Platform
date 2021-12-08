package com.uclab.leanuxplatform.controllers.screens;

import com.uclab.leanuxplatform.controllers.screens.utils.LeanUxScreens;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MyPreloader extends Preloader {

    private Stage preloaderStage;
    private Scene scene;

    public MyPreloader() {

    }

    @Override
    public void init() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(LeanUxScreens.SPLASH_SCREEN));
        Parent root1 = loader.load();
        scene = new Scene(root1);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.preloaderStage = primaryStage;
        preloaderStage.setScene(scene);
        preloaderStage.initStyle(StageStyle.UNDECORATED);
        preloaderStage.show();

    }

    @Override
    public void handleApplicationNotification(Preloader.PreloaderNotification info) {

        if (info instanceof ProgressNotification) {
            SplashScreenController.label.setText("Loading " + ((ProgressNotification) info).getProgress() * 100 + "%");
            //System.out.println("Value@ :" + ((ProgressNotification) info).getProgress());
            SplashScreenController.statProgressBar.setProgress(((ProgressNotification) info).getProgress());
        }

    }

    @Override
    public void handleStateChangeNotification(Preloader.StateChangeNotification info) {

        StateChangeNotification.Type type = info.getType();
        switch (type) {

            case BEFORE_START:
                // Called after MyApplication#init and before MyApplication#start is called.
                preloaderStage.hide();
                break;
        }

    }
}
