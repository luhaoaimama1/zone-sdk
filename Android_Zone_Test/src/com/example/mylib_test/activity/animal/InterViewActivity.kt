package com.example.mylib_test.activity.animal

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import interview.InterView

/**
 * 绘制SVG
 * Created by LGL on 2016/4/16.
 */
class InterViewActivity : FragmentActivity() {
    lateinit var interView: InterView
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(InterView(this).apply { interView = this })
        interView.setBackgroundColor(Color.parseColor("#FFAACB1C"))
        interView.startCustomAnimation()
    }

    override fun onDestroy() {
        interView.stopCustomAnimation()
        super.onDestroy()
    }

}