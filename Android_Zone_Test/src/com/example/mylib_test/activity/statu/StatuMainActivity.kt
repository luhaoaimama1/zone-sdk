package com.example.mylib_test.activity.statu

import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.example.mylib_test.R
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import com.zone.lib.base.controller.activity.controller.ActionBarActivityController
import com.zone.lib.base.controller.activity.controller.ShowState

/**
 * [2017] by Zone
 */

class StatuMainActivity : BaseFeatureActivity() {
    override fun initDefaultConifg(){
        getController(ActionBarActivityController::class.java)?.initFirst(ShowState.ShowTitle)
    }

    override fun setContentView() {
        setContentView(R.layout.a_statu_test)
    }

    override fun initData() {

    }

    override fun setListener() {

    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v?.id) {
            R.id.btLow -> btLowClick()
            R.id.btClear -> btClearClick()
            R.id.bt4FullScreen -> bt4FullScreenClick()
            R.id.btBg -> btBg()
            else -> {
            }
        }
    }

    fun btLowClick() {
        // This example uses decor view, but you can use any visible view.
        val decorView = window.decorView
        val uiOptions = View.SYSTEM_UI_FLAG_LOW_PROFILE
        decorView.systemUiVisibility = uiOptions
    }

    fun btClearClick() {
        val decorView = window.decorView
        decorView.systemUiVisibility = 0
    }

    fun bt4FullScreenClick() {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
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

    fun btBg() {
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 100)
        window.setBackgroundDrawable(ColorDrawable(-0x50000000))
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
