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
		matrix.mapPoints(dst, src);
		System.out.println("mapPoints_____src first Point X:" + src[0] + "\t  Y:" + src[1]);//src是记录的原点  [0,0]点
		System.out.println("mapPoints_____dst first Point X:"+ dst[0]+"\t  Y:"+dst[1]);//dst是 src通过矩阵变换的点 [0,0]点对应到 [100,200]

		canvas.drawBitmap(bt, matrix, paint);

		//通过下边的三个方法  可以通过  dst矩阵变化后的点  通过矩阵 逆向方法 转换到 invertDst  即是src原点位置   所以dst中[100,200]的点 即是inverDst[0,0]这个点即 src[0,0]点
		Matrix invertMatrix = new Matrix();
		matrix.invert(invertMatrix);
		invertMatrix.mapPoints(invertDst, dst);
		System.out.println("invertMatrix_____ mapPoints  invertDst first Point X:" + invertDst[0] + "\t  Y:" + invertDst[1]);
	}

}
