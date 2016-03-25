package com.zone.http2rflist.utils;

import android.util.Log;

/**
 * Created by Administrator on 2016/3/24.
 */
public class LogUtils {
    private static final String TAG="ZHttp2RfList";
    public static  boolean  writeLog=false;
    public static void d(String str){
        if (writeLog )
            Log.d(TAG,str);
    }
    public static void e(String str){
        if (writeLog )
            Log.e(TAG,str);
    }
    public static void v(String str){
        if (writeLog )
            Log.v(TAG,str);
    }
    public static void i(String str){
        if (writeLog )
            Log.i(TAG,str);
    }
}
