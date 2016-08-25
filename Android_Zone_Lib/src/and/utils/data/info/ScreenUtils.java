package and.utils.data.info;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import and.LogUtil;

public class ScreenUtils {
	public static void  requestFillWindow(Activity context){
		context.requestWindowFeature(Window.FEATURE_NO_TITLE);
		context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	public static void  requestNoTitle(Activity context){
		context.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	public static int[] getScreenPix(Activity context) {
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int[] screen=new int[2];
		screen[0]=dm.widthPixels;
		screen[1]=dm.heightPixels;
		return screen;
	}
	public static int[] getScreenPixByResources(Context context) {
		DisplayMetrics dm2 =context.getResources().getDisplayMetrics();
		int[] screen=new int[2];
		screen[0]=dm2.widthPixels;
		screen[1]=dm2.heightPixels;
		return screen;
	}
	/**
	 * 打印 显示信息
	 */
	public static DisplayMetrics printDisplayInfo(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		if (LogUtil.writeLog) {
			StringBuilder sb = new StringBuilder();
			sb.append("_______  显示信息:  ");
			sb.append("\ndensity         :").append(dm.density);
			sb.append("\ndensityDpi      :").append(dm.densityDpi);
			sb.append("\nheightPixels    :").append(dm.heightPixels);
			sb.append("\nwidthPixels     :").append(dm.widthPixels);
			sb.append("\nscaledDensity   :").append(dm.scaledDensity);
			sb.append("\nxdpi            :").append(dm.xdpi);
			sb.append("\nydpi            :").append(dm.ydpi);
			LogUtil.i(sb.toString());
		}
		return dm;
	}
}
