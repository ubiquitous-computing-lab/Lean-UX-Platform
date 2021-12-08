/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.gsr.gsr_fx;

import com.google.common.collect.Multimap;
import com.shimmerresearch.bluetooth.ShimmerBluetooth.BT_STATE;
import com.shimmerresearch.driver.BasicProcessWithCallBack;
import com.shimmerresearch.driver.CallbackObject;
import com.shimmerresearch.driver.FormatCluster;
import com.shimmerresearch.driver.ObjectCluster;
import com.shimmerresearch.driver.ShimmerMsg;
import com.shimmerresearch.exceptions.ShimmerException;
import com.shimmerresearch.pcDriver.ShimmerPC;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Fahad Ahmed Satti
 */
public class GsrService extends BasicProcessWithCallBack {
    private final static Logger LOG =  LoggerFactory.getLogger(GsrService.class); 
    private ShimmerPC mShimmer;
    private String ComPort;
    private final int _SAMPLING_RATE = 128;             //{"0Hz","10.2Hz","51.2Hz", "102.4Hz", "128Hz", "170.6Hz", "204.8Hz", "256Hz", "512Hz", "1024Hz"}
    private final int _ACCEL_RANGE = 3;               //Valid range setting values for the Shimmer 2 are 0 (+/- 1.5g), 1 (+/- 2g), 2 (+/- 4g) and 3 (+/- 6g). Valid range setting values for the Shimmer 2r are 0 (+/- 1.5g) and 3 (+/- 6g).
    private final int _GSR_RANGE = 4;                   // Numeric value defining the desired gsr range. Valid range settings are 0 (10kOhm to 56kOhm),  1 (56kOhm to 220kOhm), 2 (220kOhm to 680kOhm), 3 (680kOhm to 4.7MOhm) and 4 (Auto Range).
    private final int enabledSensors = ShimmerPC.SENSOR_GSR;
    private final String[] dataLabels = {"System_Timestamp", "GSR_Skin_Resistance", "GSR_Skin_Conductance", "GSR_Range", "User_Label"};
    private final int TIMESTAMP_INDEX = 0;
    private final int USER_LABEL_INDEX = 4;
    //private int DISTANCE_INDEX = 5;
    private final String[] effectiveDataLabels = {"GSR_Skin_Resistance", "GSR_Skin_Conductance"};
    private String userLabel;
    private CsvHandler logFile;
    private boolean _keepRunning = false;
    private List<GsrData> gsrDataList;
    private final int outlier_k = 7;

    
    public GsrService() {
        _keepRunning = true;
        initialize();
        gsrDataList = Collections.synchronizedList(new ArrayList());
    }

    /**
     * initialize the GSR device
     */
    private void initialize() {
        ComPort = "COM5";
        debug("Enabled Sensors=" + enabledSensors);
        mShimmer = new ShimmerPC("LeanUX_GSR", true);// _SAMPLING_RATE, _ACCEL_RANGE, _GSR_RANGE,
        //enabledSensors,
        //magGain, orientation);
        setWaitForData(mShimmer);
        userLabel = CognitionLabel.RELAXATION.getKey();
        logFile = new CsvHandler("output_gsr/gsr_test_" + System.currentTimeMillis() + ".csv");
        logFile.writeData(dataLabels);

        //debug("Shimmer Version:" + mShimmer.getShimmerVerObject().toString());
    }

    public void beginProcessing() {
        if (!mShimmer.isConnected()) {
            mShimmer.connect(ComPort, "");
            debug("Connect Status:" + mShimmer.isConnected());
        } else {
            debug("reconnecting....");
            mShimmer.stopStreaming();
            mShimmer.reconnect();
            debug("Connect Status:" + mShimmer.isConnected());
        }
        _keepRunning = true;

    }

    public void stopProcessing() {
        _keepRunning = false;
        mShimmer.stopStreaming();
        disconnect();
        logFile.closeWriter();

    }

