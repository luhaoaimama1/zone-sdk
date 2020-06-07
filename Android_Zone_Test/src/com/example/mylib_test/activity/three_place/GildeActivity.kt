package com.example.mylib_test.activity.three_place

import android.content.Context
import android.net.Uri
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mylib_test.R
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import com.zone.lib.utils.data.file2io2data.FileUtils
import com.zone.lib.utils.data.file2io2data.SDCardUtils
import kotlinx.android.synthetic.main.a_glide.*

/**
 *[2018/7/10] by Zone
 */
class GildeActivity : BaseFeatureActivity() {
    enum class Type { HTTP, File, Resource, Uri, Gif, Mp4, Error; }
    var type = Type.HTTP

    override fun setContentView() {
        setContentView(R.layout.a_glide)

    }

    override fun initData() {
        setImageBitmap()
    }

    override fun setListener() {
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v?.getId()) {
            R.id.bt -> {
                when (type) {
                    Type.HTTP-> type = Type.File
                    Type.File-> type = Type.Resource
                    Type.Resource-> type = Type.Uri
                    Type.Uri-> type = Type.Gif
                    Type.Gif-> type = Type.Error
                    Type.Error-> type = Type.Mp4
                    Type.Mp4-> type = Type.HTTP
                }
                setImageBitmap()
            }
        }
    }

    private fun setImageBitmap() {

        when (type) {
            GildeActivity.Type.HTTP -> {
                Glide.with(this).load("http://ww4.sinaimg.cn/mw1024/005PquKVgw1ezrgh5ppeyj30ku0kujvv.jpg")
                        .placeholder(R.drawable.ic_stub).dontAnimate()
//                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(iv)
                bt.setText("HTTP")
            }
            GildeActivity.Type.File -> {
                Glide.with(this)
                        .load(FileUtils.getFile(SDCardUtils.getSDCardDir(), "1.jpg"))
                        .placeholder(R.drawable.ic_stub).dontAnimate()
                        .into(iv)
                bt.setText("File")
            }
            GildeActivity.Type.Resource -> {
                Glide.with(this).load(R.drawable.aaaaaaaaaaaab)
                        .placeholder(R.drawable.ic_stub).dontAnimate()
                        .into(iv)
                bt.setText("Resource")
            }
            GildeActivity.Type.Uri -> {
                Glide.with(this).load(resourceIdToUri(this, R.drawable.aaaaaaaaaaaab))
                        .placeholder(R.drawable.ic_stub).dontAnimate()
                        .into(iv)
                bt.setText("Uri")
            }
            GildeActivity.Type.Gif -> {
                //Glide.load(this).load("http://ww1.sinaimg.cn/mw1024/005PquKVjw1f2jerohsz1g30ku0esb2a.gif").asGif()
                Glide.with(this).load(FileUtils.getFile(SDCardUtils.getSDCardDir(), "abc.gif")).asGif()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .fitCenter()
                        .placeholder(R.drawable.ic_stub).dontAnimate()
                        .error(R.drawable.ic_error)
                        .into(iv)
                bt.setText("Gif")
            }
            GildeActivity.Type.Mp4 -> {
                //todo 显示不了。。。 看到这页了 http://mrfu.me/2016/02/27/Glide_Displaying_Gifs_&_Videos/
                // File file = new FileUtils().getFile("面具男鬼步舞教程6个基本动作鬼步舞音乐 高清.mp4");
                val file2 = FileUtils.getFile(SDCardUtils.getSDCardDir(), "DCIM", "Camera", "VID_20160306_125251.mp4")
                Glide.with(this).load(Uri.fromFile(file2))
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .fitCenter()
                        .placeholder(R.drawable.ic_stub).dontAnimate()
                        .error(R.drawable.ic_error)
                        .into(iv)
                bt.setText("Mp4")
            }
            GildeActivity.Type.Error -> {
                Glide.with(this).load(FileUtils.getFile(SDCardUtils.getSDCardDir(), "1111.jpg"))
                        .placeholder(R.drawable.ic_stub).dontAnimate()
                        .error(R.drawable.ic_error)
                        .into(iv)
                bt.setText("Error")
            }
        }

    }

    val ANDROID_RESOURCE = "android.resource://"
    val FOREWARD_SLASH = "/"
    private fun resourceIdToUri(context: Context, resourceId: Int): Uri {
        return Uri.parse(ANDROID_RESOURCE + context.packageName + FOREWARD_SLASH + resourceId)
    }

}