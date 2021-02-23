package com.zone.keeplives;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.fanjun.keeplive.KeepLive;
import com.fanjun.keeplive.config.ForegroundNotification;
import com.fanjun.keeplive.config.ForegroundNotificationClickListener;
import com.fanjun.keeplive.config.KeepLiveService;
import com.henrik.keeplive.accountsync.SyncService;
import com.marswin89.marsdaemon.DaemonClient;
import com.zone.base_keeplive.KeepLiveBroadcasts;
import com.zone.keeplives.utils.Helper;
import com.zone.keeplives.utils.Service1;
public class KeepLives {
    //拉活
    public static void keepRelife(@NonNull Application application, @NonNull Config config) {
        KeepLiveBroadcasts.log("调用keepRelife 进程名字:" + Helper.returnProcessName(application));
        keepRelifeFromAccountSync(application);
//        keepRelifeFromMars(application);

        //主进程活着的时候仅仅触发一次
        keepLive(application, config);
    }

    private static void keepRelifeFromMars(@NonNull Application application) {
        DaemonClient mDaemonClient = new DaemonClient(Helper.createDaemonConfigurations(application.getPackageName()));
        mDaemonClient.onAttachBaseContext(application);
    }

    private static void keepRelifeFromAccountSync(@NonNull Application application) {
        //利用帐号同步机制  拉活
        String accountType = application.getString(R.string.account_type);
        String accountName = application.getString(R.string.account_name);
        SyncService.startAccountSync(application, accountName, accountType);
    }

    public static void start(Context context) {
        try {
            Intent service = new Intent(context, Service1.class);
            context.startService(service);

        } catch (Throwable t) {
            KeepLiveBroadcasts.logE("startService error" , t.getMessage());
        }
    }

    //包活
    static void keepLive(@NonNull Application application, @NonNull final Config config) {
        KeepLiveBroadcasts.log("touch keepLive");
        ForegroundNotification foregroundNotification = new ForegroundNotification(config.notificationTitle, config.notificationDescription, config.notificationIconRes,
                //定义前台服务的通知点击事件
                new ForegroundNotificationClickListener() {

                    @Override
                    public void foregroundNotificationClick(Context context, Intent intent) {
                        if (config.notificationClickListener != null) {
                            config.notificationClickListener.onForegroundNotificationClick(context, intent);
                        }
                    }
                });
        //启动保活服务
        KeepLive.startWork(application, KeepLive.RunMode.ROGUE, foregroundNotification,
                //你需要保活的服务，如socket连接、定时任务等，建议不用匿名内部类的方式在这里写
                new KeepLiveService() {
                    /**
                     * 运行中
                     * 由于服务可能会多次自动启动，该方法可能重复调用
                     */
                    @Override
                    public void onWorking() {

                    }

                    /**
                     * 服务终止
                     * 由于服务可能会被多次终止，该方法可能重复调用，需同onWorking配套使用，如注册和注销broadcast
                     */
                    @Override
                    public void onStop() {

                    }
                }
        );
    }


    public interface NotificationClickListener {
        void onForegroundNotificationClick(Context context, Intent intent);
    }

    public static class Config {
        private NotificationClickListener notificationClickListener;
        private String notificationTitle;
        private String notificationDescription;
        private int notificationIconRes;

        public static Config builder() {
            return new Config();
        }

        public Config notificationClickListener(NotificationClickListener notificationClickListener) {
            this.notificationClickListener = notificationClickListener;
            return this;
        }

        public Config notificationTitle(String notificationTitle) {
            this.notificationTitle = notificationTitle;
            return this;
        }

        public Config notificationDescription(String notificationDescription) {
            this.notificationDescription = notificationDescription;
            return this;
        }

        public Config notificationIconRes(int notificationIconRes) {
            this.notificationIconRes = notificationIconRes;
            return this;
        }

        public Config printLog(boolean isPrint) {
            KeepLiveBroadcasts.enableLog=isPrint;
            return this;
        }
    }
}
