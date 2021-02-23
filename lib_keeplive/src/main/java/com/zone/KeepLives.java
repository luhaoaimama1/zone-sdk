package com.zone;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.annotation.NonNull;
import com.zone.keeplive.service.Contants;
import com.zone.keeplive.service.DemonService;
import com.zone.keeplive.service.KeepLiveService;
import com.zone.pulllive.accountsync.AccountSyncService;

import java.util.List;

public class KeepLives {
    public static int INTENT_SERVICE_STATE_DEFAULT = 0;
    public static int INTENT_SERVICE_STATE_STOP = 1;

    public static boolean enableLog = true;
    public static final String TAG = "KeepLives";

    public static void log(String log) {
        if (enableLog) {
            Log.d(TAG, log);
        }
    }

    public static void logE(String tag1, String log) {
        if (enableLog) {
            Log.e(TAG, tag1 + "===>" + log);
        }
    }

    public static void config(@NonNull Application application) {
        if (isMain(application)) {
            log("config :isMain");
            //拉活
            keepRelifeFromAccountSync(application);

            //包活
            Intent intent = new Intent(application, KeepLiveService.class);
            application.startService(intent);
        } else {
            log("config : not Main");
        }
    }

    public static void stopKeepLiveService(@NonNull Application application) {
        Intent intent = new Intent(application, KeepLiveService.class);
        //不用静态变量是因为这个是多进程
        intent.putExtra(Contants.INTENT_SERVICE_STATE_KEY, Contants.INTENT_SERVICE_STATE_STOP);
        application.startService(intent);

        Intent intent2 = new Intent(application, DemonService.class);
        intent2.putExtra(Contants.INTENT_SERVICE_STATE_KEY, Contants.INTENT_SERVICE_STATE_STOP);
        application.startService(intent2);
    }

    private static void keepRelifeFromAccountSync(@NonNull Application application) {
        //利用帐号同步机制  拉活
        String accountType = application.getString(R.string.account_type);
        String accountName = application.getString(R.string.account_name);
        AccountSyncService.startAccountSync(application, accountName, accountType);
    }

    private static boolean isMain(@NonNull Application application) {
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager mActivityManager = (ActivityManager) application.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos = mActivityManager.getRunningAppProcesses();
        if (runningAppProcessInfos != null) {
            for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    processName = appProcess.processName;
                    break;
                }
            }
            String packageName = application.getPackageName();
            if (processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }
}
