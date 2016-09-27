package com.zone.view.test.util;

import android.graphics.PointF;
import android.graphics.RectF;
import android.util.FloatMath;

public class DragZoomRorateUtils {

	/**
	 * 
	 * @param rectf
	 * @param target
	 * @return 返回 矩形 相对target 在父控件中的 rectf  右上角
	 */
	public static RectF getRightTopRectF(RectF rectf, RectF target) {
		RectF rect = new RectF(rectf);
		rect.offset(target.right - rect.centerX(), target.top - rect.centerY());
		return rect;
	}
	/**
	 * 
	 * @param rectf
	 * @param target
	 * @return 返回 矩形 相对target 在父控件中的 rectf  左下角
	 */
	public static RectF getLeftBottomRectF(RectF rectf, RectF target) {
		RectF rect = new RectF(rectf);
		rect.offset(target.left - rect.centerX(),
				target.bottom - rect.centerY());
		return rect;
	}
	 // 计算两个触摸点之间的距离   
	public  static float distance(PointF start, PointF end) {
		float x = start.x - end.x;
		float y = start.y - end.y;
		return FloatMath.sqrt(x * x + y * y);
	}
}
