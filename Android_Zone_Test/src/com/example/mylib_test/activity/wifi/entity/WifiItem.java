package com.example.mylib_test.activity.wifi.entity;

import android.net.wifi.WifiConfiguration;

public class WifiItem {
	private String SSID;
	private  String Wifi_con="蟀諉";
	private  String Wifi_NotCon="�秏蟀諉";
	private  String WifiToString="ToString";
	private  WifiConfiguration cfg=null;
	public String getSSID() {
		return SSID;
	}
	public String getWifi_con() {
		return Wifi_con;
	}
	public String getWifi_NotCon() {
		return Wifi_NotCon;
	}
	public void setSSID(String sSID) {
		SSID = sSID;
	}
	public void setWifi_con(String wifi_con) {
		Wifi_con = wifi_con;
	}
	public void setWifi_NotCon(String wifi_NotCon) {
		Wifi_NotCon = wifi_NotCon;
	}
	public WifiConfiguration getCfg() {
		return cfg;
	}
	public void setCfg(WifiConfiguration cfg) {
		this.cfg = cfg;
	}
	public String getWifiToString() {
		return WifiToString;
	}
	public void setWifiToString(String wifiToString) {
		WifiToString = wifiToString;
	}
	
}
