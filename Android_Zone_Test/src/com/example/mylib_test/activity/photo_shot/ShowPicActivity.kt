package com.example.mylib_test.activity.photo_shot

import com.bumptech.glide.Glide
import com.example.mylib_test.R
import android.graphics.BitmapFactory
import com.zone.lib.base.controller.activity.BaseFeatureActivity

import kotlinx.android.synthetic.main.pic_show.*

import java.io.IOException

class ShowPicActivity : BaseFeatureActivity() {

    override fun setContentView() {
        setContentView(R.layout.pic_show)
        val uri = intent.data
        iv_provider!!.setImageBitmap(null)
        if (uri != null) {
            Glide.with(this).load("file://$uri").into(iv_uri!!)
            Glide.with(this).load("file://" + uri.path!!).into(iv_url!!)
        }
        Glide.with(this).load(R.drawable.abcd).into(iv_drawables!!)

        //glide加载 asset		https://github.com/bumptech/glide/issues/155
        //		Glide.with(this).load("file:///androd_asset/abcd.jpg").into(iv_assets);
        try {
            iv_assets!!.setImageBitmap(BitmapFactory.decodeStream(assets.open("abcd.jpg")))
        } catch (e: IOException) {
            e.printStackTrace()
        }

        //		iv_assets.setImageURI(Uri.parse("file:///android_asset/abcd.jpg"));

    }

    override fun initData() {
    }

    override fun setListener() {
    }

}
