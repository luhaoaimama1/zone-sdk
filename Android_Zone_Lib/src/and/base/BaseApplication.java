package and.base;

import and.utils.ToastUtils;
import and.utils.wifi.NetStatusReceiverUtils;
import and.utils.wifi.NetManager.NetStatue;
import and.utils.wifi.NetStatusReceiver.NetWorkListener;
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
