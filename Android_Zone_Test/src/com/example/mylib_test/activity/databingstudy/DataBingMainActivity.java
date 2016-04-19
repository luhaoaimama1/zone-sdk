package com.example.mylib_test.activity.databingstudy;

import android.content.Intent;
import android.view.View;

import com.example.mylib_test.R;

import and.base.activity.BaseActvity;

/**
 * Created by Administrator on 2016/4/16.
 */
public class DataBingMainActivity extends BaseActvity {
    @Override
    public void setContentView() {
        setContentView(R.layout.a_databingmain);
    }

    @Override
    public void findIDs() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bt_start:
                startActivity(new Intent(this, DataBingStartActivity.class));
                break;
            case R.id.bt_lambda:
                startActivity(new Intent(this, LambdaStartActivity.class));
                break;
            case R.id.bt_rxjava:
                startActivity(new Intent(this, RxjavaActivity.class));
                break;
        }
    }
}
