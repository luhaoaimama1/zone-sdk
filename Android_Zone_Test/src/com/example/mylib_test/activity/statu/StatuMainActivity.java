package com.example.mylib_test.activity.statu;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.mylib_test.R;
import com.zone.lib.base.activity.BaseActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * [2017] by Zone
 */

public class StatuMainActivity extends BaseActivity {
    @Override
    public void setContentView() {
        //不能被home键 页面切换 还原
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.a_statu_test);
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

    @OnClick(R.id.btLow)
    public void btLowClick() {
        // This example uses decor view, but you can use any visible view.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @OnClick(R.id.btClear)
    public void btClearClick() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(0);
    }

    @OnClick(R.id.bt4FullScreen)
    public void bt4FullScreenClick() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        View decorView = getWindow().getDecorView();
//        // Hide the status bar.
//        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(uiOptions);
//
//
//        // Remember that you should never show the action bar if the
//        // status bar is hidden, so hide that too if necessary.
//        ActionBar actionBar = getActionBar();
//        if(actionBar!=null)
//         actionBar.hide();
    }
    @OnClick(R.id.btBg)
    public void btBg() {
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 100);
        getWindow().setBackgroundDrawable(new ColorDrawable(0xb0000000));
//        View decorView = getWindow().getDecorView();
//        // Hide the status bar.
//        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(uiOptions);
//
//
//        // Remember that you should never show the action bar if the
//        // status bar is hidden, so hide that too if necessary.
//        ActionBar actionBar = getActionBar();
//        if(actionBar!=null)
//         actionBar.hide();
    }
}
