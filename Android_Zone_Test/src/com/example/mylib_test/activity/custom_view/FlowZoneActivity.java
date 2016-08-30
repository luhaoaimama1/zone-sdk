package com.example.mylib_test.activity.custom_view;

import com.example.mylib_test.R;
import com.zone.view.FlowLayout;

import and.base.activity.BaseActivity;

/**
 * Created by Administrator on 2016/4/11.
 */
public class FlowZoneActivity extends BaseActivity {
    private FlowLayout flowLayoutZone1;

    @Override
    public void setContentView() {
        setContentView(R.layout.fl_test);
    }

    @Override
    public void findIDs() {
        flowLayoutZone1=(FlowLayout)findViewById(R.id.flowLayoutZone1);
//        flowLayoutZone1.setMaxLine(1);
//        flowLayoutZone1.setMaxLine(2);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

    }
}
