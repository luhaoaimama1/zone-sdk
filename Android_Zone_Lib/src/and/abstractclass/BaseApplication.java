package and.abstractclass;

import and.log.ToastUtils;
import and.wifi.NetStatusReceiverUtils;
import and.wifi.NetManager.NetStatue;
import and.wifi.NetStatusReceiver.NetWorkListener;
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
