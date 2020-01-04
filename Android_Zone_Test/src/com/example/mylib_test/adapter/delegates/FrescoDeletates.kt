package com.example.mylib_test.adapter.delegates

import android.net.Uri
import com.example.mylib_test.R
import com.example.mylib_test.activity.three_place.FrescoActivity
import com.facebook.drawee.view.SimpleDraweeView
import com.zone.adapter3kt.data.DataWarp
import zone.com.zadapter3kt.adapterimpl.HolderExDemoImpl
import zone.com.zadapter3kt.adapterimpl.ViewDelegatesDemo

/**
 *[2018/7/10] by Zone
 */

class FrescoDeletates : ViewDelegatesDemo<FrescoActivity.Entity>() {

    override val layoutId: Int=R.layout.a_fresco_item

    override fun onBindViewHolder(position: Int, item: DataWarp<FrescoActivity.Entity>, baseHolder: HolderExDemoImpl, payloads: List<*>) {
        item.data?.let {
            baseHolder.setText(R.id.tv, it.introduce)
            val sdv = baseHolder.getView(R.id.sdv) as SimpleDraweeView
            val uri = Uri.parse(it.uri)
            sdv.setImageURI(uri)
        }

    }
}