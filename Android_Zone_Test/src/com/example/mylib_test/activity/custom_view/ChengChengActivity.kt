package com.example.mylib_test.activity.custom_view

import android.view.View
import android.widget.SeekBar

import com.example.mylib_test.R
import com.zone.lib.base.controller.activity.BaseFeatureActivity

import view.FlodLayout
import view.FlodLayoutGroup
import view.GestureView
import view.GestureView2

/**
 * Created by Administrator on 2016/4/14.
 */
class ChengChengActivity : BaseFeatureActivity() {
    override fun setContentView() {
        val type = intent.getStringExtra("type")
        if ("ges" == type) {
            setContentView(GestureView(this))
        } else if ("ges2" == type) {
            setContentView(GestureView2(this))
        } else if ("par" == type) {
            setContentView(R.layout.nb_test)
        } else if ("bt_foldViewGroup" == type) {
            setContentView(R.layout.flod_test2)
            flod2()
        } else if ("bt_fold" == type) {
            setContentView(R.layout.flod_test)
            flod()
        } else if ("bt_wheel" == type) {
            setContentView(R.layout.a_wheel)
        } else if ("FrameLayoutPadding" == type) {
            setContentView(R.layout.a_test_frame_padding)
        } else
            setContentView(R.layout.a_cheng)
    }

    private fun flod2() {
        val flod = findViewById<View>(R.id.flod) as FlodLayoutGroup
        val bar = findViewById<View>(R.id.seekBar) as SeekBar
        bar.max = 100
        bar.progress = 50
        bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                flod.setDepth(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })
        val seekBar2 = findViewById<View>(R.id.seekBar2) as SeekBar
        seekBar2.max = 100
        seekBar2.progress = 50
        seekBar2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                flod.setProgress(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })
    }

    private fun flod() {
        val flod = findViewById<View>(R.id.flod) as FlodLayout
        val bar = findViewById<View>(R.id.seekBar) as SeekBar
        bar.max = 100
        bar.progress = 50
        bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                flod.setDepth(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })
        val seekBar2 = findViewById<View>(R.id.seekBar2) as SeekBar
        seekBar2.max = 100
        seekBar2.progress = 50
        seekBar2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                flod.setProgress(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })
    }

    override fun initData() {

    }

    override fun setListener() {

    }
}
