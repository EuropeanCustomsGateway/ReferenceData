package org.ecg.refdata.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateHelper {

    public static String dateToString(java.util.Date date, String format) {
        if (date == null || format == null) {
            return null;
        }
        if (format.equals("yyyy-MM-ddTHH:mm:ss")) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date).replaceAll(" ", "T");
        } else if (format.equals("yyyy-MM-ddThh:mm:ss")) {
            return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date).replaceAll(" ", "T");
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static String dateToString(java.util.Date date) {
        return dateToString(date, "yyyy-MM-dd");
    }

    public static String dateTimeToString(java.util.Date date) {
        return dateToString(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String dateTimeToXMLString(java.util.Date date) {
        return dateToString(date, "yyyy-MM-ddTHH:mm:ss");
    }

    /**
     * Funkcja "usuwa" czas z daty
     */
    public static final Date clearTime(Date date) {
        if (date == null) {
            return null;
        } else {
            Calendar calendar = Calendar.getInstance();

            calendar.setTime(date);

            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime();
        }
    }

    
}
