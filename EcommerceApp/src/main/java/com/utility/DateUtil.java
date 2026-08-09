package com.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class providing date formatting and relative timestamp helpers.
 * 
 * @author Vishal
 */
public class DateUtil {

    private static final String DEFAULT_PATTERN = "dd MMM yyyy, hh:mm a";

    /**
     * Formats current date to standard display format.
     */
    public static String getCurrentFormattedDate() {
        return new SimpleDateFormat(DEFAULT_PATTERN).format(new Date());
    }

    /**
     * Formats given date object with custom pattern.
     */
    public static String formatDate(Date date, String pattern) {
        if (date == null) return "";
        try {
            return new SimpleDateFormat(pattern).format(date);
        } catch (Exception e) {
            return date.toString();
        }
    }

    /**
     * Formats SQL date string (yyyy-MM-dd) to friendly string (e.g. 10 Aug 2026).
     */
    public static String formatSqlDate(String sqlDateStr) {
        if (sqlDateStr == null || sqlDateStr.isEmpty()) return "";
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(sqlDateStr);
            return new SimpleDateFormat("dd MMM yyyy").format(date);
        } catch (Exception e) {
            return sqlDateStr;
        }
    }
}
