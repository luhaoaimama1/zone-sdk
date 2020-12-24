package com.example.mylib_test.activity.custom_view

import android.os.Bundle
import android.widget.ImageView
import com.example.mylib_test.R
import com.zone.lib.base.controller.activity.BaseFeatureActivity

/**
 * Created by Administrator on 2016/4/15.
 * 由于内容较多 可以看我的笔记~  搜索词:SwitchButton（IOS倾向） 的api 学习
 */
class MaqueeViewActivity : BaseFeatureActivity() {

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
    }


    override fun setContentView() {
        setContentView(R.layout.a_marquee_view)
    }

    override fun initData() {
    }

    override fun setListener() {

    }
}
