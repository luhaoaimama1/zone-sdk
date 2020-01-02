package com.example.mylib_test.activity.animal

import com.example.mylib_test.R
import android.graphics.Bitmap
import android.graphics.Bitmap.Config
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.view.View
import android.view.View.OnClickListener
import android.widget.EditText
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import kotlinx.android.synthetic.main.a_animal_colormarix.*

class ColorMarixTry : BaseFeatureActivity(), OnClickListener {

    private var bt: Bitmap? = null
    private val ed = arrayOfNulls<EditText>(20)
    private var temp: Bitmap? = null
    private var recycled: Bitmap? = null

    override fun setContentView() {
        setContentView(R.layout.a_animal_colormarix)
    }

    override fun initData() {

        bt = BitmapFactory.decodeResource(resources, R.drawable.abcd)
        for (i in ed.indices) {
            try {
                val id = R.id::class.java.getField("ed_$i").getInt(null)
                ed[i] = findViewById<View>(id) as EditText
                if (i == 0 || i == 6 || i == 12 || i == 18)
                    ed[i]?.setText("1")
                else
                    ed[i]?.setText("0")
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            }

        }

        setImage()
    }

    override fun setListener() {
    }

    private fun setImage() {
        recycled = temp
        val colorMatrix = ColorMatrix()
        val colorArr = colorMatrix.array
        for (i in colorArr.indices) {
            colorArr[i] = java.lang.Float.parseFloat(ed[i]?.getText().toString()?:"")
        }
        temp = Bitmap.createBitmap(bt!!.width, bt!!.height, Config.ARGB_8888)
        val canvas = Canvas(temp!!)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.isDither = true
        paint.colorFilter = ColorMatrixColorFilter(colorMatrix)
        canvas.drawBitmap(bt!!, 0f, 0f, paint)

        imageView1!!.setImageBitmap(temp)
        if (recycled != null && recycled != temp && !recycled!!.isRecycled) {
            recycled!!.recycle()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.reset -> reset()
            R.id.change -> setImage()
            else -> {
            }
        }
    }

    private fun reset() {
        for (i in ed.indices) {
            if (i == 0 || i == 6 || i == 12 || i == 18)
                ed[i]?.setText("1")
            else
                ed[i]?.setText("0")
        }
        setImage()
    }
}
