package com.example.mylib_test.activity.wifi

import java.util.ArrayList
import com.example.mylib_test.R
import com.example.mylib_test.activity.wifi.entity.WifiItem
import com.example.mylib_test.delegates.WifiDelegate
import com.zone.adapter3.QuickRcvAdapter
import com.zone.lib.utils.system_hardware_software_receiver_shell.software.wifi.MyWifiAnd3G

import androidx.recyclerview.widget.LinearLayoutManager
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import kotlinx.android.synthetic.main.adapter.*

class WifiAdapterActivity : BaseFeatureActivity() {
    private var mWifiAnd3G: MyWifiAnd3G? = null
    override fun setContentView() {
        setContentView(R.layout.adapter)
    }

    override fun initData() {
        mWifiAnd3G = MyWifiAnd3G(this)
        println("ip解析后地址：" + mWifiAnd3G!!.ipAddress!!)
        listView!!.layoutManager = LinearLayoutManager(this)

        val idArray = intArrayOf(R.id.Wifi_item_ssid, R.id.Wifi_item_con, R.id.Wifi_item_NotCon, R.id.Wifi_item_ToString)

        val data = ArrayList<WifiItem>()
        val mwa = MyWifiAnd3G(this)
        val cfg = mwa.configuration
        if (cfg != null) {
            var i = 0
            for (wifiConfiguration in cfg) {
                i++
                System.err.println(i.toString() + ":=========" + wifiConfiguration.SSID)
                //				System.out.println(wifiConfiguration.toString());
                val wi = WifiItem()
                wi.ssid = wifiConfiguration.SSID
                wi.wifi_con = "连接"
                wi.wifi_NotCon = "取消连接"
                wi.wifiToString = wifiConfiguration.toString()
                wi.cfg = wifiConfiguration
                data.add(wi)
            }
        }

        QuickRcvAdapter(this, data)
                .addViewHolder(WifiDelegate(mWifiAnd3G!!))
                .relatedList(listView)
    }

    override fun setListener() {
    }
}
