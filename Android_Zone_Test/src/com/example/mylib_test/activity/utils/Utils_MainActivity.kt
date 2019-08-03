package com.example.mylib_test.activity.utils

import com.zone.lib.utils.system_hardware_software_receiver_shell.software.KeyBoardUtils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.EditText
import com.example.mylib_test.*
import com.zone.lib.LogLevel
import com.zone.lib.ZLogger

class Utils_MainActivity : Activity(), OnClickListener {
    private var keyboard: EditText? = null
    private var view1: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_utils_test)
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

    private fun keyBoardTest() {
        keyboard = findViewById<View>(R.id.keyboard) as EditText
        view1 = findViewById(R.id.view1)
        object : KeyBoardUtils() {

            override fun openState(keyboardHeight: Int) {
                LogApp.i("键盘：openState 高度:$keyboardHeight")
            }

            override fun closeState(keyboardHeight: Int) {
                LogApp.i("键盘：closeState 高度:$keyboardHeight")
            }
        }.monitorKeybordState(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.openKeyboard -> KeyBoardUtils.openKeybord(keyboard, this)
            R.id.closeKeyboard -> KeyBoardUtils.closeKeybord(keyboard!!, this)
            R.id.getPhone -> startActivity(Intent(this, GetPhoneTest::class.java))
            R.id.bt_layoutClip -> startActivity(Intent(this, LayoutClipAcitivity::class.java))
            R.id.bt_log1_i -> bt_log1_i()
            R.id.bt_log12_i -> bt_log12_i()
            R.id.bt_log12_ie -> bt_log12_ie()
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
