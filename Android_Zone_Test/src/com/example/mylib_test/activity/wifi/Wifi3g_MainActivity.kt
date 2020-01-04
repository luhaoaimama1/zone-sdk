package com.example.mylib_test.activity.wifi

import com.example.mylib_test.R

import com.zone.lib.utils.activity_fragment_ui.ToastUtils
import com.zone.lib.utils.system_hardware_software_receiver_shell.software.wifi.MyWifiAnd3G
import com.zone.lib.utils.system_hardware_software_receiver_shell.software.wifi.NetManager
import com.zone.lib.utils.system_hardware_software_receiver_shell.software.wifi.NetStatusReceiverUtils
import android.content.Intent
import android.view.View
import android.view.View.OnClickListener
import com.zone.lib.base.controller.activity.BaseFeatureActivity

class Wifi3g_MainActivity : BaseFeatureActivity(), OnClickListener {

    override fun setContentView() {
        setContentView(R.layout.a_wifitest)
    }

    override fun initData() {
    }

    override fun setListener() {
    }

    override fun onClick(v: View?) {
        val mwa = MyWifiAnd3G(this)
        when (v?.id) {
            R.id.openWifi -> mwa.openWifi()
            R.id.closeWifi -> mwa.closeWifi()
            R.id.connWifi -> {
                val intent2 = Intent(this, WifiAdapterActivity::class.java)
                startActivity(intent2)
            }
            R.id.conn3G -> mwa.openOrClose3GNet(true)
            R.id.getNetStatue -> ToastUtils.showLong(this, NetManager.getNetStatue(this).toString())
            R.id.unRegister -> NetStatusReceiverUtils.unRegister(this)
            R.id.register -> NetStatusReceiverUtils.register(this) { status -> ToastUtils.showLong(this@Wifi3g_MainActivity, "±ä³É:$status") }
            else -> {
            }
        }
    }

}
