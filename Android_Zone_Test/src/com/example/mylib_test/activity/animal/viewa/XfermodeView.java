package com.example.mylib_test.activity.animal.viewa;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/3/21.
 */
public class XfermodeView extends View {
    private Context context;
    Paint paint = new Paint();
    private PorterDuff.Mode mode;

    public XfermodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public XfermodeView(Context context) {
        this(context, null);
    }

    public void setXferMode(PorterDuff.Mode mode) {
        this.mode = mode;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        onDrawFinal(canvas);
    }

    boolean borderIsCircle = false;

    public void setBordMode(boolean borderIsCircle) {
        this.borderIsCircle = borderIsCircle;
        invalidate();
    }

    private void onDrawFinal(Canvas canvas) {
        if (mode != null) {
            paint.reset();
            if (!borderIsCircle)
                bitmapXfermodeExample1(canvas);
            else
                graphXfermodeExample2(canvas);
        }
    }

    //这种对应情况图2
    private void graphXfermodeExample2(Canvas canvas) {
        //为什么这么写savelayer因为 super.draw会又底图层  想要与底层不干扰 不叠加需要save或者 直接画bitmap
        //但是画bitmap的画 那个bitmap又需要一个新的画布去叠加 所以savelayer最快了！！！！

        //这两种都行
//        canvas.saveLayer(0, 0, getWidth(), getHeight(), paint,Canvas.ALL_SAVE_FLAG);
        canvas.saveLayerAlpha(0, 0, getWidth(), getHeight(), 255,
                Canvas.ALL_SAVE_FLAG);

        paint.setColor(Color.YELLOW);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 200, paint);
        paint.setXfermode(new PorterDuffXfermode(mode));
        paint.setColor(Color.BLUE);
        canvas.drawCircle(getWidth() / 2 + 200, getHeight() / 2 + 200, 200, paint);
//        canvas.restoreToCount(1);//可以不用  按道理多图层应该是会用到！！！但是不知道为啥不用也好使!!!
    }

    //这种对应情况图1
    private void bitmapXfermodeExample1(Canvas canvas) {
        Canvas canvas2 = new Canvas();

        //画黄色的圆 满屏幕那种 bitmap
        Bitmap bt = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_4444);
        canvas2.setBitmap(bt);
        paint.setColor(Color.YELLOW);
        canvas2.drawCircle(getWidth() / 2, getHeight() / 2, 200, paint);
        //画蓝色的圆 满屏幕那种 bitmap
        Bitmap bt2 = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_4444);
        canvas2.setBitmap(bt2);
        paint.setColor(Color.BLUE);
        canvas2.drawCircle(getWidth() / 2 + 200, getHeight() / 2 + 200, 200, paint);

//        叠加模式的paint和绘制的paint不要用一个, 因为会混乱

//--------可用1 bitmap叠加 最简单的------------------------------------------------------------------------------------------------
        Paint paint2 = new Paint();
        paint2.setXfermode(null);

        canvas2.setBitmap(bt);
        paint2.setXfermode(new PorterDuffXfermode(mode));
        canvas2.drawBitmap(bt2, 0, 0, paint2);
//        //如果canvas.draw的时候 这句话是必须的 因为系统super.draw会绘制一个图层如果不制空叠加模式 会导致混乱的
//        paint.setXfermode(null);
        //最后用画布展现出那个最终合成的bitmap
        canvas.drawBitmap(bt, 0, 0, paint);
//--------可用1 结束------------------------------------------------------------------------------------------------


//--------可用3  第二图层layer 叠加方式 为 在layer内部的bitmap 而不是图层叠加
//        Paint paint2 = new Paint();
//        paint2.setXfermode(null);
//        canvas.saveLayer(0, 0, getWidth(), getHeight(), paint2,Canvas.ALL_SAVE_FLAG);
//        canvas.drawBitmap(bt, 0, 0, paint);
//        paint.setXfermode(new PorterDuffXfermode(mode));
//        canvas.drawBitmap(bt2, 0, 0, paint);
//        canvas.restoreToCount(1);//可以不用  按道理多图层应该是会用到！！！但是不知道为啥不用也好使!!!
//--------可用3 结束------------------------------------------------------------------------------------------------

//--------可用4  多图层layer 叠加 叠加方式为layer
//Tips: 如果用到 canvas.saveLayer（，，，paint，） 图层的方式进行xfermode 最好给图层一个单独的paint。不要和 图层内部draw的paint的xfermode混淆
//        Paint paint2 = new Paint();
//        paint2.setXfermode(null);
//        canvas.saveLayer(0, 0, getWidth(), getHeight(), paint2,
//                Canvas.ALL_SAVE_FLAG);
//        canvas.drawBitmap(bt, 0, 0, paint);
//
//
//        paint2.setXfermode(new PorterDuffXfermode(mode));  //这个是给 layer用的
//        canvas.saveLayer(0, 0, getWidth(), getHeight(), paint2,
//                Canvas.ALL_SAVE_FLAG);
//        canvas.drawBitmap(bt2, 0, 0, paint);
//        canvas.restoreToCount(1);//可以不用  按道理多图层应该是会用到！！！但是不知道为啥不用也好使!!!
//--------可用4 结束------------------------------------------------------------------------------------------------


    }
}
