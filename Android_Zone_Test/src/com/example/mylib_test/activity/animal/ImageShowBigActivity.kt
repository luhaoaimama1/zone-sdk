package com.example.mylib_test.activity.animal

import android.view.View
import android.widget.ImageView
import com.example.mylib_test.R
import com.zone.lib.utils.activity_fragment_ui.MeasureUtils
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import kotlinx.android.synthetic.main.a_imageshowbig.*

/**
 * Created by Administrator on 2016/4/14.
 */
class ImageShowBigActivity : BaseFeatureActivity() {
    internal var scaleType: ImageView.ScaleType? = null
    private var next: Int = 0

    override fun setContentView() {
        setContentView(R.layout.a_imageshowbig)
    }

    override fun initData() {

    }

    override fun setListener() {

    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v?.id) {
            R.id.bt -> {
                if (scaleType != null)
                    if (scaleType!!.ordinal == ImageView.ScaleType.values().size - 1)
                        next = 0
                    else
                        next = scaleType!!.ordinal + 1
                else
                    next = 0
                iv.scaleType = ImageView.ScaleType.values()[next]
                scaleType = iv.scaleType
                bt.text = scaleType!!.toString()
                MeasureUtils.measureImage(iv!!) { iv, left, top, imageShowX, imageShowY -> println("scaleType :" + iv.scaleType + "\t left:" + left + " \ttop：" + top + " \timageShowX：" + imageShowX + "\timageShowY：" + imageShowY) }
            }
            R.id.bt_refresh -> if (scaleType != null) {
                iv!!.scaleType = scaleType
                bt!!.text = scaleType!!.toString()
                MeasureUtils.measureImage(iv!!) { iv, left, top, imageShowX, imageShowY -> println("scaleType :" + iv.scaleType + "\t left:" + left + " \ttop：" + top + " \timageShowX：" + imageShowX + "\timageShowY：" + imageShowY) }
            }
            else -> {

            }
        }
    }

}
