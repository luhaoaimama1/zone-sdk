package com.example.mylib_test.activity.animal.viewa;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.zone.lib.utils.view.DrawUtils;

public class GlowView extends View {
    Paint paint = DrawUtils.getStrokePaint(Paint.Style.FILL);

    public GlowView(Context context) {
        super(context);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        paint.setColor(Color.RED);
        paint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.SOLID));
//        paint.setShadowLayer(50,70,70,Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawColor(Color.BLACK);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.drawCircle(0, 0, 100, paint);
    }

}
