package and.image;

import and.Constant;
import and.abstractclass.adapter.Adapter_MultiLayout_Zone;
import and.log.Logger_Zone;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;

public class BitmapUtils {
	private static Logger_Zone logger;

	static{
		logger= new  Logger_Zone(Adapter_MultiLayout_Zone.class,Constant.Logger_Config);
		logger.closeLog();
	}
	/**
	 *  这个方法默认中心点是图片的中心
	 * @param bitmap 位图
	 * @param degress 要旋转的角度
	 * @param recycledSrc  是否回收原图
	 * @return 旋转照片
	 */
	public static Bitmap rotateBitmap(Bitmap bitmap, int degress,boolean recycledSrc) {
		Bitmap temp =null;
		if (bitmap != null) {
			Matrix matrix = new Matrix();
			// 旋转图片 动作
			matrix.postRotate(degress);
			// 创建新的图片 这个方法默认中心点是图片的中心
			temp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),bitmap.getHeight(), matrix, true);
			if(recycledSrc)
				recycledBitmap(bitmap);
		}
		return temp;
	}

	/**
	 *  这个方法默认中心点是图片的中心
	 * @param bitmap　位图
	 * @param sx　横向的缩放
	 * @param sy　 纵向的缩放
	 *　@param recycledSrc  是否回收原图
	 * @return 旋转照片
	 */
	public static Bitmap scaleBitmap(Bitmap bitmap, float sx, float sy,boolean recycledSrc) {
		Bitmap temp =null;
		if (bitmap != null) {
			Matrix matrix = new Matrix();
			// 旋转图片 动作
			matrix.postScale(sx, sy);
			// 创建新的图片　 这个方法默认中心点是图片的中心
			temp= Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),bitmap.getHeight(), matrix, true);
			if(recycledSrc)
				recycledBitmap(bitmap);
		}
		return temp;
	}

	public static void recycledBitmap(Bitmap bitmap) {
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
			bitmap = null;
		}
		// 这个再说把 系统多了会自动调用把～
		// System.gc();
	}
	
	/**
	 * 转换图片成圆形
	 * 
	 * @param bitmap  传入Bitmap对象
	 * @return
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap,boolean recycledSrc) {
		if (bitmap == null)
			return null;
		Bitmap circleBt = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
		
		Canvas canvas=new Canvas(circleBt);
		
		Paint paint=new Paint();
		paint.setDither(true);
		paint.setAntiAlias(true);
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		canvas.saveLayer(0, 0, circleBt.getWidth(), circleBt.getHeight(), paint,Canvas.ALL_SAVE_FLAG);
		
		float radius=0F;
		if(circleBt.getWidth()<circleBt.getHeight())
			radius=circleBt.getWidth()/2;
		else
			radius=circleBt.getHeight()/2;
		
		paint.setColor(Color.RED);
		canvas.drawCircle(circleBt.getWidth()/2, circleBt.getHeight()/2,radius, paint);
		paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, 0,0, paint);
		canvas.restoreToCount(1);
		if(recycledSrc)
			recycledBitmap(bitmap);
		return circleBt;
	}
	
	// 问题就出在当加载的图片宽度和dstWidth且高度和dstHeight相同时，
	// Bitmap.createBitmap方法会返回原来的bitmap对象，这样bitmap== temp了
	/**
	 * 从新调整　bitmap的宽高　　不按比例
	 * @param src
	 * @param dstWidth　　目标宽
	 * @param dstHeight 
	 * @param recycledSrc 是否回收原位图
	 * @return
	 */
	public static Bitmap getScaledBitmapDeep(Bitmap src, int dstWidth, int dstHeight,boolean recycledSrc) {
		Bitmap temp=null;
		if (src != null) {
			if (dstWidth > 0 && dstHeight > 0) {
				if(dstWidth==src.getWidth()&&dstHeight==src.getHeight()){
					temp=copyDeep(src, false);
				}else{
					 temp = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, true);
				}
				 logger.log("getNewBitmap:复制的位图　和原图　是否为同一个："+(temp==src?"是":"不是"));
				if (recycledSrc) {
					recycledBitmap(src);
				}
			}
		}
		return temp;
	}
	/**
	 * 
	 * @param src
	 * @param recycledSrc 是否回收原位图
	 * @return
	 */
	public static Bitmap copyDeep(Bitmap src,boolean recycledSrc) {
		if (src != null) {
			Bitmap temp = src.copy(src.getConfig(), true);
			logger.log("copy:复制的位图　和原图　是否为同一个："+(temp==src?"是":"不是"));
				if (recycledSrc) {
					recycledBitmap(src);
				}
				return temp;
		}
		return null;
	}

}
