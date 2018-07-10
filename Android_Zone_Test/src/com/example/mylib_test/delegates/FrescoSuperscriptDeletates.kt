package com.example.mylib_test.delegates

import android.net.Uri
import android.support.v4.content.ContextCompat
import com.example.mylib_test.R
import com.example.mylib_test.activity.three_place.FrescoActivity
import com.example.mylib_test.activity.three_place.utils.SuperscriptDrawable
import com.facebook.drawee.view.SimpleDraweeView
import com.zone.adapter3.bean.Holder
import com.zone.adapter3.bean.ViewDelegates
import com.zone.lib.utils.data.convert.DensityUtils

/**
 *[2018/7/10] by Zone
 */

class FrescoSuperscriptDeletates : ViewDelegates<FrescoActivity.Entity>(){
    override fun getLayoutId(): Int = R.layout.a_fresco_item

    override fun fillData(posi: Int, entity: FrescoActivity.Entity?, holder: Holder<out Holder<*>>?) {
        holder!!.setText(R.id.tv, entity?.introduce);
        val sdv =holder.getView(R.id.sdv) as SimpleDraweeView;
        val uri = Uri.parse(entity?.uri);
        sdv.setImageURI(uri);
        val width = DensityUtils.dp2px(context, 20f);
        val drawable = SuperscriptDrawable(ContextCompat.getDrawable(context,R.drawable.icon));
        sdv.getHierarchy().setOverlayImage(drawable);
    }

}