    public void disconnect() {
        try {
            mShimmer.disconnect();
        } catch (ShimmerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void stream() {

        mShimmer.startStreaming();  //??

        String[] enabledSensorSignals = mShimmer.getListofEnabledChannelSignals();
        int numberOfSignals = enabledSensorSignals.length;

        for (int count = 0; count < numberOfSignals; count++) {
            debug("enabled Sensor:" + enabledSensorSignals[count]);
        }

        double deviceSamplingRate = mShimmer.getSamplingRateShimmer();
        debug("Sampling Rate:" + deviceSamplingRate);
        debug("Shimmer Hardware Version:" + mShimmer.getHardwareVersion());
        debug("Shimmer Accel Range:" + mShimmer.getAccelRange());
        debug("Shimmer GSR Range:" + mShimmer.getGSRRange());
        debug("Mag Range:" + mShimmer.getMagRange());

    }

    public void debug(String str) {
        LOG.debug("{" + System.currentTimeMillis() + "} " + str);
    }

    public void logData(ObjectCluster objectCluster) {

        Multimap<String, FormatCluster> props = objectCluster.getPropertyCluster();
        String[] dataLine = new String[dataLabels.length];
        GsrData gsrItem = new GsrData();

        // Get timestamp in one pass
        Collection<FormatCluster> labels = props.get(dataLabels[TIMESTAMP_INDEX]);
        Iterator<FormatCluster> fci = labels.iterator();
        while (fci.hasNext()) {
            FormatCluster sensoryDataItem = fci.next();
            if ("CAL".equals(sensoryDataItem.mFormat)) {
                dataLine[TIMESTAMP_INDEX] = sensoryDataItem.mData + " ";
                gsrItem.setTimeStamp((new BigDecimal(sensoryDataItem.mData)).longValue());
            }
        }

        // Now get the signal data, with labels at index 1, 2, and 3
        for (int i = 1; i < 4; i++) {
            labels = props.get(dataLabels[i]);
            fci = labels.iterator();
            while (fci.hasNext()) {
                FormatCluster sensoryDataItem = fci.next();
                if ("CAL".equals(sensoryDataItem.mFormat)) {
                    dataLine[i] = sensoryDataItem.mData + " ";
                    gsrItem.addItem(dataLabels[i] + "-CAL", sensoryDataItem.mData);
                }
            }
        }

        //Finally get the user label
        dataLine[USER_LABEL_INDEX] = userLabel;
        gsrItem.setUserAssignedLabel(userLabel);

        //debug("gsrItem:"+gsrItem);
        //if (!isOutsideBounds(gsrItem)) {
        if (gsrDataList.size() > (2 * outlier_k) + 1) {
            calculateDistanceFromNeighbours(gsrItem);
            //dataLine[DISTANCE_INDEX] = Double.toString(gsrItem.getDistance());
        } // initial entries are being made so make a basic check for entering the 
        // data values
        else {

        }

        this.gsrDataList.add(gsrItem);
        debug("gsrItem:" + String.join(",", dataLine));
        logFile.writeData(dataLine);
        //}
    }

    /**
     *
     * @param gsrItem the value of gsrItem
     */
    public void calculateDistanceFromNeighbours(GsrData gsrItem) {
        gsrItem.setDistance(Double.POSITIVE_INFINITY);
        for (int i = gsrDataList.size() - 1; i > gsrDataList.size() - (2 * outlier_k) + 1; i--) {
            double[] existingDataVector = new double[effectiveDataLabels.length];
            double[] newDataVector = new double[effectiveDataLabels.length];
            for (int j = 0; j < effectiveDataLabels.length; j++) {
                existingDataVector[j] = gsrDataList.get(i).getAllMetrics().get(effectiveDataLabels[j] + "-CAL").doubleValue();
                newDataVector[j] = gsrItem.getAllMetrics().get(effectiveDataLabels[j] + "-CAL").doubleValue();
            }
            double currentDistance = GsrUtil.cosineSimilarity(existingDataVector, newDataVector);
            debug("currentDistance:"+currentDistance);
            if (currentDistance < gsrItem.getDistance()) {
                gsrItem.setDistance(currentDistance);
            }
        }
    }

    /**
     *
     * @param gsrItem the value of gsrItem
     * @return the similarity of this item with other k records
     */
    public boolean isOutsideBounds(GsrData gsrItem) {

        return true;

    }

    @Override
    protected void processMsgFromCallback(ShimmerMsg shimmerMSG) {
        int ind = shimmerMSG.mIdentifier;
        switch (ind) {
            case ShimmerPC.MSG_IDENTIFIER_STATE_CHANGE: {
                CallbackObject callbackObject = (CallbackObject) shimmerMSG.mB;
                if (callbackObject.mState == BT_STATE.CONNECTING) {
                    debug("Shimmer device is connecting");
                } else if (callbackObject.mState == BT_STATE.CONNECTED) {
                    debug("Connection Complete");
                } else if (callbackObject.mState == BT_STATE.DISCONNECTED) {
                    debug("Shimmer device has been disconnected");
                } else if (callbackObject.mState == BT_STATE.CONNECTION_LOST) {
                    debug("Connection Lost");
                } else if (callbackObject.mState == BT_STATE.STREAMING) {
                    debug("Starting Streaming");
                    debug("Streaming Object1:" + callbackObject.mMyObject);
                    debug("Streaming Object2:" + callbackObject.toString());
                } else if (callbackObject.mState == BT_STATE.SDLOGGING) {
                    debug("Starting SD Logging");
                    debug("Streaming Object1:" + callbackObject.mMyObject);
                    debug("Streaming Object2:" + callbackObject.toString());
                } else if (callbackObject.mState == BT_STATE.STREAMING_AND_SDLOGGING) {
                    debug("Starting Streaming and SD Logging");
                    debug("Streaming Object1:" + callbackObject.mMyObject);
                    debug("Streaming Object2:" + callbackObject.toString());
                } else {
                    debug("Unexpected state:" + callbackObject.mState);
                    //disconnect();
                }
                break;
            }
            case ShimmerPC.MSG_IDENTIFIER_NOTIFICATION_MESSAGE: {
                CallbackObject callbackObject = (CallbackObject) shimmerMSG.mB;
                int msg = callbackObject.mIndicator;
                if (msg == ShimmerPC.NOTIFICATION_SHIMMER_FULLY_INITIALIZED) {
                    debug("Device ready!");
                    mShimmer.readConfigBytes();
                    debug("version:" + mShimmer.getShimmerVersion());
                    mShimmer.writeEnabledSensors(enabledSensors);
//        this.listofCompatibleSensorsShimmer2 = Arrays.asList(Configuration.Shimmer2.ListofCompatibleSensors);
//        debug(this.listofCompatibleSensorsShimmer2.stream().map(Object::toString).collect(Collectors.joining(",")));
//                    debug("--------------------------");
//                    debug(mShimmer.getSensorMap().values().stream().map(Object::toString).collect(Collectors.joining(";")));
//                    debug("--------------------------");
                    stream();
                }
                if (msg == ShimmerPC.NOTIFICATION_SHIMMER_STOP_STREAMING) {
                    debug("Stop streaming!");
                    mShimmer.stopStreaming();
                } else if (msg == ShimmerPC.NOTIFICATION_SHIMMER_START_STREAMING) {
                    debug("Start streaming!");
                } else {	//Ready for Streaming
                    debug("Ready for streaming===>" + msg);
//                    debug("--------------------------");
//                    debug(Arrays.toString(mShimmer.getListofEnabledChannelSignals()));
//                    debug("--------------------------");
                }
                break;
            }
            case ShimmerPC.MSG_IDENTIFIER_DATA_PACKET:
                //debug("Keep Running:" + _keepRunning);

                if (!_keepRunning) {
                    this.stopProcessing();
                } else if (shimmerMSG.mB instanceof ObjectCluster) {
                    ObjectCluster objc = (ObjectCluster) shimmerMSG.mB;
                    //debug("Keep Running:" + objc);
                    logData(objc);
                }
                break;

            case ShimmerPC.MSG_IDENTIFIER_PACKET_RECEPTION_RATE_OVERALL:
                CallbackObject callbackObject = (CallbackObject) shimmerMSG.mB;
                double packetReceptionRate = callbackObject.mPacketReceptionRate;
                //if (packetReceptionRate < 1) {
                    debug("Packet Reception Rate:" + Double.toString(packetReceptionRate));
                //}

            default:
                break;

        }
    }

    public String getUserLabel() {
        return userLabel;
    }

    public void setUserLabel(String userLabel) {
        this.userLabel = userLabel;
    }

    public boolean isKeepRunning() {
        return _keepRunning;
    }

    public List<GsrData> getGsrDataList() {
        return gsrDataList;
    }

    public void setGsrDataList(List<GsrData> gsrDataList) {
        this.gsrDataList = gsrDataList;
    }

    public ShimmerPC getmShimmer() {
        return mShimmer;
    }

    public void setmShimmer(ShimmerPC mShimmer) {
        this.mShimmer = mShimmer;
    }

    public String getComPort() {
        return ComPort;
    }

    public void setComPort(String ComPort) {
        this.ComPort = ComPort;
    }

    public CsvHandler getLogFile() {
        return logFile;
    }

    public void setLogFile(CsvHandler logFile) {
        this.logFile = logFile;
    }

    public int getSAMPLING_RATE() {
        return _SAMPLING_RATE;
    }
    public String getSAMPLING_RATE_STRING(){
        return _SAMPLING_RATE+"Hz";
    }

    public int getACCEL_RANGE() {
        return _ACCEL_RANGE;
    }

    public String getACCEL_RANGE_STRING() {
        
        //Valid range setting values for the Shimmer 2 are 0 (+/- 1.5g), 1 (+/- 2g), 2 (+/- 4g) and 3 (+/- 6g). Valid range setting values for the Shimmer 2r are 0 (+/- 1.5g) and 3 (+/- 6g).
        if(_ACCEL_RANGE==0){
            return "+/- 1.5g";
        }
        if(_ACCEL_RANGE==1){
            return "+/- 2g";
        }
        if(_ACCEL_RANGE==2){
            return "+/- 4g";
        }
        if(_ACCEL_RANGE==3){
            return "+/- 6g";
        }
        return "N/A";
    }

        
    public int getGSR_RANGE() {
        return _GSR_RANGE;
    }
    
    public String getGSR_RANGE_STRING() {
        //Numeric value defining the desired gsr range. Valid range settings are 0 (10kOhm to 56kOhm),  1 (56kOhm to 220kOhm), 2 (220kOhm to 680kOhm), 3 (680kOhm to 4.7MOhm) and 4 (Auto Range).
        if(_GSR_RANGE==0){
            return "10kOhm to 56kOhm";
        }
        if(_GSR_RANGE==1){
            return "56kOhm to 220kOhm";
        }
        if(_GSR_RANGE==2){
            return "220kOhm to 680kOhm";
        }
        if(_GSR_RANGE==3){
            return "680kOhm to 4.7MOhm";
        }
        if(_GSR_RANGE==4){
            return "Auto Range";
        }
        return "N/A";
    }

    public int getEnabledSensors() {
        return enabledSensors;
    }

    public int getOutlier_k() {
        return outlier_k;
    }
    
    
}
