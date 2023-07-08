package com.example.mylib_test.activity.three_place

import android.app.Activity
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.mylib_test.R
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import com.zone.lib.utils.image.BitmapUtils
import com.zone.lib.utils.view.ViewTreeObserver
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.a_fastblur.*
import org.greenrobot.eventbus.EventBus

/**
 *[2018/7/10] by Zone
 */

class LiveDataSendActivity : Activity() {
    lateinit var view:View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = View(this)
        setContentView(view)
    }

    override fun onPostResume() {
        super.onPostResume()
        ViewTreeObserver.addOnDrawListener(view,{
            for(i in 1..1000){
                EventBus.getDefault().post(ThirdParty_MainActivity.EventInt(i));
            }
        })
    }

}