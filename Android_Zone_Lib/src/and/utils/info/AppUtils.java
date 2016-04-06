package and.utils.info;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import java.util.List;

/**
 * 跟App相关的辅助类
 * 有调用系统分享!!!
 * @author zhy
 * 
 */
public class AppUtils {

	private AppUtils() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");

	}

	/**
	 * 获取应用程序名称
	 */
	public static String getAppName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			int labelRes = packageInfo.applicationInfo.labelRes;
			return context.getResources().getString(labelRes);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 得到版本号
	 * @param context
	 * @return
	 */
	public static int getAppVersion(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return info.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 1;
	}

	/**
	 * [获取应用程序版本名称信息]
	 * 
	 * @param context
	 * @return 当前应用的版本名称
	 */
	public static String getVersionName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			return packageInfo.versionName;

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * todo 调用系统分享!!!
	 */
	public static void shareToOtherApp(Context context,String title,String content, String dialogTitle ) {
		Intent intentItem = new Intent(Intent.ACTION_SEND);
		intentItem.setType("text/plain");
		intentItem.putExtra(Intent.EXTRA_SUBJECT, title);
		intentItem.putExtra(Intent.EXTRA_TEXT, content);
		intentItem.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(Intent.createChooser(intentItem, dialogTitle));
	}

	/**
	 * need < uses-permission android:name =“android.permission.GET_TASKS” />
	 * 判断是否前台运行
	 */
	public static boolean isRunningForeground(Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
		if (taskList != null && !taskList.isEmpty()) {
			ComponentName componentName = taskList.get(0).topActivity;
			if (componentName != null && componentName.getPackageName().equals(context.getPackageName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取App包 信息版本号
	 * @param context
	 * @return
	 */
	public PackageInfo getPackageInfo(Context context) {
		PackageManager packageManager = context.getPackageManager();
		PackageInfo packageInfo = null;
		try {
			packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return packageInfo;
	}
	/**
	 * 获取指定程序信息
	 */
	public static PackageInfo getPackageInfo(Context context, String pkg) {
		try {
			return context.getPackageManager().getPackageInfo(pkg, 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取指定程序信息
	 */
	public static ApplicationInfo getApplicationInfo(Context context, String pkg) {
		try {
			return context.getPackageManager().getApplicationInfo(pkg, 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
