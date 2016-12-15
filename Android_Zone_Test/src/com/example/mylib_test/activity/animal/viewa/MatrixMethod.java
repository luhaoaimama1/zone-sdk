package com.example.mylib_test.activity.animal.viewa;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.mylib_test.R;

import and.utils.view.DrawUtils;
import and.utils.image.compress2sample.SampleUtils;

/**
 * Created by fuzhipeng on 16/8/24.
 */
public class MatrixMethod extends View {
    private final Bitmap bt;
    private final Paint paint;
    private final Paint paintStorke;

    public MatrixMethod(Context context) {
        this(context, null);
    }

    public MatrixMethod(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MatrixMethod(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bt = SampleUtils.load(context, R.drawable.aaaaaaaaaaaab).overrideW(300).bitmap();
        paint = DrawUtils.getBtPaint();
        paintStorke = DrawUtils.getStrokePaint(Paint.Style.STROKE,5);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setAlpha(255);
        canvas.save();
        canvas.translate(getWidth()/2-bt.getWidth()/2,0);
        ploy(canvas,3);

        canvas.save();
        canvas.translate(0,(bt.getHeight()+50));
        ployTWO(canvas);//两个点的时候  位移旋转缩放;
        canvas.restore();

        canvas.save();
        canvas.translate(0,(bt.getHeight()+50)*2);
        ployOne(canvas);//一个点的时候相当于位移
        canvas.restore();
        canvas.drawBitmap(bt, 0, (bt.getHeight()+50)*3, paint);

        canvas.restore();

        canvas.translate(50, 0);
        RectF rect = new RectF(0, 0, 200, 200);
        RectF rect2 = new RectF(0, 0, 400, 400);
        paintStorke.setColor(Color.CYAN);
        paintStorke.setAlpha(255/2);
        canvas.drawRect(rect,paintStorke);
        canvas.drawRect(rect2,paintStorke);
        skew(canvas, rect,0F,0F,Color.BLACK);
        skew(canvas, rect,0F,0.5F,Color.BLUE);
        skew(canvas, rect,0.5F,0F,Color.RED);
        skew(canvas, rect,0.5F,0.5F,Color.GREEN);

    }

    private void skew(Canvas canvas, RectF rect,float kx, float ky,int color) {
        canvas.save();
        Matrix skaw=new Matrix();
//        skaw.postTranslate(100,0);
        //Xnew=考虑坐标系远点变换,最后加上(xT,yT) +(xT*ky,yT*kx)
        //Ynew=Yold+tY+kx*tY;
        skaw.postSkew(kx,ky);
        canvas.concat(skaw);
        paintStorke.setColor(color);
        paintStorke.setAlpha(255/2);
        canvas.drawRect(rect,paintStorke);
        canvas.restore();
    }

    private void ploy(Canvas canvas,int number) {
        canvas.save();
        float[] src = {0, 0,                                    // 左上
                bt.getWidth(), 0,                          // 右上
                bt.getWidth(), bt.getHeight(),        // 右下
                0, bt.getHeight()};                        // 左下

        float[] dst = {0, 0,                                    // 左上
                bt.getWidth(),50,                        // 右上
                bt.getWidth(), bt.getHeight() -50,  // 右下
                0, bt.getHeight()};                        // 左下

        Matrix ploy=new Matrix();
        ploy.setPolyToPoly(src,0,dst,0,number);
        canvas.drawBitmap(bt,ploy, paint);
        canvas.restore();
    }
    private void ployTWO(Canvas canvas) {
        canvas.save();
        float[] src = {0, 0,                                    // 左上
                bt.getWidth(), 0,                          // 右上
                bt.getWidth(), bt.getHeight(),        // 右下
                0, bt.getHeight()};                        // 左下

        float[] dst = {0, 0,                                    // 左上
                bt.getWidth(),50,                        // 右上
                bt.getWidth(), bt.getHeight() -50,  // 右下
                0, bt.getHeight()};                        // 左下

        Matrix ploy=new Matrix();
        ploy.setPolyToPoly(src,0,dst,0,2);
        canvas.drawBitmap(bt,ploy, paint);
        canvas.restore();
    }
    private void ployOne(Canvas canvas) {
        canvas.save();
        float[] src = {0, 0,                                    // 左上
                bt.getWidth(), 0,                          // 右上
                bt.getWidth(), bt.getHeight(),        // 右下
                0, bt.getHeight()};                        // 左下

        float[] dst = {0+100, 0,                                    // 左上
                bt.getWidth(),50,                        // 右上
                bt.getWidth(), bt.getHeight() -50,  // 右下
                0, bt.getHeight()};                        // 左下

        Matrix ploy=new Matrix();
        ploy.setPolyToPoly(src,0,dst,0,1);
        canvas.drawBitmap(bt,ploy, paint);
        canvas.restore();
    }

}
