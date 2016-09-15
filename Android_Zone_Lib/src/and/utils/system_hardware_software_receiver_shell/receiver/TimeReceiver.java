package and.utils.system_hardware_software_receiver_shell.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import and.LogUtil;

/**
 * 时间广播
 *
 * @author MaTianyu
 * @date 2015-03-09
 */
public class TimeReceiver extends BroadcastReceiver {
    private TimeListener timeListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (LogUtil.writeLog) {
            LogUtil.i( "action: " + intent.getAction());
            LogUtil.d( "intent : ");
            Bundle bundle = intent.getExtras();
            for (String key : bundle.keySet()) {
                LogUtil.d( key + " : " + bundle.get(key));
            }
        }
        if (Intent.ACTION_TIME_TICK.equals(intent.getAction())) {
            if (timeListener != null) {
                timeListener.onTimeTick();
            }
        } else if (Intent.ACTION_TIME_CHANGED.equals(intent.getAction())) {
            if (timeListener != null) {
                timeListener.onTimeChanged();
            }
        } else if (Intent.ACTION_TIMEZONE_CHANGED.equals(intent.getAction())) {
            if (timeListener != null) {
                timeListener.onTimeZoneChanged();
            }
        }
    }

    public void registerReceiver(Context context, TimeListener timeListener) {
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_TIME_CHANGED);
            filter.addAction(Intent.ACTION_TIME_TICK);
            filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
            filter.setPriority(Integer.MAX_VALUE);
            context.registerReceiver(this, filter);
            this.timeListener = timeListener;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unRegisterReceiver(Context context) {
        try {
            context.unregisterReceiver(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static interface TimeListener {
        /**
         * 时区改变
         */
        public void onTimeZoneChanged();

        /**
         * 设置时间
         */
        public void onTimeChanged();

        /**
         * 每分钟调用
         */
        public void onTimeTick();
    }
}
