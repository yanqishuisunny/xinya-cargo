package com.commom.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class LocalDateTimeUtils {

    public static String FORMATE_SINGLE_YEAR = "yyyy";
    public static String FORMATE_SINGLE_MONTH = "MM";
    public static String FORMATE_DATE = "yyyy-MM-dd";
    public static String FORMATE_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * Date  转  localdatetime
     * @param date
     * @return
     */
    public static LocalDateTime dateToLDT(Date date){
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     *  localdatetime  转 date
     * @param time
     * @return
     */
    public static Date ldtToDate(LocalDateTime time){
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }



}
