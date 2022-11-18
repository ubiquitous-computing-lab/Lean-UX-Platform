package com.uclab.leanuxplatform.services.reasoner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

public class KRFUtil {
    private static Logger log = LogManager.getLogger(KRFUtil.class);
    public static ObjectMapper objectMapper = new ObjectMapper();
    
    public static int timeToSec(String time) {
        time = time.replaceAll(" ", "").toLowerCase();
        if (time.length() < 2) {
            log.error("Invalid Time Format: {}", time);
            return 0;
        }
        if(time.contains(".")){
            time = getTimeFromDecimal(time);
        }
        String[] timeParts = time.split(":");
        int seconds = 0;
        for (String part : timeParts) {
            if (part == null || part.length() == 1) {
                continue;
            }
            seconds += getSeconds(part);
        }
        return seconds;
    }

    public static int getSeconds(String timePart) {
        int secs = 0;
        if (timePart.contains("h")) {
            timePart = timePart.replace("h", "");
            if (timePart.length() < 1) {
                log.error("Invalid hours: {}", timePart);
                return 0;
            }
            int hour = Integer.parseInt(timePart);
            secs = hour * 3600;
        } else if (timePart.contains("m")) {
            timePart = timePart.replace("m", "");
            if (timePart.length() < 1) {
                log.error("Invalid minutes: {}", timePart);
                return 0;
            }
            int minute = Integer.parseInt(timePart);
            secs = minute * 60;
        } else {
            if (timePart.contains("s")) {
                timePart = timePart.replace("s", "");
                if (timePart.length() < 1) {
                    log.error("Invalid seconds: {}", timePart);
                    return 0;
                }
                secs = Integer.parseInt(timePart);
            }
        }

        return secs;
    }
    
    public static String getTimeFromDecimal(String time){
        String p1, p2, tag1 = null, tag2 = "";
        String[] parts = time.split("\\.");
        p1 = parts[0];
        if(parts[1].contains("h")){
            tag1 = "h";
            tag2 = "m";
        } else if (parts[1].contains("m")){
            tag1 = "m";
            tag2 = "s";
        } else if (parts[1].contains("s")){
            tag1 = "s";
        }
        try{
            p2 = parts[1].replace(tag1, "");
            int v = Integer.parseInt(p2);
            v = (v*60) / 10;
            time = p1 + tag1 + ":" + v + tag2;
        }catch(Exception ex){
            log.error("Invalid Time value {}", time);
        }
        
        return time;
    }
}
