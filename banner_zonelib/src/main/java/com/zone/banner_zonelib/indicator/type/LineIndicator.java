package com.zone.banner_zonelib.indicator.type;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.zone.banner_zonelib.indicator.type.abstarct.ShapeIndicator;

/**
 * Created by Administrator on 2016/1/27.
 */
public class LineIndicator extends ShapeIndicator {

    public LineIndicator(int width, int height) {
        super(width, height);
        setBetweenMargin(width / 2);
    }

    @Override
    protected void getBitmap(Bitmap bitmap, ShapeEntity shapeStyle) {
        Canvas canvas = new Canvas(bitmap);
        //画fill
        initPaint();
        paint.setStyle(Paint.Style.FILL);
        if (!shapeStyle.isHaveFillColor())
            paint.setColor(Color.TRANSPARENT);
        else
            paint.setColor(shapeStyle.getFillColor());
        canvas.drawRect(0 + shapeStyle.getStrokeWidthHalf() * 2, 0 + shapeStyle.getStrokeWidthHalf() * 2,
                width - shapeStyle.getStrokeWidthHalf() * 2, height - shapeStyle.getStrokeWidthHalf() * 2, paint);
        //画stroke
        initPaint();
        paint.setStyle(Paint.Style.STROKE);
        if (!shapeStyle.isHaveStrokeColor())
            paint.setColor(Color.TRANSPARENT);
        else
            paint.setColor(shapeStyle.getStrokeColor());
        paint.setStrokeWidth(shapeStyle.getStrokeWidthHalf() * 2);
        canvas.drawRect(0 + shapeStyle.getStrokeWidthHalf(), 0 + shapeStyle.getStrokeWidthHalf(),
                width - shapeStyle.getStrokeWidthHalf(), height - shapeStyle.getStrokeWidthHalf(), paint);
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
