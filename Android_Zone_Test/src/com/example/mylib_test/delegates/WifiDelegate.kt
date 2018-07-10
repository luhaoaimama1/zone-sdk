package com.example.mylib_test.delegates

import android.view.View
import com.example.mylib_test.R
import com.example.mylib_test.activity.wifi.entity.WifiItem
import com.zone.adapter3.bean.Holder
import com.zone.adapter3.bean.ViewDelegates
import com.zone.lib.utils.system_hardware_software_receiver_shell.software.wifi.MyWifiAnd3G

/**
 *[2018/7/10] by Zone
 */
class WifiDelegate(val mWifiAnd3G: MyWifiAnd3G) : ViewDelegates<WifiItem>() {

    override fun getLayoutId(): Int = R.layout.wifi_item

    override fun fillData(i: Int, wifiItem: WifiItem?, holder: Holder<out Holder<*>>?) {
        val listener = View.OnClickListener { v ->
            when (v.getId()) {
                R.id.Wifi_item_con -> {
                    System.out.println("连接==========" + wifiItem?.ssid);
                    System.out.println("ssid" + wifiItem?.ssid);
//					System.out.println("cfg"+it.getCfg());
                    mWifiAnd3G.connectConfiguration(wifiItem?.cfg, 10, 300);
                }
                R.id.Wifi_item_NotCon -> {
                    System.out.println("断开==========" + wifiItem?.ssid);
                    mWifiAnd3G.disconnectWifi();
                }
                R.id.Wifi_item_ToString -> {
                    System.out.println("toString==========" + wifiItem?.wifiToString);
                }
            }
        }
        holder!!.setText(R.id.Wifi_item_ssid, wifiItem?.ssid ?: "")
                .setText(R.id.Wifi_item_con, wifiItem?.wifi_con ?: "")
                .setText(R.id.Wifi_item_NotCon, wifiItem?.wifi_NotCon ?: "")
                .setText(R.id.Wifi_item_ToString, "toString")
                .setOnClickListener(listener, R.id.Wifi_item_con,
                        R.id.Wifi_item_NotCon, R.id.Wifi_item_ToString);
    }

}