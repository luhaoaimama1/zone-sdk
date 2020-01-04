package com.example.mylib_test.activity.animal


import com.example.mylib_test.R

import android.graphics.Bitmap
import android.graphics.Bitmap.Config
import android.graphics.BitmapFactory
import android.graphics.Color
import android.widget.ImageView
import com.zone.lib.base.controller.activity.BaseFeatureActivity

class PixelsAcitivity : BaseFeatureActivity() {
    private lateinit var iv: ImageView
    private lateinit var bt: Bitmap
    private lateinit var pixels: IntArray
    private lateinit var newPixels: IntArray
    private lateinit var newBt: Bitmap
    override fun setContentView() {
        iv = ImageView(this)
        setContentView(iv)
    }

    override fun initData() {
        bt = BitmapFactory.decodeResource(resources, R.drawable.abcd)
        //Android系统不允许修改原图
        newBt = Bitmap.createBitmap(bt.width, bt.height, Config.ARGB_8888)

        pixels = IntArray(bt.width * bt.height)
        //得到所有的像素点
        bt.getPixels(pixels, 0, bt.width, 0, 0, bt.width, bt.height)

        println(" pixels.length:" + pixels.size)
        newPixels = IntArray(pixels.size)
        for (i in pixels.indices) {
            //得到每个像素点的像素值
            val color = pixels[i]
            //得到对应的ARGB值
            val r = Color.red(color)
            val g = Color.green(color)
            val b = Color.blue(color) * 0//去掉蓝色
            val a = Color.alpha(color)
            //通过argb合成 像素值
            newPixels[i] = Color.argb(a, r, g, b)
        }
        newBt.setPixels(newPixels, 0, bt.width, 0, 0, bt.width, bt.height)
        iv.setImageBitmap(newBt)
    }

    override fun setListener() {
    }
}
