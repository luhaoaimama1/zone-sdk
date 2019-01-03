package com.example.mylib_test.delegates

import android.net.Uri
import com.example.mylib_test.R
import com.example.mylib_test.activity.three_place.FrescoActivity
import com.facebook.drawee.view.SimpleDraweeView
import com.zone.adapter3.bean.Holder
import com.zone.adapter3.bean.ViewDelegates
import com.zone.okhttp.utils.MainHandlerUtils.onFailure
import android.graphics.drawable.Animatable
import android.util.Log
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.AbstractDraweeController
import com.facebook.drawee.generic.*
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.common.ImageDecodeOptions
import com.facebook.imagepipeline.image.ImageInfo


/**
 *[2018/7/10] by Zone
 */

class FrescoProcessorDeletates : ViewDelegates<FrescoActivity.Entity>() {

    override fun getLayoutId(): Int = R.layout.a_fresco_item

    override fun fillData(posi: Int, entity: FrescoActivity.Entity, holder: Holder<*>) {
        holder.setText(R.id.tv, entity.introduce+"看log:zone=")
        val sdv = holder.getView(R.id.sdv) as SimpleDraweeView
        load(sdv, entity.uri, R.color.orange)
    }

    fun load(simpleDraweeView: SimpleDraweeView, url: String, mPlaceholder: Int) {

        val decodeOptions = ImageDecodeOptions.newBuilder().setForceStaticImage(true)
            .setDecodePreviewFrame(true).build()


        //进度条
        if(simpleDraweeView.hierarchy !is GenericDraweeHierarchyProgress){
            val genericDraweeHierarchy = GenericDraweeHierarchyBuilderProgress(simpleDraweeView.resources).build() as GenericDraweeHierarchyProgress
            genericDraweeHierarchy.progressListener =object : ProgressListener {
                override fun updateProgress(progress: Float) {
                    //并不是每次都有~  走内存的话 不会走此此方法
                    Log.e("zone=","FrescoProcessor=>updateProgress:$progress")
                }
            }
            simpleDraweeView.hierarchy= genericDraweeHierarchy
            simpleDraweeView.hierarchy.apply {
                setPlaceholderImage(mPlaceholder)
            }
        }

        val request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
            .setImageDecodeOptions(decodeOptions)
            .build()


        val controller = Fresco.newDraweeControllerBuilder()
            .setOldController(simpleDraweeView.controller)
            //完成失败监听
            .setControllerListener(object : BaseControllerListener<ImageInfo>() {
                override fun onFinalImageSet(id: String?, imageInfo: ImageInfo?, animatable: Animatable?) {
                    super.onFinalImageSet(id, imageInfo, animatable)
                    Log.e("zone=","FrescoProcessor=>onFinalImageSet")
                }

                override fun onFailure(id: String?, throwable: Throwable?) {
                    super.onFailure(id, throwable)
                    Log.e("zone=","FrescoProcessor=>onFailure")
                }

                override fun onSubmit(id: String?, callerContext: Any?) {
                    super.onSubmit(id, callerContext)
                    Log.e("zone=","FrescoProcessor=>onSubmit")
                }
            })
            .setImageRequest(request)
            .build()
        simpleDraweeView.controller = controller
    }

}