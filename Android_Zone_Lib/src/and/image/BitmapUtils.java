package and.image;
import and.LogUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;

import java.io.ByteArrayOutputStream;

public class BitmapUtils {

	public static void recycledBitmap(Bitmap bitmap) {
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
			bitmap = null;
		}
		// 这个再说把 系统多了会自动调用把～
		// System.gc();
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
		canvas.saveLayer(0, 0, circleBt.getWidth(), circleBt.getHeight(), paint, Canvas.ALL_SAVE_FLAG);
		
		float radius=0F;
		if(circleBt.getWidth()<circleBt.getHeight())
			radius=circleBt.getWidth()/2;
		else
			radius=circleBt.getHeight()/2;
		
		paint.setColor(Color.RED);
		canvas.drawCircle(circleBt.getWidth()/2, circleBt.getHeight()/2,radius, paint);
		paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, 0, 0, paint);
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
				LogUtil.d("getNewBitmap:复制的位图　和原图　是否为同一个：" + (temp == src ? "是" : "不是"));
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
			LogUtil.d("copy:复制的位图　和原图　是否为同一个：" + (temp == src ? "是" : "不是"));
				if (recycledSrc) {
					recycledBitmap(src);
				}
				return temp;
		}
		return null;
	}

	//-------------------------------引用别人的---------------------------------------------------------------

	/**
	 * Byte[]转Bitmap
	 */
	public static Bitmap bytes2Bitmap(byte[] data) {
		return BitmapFactory.decodeByteArray(data, 0, data.length);
	}

	/**
	 * Bitmap转Byte[]
	 */
	public static byte[] bitmap2Bytes(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		return baos.toByteArray();
	}
	/**
	 * Bitmap转Byte[]
	 * @param bmp
	 * @param needRecycle 转换完毕后是否需要回收bitmap
	 * @return
	 */
	public static byte[] bitmap2ByteArray(final Bitmap bmp, final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}

		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Bitmap转Drawable
	 */
	public static Drawable bitmap2Drawable(Bitmap bitmap) {
		return new BitmapDrawable(bitmap);
	}

	/**
	 * Drawable转Bitmap
	 */
	public static Bitmap drawable2Bitmap(Drawable drawable) {
		BitmapDrawable bd = (BitmapDrawable) drawable;
		return bd.getBitmap();
	}

	/**
	 * 得到bitmap的大小
	 */
	public static int getBitmapSize(Bitmap bitmap) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
			return bitmap.getAllocationByteCount();
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
			return bitmap.getByteCount();
		}
		// 在低版本中用一行的字节x高度
		return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
	}
	/**
	 * 获得圆角图片的方法
	 *
	 * @param bitmap
	 * @param roundPx
	 *            一般设成14
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	/**
	 * 获得带倒影的图片方法
	 *
	 * @param bitmap
	 * @return
	 */
	public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
		final int reflectionGap = 4;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
				width, height / 2, matrix, false);

		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 2), Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		Paint deafalutPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
				bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
				0x00ffffff, Shader.TileMode.CLAMP);
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);

		return bitmapWithReflection;
	}






}
