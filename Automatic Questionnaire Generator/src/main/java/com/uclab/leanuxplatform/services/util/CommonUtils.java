/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.services.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mohsin.jamil
 */
public class CommonUtils {

    private static final Logger log = LoggerFactory.getLogger(CommonUtils.class);
    /*
     * Basic method to read the contents of a file
    */
    public static String loadData(String filePath) throws Exception {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(
                    new File(filePath)));
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        }
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line.trim());
        }
        br.close();
        return sb.toString();
    }

    public static ZonedDateTime getDateTimeNow() {
         // Get current date time
        ZonedDateTime currentDateTime = ZonedDateTime.now();
        return currentDateTime;
    }
    
    public static String getFormattedDateTime(ZonedDateTime ldt) {
       if(ldt==null) ldt = getDateTimeNow();
        //Build formatter
//        SimpleDateFormat dateFormatter = new SimpleDateFormat();
//
//            Date projectStartDate = new Date();
//            String strDate = dateFormatter.format(projectStartDate);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
        //Format LocalDateTime
        
        String formattedDateTime = ldt.format(formatter);
        log.debug("Time now:"+formattedDateTime);
        return formattedDateTime;
    }
    
    
}

