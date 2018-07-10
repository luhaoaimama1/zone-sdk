package com.example.mylib_test.delegates

import android.net.Uri
import com.example.mylib_test.R
import com.example.mylib_test.activity.three_place.FrescoActivity
import com.facebook.drawee.view.SimpleDraweeView
import com.zone.adapter3.bean.Holder
import com.zone.adapter3.bean.ViewDelegates

/**
 *[2018/7/10] by Zone
 */

class FrescoDeletates : ViewDelegates<FrescoActivity.Entity>() {

    override fun getLayoutId(): Int = R.layout.a_fresco_item

    override fun fillData(posi: Int, entity: FrescoActivity.Entity, holder: Holder<*>) {
        holder.setText(R.id.tv, entity.introduce)
        val sdv = holder.getView(R.id.sdv) as SimpleDraweeView
        val uri = Uri.parse(entity.uri)
        sdv.setImageURI(uri)
    }
}