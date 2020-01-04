package com.example.mylib_test.activity.animal

import android.view.View

import com.example.mylib_test.R
import com.example.mylib_test.activity.animal.helper.PopXfermode
import com.zone.lib.base.controller.activity.BaseFeatureActivity

import kotlinx.android.synthetic.main.a_animal_xfermode.*


/**
 * Created by Administrator on 2016/3/21.
 */
class XfermodeActivity : BaseFeatureActivity() {
    private lateinit var popXfermode: PopXfermode

    internal var borderIsCircleProperty = false

    override fun setContentView() {
        setContentView(R.layout.a_animal_xfermode)
    }

    override fun initData() {
        xfermodeView.setBordMode(borderIsCircleProperty)
        popXfermode = PopXfermode(this, xfermodeView, bt_pop)
    }

    override fun setListener() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_pop -> popXfermode.show()
            R.id.borderIsCircle -> {
                borderIsCircleProperty = !borderIsCircleProperty
                xfermodeView.setBordMode(borderIsCircleProperty)
                if (borderIsCircleProperty)
                    borderIsCircle.text = "边界是圆"
                else
                    borderIsCircle.text = "边界是手机大小"
            }
            else -> {
            }
        }
        super.onClick(v)
    }
}
