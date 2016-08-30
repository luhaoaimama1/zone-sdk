package com.example.mylib_test.activity.animal.viewa;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.mylib_test.R;

public class PorterDuffXfermode1 extends View{
	Paint  paint=new Paint();
	private Context context;
	public PorterDuffXfermode1(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
	}
	public PorterDuffXfermode1(Context context) {
		super(context);
		this.context=context;
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvasYuan(canvas);
//		paint.reset();
//		canvas.drawBitmap(getBitmap(), 0, 0, paint);
		
	}
	private Bitmap getBitmap() {
		Bitmap bt = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);
		Canvas canvas=new Canvas(bt);
		canvas.drawColor(Color.RED);
		canvas.saveLayer(0, 0, getWidth(), getHeight(), paint,
                Canvas.ALL_SAVE_FLAG);
		paint.reset(); 
//		canvas.drawColor(Color.RED);
		canvas.drawCircle(getWidth()/2, getHeight()/2, 200, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher), 0,0, paint);
//		paint.setXfermode(null); 
////		canvas.saveLayer(0, 0, getWidth(), getHeight(), null,
////                Canvas.ALL_SAVE_FLAG);
//		canvas.translate(100, 100);
////		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//		paint.setColor(Color.BLUE);
//		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));; 
//		canvas.drawCircle(getWidth()/2, getHeight()/2, 200, paint);
//		paint.setXfermode(null); 
		canvas.restoreToCount(1);
		return bt;
	}
	private void canvasYuan(Canvas canvas) {
		paint.reset();
//		canvas.save();
//		canvas.saveLayer(0, 0, getWidth(), getHeight(), paint,
//                Canvas.ALL_SAVE_FLAG);
		canvas.saveLayerAlpha(0, 0, getWidth(), getHeight(), 255,
				Canvas.ALL_SAVE_FLAG);
		paint.setColor(Color.BLUE);
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, 200, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		Bitmap icBit = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
		canvas.drawBitmap(icBit, getWidth()/2, getHeight()/2-(200+icBit.getWidth()/2), paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));

		canvas.saveLayer(0, 0, getWidth(), getHeight(), paint,
                Canvas.ALL_SAVE_FLAG);
		canvas.translate(200, 200);
		paint.setColor(Color.RED);
		canvas.drawCircle(getWidth()/2, getHeight()/2, 200, paint);
//		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//		canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.abcd), 0,0, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
		canvas.restore();
		canvas.saveLayer(0, 0, getWidth(), getHeight(), paint,
                Canvas.ALL_SAVE_FLAG);
		canvas.translate(-100, 200);
		paint.setColor(Color.YELLOW);
		canvas.drawCircle(getWidth()/2, getHeight()/2, 200, paint);
//		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//		canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.abcd), 0,0, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.restoreToCount(1);
//		paint.reset(); 
	}

}
