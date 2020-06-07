package com.zone.lib.base.controller.activity.controller

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.text.format.DateFormat
import com.zone.lib.LogZSDK
import com.zone.lib.base.controller.RequestCodeConfig
import com.zone.lib.utils.data.convert.Uri2PathUtil
import com.zone.lib.utils.data.file2io2data.FileUtils
import com.zone.lib.utils.data.file2io2data.SDCardUtils
import com.zone.lib.base.controller.activity.base.ActivityController
import com.zone.lib.base.controller.activity.base.FeatureActivity
import java.io.File
import java.util.*

abstract class PictureActivityController(activity: FeatureActivity) : ActivityController<FeatureActivity>(activity) ,
        PictureContract.Controller{
    companion object {
        private var path: String? = null

        private var outFile = FileUtils.getFile(SDCardUtils.getSDCardDir(), "Zone", "picSave")
    }

    enum class Type {
        None, Camera, Photos, PickPicture;
    }

    /**
     * 判断文件是否存在
     * @return
     */
    private val isFileExist: Boolean
        get() {
            val file = File(path!!)
            return file.exists()
        }

    override fun openCamera() {
        val picName = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)).toString() + ".jpg"
        LogZSDK.d("照片名格式：yyyyMMdd_hhmmss.jpg")
        outFile = File(outFile, picName)
        path = outFile.path
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile))
        getActivity()?.startActivityForResult(intent, RequestCodeConfig.getRequestCode(RequestCodeConfig.Feature_Pic_REQUESTCODE_CAMERA))
    }

    override fun openPhotos() {
        val intent = Intent(Intent.ACTION_PICK, null)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        getActivity()?.startActivityForResult(intent, RequestCodeConfig.getRequestCode(RequestCodeConfig.Feature_Pic_REQUESTCODE_PHOTOS))
    }

    override fun pickPicture() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        getActivity()?.startActivityForResult(intent, RequestCodeConfig.getRequestCode(RequestCodeConfig.Feature_Pic_REQUESTCODE_PICK_PHOTOS))
    }

    /**
     *
     * @param path 照片的路径  已经返回了 你调用就行
     */
    protected abstract fun getReturnedPicPath(path: String?, type: Type)

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode == -1) {
            when (RequestCodeConfig.getSwitchRequestCode(requestCode)) {
                RequestCodeConfig.Feature_Pic_REQUESTCODE_CAMERA ->
                    if (isFileExist) {
                        getReturnedPicPath(path, Type.Camera)
                    } else getReturnedPicPath(null, Type.Camera)

                RequestCodeConfig.Feature_Pic_REQUESTCODE_PHOTOS -> {
                    val data = intent?.data
                    val activity = getActivity()
                    if (activity != null && data != null) {
                        getReturnedPicPath(Uri2PathUtil.getRealPathFromUri(activity, data), Type.Photos)
                    } else {
                        getReturnedPicPath(null, Type.Photos)
                    }
                }
                RequestCodeConfig.Feature_Pic_REQUESTCODE_PICK_PHOTOS -> {
                    val data = intent?.data
                    val activity = getActivity()
                    if (activity != null && data != null) {
                        getReturnedPicPath(Uri2PathUtil.getRealPathFromUri(activity, data), Type.PickPicture)
                    } else {
                        getReturnedPicPath(null, Type.PickPicture)
                    }
                }
            }
        }
    }
}
