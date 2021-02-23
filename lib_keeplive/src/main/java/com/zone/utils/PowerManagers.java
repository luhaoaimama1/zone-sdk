package com.zone.utils;

import android.content.Context;
import android.os.PowerManager;

public class PowerManagers {

    public static boolean isScreenOn(Context context) {
        try {
            PowerManager pm = (PowerManager) context.getApplicationContext().getSystemService(Context.POWER_SERVICE);
            return pm.isScreenOn();
        } catch (Exception e) {
            return true;
        }
    }
}
