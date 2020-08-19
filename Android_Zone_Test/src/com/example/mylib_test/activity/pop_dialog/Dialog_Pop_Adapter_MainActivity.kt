package com.example.mylib_test.activity.pop_dialog

import com.example.mylib_test.activity.pop_dialog.dialog.ZDialog
import com.zone.lib.utils.activity_fragment_ui.ToastUtils

import view.DialogCustemZone

import com.zone.view.FlowLayout

import com.example.mylib_test.R
import com.example.mylib_test.activity.pop_dialog.pop.Pop_Bottom
import com.example.mylib_test.activity.pop_dialog.pop.Pop_Photo
import android.app.AlertDialog.Builder
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.mylib_test.LogApp
import com.example.mylib_test.activity.pop_dialog.pop.PopFullScrenn
import com.zone.lib.ZLogger
import com.zone.lib.base.controller.activity.BaseFeatureActivity

class Dialog_Pop_Adapter_MainActivity : BaseFeatureActivity() {

    override fun setContentView() {
        setContentView(R.layout.a_pop_dialog_adapter)
    }

    override fun initData() {
    }

    override fun setListener() {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.pop -> Pop_Photo(this, R.id.flowLayoutZone1, R.id.pop).show()
            R.id.popFull -> PopFullScrenn(this).show()
            R.id.pop_bottom -> Pop_Bottom(this, R.id.pop_bottom).show()
            R.id.tag -> tabBlue()
            //启动FxService
            R.id.bt_floatView -> startService(Intent(this, FxService::class.java))
            R.id.bt_floatViewClose -> floatViewClose()
            R.id.custom_dialog -> customDialog()
            R.id.dialogFullScreen -> ZDialog(this).show()
            R.id.bt_floatNotification -> startService(Intent(this, NoticationService::class.java))
            else -> {
            }
        }
    }

    private fun customDialog() {
        object : DialogCustemZone(this) {

            override fun notSure() {
                toToast("这个is not OK!")
            }

            override fun isSure() {
                toToast("这个is OK,流逼！")
            }

            override fun addSetProperty(db: Builder) {
                db.setIcon(R.drawable.ic_launcher)
            }

        }.show()
    }

    private fun tabBlue() {
        val fz = findViewById<View>(R.id.flowLayoutZone1) as FlowLayout
        val bt2 = fz.findViewWithTag<View>("blue") as Button
        bt2.setBackgroundColor(Color.BLUE)
    }

    private fun floatViewClose() {
        //uninstallApp("com.phicomm.hu");
        val intent2 = Intent(this, FxService::class.java)
        //终止FxService
        stopService(intent2)
    }

    fun toToast(str: String) {
        ToastUtils.showShort(this, str)
    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        LogApp.d("R.id.pop  onPostCreate:${findViewById<View>(R.id.pop).height}")
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        LogApp.d("R.id.pop  onPostCreate2:${findViewById<View>(R.id.pop).height}")
    }
    override fun onPostResume() {
        super.onPostResume()
        LogApp.d("R.id.pop  onPostResume:${findViewById<View>(R.id.pop).height}")
    }

    override fun onStart() {
        super.onStart()
        LogApp.d("R.id.pop  onStart:${findViewById<View>(R.id.pop).height}")
    }
    override fun onResume() {
        super.onResume()
        LogApp.d("R.id.pop  onResume:${findViewById<View>(R.id.pop).height}")
    }
}
