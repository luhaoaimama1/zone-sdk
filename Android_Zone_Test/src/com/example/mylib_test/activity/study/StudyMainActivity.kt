package com.example.mylib_test.activity.study

import android.view.View
import android.content.Intent
import com.example.mylib_test.LogApp
import com.example.mylib_test.R
import com.example.mylib_test.activity.study.ui.*
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import com.zone.lib.utils.data.info.PrintLog
import java.io.File
import java.lang.IllegalStateException


/**
 *[2018/11/14] by Zone
 */
class StudyMainActivity : BaseFeatureActivity() {

    override fun setContentView() {
        setContentView(R.layout.a_study_main)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
    }


    override fun initData() {
    }

    override fun setListener() {
    }

    override fun onClick(v: View?) {
        super.onClick(v)

        when (v?.id) {
            R.id.android_pdf -> startActivity(Intent(this, PDFActivity::class.java))
            R.id.x5_open_office -> startActivity(Intent(this, X5Activity::class.java))
            R.id.pdfbox -> startActivity(Intent(this, PDFBoxActivity::class.java))
            R.id.google_office -> startActivity(Intent(this, GoogleOfficeActivity::class.java))
            R.id.tips -> startActivity(Intent(this, TipsActivity::class.java))
            R.id.dynamicLogo -> startActivity(Intent(this, DynamicLogoActivity::class.java))
            R.id.bt_data_crash -> startActivity(Intent(this, CrashDataActivity::class.java))
            R.id.bt_song_scroll -> startActivity(Intent(this, SongScrollActivity::class.java))
            R.id.bt_drag_view -> startActivity(Intent(this, DragActivity::class.java))
            R.id.bt_list_filter -> startActivity(Intent(this, RecyclerViewDatasActivity::class.java))
            R.id.bt_crash -> crashPrint()
            R.id.bt_service -> ForegroudService.startService(this)
            R.id.bt_NotifiBindservice -> ForegroudService.startServiceShowNotification(this)
            R.id.bt_NotifiHideservice ->  ForegroudService.startServiceHideNotification(this)
            R.id.bt_display_cutout -> startActivity(Intent(this, DisplayCutoutActivity::class.java))
            R.id.bt_ky_ad_view -> startActivity(Intent(this, KYADViewActivity::class.java))
            R.id.bt_ky_scrollTop -> startActivity(Intent(this, KYNestScrollTopViewActivity::class.java))
            R.id.bt_ky_scrollTop_appbar -> startActivity(Intent(this, KYAppBarNestScrollTopViewActivity::class.java))
            R.id.bt_go_this -> startActivity(Intent(this, StudyMainActivity::class.java))
            R.id.bt_rv_hori -> startActivity(Intent(this, RvHoriActivity::class.java))
            R.id.bt_a_hashcode -> {
                entity = Entity(entity.a + 1)
                LogApp.d("hashcode:${hashCode()}")
            }
            else -> {
            }
        }
    }
    private var entity=Entity(1)

    class Entity(var a: Int)

    override fun equals(other: Any?): Boolean {
        return super<BaseFeatureActivity>.equals(other)
    }

    private fun crashPrint() {
        PrintLog.e("test错误")
        throw IllegalStateException("崩溃了！")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }
}