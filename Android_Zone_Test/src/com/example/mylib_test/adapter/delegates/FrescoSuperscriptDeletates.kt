package com.example.mylib_test.adapter.delegates

import android.net.Uri
import androidx.core.content.ContextCompat
import com.example.mylib_test.R
import com.example.mylib_test.activity.three_place.FrescoActivity
import com.example.mylib_test.activity.three_place.utils.SuperscriptDrawable
import com.facebook.drawee.view.SimpleDraweeView
import com.zone.adapter3kt.data.DataWarp
import com.zone.lib.utils.data.convert.DensityUtils
import zone.com.zadapter3kt.adapterimpl.HolderExDemoImpl
import zone.com.zadapter3kt.adapterimpl.ViewDelegatesDemo

/**
 *[2018/7/10] by Zone
 */

class FrescoSuperscriptDeletates : ViewDelegatesDemo<FrescoActivity.Entity>() {
    override val layoutId: Int= R.layout.a_fresco_item

    override fun onBindViewHolder(position: Int, item: DataWarp<FrescoActivity.Entity>, baseHolder: HolderExDemoImpl, payloads: List<*>) {
        item.data?.let {
            baseHolder.setText(R.id.tv, it.introduce);
            val sdv = baseHolder.getView(R.id.sdv) as SimpleDraweeView;
            val uri = Uri.parse(it.uri);
            sdv.setImageURI(uri);
            val width = DensityUtils.dp2px(baseHolder.itemView.context, 20f)
            val drawable1 = ContextCompat.getDrawable(baseHolder.itemView.context, R.drawable.icon)
            if (drawable1 == null) return
            val drawable = SuperscriptDrawable(drawable1);
            sdv.getHierarchy().setOverlayImage(drawable);
        }

    }
}