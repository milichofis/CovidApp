package com.example.covidapp.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeHelper {

    public static String unixTimeStampToDate(double unixTimeStamp){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        date.setTime((long)unixTimeStamp*1000);
        return dateFormat.format(date);
    }

    public static String getDateNow(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getDateNow(String pattern){
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        Calendar cal = calendar();
        Date date = cal.getTime();
        return dateFormat.format(date);
    }

    public static String getYesterday(String pattern){
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        Calendar cal = calendar();
        cal.add(Calendar.DATE, -1);
        Date date = cal.getTime();
        return dateFormat.format(date);
    }

    public static String getTwoDaysAgo(String pattern){
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        Calendar cal = calendar();
        cal.add(Calendar.DATE, -2);
        Date date = cal.getTime();
        return dateFormat.format(date);
    }

    private static Calendar calendar() {
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }
}
