package com.example.mylib_test.activity.animal.viewa;


import com.example.notperfectlib.R;

import and.utils.image.BitmapUtils;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class BitmapTry extends View {

	private Bitmap bt;
	private Paint paint = new Paint();

	public BitmapTry(Context context) {
		super(context);
		init(context);
	
	}

	public BitmapTry(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		bt = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_launcher);
		
		BitmapUtils.getScaledBitmapDeep(bt, bt.getWidth(), bt.getHeight(),false);
		BitmapUtils.copyDeep(bt,false);
		
		paint.setDither(true);
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		paint.setColor(Color.RED);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(3);
		paint.setTextSize(30);


		Bitmap tmep = bt.copy(Config.ARGB_8888, true);
		Canvas tmepCanvas = new Canvas(tmep);
		tmepCanvas.drawCircle(0, 0, tmep.getWidth(), paint);
		canvas.drawBitmap(tmep, 0, 200, paint);
		paint.setStrokeWidth(0);
		canvas.drawText("复制的原图是可以修改的", 0,400, paint);
		
		
		canvas.drawBitmap(bt, 0, 0, paint);
		canvas.drawText("原图", 0, 150, paint);
		
//        如果把原图 放到Canvas里去修改 那么会爆出这个错误
//AndroidRuntime(25875): java.lang.IllegalStateException: Immutable bitmap passed to Canvas constructor
//		Canvas tmepCanvas2 = new Canvas(bt);
//		tmepCanvas.drawCircle(0, 0, tmep.getWidth(), paint);
//		canvas.drawBitmap(bt, 300, 0, paint);
//		canvas.drawText("原图不能修改",  300, 150, paint);
	}

}
