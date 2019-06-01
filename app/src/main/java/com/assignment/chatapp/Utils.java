package com.assignment.chatapp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String getTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        return formatter.format(date);
    }

    public static Date getCurrentDateTime(){
        Date date = new Date();
        return date;
    }



}
