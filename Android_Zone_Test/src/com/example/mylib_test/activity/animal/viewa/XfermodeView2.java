package com.example.mylib_test.activity.animal.viewa;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.View;

import and.utils.view.graphics.MatteUtils;
import and.utils.view.graphics.basic.Circle;

/**
 * Created by Administrator on 2016/3/21.
 */
public class XfermodeView2 extends View {
    Paint paint = new Paint();

    public XfermodeView2(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Canvas canvas2 = new Canvas();
        //画黄色的圆 满屏幕那种 bitmap
        Bitmap bt = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_4444);
        canvas2.setBitmap(bt);
        //1:绘制红色的矩形
        paint.setColor(Color.RED);
        canvas2.drawRect(getWidth() / 2 - 200, getHeight() / 2 - 200, getWidth() / 2 + 200, getHeight() / 2 + 300, paint);
        //2:saveLayer
        canvas2.saveLayer(0, 0, getWidth(), getHeight(), paint,
                Canvas.ALL_SAVE_FLAG);
        //3:绘制黄色的圆
        paint.setColor(Color.YELLOW);
        canvas2.drawCircle(getWidth() / 2, getHeight() / 2, 200, paint);

        //4:用我的工具类进行 matte
        Matrix mar = new Matrix();
        mar.postTranslate(-100, 0);
        paint.setColor(Color.BLUE);
        MatteUtils.setMatte(canvas2, new Circle(getWidth() / 2 + 200, getHeight() / 2 + 200, 200F).getPath()
                , mar, paint, PorterDuff.Mode.SRC_OUT);

        //5:绘制个浅蓝色的圆看看 上面的工具类对此有影响不
        Circle topCircle = new Circle(getWidth() / 2 + 200, getHeight() / 2 + 200, 100F);
        paint.setColor(Color.CYAN);
        canvas2.drawPath(topCircle.getPath(), paint);
        canvas2.restore();//画布提交  对应上面的 保存图层,不然会显示不出来 saveLayer的那层画面;

        canvas.drawBitmap(bt, 0, 0, paint);
    }
}
