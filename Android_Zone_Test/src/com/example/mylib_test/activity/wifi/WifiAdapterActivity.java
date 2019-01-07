package com.example.mylib_test.activity.wifi;

import java.util.ArrayList;
import java.util.List;
import com.example.mylib_test.R;
import com.example.mylib_test.activity.wifi.entity.WifiItem;
import com.example.mylib_test.delegates.WifiDelegate;
import com.zone.adapter3.QuickRcvAdapter;
import com.zone.lib.utils.system_hardware_software_receiver_shell.software.wifi.MyWifiAnd3G;
import android.app.Activity;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WifiAdapterActivity extends Activity {
	private RecyclerView listView ;
	private MyWifiAnd3G mWifiAnd3G; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adapter);
		init();
	}
	private void init() {
		mWifiAnd3G = new MyWifiAnd3G(this);
		System.out.println("ip解析后地址："+mWifiAnd3G.getIPAddress());
		listView = (RecyclerView) findViewById(R.id.listView);
		listView.setLayoutManager(new LinearLayoutManager(this));

		int[] idArray=new int[]{R.id.Wifi_item_ssid,R.id.Wifi_item_con,R.id.Wifi_item_NotCon,R.id.Wifi_item_ToString};
		
		List<WifiItem> data=new ArrayList<WifiItem>();
		MyWifiAnd3G mwa = new MyWifiAnd3G(this);
		List<WifiConfiguration> cfg = mwa.getConfiguration();
		if(cfg!=null)
		{
			int i=0;
			for (WifiConfiguration wifiConfiguration : cfg) {
				i++;
				System.err.println(i+":========="+wifiConfiguration.SSID);
//				System.out.println(wifiConfiguration.toString());
				WifiItem wi = new WifiItem();
				wi.setSSID(wifiConfiguration.SSID);
				wi.setWifi_con("连接");
				wi.setWifi_NotCon("取消连接");
				wi.setWifiToString(wifiConfiguration.toString());
				wi.setCfg(wifiConfiguration);
				data.add(wi);
			}
		}

		new QuickRcvAdapter<WifiItem>(this, data)
				.addViewHolder(new WifiDelegate(mWifiAnd3G))
				.relatedList(listView);
	}
}
