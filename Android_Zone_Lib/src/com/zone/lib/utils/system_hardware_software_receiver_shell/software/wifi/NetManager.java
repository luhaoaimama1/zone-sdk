package com.zone.lib.utils.system_hardware_software_receiver_shell.software.wifi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
public class NetManager {
	public static final String Net_MOBILE = "MOBILE";
	public static final String Net_WIFI = "WIFI";
	public enum NetStatue{
		WIFI,MOBILE,NO_WORK;
	}

	public static NetStatue getNetStatue(Context context){
		 ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo info=connectivityManager.getActiveNetworkInfo();
         NetStatue status=NetStatue.NO_WORK;
         if(info != null && info.isAvailable()) {
             if(Net_MOBILE.equals(info.getTypeName())){
            	 status=NetStatue.MOBILE;
             } else if(Net_WIFI.equals(info.getTypeName())){
            	 status=NetStatue.WIFI;
             }
         }
         return status;
	}
	/**
	 * 判断wifi是否处于连接状态
	 * 
	 * @return boolean :返回wifi是否连接
	 */
	public static boolean isWIFI(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		boolean result = false;
		if (networkInfo != null) {
			result = networkInfo.isConnected();
		}
		return result;
	}

	/**
	 * 判断是否APN列表中某个渠道处于连接状态
	 * 
	 * @return 返回是否连接
	 */
	public static boolean isMoble(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		boolean result = false;
		if (networkInfo != null) {
			result = networkInfo.isConnected();
		}
		return result;
	}
	public static boolean haveNetWork(Context context) {
		 ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
       NetworkInfo info=connectivityManager.getActiveNetworkInfo();
       if(info==null){
       	 return false;
       }
       if(info.getState()==NetworkInfo.State.CONNECTED){
       	return true;
       }
       return false;
	}
}
