package com.example.mylib_test.delegates

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import com.example.mylib_test.R
import com.example.mylib_test.activity.three_place.FrescoActivity
import com.facebook.drawee.view.SimpleDraweeView
import com.zone.adapter3.bean.Holder
import com.zone.adapter3.bean.ViewDelegates
import com.zone.okhttp.utils.MainHandlerUtils.onFailure
import android.graphics.drawable.Animatable
import android.os.Build
import android.util.Log
import com.facebook.cache.common.CacheKey
import com.facebook.cache.common.SimpleCacheKey
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerFactory
import com.facebook.drawee.controller.AbstractDraweeController
import com.facebook.drawee.controller.ControllerListener
import com.facebook.drawee.generic.*
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.common.ImageDecodeOptions
import com.facebook.imagepipeline.core.ImagePipeline
import com.facebook.imagepipeline.image.ImageInfo
import com.facebook.imagepipeline.request.BasePostprocessor


/**
 *[2018/7/10] by Zone
 */

class FrescoProcessorDealDeletates : ViewDelegates<FrescoActivity.Entity>() {

    override fun getLayoutId(): Int = R.layout.a_fresco_item

    override fun fillData(posi: Int, entity: FrescoActivity.Entity, holder: Holder<*>) {
        holder.setText(R.id.tv, entity.introduce + "看log:zone=")
        val sdv = holder.getView(R.id.sdv) as SimpleDraweeView
        load(sdv, entity.uri, R.color.font_focus)
    }

    fun load(simpleDraweeView: SimpleDraweeView, url: String, mPlaceholder: Int) {

        val decodeOptions = ImageDecodeOptions.newBuilder().setForceStaticImage(true)
            .setDecodePreviewFrame(true).build()

        //进度条
        if (simpleDraweeView.hierarchy !is GenericDraweeHierarchyProgress) {
            val genericDraweeHierarchy = GenericDraweeHierarchyBuilderProgress(simpleDraweeView.resources).build() as GenericDraweeHierarchyProgress
            genericDraweeHierarchy.progressListener = object : ProgressListener {
                override fun updateProgress(progress: Float) {
                    //并不是每次都有~  走内存的话 不会走此此方法
                    Log.e("zone=", "FrescoProcessor=>updateProgress:$progress")
                }
            }
            simpleDraweeView.hierarchy = genericDraweeHierarchy
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
                    Log.e("zone=", "FrescoProcessor=>onFinalImageSet")

                    val request2 =  ImageRequestBuilder.fromRequest(request)
                        .setPostprocessor(BlurPostprocessor())
                        .build()

                    val controller =Fresco.newDraweeControllerBuilder()
                        .setOldController(simpleDraweeView.controller)
                        .setImageRequest(request2)
                        .build()
                    simpleDraweeView.controller = controller
                }

                override fun onFailure(id: String?, throwable: Throwable?) {
                    super.onFailure(id, throwable)
                    Log.e("zone=", "FrescoProcessor=>onFailure")
                }

                override fun onSubmit(id: String?, callerContext: Any?) {
                    super.onSubmit(id, callerContext)
                    Log.e("zone=", "FrescoProcessor=>onSubmit")
                }
            })
            .setImageRequest(request)
            .build()
        simpleDraweeView.controller = controller
    }

class  PipelineDraweeControllerBuilderV2 : PipelineDraweeControllerBuilder {
    constructor(context: Context?, pipelineDraweeControllerFactory: PipelineDraweeControllerFactory?, imagePipeline: ImagePipeline?, boundControllerListeners: MutableSet<ControllerListener<Any>>?) : super(context, pipelineDraweeControllerFactory, imagePipeline, boundControllerListeners)

}
    class BlurPostprocessor : BasePostprocessor() {
        override fun process(bitmap: Bitmap?) {
            super.process(bitmap)
            bitmap?.let {
                for (x in 0 until bitmap.width step 2) {
                    for (y in 0 until  bitmap.height step 2) {
                        bitmap.setPixel(x, y, Color.RED)
                    }
                }
            }
        }
    }
}