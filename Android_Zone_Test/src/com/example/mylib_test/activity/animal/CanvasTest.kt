package com.example.mylib_test.activity.animal

import com.example.mylib_test.R
import com.example.mylib_test.activity.animal.utils.test.MatrixStudy

import com.zone.lib.utils.image.BitmapUtils

import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import com.example.mylib_test.activity.animal.viewa.*
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import view.LrcView

class CanvasTest : BaseFeatureActivity() {

    internal var values = FloatArray(9)
    internal var ed = arrayOfNulls<EditText>(9)
    override fun setContentView() {
        val type = intent.getStringExtra("type")
        if ("layer" == type) {
            setContentView(Canvas1(this))
        }
        if ("shader" == type) {
            setContentView(R.layout.a_shader)
        }
        if ("bt_surface" == type) {
            setContentView(SimpleDraw(this))
        }
        if ("bt_matrixMethod" == type) {
            setContentView(MatrixMethod(this))
        }
        if ("bt_MatrixPre" == type) {
            setContentView(MatrixView(this))
        }
        if ("bt_MatrixStudy" == type) {
            setContentView(R.layout.a_animal_matrixstudy)
            initMatrixStudy()
        }
        if ("bt_bitmap" == type) {
            setContentView(R.layout.a_btimap_copy)
        }
        if ("bt_draw" == type) {
            setContentView(BaseDraw(this))
        }
        if ("bt_bitmaptoRound" == type) {
            val iv = ImageView(this)
            iv.setImageBitmap(BitmapUtils.toRoundBitmap(BitmapFactory.decodeResource(resources, R.drawable.abcd), true))
            iv.setBackgroundColor(Color.YELLOW)
            setContentView(iv)
        }
        if ("bt_bitmaptoRorate" == type) {
            val iv = ImageView(this)
            iv.setImageBitmap(BitmapUtils.rotateBitmap(BitmapFactory.decodeResource(resources, R.drawable.abcd), 45, true))
            iv.setBackgroundColor(Color.YELLOW)
            setContentView(iv)
        }
        if ("bt_bitmaptoScale" == type) {
            val iv = ImageView(this)
            iv.setImageBitmap(BitmapUtils.scaleBitmap(BitmapFactory.decodeResource(resources, R.drawable.abcd), 0.5f, 0.5f, true))
            iv.scaleType = ScaleType.CENTER
            iv.setBackgroundColor(Color.YELLOW)
            setContentView(iv)
        }
        if ("bt_drawText" == type) {
            setContentView(DrawTextView(this))
        }
        if ("bt_drawTextUtils" == type) {
            setContentView(DrawTextTestView(this))
        }
        if ("bt_LightingColorFilter" == type) {
            setContentView(LightingColorFilterView(this))
        }
        if ("bt_glow" == type) {
            setContentView(GlowView(this))
        }

        if ("LrcView" == type) {
            val lrcView = LrcView(this)
            lrcView.setPaintDp(20f, 14f, 6f, 8f, 22f, 34f);
            setContentView(lrcView)
        }

    }

    override fun initData() {
    }

    override fun setListener() {
    }

    private fun initMatrixStudy() {
        val matrix = Matrix()
        matrix.getValues(values)

        for (i in values.indices) {
            try {
                val id = R.id::class.java.getField("ed_$i").getInt(null)
                ed[i] = findViewById<View>(id) as EditText
                ed[i]?.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

                    }

                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                    }

                    override fun afterTextChanged(s: Editable) {

                        try {
                            val number = java.lang.Float.parseFloat(s.toString())
                            (findViewById<View>(R.id.ms) as MatrixStudy).set(i, number)
                        } catch (e: Exception) {
                        }

                    }
                })
                ed[i]?.setText(values[i].toString() + "")
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            }

        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.reset -> reset()

            else -> {
            }
        }
    }

    private fun reset() {
        val matrix = Matrix()
        matrix.getValues(values)
        for (i in values.indices) {
            ed[i]?.setText(values[i].toString() + "")
        }
    }
}
