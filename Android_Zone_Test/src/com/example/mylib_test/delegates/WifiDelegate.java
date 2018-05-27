package com.example.mylib_test.delegates;

import android.view.View;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.wifi.entity.WifiItem;
import com.zone.adapter3.bean.Holder;
import com.zone.adapter3.bean.ViewDelegates;
import com.zone.lib.utils.system_hardware_software_receiver_shell.software.wifi.MyWifiAnd3G;

/**
 * [2018] by Zone
 */

public class WifiDelegate extends ViewDelegates<WifiItem> {
    private final MyWifiAnd3G mWifiAnd3G;

    public WifiDelegate(MyWifiAnd3G mWifiAnd3G) {
        this.mWifiAnd3G = mWifiAnd3G;
    }

    @Override
    public int getLayoutId() {
        return R.layout.wifi_item;
    }

    @Override
    public void fillData(int i, WifiItem wifiItem, Holder holder) {
        //能得到View就能设置 事件了


        //给view做监听事件处理
        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                switch (v.getId()) {
                    case R.id.Wifi_item_con:
                        System.out.println("连接==========" + wifiItem.getSSID());
                        System.out.println("ssid" + wifiItem.getSSID());
//							System.out.println("cfg"+it.getCfg());
                        mWifiAnd3G.connectConfiguration(wifiItem.getCfg(), 10, 300);
                        break;
                    case R.id.Wifi_item_NotCon:
                        System.out.println("断开==========" + wifiItem.getSSID());
                        mWifiAnd3G.disconnectWifi();
                        break;
                    case R.id.Wifi_item_ToString:
                        System.out.println("toString==========" + wifiItem.getWifiToString());
                        break;

                    default:
                        break;
                }
            }
        };
        holder.setText(R.id.Wifi_item_ssid, wifiItem.getSSID())
                .setText(R.id.Wifi_item_con, wifiItem.getWifi_con())
                .setText(R.id.Wifi_item_NotCon, wifiItem.getWifi_NotCon())
                .setText(R.id.Wifi_item_ToString, "toString")
                .setOnClickListener(listener, R.id.Wifi_item_con,
                        R.id.Wifi_item_NotCon, R.id.Wifi_item_ToString);
    }
}
