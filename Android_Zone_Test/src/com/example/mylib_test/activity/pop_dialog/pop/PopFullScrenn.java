package com.example.mylib_test.activity.pop_dialog.pop;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.mylib_test.LogApp;
import com.example.mylib_test.R;
import com.zone.lib.base.BaseAnimatePopWindow;

public class PopFullScrenn extends BaseAnimatePopWindow implements OnClickListener {
    public PopFullScrenn(Activity activity) {
        super(activity);
        setPopContentView(R.layout.popwindow_fill, -1);
    }

    @Override
    protected void setWH(int width, int height) {
        super.setWH(width, height);
        setClippingEnabled(false);
    }

    @Override
    protected void findView(View mMenuView) {
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void setLocation(View view) {
        setAnimationStyle(R.anim.anim_enter, R.anim.anim_exit);
        setAnimationListener((interpolatedTime, isEnter) -> {
            LogApp.INSTANCE.d("interpolatedTime:"+interpolatedTime +"\t isEnter："+isEnter);
        });
        this.showAtLocation(view, Gravity.NO_GRAVITY, 0, 0);
    }

    @Override
    public void onClick(View v) {

    }
}
