package com.example.mylib_test.activity.animal

import android.widget.SeekBar

import com.example.mylib_test.R
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import kotlinx.android.synthetic.main.a_animal_damping.*


/**
 * Created by fuzhipeng on 16/7/29.
 */
class DampingActivity : BaseFeatureActivity() {
    override fun setContentView() {
        setContentView(R.layout.a_animal_damping)
    }

    override fun initData() {

    }

    override fun setListener() {
        maxX.setOnSeekBarChangeListener(object : WaveActivity.SimpleOnSeekBarChangeListener() {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                bt_wave.setMaxX(progress.toFloat())
            }
        })

        maxY.setOnSeekBarChangeListener(object : WaveActivity.SimpleOnSeekBarChangeListener() {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                bt_wave.setMaxY(progress.toFloat())
            }
        })

        dampingRadio.setOnSeekBarChangeListener(object : WaveActivity.SimpleOnSeekBarChangeListener() {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                bt_wave.setDampingRadio(progress.toFloat())
            }
        })
    }

}
