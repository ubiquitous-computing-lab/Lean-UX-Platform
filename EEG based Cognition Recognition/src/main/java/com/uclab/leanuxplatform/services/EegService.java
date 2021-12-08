/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.services;

import com.emotiv.iedk.Edk;
import com.emotiv.iedk.EdkErrorCode;
import com.emotiv.iedk.EmoState;
import com.emotiv.iedk.PerformanceMetrics;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.DoubleByReference;
import com.sun.jna.ptr.IntByReference;
import com.uclab.leanuxplatform.models.EegData;
import com.uclab.leanuxplatform.models.constants.CognitionLabel;
import com.uclab.leanuxplatform.services.couch.RemoteCouchDbServer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Fahad Ahmed Satti
 */
@Service
public class EegService {

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    RemoteCouchDbServer couchController;

    final long NANOSEC_PER_SEC = 1000L * 1000 * 1000;
    // 60 seconds run time
    final long RUN_TIME = 60 * NANOSEC_PER_SEC;

    float timeStamp = 0;
    int errorCode;
    int state;
    int eventType;
    
    IntByReference batteryLevel = new IntByReference(0);
    IntByReference maxBatteryLevel = new IntByReference(0);

    private List<EegData> eegDataList = new ArrayList<>();
    //HashMap<String, Float> scores;
    boolean isUserAdded = false;
    Pointer ptrEnginEvent;
    Pointer ptrEngineState;
    IntByReference ptrUserID;
    private Thread _eegThread = null;
    private int participant;
    private int project;
    private int task;
    private boolean isDeviceReady = false;
    private boolean isCollectingData = false;

    public EegService() {
        try {
            _eegThread = new Thread(_emotivEegRunnable);
        } catch (Throwable e) {
            System.out.println("Error in EEG");
            e.printStackTrace();
        }
    }

    public boolean init() {
        try {
            this.errorCode = Edk.INSTANCE.IEE_EngineConnect("Emotiv Systems-5");
            ptrEnginEvent = Edk.INSTANCE.IEE_EmoEngineEventCreate();
            ptrEngineState = Edk.INSTANCE.IEE_EmoStateCreate();
            ptrUserID = new IntByReference();
            this.eegDataList = Collections.synchronizedList(new ArrayList());
            isDeviceReady = true;
        } catch (Throwable e) {
            System.out.println("Error in Body Language");
            e.printStackTrace();
        }
        return isDeviceReady;
    }

    public void beginProcessing() {
        //System.out.println("Performance Metrics Example");
//        if (_eegThread != null) {
//            log.debug("EEG Thread != null");
//            this.stopProcessing();
//        }
        if (!isDeviceReady) {
            init();
        }

        if (this.eegDataList != null) {
            this.eegDataList.clear();
        } else {
            this.eegDataList = Collections.synchronizedList(new ArrayList());
        }
        try {
            _eegThread = new Thread(_emotivEegRunnable);
            log.debug("EEG Service begin processing");
            couchController.preventFurtherUpload(false);
            isCollectingData = true;
            _eegThread.setName("EEG-Client");
            _eegThread.start();
        } catch (Throwable e) {
            System.out.println("Error in EEG Thread begin processing");
            e.printStackTrace();
        }
    }

