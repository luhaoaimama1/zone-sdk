package com.example.mylib_test.delegates

import com.example.mylib_test.R
import com.zone.adapter3.bean.Holder
import com.zone.adapter3.bean.ViewDelegates

/**
 *[2018/7/10] by Zone
 */
class TextDelegates : ViewDelegates<String>() {

    override fun getLayoutId(): Int = R.layout.item_recycler
    override fun fillData(i: Int, s: String?, holder: Holder<out Holder<*>>?) {
        holder!!.setText(R.id.id_num, s)
    }
}
