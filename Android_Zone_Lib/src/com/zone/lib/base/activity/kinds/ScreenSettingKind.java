package com.zone.lib.base.activity.kinds;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

import com.zone.lib.base.activity.kinds.callback.ActivityKinds;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by fuzhipeng on 16/8/30.
 */
public class ScreenSettingKind extends ActivityKinds {

    public ScreenSettingKind(Activity activity) {
        super(activity);
    }

    /**
     * 必须在 setContentView 之前用 否则无效！
     */
    public void setNoTitle() {
        //设置无标题
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * setContentView 之后用
     */
    public void setNoTitle_AppCompatActivity() {
        //设置无标题
        try {
            ((AppCompatActivity) activity).getSupportActionBar().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 必须在 setContentView 之前用 否则无效!
     */
    public void setFullScreen() {
        //设置全屏
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
