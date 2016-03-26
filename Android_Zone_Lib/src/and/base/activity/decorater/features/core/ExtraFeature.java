package and.base.activity.decorater.features.core;

import android.app.Activity;
import android.content.Intent;

/**
 * 这套　主要为了　
 * 针对生命周期的onActivityResult 当然　例如照相机的onResume初始化　onPause摧毁
 *　设计的(可以包含很多方法用来调用)
 *
 *	经验：因为外部setonclick会顶掉这里的　故onclick就不封装了　仅仅封装方法！！！
 * @author 123
 *
 */
public abstract class ExtraFeature {
	public  Activity activity=null;
	public ExtraFeature(Activity activity) {
		if(activity==null)
			throw new IllegalArgumentException("arg:activity may be null!");
		this.activity=activity;
	}
	public abstract void init();
	public abstract void onActivityResult(int requestCode, int resultCode, Intent intent);
	public abstract void destory();
}
