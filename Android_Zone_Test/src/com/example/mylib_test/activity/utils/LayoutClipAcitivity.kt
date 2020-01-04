package com.example.mylib_test.activity.utils

import com.example.mylib_test.R
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import com.zone.view.FlowLayout
import com.zone.lib.utils.view.AnimationUtils
import com.zone.lib.utils.view.ViewUtils
import kotlinx.android.synthetic.main.a_layout_clip.*

/**
 * Created by fuzhipeng on 16/9/29.
 */

class LayoutClipAcitivity : BaseFeatureActivity() {

    override fun setContentView() {
        setContentView(R.layout.a_layout_clip)
    }

    override fun initData() {

    }

    override fun setListener() {
        ll_main.setOnClickListener {
            val parent: FlowLayout = FlowLayout(this)
            val ivs = ViewUtils.clipView(iv_clip, parent, 3, 3)
            parent.post {
                AnimationUtils.explode(ivs, 3)//不加这个侧脸不出view 的宽高 就没办法计算了 其实能但是这个省事;
            }
        }
    }

}
