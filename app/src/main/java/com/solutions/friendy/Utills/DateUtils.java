package com.solutions.friendy.Utills;

import com.cometchat.pro.helpers.Logger;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DateUtils {

    public static String getTimeStringFromTimestamp(long timestamp, String format) {

        java.util.Date dt = new java.util.Date(timestamp * 1000);

        Timestamp time = new Timestamp(dt.getTime());
        Logger.debug("timestamp", time + "rime" + dt.getDate() + " inside");
        String str = getMessageTime(time.toString(), format);
        Logger.debug("timestamp", str + dt.getTime() + " inside");
        return str;
    }

    public static String getMessageTime(String time, String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        Timestamp timestamp = Timestamp.valueOf(time);
        String str = format.format(timestamp);
        return str;
    }
}
