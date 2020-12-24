package com.example.mylib_test.activity.animal.viewa;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.zone.lib.utils.view.DrawUtils;

public class GlowView extends View {
    Paint paint = DrawUtils.getStrokePaint(Paint.Style.FILL);
    Paint paint2 = DrawUtils.getStrokePaint(Paint.Style.STROKE);
    Paint paintOutter = DrawUtils.getStrokePaint(Paint.Style.STROKE);

    public GlowView(Context context) {
        super(context);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        paint.setColor(Color.WHITE);
        paint.setMaskFilter(new BlurMaskFilter(70, BlurMaskFilter.Blur.NORMAL));
        paint2.setStrokeWidth(3F);
        paint2.setColor(Color.RED);

        paintOutter.setStrokeWidth(3F);
        paintOutter.setColor(Color.RED);
//        paint.setShadowLayer(4,70,70,Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        canvas.translate(getWidth() / 2, getHeight() / 2);

//        canvas.drawCircle(0, 0, 100, paint);
        canvas.drawOval(new RectF(-300,-100,300,100), paint);
//        canvas.drawOval(new RectF(-300,-100,300,100), paint2);
//        canvas.drawOval(new RectF(-335,-135,335,135), paintOutter);
    }

}
