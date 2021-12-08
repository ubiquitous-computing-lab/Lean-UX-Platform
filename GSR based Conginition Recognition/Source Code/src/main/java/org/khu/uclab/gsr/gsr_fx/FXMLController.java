package org.khu.uclab.gsr.gsr_fx;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FXMLController implements Initializable {

    GsrService gs;
    
    private final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
    private final PseudoClass infoClass = PseudoClass.getPseudoClass("info");
    
    @FXML
    private Button connect;

    @FXML
    private Label gsrState;

    @FXML
    private Button disconnect;

    @FXML
    private Button stressButton;

    @FXML
    private Button Focused;

    @FXML
    private Button excitedButton;

    @FXML
    private Button relaxButton;

    @FXML
    private Button interestButton;

    @FXML
    private TextField commPort;
    
    @FXML
    private Label samplingRate;

    @FXML
    private Label accelRange;

    @FXML
    private Label gsrRange;

    @FXML
    private LineChart<String, Number> skinConductanceChart;

    @FXML
    private LineChart<String, Number> skinResistanceChart;

    private ScheduledExecutorService scheduledExecutorService;
    final int WINDOW_SIZE = 500;

    private void setUpValidation(final TextField tf) {
        tf.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                validate(tf);
            }

        });

        validate(tf);
    }

    private void validate(TextField tf) {
        ObservableList<String> styleClass = tf.getStyleClass();
        if (tf.getText().trim().length() == 0) {
            tf.pseudoClassStateChanged(errorClass, true);
            tf.pseudoClassStateChanged(infoClass, false);
        } else {
            tf.pseudoClassStateChanged(errorClass, false);
            tf.pseudoClassStateChanged(infoClass, true);
        }

    }

    @FXML
    void connectGsr(ActionEvent event) {
        gsrState.setText("Status: Starting GSR data collection");
        gs.beginProcessing();
    }
    
    @FXML
    void disconnectGsr(ActionEvent event) {
        gsrState.setText("Status: Stopping GSR data collection");
        gs.stopProcessing();
        gsrState.setText("Status: Data collection has been stopped");
    }

    @FXML
    void excite(ActionEvent event) {
        gs.setUserLabel(CognitionLabel.EXCITEMENT.getKey());

    }

    @FXML
    void focus(ActionEvent event) {
        gs.setUserLabel(CognitionLabel.FOCUS.getKey());
    }

    @FXML
    void interest(ActionEvent event) {
        gs.setUserLabel(CognitionLabel.INTEREST.getKey());
    }

    @FXML
    void relax(ActionEvent event) {
        gs.setUserLabel(CognitionLabel.RELAXATION.getKey());
    }

    @FXML
    void stress(ActionEvent event) {
        gs.setUserLabel(CognitionLabel.STRESS.getKey());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gs = new GsrService();
        setUpValidation(commPort);
        commPort.setText(gs.getComPort());
        
        samplingRate.setText("Sampling Rate: "+gs.getSAMPLING_RATE_STRING());
        accelRange.setText("Accel Range: "+gs.getACCEL_RANGE_STRING());
        gsrRange.setText("GSR Range: "+gs.getGSR_RANGE_STRING());
        XYChart.Series<String, Number> series_skinConductance = new XYChart.Series<>();
        series_skinConductance.setName("Skin Conductance");

        XYChart.Series<String, Number> series_skinResistance = new XYChart.Series<>();
        series_skinResistance.setName("Skin Resistance");

        skinConductanceChart.getData().add(series_skinConductance);
        skinResistanceChart.getData().add(series_skinResistance);

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(() -> {

            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

            Platform.runLater(() -> {
                //System.out.println("size:"+gs.getGsrDataList().size());
                if (gs.isKeepRunning() && gs.getGsrDataList().size() > 1) {
                    GsrData gd = gs.getGsrDataList().get(gs.getGsrDataList().size() - 1);
                    series_skinConductance.getData().add(new XYChart.Data<>(simpleDateFormat.format(gd.getTimeStamp()), gd.getValByLabel("GSR_Skin_Conductance-CAL")));

                    series_skinResistance.getData().add(new XYChart.Data<>(simpleDateFormat.format(gd.getTimeStamp()), gd.getValByLabel("GSR_Skin_Resistance-CAL")));
                    System.out.println(simpleDateFormat.format(gd.getTimeStamp()));
                    System.out.println(gd.getValByLabel("GSR_Skin_Resistance-CAL"));

                }

                if (series_skinConductance.getData().size() > WINDOW_SIZE) {
                    series_skinConductance.getData().remove(0);
                }
                if (series_skinResistance.getData().size() > WINDOW_SIZE) {
                    series_skinResistance.getData().remove(0);
                }

            });

        }, 0, 1, TimeUnit.SECONDS);

        gsrState.setText("Status: Initialized");
    }

}
