package com.uclab.leanuxplatform;

import com.uclab.leanuxplatform.controllers.screens.MyPreloader;
import com.sun.javafx.application.LauncherImpl;
import com.uclab.leanuxplatform.config.LeanUXContextConfig;
import com.uclab.leanuxplatform.controllers.screens.ScreensController;
import com.uclab.leanuxplatform.controllers.screens.utils.LeanUxScreens;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp extends Application {

    private static final int COUNT_LIMIT = 100;
    
    @Override
    public void start(Stage stage) throws Exception {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(LeanUXContextConfig.class);
        ScreensController bean = context.getBean(ScreensController.class);
        bean.init(stage);

        bean.loadScreen(LeanUxScreens.LOGIN);

    }

    @Override
    public void init() throws Exception {
       // Perform some heavy lifting (i.e. database start, check for application updates, etc. )
        for (int i = 1; i <= COUNT_LIMIT; i++) {
            double progress = (double) i / COUNT_LIMIT;
            //System.out.println("progress: " + progress);
            LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
            Thread.sleep(100);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LauncherImpl.launchApplication(MainApp.class, MyPreloader.class, args);
    }

}
