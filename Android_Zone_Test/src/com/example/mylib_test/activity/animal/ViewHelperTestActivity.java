package com.example.mylib_test.activity.animal;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.mylib_test.R;
import com.nineoldandroids.view.ViewHelper;

import and.base.activity.BaseActivityZ;

/**
 * Created by Administrator on 2016/1/28.
 */
public class ViewHelperTestActivity extends BaseActivityZ {
    private Button bt1, bt2, bt3;
    private LinearLayout ll_main;

    @Override
    public void setContentView() {
        setContentView(R.layout.a_viewhelper);
    }

    @Override
    public void findIDs() {
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        bt3 = (Button) findViewById(R.id.bt3);
        ll_main = (LinearLayout) findViewById(R.id.ll_main);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {
        ll_main.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_main:
                startAni();
                break;
            case R.id.bt2:
                startAni2();
                break;
            case R.id.bt3:
                startAni3();
                break;
        }
        super.onClick(v);
    }

    private void startAni() {
        ViewHelper.setY(bt1, 100);
    }

    private void startAni2() {
//        ViewHelper.setY(bt1,100);
        ViewHelper.setTranslationY(bt1, 100);
    }

    private void startAni3() {
        ViewHelper.setScrollX(ll_main, 100);
        ViewHelper.setScrollX(bt3, -100);
    }
}
