package com.example.mylib_test.activity.wifi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.mylib_test.R;
import com.example.mylib_test.R.id;
import com.example.mylib_test.R.layout;
import com.example.mylib_test.activity.wifi.entity.WifiItem;
import com.zone.adapter.adapter.Adapter_Zone;
import com.zone.adapter.adapter.core.ViewHolder_Zone;

import and.wifi.MyWifiAnd3G;
import android.app.Activity;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
		
		
		Adapter_Zone<WifiItem> adpt=new Adapter_Zone<WifiItem>(this, data) {

			@Override
			public int setLayoutID() {
				return R.layout.wifi_item;
			}

			@Override
			public void setData(ViewHolder_Zone holder, final WifiItem dataIndex,
					int position) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				//能得到View就能设置 事件了
				//找到View
				TextView Wifi_item_ssid=(TextView)holder.findViewById(R.id.Wifi_item_ssid);
				Button Wifi_item_con=(Button)holder.findViewById(R.id.Wifi_item_con);
				Button Wifi_item_NotCon=(Button)holder.findViewById(R.id.Wifi_item_NotCon);
				Button Wifi_item_ToString=(Button)holder.findViewById(R.id.Wifi_item_ToString);
				
				//给view赋值
				Wifi_item_ssid.setText(dataIndex.getSSID());
//				System.out.println("index:"+arg0+"==="+dataIndex.getSSID());
				Wifi_item_con.setText(dataIndex.getWifi_con());
				Wifi_item_NotCon.setText(dataIndex.getWifi_NotCon());
				Wifi_item_ToString.setText("toString");
			
				//给view做监听事件处理
				OnClickListener listener = new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						switch (v.getId()) {
						case R.id.Wifi_item_con:
							System.out.println("连接=========="+dataIndex.getSSID());
							System.out.println("ssid"+dataIndex.getSSID());
//							System.out.println("cfg"+it.getCfg());
							mWifiAnd3G.connectConfiguration(dataIndex.getCfg(), 10, 300);
							break;
						case R.id.Wifi_item_NotCon:
							System.out.println("断开=========="+dataIndex.getSSID());
							mWifiAnd3G.disconnectWifi();
							break;
						case R.id.Wifi_item_ToString:
							System.out.println("toString=========="+dataIndex.getWifiToString());
							break;

						default:
							break;
						}
					}
				};
				//给view添加监听事件
				Wifi_item_con.setOnClickListener(listener);
				Wifi_item_NotCon.setOnClickListener(listener);
				Wifi_item_ToString.setOnClickListener(listener);	
			}

		};
		listView.setAdapter(adpt);
	}
}
