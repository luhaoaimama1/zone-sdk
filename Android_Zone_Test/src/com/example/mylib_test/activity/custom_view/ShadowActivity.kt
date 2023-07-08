package com.example.mylib_test.activity.custom_view


import android.os.Build
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import com.example.mylib_test.R
//import com.skydoves.colorpickerview.listeners.ColorListener
//import com.skydoves.colorpickerview.listeners.ColorPickerViewListener

import com.zone.lib.base.controller.activity.BaseFeatureActivity
import com.zone.lib.utils.data.convert.DensityUtils
import kotlinx.android.synthetic.main.a_showdow.*

class ShadowActivity : BaseFeatureActivity() {
    override fun setContentView() {
        setContentView(R.layout.a_showdow)

        elevationSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                view2.elevation = DensityUtils.dp2px(this@ShadowActivity, 1f).toFloat() * progress
                cardView.cardElevation = DensityUtils.dp2px(this@ShadowActivity, 1f).toFloat() * progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        translationZSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                view2.translationZ = DensityUtils.dp2px(this@ShadowActivity, 1f).toFloat() * progress
//                cardView.maxCardElevation = DensityUtils.dp2px(this@ShadowActivity, 1f).toFloat() * progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

//        colorPickerView.setColorListener(object : ColorListener {
//            override fun onColorSelected(color: Int, fromUser: Boolean) {
//            }
//        })

    }

    override fun initData() {
    }

    override fun setListener() {
    }
}
