package com.henrik.keeplive.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zone.base_keeplive.KeepLiveBroadcasts;


/**
 * 这个是app狗带或者手机重启之后把app拉起的receiver,
 * 一般监听 android.intent.action.BOOT_COMPLETED 和 android.intent.action.MEDIA_MOUNTED 就能应付手机重启,
 * Created by pelli on 2015/12/4.
 * add  by henrikwu.  监听一下系统广播 拉活
 * <intent-filter android:priority="2147483647">
 * <action android:name="android.intent.action.BOOT_COMPLETED"/>
 * </intent-filter>
 * <intent-filter android:priority="2147483647">
 * <action android:name="android.intent.action.ACTION_SHUTDOWN"/>
 * </intent-filter>
 * <intent-filter>
 * <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
 * <action android:name="android.intent.action.TIME_SET" />
 * <action android:name="android.intent.action.TIMEZONE_CHANGED" />
 * <action android:name="android.net.wifi.WIFI_STATE_CHANGED" />
 * <action android:name="android.intent.action.BATTERY_CHANGED" />
 * </intent-filter>
 * <intent-filter>
 * <action android:name="android.intent.action.MEDIA_BAD_REMOVAL"/>
 * <action android:name="android.intent.action.MEDIA_EJECT"/>
 * <action android:name="android.intent.action.MEDIA_MOUNTED"/>
 * <action android:name="android.intent.action.MEDIA_REMOVED"/>
 * <action android:name="android.intent.action.MEDIA_SCANNER_FINISHED"/>
 * <action android:name="android.intent.action.MEDIA_SCANNER_STARTED"/>
 * <action android:name="android.intent.action.MEDIA_SHARED"/>
 * <action android:name="android.intent.action.MEDIA_UNMOUNTED"/>
 * <p>
 * <data android:scheme="file"/>
 * </intent-filter>
 */
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        KeepLiveBroadcasts.sendBroadcast(context, "BootReceiver", (intent == null ? "" : intent.getAction()));
    }
}
