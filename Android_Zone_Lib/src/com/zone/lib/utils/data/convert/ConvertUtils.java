package com.zone.lib.utils.data.convert;

import com.zone.lib.utils.data.check.EmptyCheck;

/**
 * [2017] by Zone
 */

public class ConvertUtils {

    public final static int toInt(Object value, int defaultValue) {

        if (value == null || "".equals(value.toString()))
            return defaultValue;

        try {
            return Integer.parseInt(value.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    public final static long toLong(Object value, long defaultValue) {

        if (value == null || "".equals(value.toString()))
            return defaultValue;

        try {
            return Long.parseLong(value.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    public final static double toDoulbe(Object value, long defaultValue) {

        if (value == null || "".equals(value.toString()))
            return defaultValue;

        try {
            return Double.parseDouble(value.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    public final static float toFloat(Object value, float defaultValue) {

        if (value == null || "".equals(value.toString()))
            return defaultValue;

        try {
            return Float.parseFloat(value.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    public final static String toString(Object value, String defaultValue) {

        if (value == null)
            return defaultValue;

        return value.toString();
    }

    public final static String subString(String content, int start, int end, String defaultValue) {

        if (EmptyCheck.isEmpty(content))
            return defaultValue;

        try {
            return content.substring(start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    public final static String subString(String content, int start, String defaultValue) {

        if (EmptyCheck.isEmpty(content))
            return defaultValue;

        try {
            return content.substring(start);
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

}
