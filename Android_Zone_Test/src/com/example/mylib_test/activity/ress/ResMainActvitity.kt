package com.example.mylib_test.activity.ress

import android.view.View
import com.example.mylib_test.R

import com.zone.lib.base.controller.activity.BaseFeatureActivity
import kotlinx.android.synthetic.main.a_resmain.*
import view.ImageTextView

/**
 * Created by Administrator on 2016/5/14.
 */
class ResMainActvitity : BaseFeatureActivity() {
    override fun setContentView() {
        setContentView(R.layout.a_resmain)
    }


    override fun initData() {

    }

    override fun setListener() {

    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v?.id) {
            R.id.itv -> {
                itv.setDrawable(ImageTextView.Bottom, -1,100f, 100f, 50f)
                itv.setDrawable(ImageTextView.LEFT, R.drawable.a1,100f, 100f, 50f)
//                itv.setDrawable(ImageTextView.RIGHT, R.drawable.a1,100f, 100f, 50f)
            }
            else -> {
            }
        }
    }

}
