package com.example.mylib_test.activity.system

import com.example.mylib_test.R

import com.zone.lib.utils.activity_fragment_ui.ToastUtils

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.mylib_test.activity.animal.PixelsAcitivity
import com.example.mylib_test.activity.statu.StatuMainActivity
import com.example.mylib_test.activity.statu.StatueBarModeActivity
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import com.zone.lib.base.controller.activity.controller.ActionBarActivityController
import com.zone.lib.base.controller.activity.controller.ShowState

class SystemMainActivity : BaseFeatureActivity() {

    companion object {
        var ResponseCode = 11111
        var RequestCode = 22222
    }

    override fun initDefaultConifg(){
        getController(ActionBarActivityController::class.java)?.initFirst(ShowState.ShowTitle)
    }

    override fun setContentView() {
        setContentView(R.layout.a_system_test)
    }

    override fun initData() {
    }

    override fun setListener() {
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true//true创建的菜单才能显示
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_settings -> {
            }
            R.id.sb -> { }
            else -> {
            }
        }
        ToastUtils.showLong(this, "干啥啊别碰我")
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_activityResult -> startActivityForResult(Intent(this, ResultActivity2::class.java), SystemMainActivity.RequestCode)
            R.id.bt_layoutInflater -> startActivityForResult(Intent(this, LayoutInflaterActivity::class.java), SystemMainActivity.RequestCode)
            R.id.crash -> throw NullPointerException("crash test")
            R.id.bt_system_status ->  startActivity(Intent(this, StatuMainActivity::class.java))
            R.id.bt_statuBarMode ->  startActivity(Intent(this, StatueBarModeActivity::class.java))
            else -> {
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        println("first onActivityResult____ requestCode:$requestCode\t resultCode$resultCode")
    }

}
