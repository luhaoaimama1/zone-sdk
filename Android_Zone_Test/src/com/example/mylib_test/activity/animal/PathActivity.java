package com.example.mylib_test.activity.animal;

import com.zone.view.test.DampingView;
import com.zone.view.test.FlexibleBall;
import com.zone.view.test.QQBizierView;

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
