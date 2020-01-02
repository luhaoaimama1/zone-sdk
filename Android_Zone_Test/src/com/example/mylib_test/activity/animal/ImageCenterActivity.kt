package com.example.mylib_test.activity.animal

import com.example.mylib_test.R
import com.zone.lib.utils.image.compress2sample.SampleUtils
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import view.ImageCenter

/**
 * Created by Administrator on 2016/4/14.
 */
class ImageCenterActivity : BaseFeatureActivity() {

    override fun setContentView() {
        val view = ImageCenter(this)
        setContentView(view)
        val bt = SampleUtils.load(this, R.drawable.aaaaaaaaaaaab)
                .bitmap()
        view.bitmap = bt
    }

    override fun initData() {

    }

    override fun setListener() {

    }
}
