package com.uclab.leanuxplatform.services.util;

import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeHandler {

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public static DateTimeFormatter getFormatter() {
        return FORMATTER;
    }

    //private static DateFormat dateFormat = new SimpleDateFormat("", Locale.KOREA);
    public static String getBeforeTimeSec(String timeStr, long sec) {
        LocalDateTime timeObject = LocalDateTime.parse(timeStr, FORMATTER);
        timeObject = timeObject.minusSeconds(sec);
        return timeObject.format(FORMATTER);
    }

    public static String getAfterTimeSec(String timeStr, long sec) {

        LocalDateTime timeObject = LocalDateTime.parse(timeStr, FORMATTER);
        timeObject = timeObject.plusSeconds(sec);
        return timeObject.format(FORMATTER);
    }

    public static String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(FORMATTER);
    }

    public static LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now();
    }

    public static Long getSecs(String time) {
        String[] tokens = time.split(":");
        int secondsToMs = Integer.parseInt(tokens[2]);
        int minutesToMs = Integer.parseInt(tokens[1]) * 60;
        int hoursToMs = Integer.parseInt(tokens[0]) * 3600;
        long total = secondsToMs + minutesToMs + hoursToMs;
        return total;
    }

    /*
    * Should not be used
    *
     */
    @Deprecated
    public static String extractTimeFromTimeStr(String time) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.split(":")[0]));
        cal.set(Calendar.MINUTE, Integer.parseInt(time.split(":")[1]));
        cal.set(Calendar.SECOND, Integer.parseInt(time.split(":")[2]));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String taskTime = formatter.format(cal.getTime());
        return taskTime;
    }

    @Deprecated
    public static String getDateTimeStr(Long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String taskTime = formatter.format(cal.getTime());
        return taskTime;

    }

}