    public void stopProcessing() {
        if (_eegThread != null) {
            try {
                isDeviceReady = false;
                this.eegDataList.clear();
                isCollectingData = false;
                log.debug("EEG Service stop processing");
                couchController.preventFurtherUpload(true);
                Edk.INSTANCE.IEE_EngineDisconnect();
                //log.debug("Upload condition:"+couchController.getUploader().isAllowUpload());
                //_eegThread.interrupt();
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
            _eegThread = null;
        }

    }
    private final Runnable _emotivEegRunnable = new Runnable() {
        @Override
        public void run() {
            log.debug("Start of EEG Thread:" + isIsCollectingData());
            //RemoteCouchDbServer couchController = new RemoteCouchDbServer();
            while (isIsCollectingData()) {
                try {
                    Thread.sleep(3000);
                    state = Edk.INSTANCE.IEE_EngineGetNextEvent(ptrEnginEvent);
                    if (state == EdkErrorCode.EDK_OK.ToInt()) {
                        eventType = Edk.INSTANCE.IEE_EmoEngineEventGetType(ptrEnginEvent);
                        errorCode = Edk.INSTANCE.IEE_EmoEngineEventGetUserId(ptrEnginEvent, ptrUserID);

                        float timestamp = EmoState.INSTANCE.IS_GetTimeFromStart(ptrEngineState);
                        System.out.print(timestamp + ", ");

                        System.out.print(EmoState.INSTANCE.IS_GetWirelessSignalStatus(ptrEngineState) + ", ");

                        EmoState.INSTANCE.IS_GetBatteryChargeLevel(ptrEngineState, batteryLevel, maxBatteryLevel);
                        System.out.print(batteryLevel.getValue() + ", ");

                        if (eventType == Edk.IEE_Event_t.IEE_EmoStateUpdated.ToInt()) {
                            timeStamp = EmoState.INSTANCE.IS_GetTimeFromStart(ptrEngineState);
                            //System.out.format("Time stamp (s): %7.3f --- ", timeStamp);
                            errorCode = Edk.INSTANCE.IEE_EmoEngineEventGetEmoState(ptrEnginEvent, ptrEngineState);
                            // Add EEG data into global list. Should be careful that this is not updated by
                            // multiple threads concurrently.
                            EegData temp = getScaledPerformanceMetricsScore(ptrEngineState);
                            //log.debug(temp.toString());
                            if (couchController.init()) {
                                couchController.uploadEeg(project, participant,
                                        task, temp.getCognitionByCognitionLabel(CognitionLabel.INTEREST).getData().doubleValue(),
                                        temp.getCognitionByCognitionLabel(CognitionLabel.STRESS).getData().doubleValue(),
                                        temp.getCognitionByCognitionLabel(CognitionLabel.RELAXATION).getData().doubleValue(),
                                        temp.getCognitionByCognitionLabel(CognitionLabel.EXCITEMENT).getData().doubleValue(),
                                        temp.getCognitionByCognitionLabel(CognitionLabel.FOCUS).getData().doubleValue(),
                                        temp);

                                getEegDataList().add(temp);
                            }
                        } else if (eventType == Edk.IEE_Event_t.IEE_UserRemoved.ToInt()) {
                            log.debug("User Removed:" + participant);
                            break;
                        }

                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(EegService.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Throwable e) {
                    System.out.println("Error in EEG Thread");
                    e.printStackTrace();
                }
            }
            //couchController.close();
            log.debug("Disconnect Step 0!");

            Edk.INSTANCE.IEE_EngineDisconnect();
            log.debug("Disconnect Step 1!");
            Edk.INSTANCE.IEE_EmoStateFree(ptrEngineState);
            log.debug("Disconnect Step 2!");
            Edk.INSTANCE.IEE_EmoEngineEventFree(ptrEnginEvent);
            log.debug("Disconnect Step 3!");
            log.debug("Stopping EEG Thread");
        }

        // Get calculated PM score from scaled PM scores
        private double getCalculatedScore(DoubleByReference raw, DoubleByReference min, DoubleByReference max) {
            double score;//raw.getValue();
            //log.debug("Performance Metrics: raw " + raw.getValue() + ", max " + max.getValue() + ", min " + min.getValue());
            if (raw.getValue() < min.getValue()) {
                score = 0;
            } else if (raw.getValue() > max.getValue()) {
                score = 1;
            } else if (max.getValue() != min.getValue()) {
                score = (float) ((raw.getValue() - min.getValue()) / (max.getValue() - min.getValue()));
            } else {
                score = 0;
            }
            return score;
        }

        // Get calculated PM score
        private EegData getScaledPerformanceMetricsScore(Pointer eState) {
            EegData stagingEegData = new EegData();
            DoubleByReference ptrRawScore = new DoubleByReference(0);
            DoubleByReference ptrMinScale = new DoubleByReference(0);
            DoubleByReference ptrMaxScale = new DoubleByReference(0);
            PerformanceMetrics.INSTANCE.IS_PerformanceMetricGetStressModelParams(eState, ptrRawScore, ptrMinScale, ptrMaxScale);
            stagingEegData.addCognitionDataItem(CognitionLabel.STRESS, getCalculatedScore(ptrRawScore, ptrMinScale, ptrMaxScale));

            PerformanceMetrics.INSTANCE.IS_PerformanceMetricGetEngagementBoredomModelParams(eState, ptrRawScore, ptrMinScale, ptrMaxScale);
            stagingEegData.addCognitionDataItem(CognitionLabel.FOCUS, getCalculatedScore(ptrRawScore, ptrMinScale, ptrMaxScale));

            PerformanceMetrics.INSTANCE.IS_PerformanceMetricGetRelaxationModelParams(eState, ptrRawScore, ptrMinScale, ptrMaxScale);
            stagingEegData.addCognitionDataItem(CognitionLabel.RELAXATION, getCalculatedScore(ptrRawScore, ptrMinScale, ptrMaxScale));

            PerformanceMetrics.INSTANCE.IS_PerformanceMetricGetInstantaneousExcitementModelParams(eState, ptrRawScore, ptrMinScale, ptrMaxScale);
            stagingEegData.addCognitionDataItem(CognitionLabel.EXCITEMENT, getCalculatedScore(ptrRawScore, ptrMinScale, ptrMaxScale));

            PerformanceMetrics.INSTANCE.IS_PerformanceMetricGetInterestModelParams(eState, ptrRawScore, ptrMinScale, ptrMaxScale);
            stagingEegData.addCognitionDataItem(CognitionLabel.INTEREST, getCalculatedScore(ptrRawScore, ptrMinScale, ptrMaxScale));

            stagingEegData.setFinalCognition(stagingEegData.getMaxCognitionByScore());
            return stagingEegData;
        }
    };

    public int getParticipant() {
        return participant;
    }

    public void setParticipant(int participant) {
        this.participant = participant;
    }

    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
    }

    public int getTask() {
        return task;
    }

    public void setTask(int task) {
        this.task = task;
    }

    public boolean isIsCollectingData() {
        return isCollectingData;
    }

    public void setIsCollectingData(boolean isCollectingData) {
        this.isCollectingData = isCollectingData;
    }

    public boolean isIsDeviceReady() {
        return isDeviceReady;
    }

    public void setIsDeviceReady(boolean isDeviceReady) {
        this.isDeviceReady = isDeviceReady;
    }

    public List<EegData> getEegDataList() {
        return eegDataList;
    }

    public boolean isRunning() {
        return isDeviceReady && isCollectingData;
    }

    public double getBatteryLevel() {
        if (isDeviceReady) {
            return batteryLevel.getValue();
        }
        return 0.0;
    }

    public double getSignalLevel() {
        if (isDeviceReady) {
            return EmoState.INSTANCE.IS_GetWirelessSignalStatus(ptrEngineState);
        }
        return 0.0;
    }

}
