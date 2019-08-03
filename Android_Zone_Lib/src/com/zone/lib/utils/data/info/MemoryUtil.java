package com.zone.lib.utils.data.info;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.text.format.Formatter;

import com.zone.lib.LogLevel;
import com.zone.lib.LogZSDK;

/**
 * Get memory info.
 *
 * @author MaTianyu
 * @date 2015-04-19
 */
public class MemoryUtil {
    /**
     * Get memory info of device.
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static ActivityManager.MemoryInfo getMemoryInfo(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return mi;
    }

    /**
     * Print Memory info.
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static ActivityManager.MemoryInfo printMemoryInfo(Context context) {
        ActivityManager.MemoryInfo mi = getMemoryInfo(context);
        if (LogZSDK.INSTANCE.levelOK(LogLevel.i)) {
            StringBuilder sb = new StringBuilder();
            sb.append("_______  Memory :   ");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                sb.append("\ntotalMem        :").append(mi.totalMem);
            }
            sb.append("\navailMem        :").append(mi.availMem);
            sb.append("\nlowMemory       :").append(mi.lowMemory);
            sb.append("\nthreshold       :").append(mi.threshold);
            LogZSDK.INSTANCE.i(sb.toString());
        }
        return mi;
    }

    /**
     * Get available memory info.
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static String getAvailMemory(Context context) {// 获取android当前可用内存大小
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        // mi.availMem; 当前系统的可用内存
        return Formatter.formatFileSize(context, mi.availMem);// 将获取的内存大小规格化
    }

}
