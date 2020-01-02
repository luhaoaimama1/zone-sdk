package com.example.mylib_test.activity.animal

import android.graphics.Bitmap
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Matrix
import android.graphics.PorterDuff
import android.widget.SeekBar

import com.example.mylib_test.R
import com.zone.lib.base.controller.activity.BaseFeatureActivity

import com.zone.lib.utils.image.WaveHelper
import com.zone.lib.utils.image.compress2sample.SampleUtils
import com.zone.lib.utils.image.BitmapComposer
import kotlinx.android.synthetic.main.a_animal_wave.*

//参考:https://github.com/race604/WaveLoading
class WaveActivity : BaseFeatureActivity() {
    var waveHelper: WaveHelper? = null
    override fun setContentView() {
        setContentView(R.layout.a_animal_wave)
    }

    override fun initData() {
        image2.post {
            val bt = SampleUtils.load(this@WaveActivity, R.drawable.aaaaaaaaaaaab)
                    .bitmap()
            val colorMatrix = ColorMatrix()
            colorMatrix.setSaturation(0f)

            val bitmapComposer = BitmapComposer.newComposition(bt.width, bt.height, Bitmap.Config.ARGB_8888)
            val first = Matrix()
            first.postTranslate(0f, -20f)
            waveHelper = WaveHelper(bt.width, bt.height, object : WaveHelper.RefreshCallback {
                override fun refresh(wave: Bitmap) {
                    val mb = BitmapComposer.Layer.bitmap(bt)
                            .colorFilter(ColorMatrixColorFilter(colorMatrix))
                    val kb = BitmapComposer.Layer.bitmap(bt)
//    .colorFilter(new ColorMatrixColorFilter(colorMatrix))
                            .mask(wave, PorterDuff.Mode.DST_IN)
//                            .matrix(first)
                    val render = bitmapComposer.clear()
                            .newLayer(mb)
                            .newLayer(kb)
                            .render()
                    image2.setImageBitmap(render)
                }
            })
        }
    }

    override fun setListener() {
        level_seek.setOnSeekBarChangeListener(object : SimpleOnSeekBarChangeListener() {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                bt_wave.setLevel(progress.toFloat() / seekBar.max)
                waveHelper?.setLevelProgress(progress.toFloat() / seekBar.max)
            }
        })
        amplitude_seek.setOnSeekBarChangeListener(object : SimpleOnSeekBarChangeListener() {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                bt_wave.setWaveAmplitude(progress)
                waveHelper?.amplitude = progress.toFloat()
            }
        })

        length_seek.setOnSeekBarChangeListener(object : SimpleOnSeekBarChangeListener() {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                bt_wave.setWaveLength(progress)
                waveHelper?.length = progress.toFloat()
            }
        })

        speed_seek.setOnSeekBarChangeListener(object : SimpleOnSeekBarChangeListener() {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                bt_wave.setWaveSpeed(progress)
                waveHelper?.speed = progress.toFloat()
            }
        })
        speed_OffsetXLength.setOnSeekBarChangeListener(object : SimpleOnSeekBarChangeListener() {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                bt_wave.setOffsetXRadioOfLength(progress.toFloat() / seekBar.max)
                waveHelper?.setOffsetXRadioOfLength(progress.toFloat() / seekBar.max)
            }
        })


    }

    open class SimpleOnSeekBarChangeListener : SeekBar.OnSeekBarChangeListener {

        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            // Nothing
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {
            // Nothing
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            // Nothing
        }
    }

}
