package com.example.mylib_test.activity.statu;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

public class StatusUtils {
    @TargetApi(19)
    public static void setTranslucentStatus(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        // WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public enum StatusMode {
        UNKNOW, LIGHT, DARK
    }

    public static StatusMode getMode(@Nullable Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || activity == null || activity.getWindow() == null || activity.getWindow().getDecorView() == null) {
            return StatusMode.UNKNOW;
        }
        int systemUiVisibility = activity.getWindow().getDecorView().getSystemUiVisibility();
        boolean lightStatusBar = (systemUiVisibility & View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) != 0;
        if (lightStatusBar) return StatusMode.LIGHT;
        else return StatusMode.DARK;
    }

}
