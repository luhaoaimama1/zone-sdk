package com.example.mylib_test.activity.custom_view;

import com.example.mylib_test.R;

import and.base.activity.BaseActivityZ;
import view.GestureView;
import view.GestureView2;

/**
 * Created by Administrator on 2016/4/14.
 */
public class ChengChengActivity extends BaseActivityZ {
    @Override
    public void setContentView() {
        String type = getIntent().getStringExtra("type");
        if("ges".equals(type)){
            setContentView(new GestureView(this));
        }else if("ges2".equals(type)){
            setContentView(new GestureView2(this));
        } else
            setContentView(R.layout.a_cheng);
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
}
