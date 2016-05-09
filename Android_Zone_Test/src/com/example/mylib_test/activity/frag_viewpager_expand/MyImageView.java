package com.example.mylib_test.activity.frag_viewpager_expand;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;

public class MyImageView extends ImageView {
	private float startpoint = 0;
	private float kuand = 0;

	private Paint paint;

	public MyImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
	
		super.onDraw(canvas);
		paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setStyle(Style.FILL);
		//canvas.drawRect(0, 0, 10, 10, paint);
		canvas.drawRect(startpoint, 0, startpoint + kuand
				/ 4, 20, paint);

	}

	public void onScrolled(float a, float b) {
		// TODO Auto-generated method stub
		startpoint = a;
		kuand = b;
		invalidate();

	}

}
