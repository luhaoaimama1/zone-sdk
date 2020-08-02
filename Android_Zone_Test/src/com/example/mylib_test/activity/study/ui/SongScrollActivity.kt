package com.example.mylib_test.activity.study.ui

import android.text.SpannableString
import android.text.Spanned
import android.text.style.BulletSpan
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.mylib_test.R
import com.example.mylib_test.activity.touch.NestedScrollBActivity
import com.example.mylib_test.activity.touch.view.ViewDragStudyFrame
import com.example.mylib_test.adapter.delegates.TextDelegates
import com.zone.adapter3kt.QuickAdapter
import com.zone.lib.base.controller.activity.BaseFeatureActivity

import com.zone.lib.base.controller.activity.controller.SwipeBackActivityController
import com.zone.lib.utils.activity_fragment_ui.ToastUtils
import kotlinx.android.synthetic.main.a_nestscrollb.*
import kotlinx.android.synthetic.main.a_nestscrollb.rv
import kotlinx.android.synthetic.main.a_song_scroll.*

/**
 * Created by Zone on 2016/1/29.
 */
class SongScrollActivity : BaseFeatureActivity() {
    override fun initBaseControllers() {
        super.initBaseControllers()
        unRegisterPrestener(SwipeBackActivityController::class.java)
    }

    override fun setContentView() {
        setContentView(R.layout.a_song_scroll)
    }

    override fun initData() {
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = QuickAdapter<String>(this).apply {
            registerDelegate(TextDelegates())
            add(NestedScrollBActivity.data)
        }

        val string = SpannableString("Text with\nBullet pointjkdakjfalkjdlkajlfd;ldskjasflkjasdlk;fjals;kdjfasdk;fjsal;kdjfla;djfs;");
        string.setSpan(BulletSpan(), 10, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        et.setText(string)
    }

    override fun setListener() {

    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v?.id) {
//            R.id.tv_back -> {}
//            R.id.tv_mMenuView ->{}
            else -> {
            }
        }
    }
}
