//package com.zone.keeplive.receiver;
//
//import android.app.PendingIntent;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Handler;
//import com.zone.KeepLives;
//import com.zone.keeplive.activity.OnePixelActivity;
//
//public class ScreenOffOnBroadcastReceiver extends BroadcastReceiver {
//
//    public static final String ACTION_SCREEN_ON = "_screen_on";
//    public static final String ACTION_SCREEN_OFF = "_screen_off";
//
//    //zone todo: 2021/2/23  包名统一 提取
//    public static void sendBroad(Context context, boolean screenOn) {
//        Intent broadcastIntent=new Intent();
//        if (screenOn) {
//            broadcastIntent .setAction(ACTION_SCREEN_ON);
//        } else {
//            broadcastIntent.setAction(ACTION_SCREEN_OFF);
//        }
//        context.sendBroadcast(broadcastIntent);
//    }
//
//    private Handler mHander = new Handler();
//    private String action = "";
//
//    public ScreenOffOnBroadcastReceiver() {
//    }
//
//    @Override
//    public void onReceive(final Context context, Intent intent) {
//        KeepLives.log("ScreenOffOnReceiver \t action:" + intent.getAction());
//        if (intent == null || intent.getAction() == null || action.equals(intent.getAction()))
//            return;
//
//        action = intent.getAction();
//        if (action.equals(ACTION_SCREEN_OFF)) {
////            //防止频繁启动一像素保护
//            mHander.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if (action.equals(ACTION_SCREEN_OFF)) {
//                        screenOffStartActivity(context);
//                    }
//                }
//            }, 2500);
//        }
//    }
//
//
//    /**
//     * 息屏:启动 一像素页面 并且播放无声音音乐
//     * 屏幕恢复:在OnePixelActivity onResume中处理了 也就是关闭一像素和停止播放无声音乐
//     */
//    public static void screenOffStartActivity(Context context) {
//        Intent intent2 = new Intent(context, OnePixelActivity.class);
//        intent2.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2, 0);
//        try {
//            pendingIntent.send();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}
