package com.example.mylib_test.activity.animal;

import view.FlexibleBall ;
import view.QQBizierView ;

import and.base.activity.BaseActivity;


/**
 * Created by fuzhipeng on 16/7/29.
 */
public class PathActivity extends BaseActivity {
    @Override
    public void setContentView() {
        String type = getIntent().getStringExtra("type");
        if ("QQBizierView".equals(type))
            setContentView(new QQBizierView(this));
        if ("FlexibleBall".equals(type))
            setContentView(new FlexibleBall(this));
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
