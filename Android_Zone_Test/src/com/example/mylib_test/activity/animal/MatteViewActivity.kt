package com.example.mylib_test.activity.animal

import com.example.mylib_test.R
import com.nineoldandroids.view.ViewHelper

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.PointF
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.view.View
import android.view.animation.LinearInterpolator
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import com.zone.lib.utils.data.file2io2data.FileUtils
import com.zone.lib.utils.data.file2io2data.SDCardUtils
import kotlinx.android.synthetic.main.a_ae_matte_view.*
import kotlinx.android.synthetic.main.a_ae_matte_view.iv
import kotlinx.android.synthetic.main.a_anipro.*
import kotlinx.android.synthetic.main.a_glide.*

class MatteViewActivity : BaseFeatureActivity() {
    override fun setContentView() {
//        setContentView(R.layout.a_ae_matte_view)
        setContentView(R.layout.a_ae_matte_view2)
    }

    override fun initData() {
//        mcl.porterDuffXfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
//        Glide.with(this)
////            .load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fgif.55.la%2Fuploads%2F20210318%2F9d2da813391c0fda079e64f15cdc0e88.gif&refer=http%3A%2F%2Fgif.55.la&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1662646776&t=cc159ce8555731bcbdbecade3ff8f626")
//            .load("https://ss0.baidu.com/7Po3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/c75c10385343fbf26df6edd1b67eca8064388f6e.jpg")
//            .asGif()
//            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//            .fitCenter()
//            .placeholder(R.drawable.ic_stub).dontAnimate()
//            .error(R.drawable.ic_error)
//            .into(iv);
    }

    override fun setListener() {
    }
}
