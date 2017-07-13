package com.zone.lib.utils.data.convert;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by fuzhipeng on 2016/10/20.
 * 天+-,月加减 请查笔记；
 */

public class DateUtil {

    public static String FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String date2str(Date date) {
        return date2str(date, FORMAT);
    }

    public static String date2str(Date date, String format) {
        String dateStr = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            dateStr = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    public static Date str2Date(String str) {
        return str2Date(str, FORMAT);
    }

    /**
     * string -> date
     */
    public static Date str2Date(String str, String format) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Calendar str2Calendar(String str) {
        return str2Calendar(str, FORMAT);
    }

    public static Calendar str2Calendar(String str, String format) {
        Calendar c = Calendar.getInstance();
        c.setTime(str2Date(str, format));
        return c;
    }

    public static String calendar2str(Calendar calendar) {
        return calendar2str(calendar, FORMAT);
    }

    public static String calendar2str(Calendar calendar, String format) {
        return date2str(calendar.getTime(), format);
    }


    /**
     * @param timestamp1
     * @param timestamp2
     * @return new long[]{diffDays,diffHours,diffMinutes,diffSeconds,diff};
     */
    public static long[] diffTime(long timestamp1, long timestamp2) {
        long diff = timestamp2 - timestamp1;
        long diffSeconds = Math.abs(diff / 1000);
        long diffMinutes = Math.abs(diff / (60 * 1000));
        long diffHours = Math.abs(diff / (60 * 60 * 1000));
        long diffDays = Math.abs(diff / (24 * 60 * 60 * 1000));
        return new long[]{diffDays, diffHours, diffMinutes, diffSeconds, diff};
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate2(Date dt) {
        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
        return dateFm.format(dt);
    }


    //--------------------------防Month坑方法----------------------------------
    /**
     * 只有月份 从0开始 所以少1，其他都是正常的 （设置1  其实是2）
     * 防Month坑方法
     * @param calendar
     * @param field
     * @param value
     */
    public static void set(Calendar calendar, int field, int value) {
        if (Calendar.MONTH == field) {
            calendar.set(field, value - 1);
        } else
            calendar.set(field, value);

    }

    /**
     * 只有月份 从0开始 所以少1，其他都是正常的 （设置1  其实是2）
     * 防Month坑方法
     * @param calendar
     * @param field
     */
    public static int get(Calendar calendar, int field) {
        if (Calendar.MONTH == field)
            return calendar.get(field) + 1;
        else
            return calendar.get(field);
    }

    /**
     * 只有月份 从0开始 所以少1，其他都是正常的 （设置1  其实是2）
     * 防Month坑方法
     * @param calendar
     */
    public static void set(Calendar calendar, int year, int month, int date) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, date);
    }
    /**
     * 只有月份 从0开始 所以少1，其他都是正常的 （设置1  其实是2）
     * 防Month坑方法
     * @param calendar
     */
    public static void set(Calendar calendar, int year, int month, int date, int hourOfDay, int minute) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, date);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
    }
    /**
     * 只有月份 从0开始 所以少1，其他都是正常的 （设置1  其实是2）
     * 防Month坑方法
     * @param calendar
     */
    public static void set(Calendar calendar, int year, int month, int date, int hourOfDay, int minute,
                           int second) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, date);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
    }

    /**
     * add 没坑 可以用本身的
     * @param calendar
     * @param field
     * @param value
     */
    public static void add(Calendar calendar, int field, int value) {
        calendar.add(field, value);
    }

}
