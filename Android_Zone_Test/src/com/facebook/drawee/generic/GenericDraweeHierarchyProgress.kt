package com.facebook.drawee.generic

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter


//使用范例
//var hierarchy = GenericDraweeHierarchyBuilder.newInstance(getResources())
//    .setActualImageColorFilter(PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN))
//    .setPlaceholderImage(tinted)
//    .setFailureImage(tinted)
//    .setFadeDuration(0)
//    .build()
//imageView.setHierarchy(hierarchy);
//imageView.setImageURI(createWebpUrl(selected ? info.selectedIconUrl : info.normalIconUrl));

interface  ProgressListener{
    fun updateProgress(progress: Float)
}
class GenericDraweeHierarchyProgress : GenericDraweeHierarchy {
    var progressListener:ProgressListener?=null

    constructor(builder: GenericDraweeHierarchyBuilder?) : super(builder)

    override fun setProgress(progress: Float, immediate: Boolean) {
        super.setProgress(progress, immediate)
        progressListener?.updateProgress(progress)
    }
}