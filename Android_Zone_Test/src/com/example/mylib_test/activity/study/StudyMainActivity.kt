package com.example.mylib_test.activity.study

import android.view.View
import android.content.Intent
import com.example.mylib_test.LogApp
import com.example.mylib_test.R
import com.example.mylib_test.activity.study.ui.*
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import com.zone.lib.utils.activity_fragment_ui.ToastUtils
import com.zone.lib.utils.data.file2io2data.FileUtils
import com.zone.lib.utils.data.file2io2data.SDCardUtils
import com.zone.lib.utils.data.info.LogcatHelper
import com.zone.lib.utils.data.info.LogcatHelperLevel
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
        LogcatHelper.PATH_LOGCAT = FileUtils.getFile(SDCardUtils.getSDCardDir(), "Log2").path
    }

    override fun setListener() {
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        val instance = LogcatHelper.getInstance(this@StudyMainActivity)
        when (v?.id) {
            R.id.android_pdf -> startActivity(Intent(this, PDFActivity::class.java))
            R.id.x5_open_office -> startActivity(Intent(this, X5Activity::class.java))
            R.id.pdfbox -> startActivity(Intent(this, PDFBoxActivity::class.java))
            R.id.google_office -> startActivity(Intent(this, GoogleOfficeActivity::class.java))
            R.id.tips -> startActivity(Intent(this, TipsActivity::class.java))
            R.id.dynamicLogo -> startActivity(Intent(this, DynamicLogoActivity::class.java))
            R.id.bt_data_crash -> startActivity(Intent(this, CrashDataActivity::class.java))
            R.id.bt_start_logcat_collection -> {
                if (!instance.isRuning()) {
                    instance.start()
                    log()
                } else {
                    ToastUtils.showLong(this, "正在运行中！")
                }
            }
            R.id.bt_start_logcat_collection_type -> {
                File(instance.getLogFile()).delete()
                LogsPop(this ,object :LogsPop.Callback{
                    override fun click(name: String) {
                        instance.start(LogcatHelperLevel.valueOf(name))
                        log()
                    }
                }).show()
            }
            R.id.bt_restart_logcat_collection -> {
                instance.start()
                log()
            }
            R.id.bt_stop_logcat_collection -> {
                ToastUtils.showLong(this, "停止记录")
                instance.stop()
            }
            R.id.bt_crash -> {
                throw IllegalStateException("崩溃了！")
            }
            else -> {
            }
        }
    }

    private fun log() {
        ToastUtils.showLong(this, "开始记录")
        LogApp.e("e娃哈哈")
        LogApp.d("d在哪里额")
        LogApp.v("v在哪里额")
        LogApp.i("i在哪里额")
        LogApp.w("w在哪里额")
        LogApp.wtf("wtf在哪里额")
    }
}