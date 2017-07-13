package com.example.mylib_test.activity.custom_view;

import android.view.View;
import com.example.mylib_test.R;

import com.zone.lib.base.activity.BaseActivity;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/4/15.
 * 由于内容较多 可以看我的笔记~  搜索词:SwitchButton（IOS倾向） 的api 学习
 */
public class RippleViewActivity extends BaseActivity {


    @Override
    public void setContentView() {
        setContentView(R.layout.test_switchbutton);
        ButterKnife.bind(this);
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
            case R.id.switch_no:
                break;
            case R.id.switch_on:
                break;
        }
    }

}
