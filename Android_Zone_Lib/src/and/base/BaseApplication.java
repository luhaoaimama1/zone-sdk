package and.base;

import and.utils.activity_fragment_ui.ToastUtils;
import and.utils.system_hardware_software_receiver_shell.software.wifi.NetStatusReceiverUtils;
import and.utils.system_hardware_software_receiver_shell.software.wifi.NetManager.NetStatue;
import and.utils.system_hardware_software_receiver_shell.software.wifi.NetStatusReceiver.NetWorkListener;
import android.app.Application;

public class BaseApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		NetStatusReceiverUtils.register(this, new NetWorkListener() {
			
			@Override
			public void netWorkChange(NetStatue status) {
				switch (status) {
				case MOBILE:
					break;
				case WIFI:
					break;
				case NO_WORK:
					ToastUtils.showLong(BaseApplication.this, "当前网络不可用");
					break;

				default:
					break;
				}
			}
		});
	}
}
