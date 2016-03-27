package com.example.mylib_test.activity.wifi;

import java.util.ArrayList;
import java.util.List;
import com.example.mylib_test.R;
import com.example.mylib_test.activity.wifi.entity.WifiItem;
import com.zone.adapter.QuickAdapter;
import com.zone.adapter.callback.Helper;
import and.wifi.MyWifiAnd3G;
import android.app.Activity;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class WifiAdapterActivity extends Activity {
	private ListView listView ;
	private MyWifiAnd3G mWifiAnd3G; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adapter);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		mWifiAnd3G = new MyWifiAnd3G(this);
		System.out.println("ip解析后地址："+mWifiAnd3G.getIPAddress());
		listView = (ListView) findViewById(R.id.listView);
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


		QuickAdapter<WifiItem> adpt=new QuickAdapter<WifiItem>(this, data) {

			@Override
			public void convert(Helper helper, final WifiItem item, boolean itemChanged, int layoutId) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				//能得到View就能设置 事件了


				//给view做监听事件处理
				OnClickListener listener = new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						switch (v.getId()) {
							case R.id.Wifi_item_con:
								System.out.println("连接=========="+item.getSSID());
								System.out.println("ssid"+item.getSSID());
//							System.out.println("cfg"+it.getCfg());
								mWifiAnd3G.connectConfiguration(item.getCfg(), 10, 300);
								break;
							case R.id.Wifi_item_NotCon:
								System.out.println("断开=========="+item.getSSID());
								mWifiAnd3G.disconnectWifi();
								break;
							case R.id.Wifi_item_ToString:
								System.out.println("toString=========="+item.getWifiToString());
								break;

							default:
								break;
						}
					}
				};
				helper.setText(R.id.Wifi_item_ssid,item.getSSID())
						.setText(R.id.Wifi_item_con, item.getWifi_con())
						.setText(R.id.Wifi_item_NotCon, item.getWifi_NotCon())
						.setText(R.id.Wifi_item_ToString, "toString")
						.setOnClickListener(R.id.Wifi_item_con, listener)
						.setOnClickListener(R.id.Wifi_item_NotCon, listener)
						.setOnClickListener(R.id.Wifi_item_ToString,listener);
			}

			@Override
			public int getItemLayoutId(WifiItem wifiItem, int position) {
				return  R.layout.wifi_item;
			}

		};
		adpt.relatedList(listView);
	}
}
