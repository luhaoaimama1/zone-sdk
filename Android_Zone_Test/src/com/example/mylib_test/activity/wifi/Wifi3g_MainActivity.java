package com.example.mylib_test.activity.wifi;

import com.example.mylib_test.R;

import com.zone.lib.utils.activity_fragment_ui.ToastUtils;
import com.zone.lib.utils.system_hardware_software_receiver_shell.software.wifi.MyWifiAnd3G;
import com.zone.lib.utils.system_hardware_software_receiver_shell.software.wifi.NetManager;
import com.zone.lib.utils.system_hardware_software_receiver_shell.software.wifi.NetStatusReceiverUtils;
import com.zone.lib.utils.system_hardware_software_receiver_shell.software.wifi.NetManager.NetStatue;
import com.zone.lib.utils.system_hardware_software_receiver_shell.software.wifi.NetStatusReceiver.NetWorkListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Wifi3g_MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_wifitest);
	}
	@Override
	public void onClick(View v) {
		MyWifiAnd3G mwa = new MyWifiAnd3G(this);
		switch (v.getId()) {
		case R.id.openWifi:
			mwa.openWifi();
			break;
		case R.id.closeWifi:
			mwa.closeWifi();
			break;
		case R.id.connWifi:
			Intent intent2 = new Intent(this, WifiAdapterActivity.class);
			startActivity(intent2);
			break;
		case R.id.conn3G:
			mwa.openOrClose3GNet(true);
			break;
		case R.id.getNetStatue:
			ToastUtils.showLong(this, NetManager.getNetStatue(this).toString());
			break;
		case R.id.unRegister:
			NetStatusReceiverUtils.unRegister(this);
			break;
		case R.id.register:
			NetStatusReceiverUtils.register(this, new NetWorkListener() {
				@Override
				public void netWorkChange(NetStatue status) {
					ToastUtils.showLong(Wifi3g_MainActivity.this, "±ä³É:"+status.toString());
				}
			});
			break;

		default:
			break;
		}
	}

}
