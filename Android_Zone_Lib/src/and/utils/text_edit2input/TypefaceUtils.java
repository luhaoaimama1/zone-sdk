package and.utils.text_edit2input;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class TypefaceUtils {
	private static String ttfStr="";
	/**
	 *
	 * @param fontsPath  For example:<b>"fonts/zhanghaishan2.0.ttf"<b>
	 */
	public static void initTTF(String fontsPath){
		ttfStr=fontsPath;
	}

	/**
	 *
	 * @param context
	 * @param view
	 */
	public static void useTypeface(Context context,TextView view){
		if("".equals(ttfStr))
			throw new IllegalStateException("method:initTTF may be not use!");
		 Typeface typeFace = Typeface.createFromAsset(context.getAssets(),ttfStr );
		 view.setTypeface(typeFace);
	}
	/**
	 *
	 * @param context
	 * @param view
	 */
	public static void useTypeface(Context context,TextView view,String fontsPath){
		if("".equals(ttfStr))
			throw new IllegalStateException("method:initTTF may be not use!");
		 Typeface typeFace = Typeface.createFromAsset(context.getAssets(),fontsPath);
		 view.setTypeface(typeFace);
	}
}
