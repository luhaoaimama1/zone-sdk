package com.example.mylib_test.activity.touch;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.touch.view.FrameLogLayout;
import com.example.mylib_test.activity.touch.view.TextLogView;
import com.zone.lib.base.activity.BaseActivity;

/**
 * Created by fuzhipeng on 2016/11/22.
 */

public class EventPassLogActivity extends BaseActivity {
    private StringBuffer sb = new StringBuffer();
    private FrameLogLayout red, green;
    private TextLogView view;

    @Override
    public void setContentView() {
        setContentView(R.layout.a_eventpasslog);
    }

    @Override
    public void findIDs() {
        red = (FrameLogLayout) findViewById(R.id.red);
        green = (FrameLogLayout) findViewById(R.id.green);
        view = (TextLogView) findViewById(R.id.view);
        view.setOnClickListener(this);
        red.setActiviy(this);
        green.setActiviy(this);
        view.setActiviy(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

    }

    public void log(View view, String str) {
        String pre = "";
        if (view != null) {
            if (view instanceof FrameLogLayout) {
                pre = "FrameLogLayout";
                if (view == red)
                    pre += "1";
                if (view == green)
                    pre += "2";

            } else
                pre = "TextLogView";
        } else {
            pre = "Activity";
        }

        Log.e("EventPassLogActivity", pre + "   " + str);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        log(null, "dispatchTouchEvent");
        boolean result = super.dispatchTouchEvent(ev);
        log(null, "dispatchTouchEvent 结果->:" + result);
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        log(null, "onTouchEvent");
        boolean result = super.onTouchEvent(event);
        log(null, "onTouchEvent 结果->:" + result);
        return result;
    }

}
