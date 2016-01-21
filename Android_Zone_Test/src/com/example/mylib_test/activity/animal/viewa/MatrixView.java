package com.example.mylib_test.activity.animal.viewa;

import com.example.notperfectlib.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.View;

public class MatrixView extends View {
	Paint paint=new Paint();
	private Bitmap bt;
	float[] dst,src;
	private float[] invertDst;
	public MatrixView(Context context) {
		super(context);
		bt=BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		src=new float[]{0,0, 0,bt.getWidth(), 0,bt.getHeight(),  bt.getWidth(),bt.getHeight()};
		dst=new float[src.length];
		invertDst=new float[src.length];
	}

	@Override
	protected void onDraw(Canvas canvas) {
//		super.onDraw(canvas);
		Matrix matrix=new Matrix();
		matrix.postTranslate(100, 200);
		//矩阵映射： 即把此矩阵 未作用的点  映射到   到调用此方法之前的矩阵作用 的点
		matrix.mapPoints(dst, src);
		System.out.println("mapPoints_____dst first Point X:"+ dst[0]+"\t  Y:"+dst[1]);
		
		canvas.drawBitmap(bt, matrix, paint);
		
		//矩阵反转 后映射：  即把 矩阵现在的点   通过反转矩阵 返回到此矩阵作用之前对应的点
		Matrix invertMatrix=new Matrix();
		matrix.invert(invertMatrix);
		invertMatrix.mapPoints(invertDst, dst);
		System.out.println("invertMatrix_____ mapPoints  invertDst first Point X:"+ invertDst[0]+"\t  Y:"+invertDst[1]);
	}

}
