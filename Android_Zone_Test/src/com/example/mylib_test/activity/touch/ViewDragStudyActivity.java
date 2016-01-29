package com.example.mylib_test.activity.touch;

import android.view.View;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.touch.view.ViewDragStudyFrame;

import and.abstractclass.BaseActvity;
import and.log.ToastUtils;

/**
 * Created by Zone on 2016/1/29.
 */
public class ViewDragStudyActivity extends BaseActvity  {
    @Override
    public void setContentView() {
        setContentView(R.layout.a_viewdragstudy);
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
        switch (v.getId()){
          case R.id.tv_back:
              ((ViewDragStudyFrame)findViewById(R.id.viewDragStudyFrame)).toggle();
            break;
          case R.id.tv_mMenuView:
              ToastUtils.showLong(this,"没有tryCaptureView 可以点击");
            break;
        }
    }
}
