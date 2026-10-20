package com.automationexercise.utils;

public class TimeManager {

    //screenshots-logs-reports
public static String  getTimeStamp(){
    return new java.text.SimpleDateFormat("yyyy-MM-dd_hh-mm-ss").format(new java.util.Date());

}

//unique timestamp for each data

public static String getSimpleTimeStamp(){
    return Long.toString(System.currentTimeMillis());

}
}
