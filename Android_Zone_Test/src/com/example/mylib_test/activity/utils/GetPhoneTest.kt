package com.example.mylib_test.activity.utils

import com.zone.lib.utils.activity_fragment_ui.ToastUtils
import com.zone.lib.utils.view.special_view.editview.SpannableUtils
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.TextView
import com.zone.lib.base.controller.activity.BaseFeatureActivity

class GetPhoneTest : BaseFeatureActivity() {
    override fun setContentView() {
        val tv = TextView(this)
        tv.layoutParams = LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT)
        tv.gravity = Gravity.CENTER
        SpannableUtils.contentToPhone(tv, this, Content, Color.RED) { widget, phone -> ToastUtils.showLong(this@GetPhoneTest, phone) }
        setContentView(tv)
    }

    override fun initData() {
    }

    override fun setListener() {
    }

    companion object {
        private val Content = ("wo185106400111we18510640011ishenmald185-1064-0011klajldj"
                + "185-1064-0011fadfjladjkfl4002342222akjdfajdflkajdfl400-234-2222ajdflka22223334jld"
                + "fjl2222-3334ajd 2222-33344aa133-7015-6232")
    }
}
