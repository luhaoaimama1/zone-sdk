package com.example.mylib_test.activity.databingstudy;

import android.content.Intent;
import android.view.View;

import com.example.mylib_test.R;

import com.zone.lib.base.activity.BaseActivity;

/**
 * Created by Administrator on 2016/4/16.
 */
public class DataBingMainActivity extends BaseActivity {
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
        }
    }
}
