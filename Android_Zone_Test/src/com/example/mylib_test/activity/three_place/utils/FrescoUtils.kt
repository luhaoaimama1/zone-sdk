package com.example.mylib_test.activity.three_place.utils

import android.content.Context
import com.example.mylib_test.adapter.delegates.FrescoProcessorDealDeletates
import com.facebook.common.executors.UiThreadImmediateExecutorService
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.interfaces.DraweeHierarchy
import com.facebook.drawee.view.DraweeView
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder

object FrescoUtils{
    // 需要了解的方法 ：simpleDraweeView.setImageRequest(request2)

    fun loadBitmap(context: Context, imageRequest:ImageRequest,listener:BaseBitmapDataSubscriber) {
        val imagePipeline = Fresco.getImagePipeline()
        val dataSource = imagePipeline.fetchDecodedImage(imageRequest, context)
        dataSource.subscribe(listener, UiThreadImmediateExecutorService.getInstance())
    }

    fun setRequest(draweeView: DraweeView<DraweeHierarchy>, imageRequest:ImageRequest){
        val request2 =  ImageRequestBuilder.fromRequest(imageRequest)
            .setPostprocessor(FrescoProcessorDealDeletates.BlurPostprocessor())
            .build()

        val controller =Fresco.newDraweeControllerBuilder()
            .setOldController(draweeView.controller)
            .setImageRequest(request2)
            .build()
        draweeView.controller = controller
    }

}