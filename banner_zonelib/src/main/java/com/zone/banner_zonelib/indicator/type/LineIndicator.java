package com.zone.banner_zonelib.indicator.type;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.zone.banner_zonelib.indicator.type.abstarct.AbstractIndicator;

/**
 * Created by Administrator on 2016/1/27.
 */
public class LineIndicator extends AbstractIndicator {
    private Paint paint = null;
    private Bitmap defaultBitmap, selectedBitmap;
    private ShapeEntity defaultShapeStyle, topShapeStyle;

    public LineIndicator(int width, int height) {
        super(width, height);
        paint = new Paint();
        //设置默认的
        setShapeEntity(new ShapeEntity().setStrokeColor(Color.WHITE), new ShapeEntity().setFillColor(Color.RED));
    }

    public LineIndicator setShapeEntity(ShapeEntity defaultCircleStyle, ShapeEntity topCircleStyle){
        this.defaultShapeStyle =defaultCircleStyle;
        this.topShapeStyle =topCircleStyle;

        createDefalutBitmap();
        createSelectedBitmap();
        setBetweenMargin(width/2);
        return this;
    }

    private void initPaint() {
        paint.reset();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(true);
    }

    private void createDefalutBitmap() {
        defaultBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(defaultBitmap);
        //画fill
        initPaint();
        paint.setStyle(Paint.Style.FILL);
        if (!defaultShapeStyle.isHaveFillColor())
            paint.setColor(Color.TRANSPARENT);
        else
            paint.setColor(defaultShapeStyle.getFillColor());
        canvas.drawRect(0 + defaultShapeStyle.getStrokeWidthHalf()*2, 0 + defaultShapeStyle.getStrokeWidthHalf()*2,
                width - defaultShapeStyle.getStrokeWidthHalf()*2, height - defaultShapeStyle.getStrokeWidthHalf()*2, paint);
        //画stroke
        initPaint();
        paint.setStyle(Paint.Style.STROKE);
        if (!defaultShapeStyle.isHaveStrokeColor())
            paint.setColor(Color.TRANSPARENT);
        else
            paint.setColor(defaultShapeStyle.getStrokeColor());
        paint.setStrokeWidth(defaultShapeStyle.getStrokeWidthHalf()*2);
        canvas.drawRect(0 + topShapeStyle.getStrokeWidthHalf(),0+topShapeStyle.getStrokeWidthHalf(),
                width-topShapeStyle.getStrokeWidthHalf(), height-topShapeStyle.getStrokeWidthHalf(), paint);
    }

    private void createSelectedBitmap() {
        selectedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(selectedBitmap);
        //画fill
        initPaint();
        paint.setStyle(Paint.Style.FILL);
        if (!topShapeStyle.isHaveFillColor())
            paint.setColor(Color.TRANSPARENT);
        else
            paint.setColor(topShapeStyle.getFillColor());
        canvas.drawRect(0 + topShapeStyle.getStrokeWidthHalf()*2,0+topShapeStyle.getStrokeWidthHalf()*2,
                width-topShapeStyle.getStrokeWidthHalf()*2, height-topShapeStyle.getStrokeWidthHalf()*2, paint);
        //画stroke
        initPaint();
        paint.setStyle(Paint.Style.STROKE);
        if (!topShapeStyle.isHaveStrokeColor())
            paint.setColor(Color.TRANSPARENT);
        else
            paint.setColor(topShapeStyle.getStrokeColor());
        paint.setStrokeWidth(topShapeStyle.getStrokeWidthHalf()*2);
        canvas.drawRect(0 + topShapeStyle.getStrokeWidthHalf(),0+topShapeStyle.getStrokeWidthHalf(),
                width-topShapeStyle.getStrokeWidthHalf(), height-topShapeStyle.getStrokeWidthHalf(), paint);
    }

    @Override
    public Bitmap getDefaultBitmap(int position) {
        return defaultBitmap;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //TODO 注意 第一次加载的时候  走这个而不走 onPageSelected
        if(positionOffsetPixels==0)
            ivTop.setImageBitmap(selectedBitmap);
        //TODO 这个方法也可以处理图像透明度渐变等特效
    }

}
