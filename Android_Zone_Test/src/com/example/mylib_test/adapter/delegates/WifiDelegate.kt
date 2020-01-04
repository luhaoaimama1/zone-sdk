package com.example.mylib_test.adapter.delegates

import android.view.View
import com.example.mylib_test.R
import com.example.mylib_test.activity.wifi.entity.WifiItem
import com.zone.adapter3kt.data.DataWarp
import com.zone.lib.utils.system_hardware_software_receiver_shell.software.wifi.MyWifiAnd3G
import zone.com.zadapter3kt.adapterimpl.HolderExDemoImpl
import zone.com.zadapter3kt.adapterimpl.ViewDelegatesDemo

/**
 *[2018/7/10] by Zone
 */
class WifiDelegate(val mWifiAnd3G: MyWifiAnd3G) : ViewDelegatesDemo<WifiItem>() {
    override val layoutId: Int = R.layout.wifi_item

    override fun onBindViewHolder(position: Int, item: DataWarp<WifiItem>, baseHolder: HolderExDemoImpl, payloads: List<*>) {
        item.data?.let { wifiItem ->
            val listener = View.OnClickListener { v ->
                when (v.getId()) {
                    R.id.Wifi_item_con -> {
                        System.out.println("连接==========" + wifiItem.ssid);
                        System.out.println("ssid" + wifiItem.ssid);
//					System.out.println("cfg"+it.getCfg());
                        mWifiAnd3G.connectConfiguration(wifiItem.cfg, 10, 300);
                    }
                    R.id.Wifi_item_NotCon -> {
                        System.out.println("断开==========" + wifiItem.ssid);
                        mWifiAnd3G.disconnectWifi();
                    }
                    R.id.Wifi_item_ToString -> {
                        System.out.println("toString==========" + wifiItem.wifiToString);
                    }
                }
            }
            baseHolder.setText(R.id.Wifi_item_ssid, wifiItem.ssid ?: "")
                    .setText(R.id.Wifi_item_con, wifiItem.wifi_con ?: "")
                    .setText(R.id.Wifi_item_NotCon, wifiItem.wifi_NotCon ?: "")
                    .setText(R.id.Wifi_item_ToString, "toString")
                    .setOnClickListener(listener, R.id.Wifi_item_con,
                            R.id.Wifi_item_NotCon, R.id.Wifi_item_ToString);
        }

    }

}