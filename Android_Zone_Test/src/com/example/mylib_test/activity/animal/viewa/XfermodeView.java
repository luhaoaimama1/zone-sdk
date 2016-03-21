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
public class XfermodeView extends View{
    private  Context context;
    Paint paint=new Paint();
    private PorterDuff.Mode mode;

    public XfermodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    public XfermodeView(Context context) {
        this(context, null);
    }
    public void setXferMode(PorterDuff.Mode mode){
        this.mode=mode;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        onDrawFinal(canvas);
    }


    private void onDrawFinal(Canvas canvas) {
        if (mode!=null) {
            paint.reset();
//            bitmapXfermodeExample1(canvas);
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
        Canvas canvas2=new Canvas();
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

//--------可用1 bitmap叠加 最简单的------------------------------------------------------------------------------------------------
//        canvas2.setBitmap(bt);
//        paint.setXfermode(new PorterDuffXfermode(mode));
//        canvas2.drawBitmap(bt2, 0, 0, paint);
//        //如果canvas.draw的时候 这句话是必须的 因为系统super.draw会绘制一个图层如果不制空叠加模式 会导致混乱的
//        paint.setXfermode(null);
//        //最后用画布展现出那个最终合成的bitmap
//        canvas.drawBitmap(bt, 0, 0, paint);
//--------可用1 结束------------------------------------------------------------------------------------------------


//--------可用2  bitmap叠加   最後发现模式和 setBitmap和此相当  并且 叠加模式可以在draw上用而且可以的不是一次
//        Bitmap bt3 = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_4444);
//        canvas2=new Canvas(bt3);
//        canvas2.drawBitmap(bt, 0, 0, paint);
//        paint.setXfermode(new PorterDuffXfermode(mode));
//        canvas2.drawBitmap(bt2, 0, 0, paint);
//        paint.setXfermode(null);
//        canvas.drawBitmap(bt3, 0, 0, paint);
//--------可用2 结束------------------------------------------------------------------------------------------------

//--------可用3  第二图层layer 叠加

//        paint.setXfermode(null);//此行要注意 super.draw也会话的 如果用叠加模式 会混乱的 当然默认模式是null 如果没写过 可以不写此行
//        canvas.saveLayer(0, 0, getWidth(), getHeight(), paint,
//                Canvas.ALL_SAVE_FLAG);
//        canvas.drawBitmap(bt, 0, 0, paint);
//        paint.setXfermode(new PorterDuffXfermode(mode));
//        canvas.drawBitmap(bt2, 0, 0, paint);
//        canvas.restoreToCount(1);//可以不用  按道理多图层应该是会用到！！！但是不知道为啥不用也好使!!!
//--------可用3 结束------------------------------------------------------------------------------------------------

//--------可用4  多图层layer 叠加
//        paint.setXfermode(null);//此行要注意 super.draw也会话的 如果用叠加模式 会混乱的 当然默认模式是null 如果没写过 可以不写此行
        canvas.saveLayer(0, 0, getWidth(), getHeight(), paint,
                Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(bt, 0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(mode));

        //注意这里savelayer的paint 是有叠加模式的！！！！！！！！！！！！！！！！！
        canvas.saveLayer(0, 0, getWidth(), getHeight(), paint,
                Canvas.ALL_SAVE_FLAG);
        paint.setXfermode(null);//注意这里需要吧叠加模式制空 不然会与layer的空位图 进行叠加
        canvas.drawBitmap(bt2, 0, 0, paint);
//        canvas.restoreToCount(1);//可以不用  按道理多图层应该是会用到！！！但是不知道为啥不用也好使!!!
//--------可用4 结束------------------------------------------------------------------------------------------------
    }
}
