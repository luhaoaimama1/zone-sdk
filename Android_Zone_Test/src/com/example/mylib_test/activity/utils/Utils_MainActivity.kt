package com.example.mylib_test.activity.utils

import com.zone.lib.utils.system_hardware_software_receiver_shell.software.KeyBoardUtils

import android.content.Intent
import android.util.DisplayMetrics
import android.view.View
import com.example.mylib_test.*
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import com.zone.lib.LogLevel
import com.zone.lib.ZLogger
import com.zone.lib.utils.data.file2io2data.ClipboardManagerUtils
import kotlinx.android.synthetic.main.a_utils_test.*
import com.zone.lib.base.controller.activity.controller.ActionBarActivityController
import com.zone.lib.base.controller.activity.controller.ShowState


class Utils_MainActivity : BaseFeatureActivity() {
    //https://stackoverflow.com/questions/7417123/android-how-to-adjust-layout-in-full-screen-mode-when-softkeyboard-is-visible
    //-> fullscreen mode doesn't resize  全屏模式不能 resize
    override fun initDefaultConifg(){
        getController(ActionBarActivityController::class.java)?.initFirst(ShowState.HideTitle)
    }

    override fun setContentView() {
        setContentView(R.layout.a_utils_test)
    }

    override fun initData() {
        keyBoardTest()
        val metric = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metric)
        val width = metric.widthPixels  // 屏幕宽度（像素）
        val height = metric.heightPixels  // 屏幕高度（像素）
        val density = metric.density  // 屏幕密度（0.75 / 1.0 / 1.5）
        val densityDpi = metric.densityDpi  // 屏幕密度DPI（120 / 160 / 240）
        LogApp.i("density=$density; densityDPI=$densityDpi")
        val tem = resources.getDimension(R.dimen.test)
        System.err.println(tem)
    }

    override fun setListener() {
    }

    private fun keyBoardTest() {
        object : KeyBoardUtils() {
            override fun openState(keyboardHeight: Int) {
                LogApp.i("键盘：openState 高度:$keyboardHeight")
            }

            override fun closeState(keyboardHeight: Int) {
                LogApp.i("键盘：closeState 高度:$keyboardHeight")
            }
        }.monitorKeybordState(this@Utils_MainActivity)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.openKeyboard -> KeyBoardUtils.openKeybord(keyboard, this)
            R.id.closeKeyboard -> KeyBoardUtils.closeKeybord(keyboard, this)
            R.id.getPhone -> startActivity(Intent(this, GetPhoneTest::class.java))
            R.id.bt_layoutClip -> startActivity(Intent(this, LayoutClipAcitivity::class.java))
            R.id.bt_log1_i -> bt_log1_i()
            R.id.bt_log12_i -> bt_log12_i()
            R.id.bt_log12_ie -> bt_log12_ie()
            R.id.getClipboardContent -> LogApp.i(ClipboardManagerUtils.getClipBoardText(this))
            R.id.setClipboardContent -> ClipboardManagerUtils.setClipeBoardContent(this,"123")
            R.id.testHide ->{
                when (testHide2.visibility) {
                    View.VISIBLE-> testHide2.visibility=View.GONE
                    View.GONE-> testHide2.visibility=View.VISIBLE
                    else -> {
                    }
                }
            }
            else -> {
            }
        }

    }

    private fun bt_log1_i() {
        ZLogger.logLevelList.clear()
        ZLogger.logLevelList.add(LogLevel.i)
        ZLogger.mayLoggerList.clear()
        ZLogger.mayLoggerList.add(Log1)
        Log1.e("eeee111")
        Log1.i("iiii111")
        Log2.e("eeee222")
        Log2.i("iiii222")

        MainActivity2.initLogger()
    }

    private fun bt_log12_i() {
        ZLogger.logLevelList.clear()
        ZLogger.logLevelList.add(LogLevel.i)
        ZLogger.mayLoggerList.clear()
        ZLogger.mayLoggerList.addAll(listOf<ZLogger>(Log1, Log2))
        Log1.e("eeee111")
        Log1.i("iiii111")
        Log2.e("eeee222")
        Log2.i("iiii222")

        MainActivity2.initLogger()
    }

    private fun bt_log12_ie() {
        ZLogger.logLevelList.clear()
        ZLogger.mayLoggerList.clear()
        ZLogger.mayLoggerList.addAll(listOf<ZLogger>(Log1, Log2))
        Log1.e("eeee111")
        Log1.i("iiii111")
        Log2.e("eeee222")
        Log2.i("iiii222")

        MainActivity2.initLogger()
    }
}
