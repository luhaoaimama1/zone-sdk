package com.example.mylib_test.base.controller


import java.io.File
import java.util.Calendar
import java.util.Locale

import com.zone.lib.LogZSDK
import com.zone.lib.base.controller.RequestCodeConfig
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.text.format.DateFormat
import com.zone.lib.utils.data.convert.Uri2PathUtil
import com.zone.lib.base.controller.activity.ActivityController
import com.zone.lib.base.controller.activity.FeatureActivity

abstract class SystemClipActivityController(activity: FeatureActivity, val saveFolder: String) : ActivityController<FeatureActivity>(activity) {
    private var savePath: Uri? = null

    fun cropImageUri(path: String) {
        val uri = Uri.parse("file:///$path")
        //小米特殊的intent action
        //		不难知道，我们从相册选取图片的Action为Intent.ACTION_GET_CONTENT。
        //		 Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        val intent = Intent("com.android.camera.action.CROP")
        //可以选择图片类型，如果是*表明所有类型的图片
        intent.setDataAndType(uri, "image/*")
        // 下面这个crop = true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true")
        //裁剪时是否保留图片的比例，这里的比例是1:1
        intent.putExtra("explode", true)

        // 这两项为裁剪框的比例.   固定比率　　就同时缩放了。。。
        //		 intent.putExtra("aspectX", 2);
        //		 intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高　　就是固定裁剪图的大小了　不要固定
        //		 intent.putExtra("outputX", outputX);
        //		 intent.putExtra("outputY", outputY);

        //是否将数据保留在Bitmap中返回
        intent.putExtra("return-data", true)
        //设置输出的格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        intent.putExtra("noFaceDetection", true) // no face detection

        val picName = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)).toString() + ".jpg"
        val saveFile = File(saveFolder, picName)
        savePath = Uri.fromFile(saveFile)
        LogZSDK.d("savePath Uri:" + savePath!!)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, savePath)//输出地址
        getActivity()?.startActivityForResult(intent, RequestCodeConfig.getRequestCode(RequestCodeConfig.Feature_SystemClip_REQUESTCODE_Clip))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        when (RequestCodeConfig.getSwitchRequestCode(requestCode)) {
            RequestCodeConfig.Feature_SystemClip_REQUESTCODE_Clip -> if (intent != null) {
                LogZSDK.d("onActivityResult  savePath:" + savePath!!)
                getReturnedClipPath(Uri2PathUtil.getRealPathFromUri(getActivity(), savePath))
            }
        }
    }

    abstract fun getReturnedClipPath(savePath: String?)
}

