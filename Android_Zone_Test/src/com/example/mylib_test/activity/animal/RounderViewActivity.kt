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

class RounderViewActivity : BaseFeatureActivity() {
    override fun setContentView() {
        setContentView(R.layout.a_animal_rounder)
    }

    override fun initData() {
    }

    override fun setListener() {
    }
}
