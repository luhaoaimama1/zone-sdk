package com.zone.banner_zonelib.indicator.type;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.zone.banner_zonelib.indicator.type.abstarct.ShapeIndicator;

/**
 * Created by Administrator on 2016/1/27.
 */
public class CircleIndicator extends ShapeIndicator {

    private CircleIndicator(int width, int height) {
        super(2 * width, 2 * height);
        setBetweenMargin(width);
    }

    public CircleIndicator(int radius) {
        this(radius, radius);
    }

    @Override
    protected void getBitmap(Bitmap bitmap, ShapeEntity circleStyle) {
        float radius = (int)(width / 2)*1F;
        Canvas canvas = new Canvas(bitmap);
        //画fill
        initPaint();
        paint.setStyle(Paint.Style.FILL);
        if (!circleStyle.isHaveFillColor())
            paint.setColor(Color.TRANSPARENT);
        else
            paint.setColor(circleStyle.getFillColor());
        canvas.drawCircle(radius, radius, radius - circleStyle.getStrokeWidthHalf() * 2, paint);
        //画stroke
        initPaint();
        paint.setStyle(Paint.Style.STROKE);
        if (!circleStyle.isHaveStrokeColor())
            paint.setColor(Color.TRANSPARENT);
        else
            paint.setColor(circleStyle.getStrokeColor());
        paint.setStrokeWidth(circleStyle.getStrokeWidthHalf() * 2);
        canvas.drawCircle(radius, radius, radius - circleStyle.getStrokeWidthHalf(), paint);
    }

    @Override
    public Bitmap getDefaultBitmap(int position) {
        return defaultBitmap;
    }

    @Override
    public Bitmap getSelectedBitmap(int position) {
        return selectedBitmap;
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        indicatorView.getIv_Top().setImageBitmap(getSelectedBitmap(position));
    }


}
