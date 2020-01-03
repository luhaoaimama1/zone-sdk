package com.example.mylib_test.adapter.delegates

import com.example.mylib_test.R
import com.zone.adapter3kt.data.DataWarp
import zone.com.zadapter3kt.adapterimpl.HolderExDemoImpl
import zone.com.zadapter3kt.adapterimpl.ViewDelegatesDemo

/**
 *[2018/7/10] by Zone
 */
class TextType2Delegates: ViewDelegatesDemo<String>(){
    override val layoutId: Int=R.layout.item_rc_textview

    override fun onBindViewHolder(position: Int, item: DataWarp<String>, baseHolder: HolderExDemoImpl, payloads: List<*>) {
        item.data?.let {
            baseHolder.setText(R.id.tv, it)
        }
    }
}