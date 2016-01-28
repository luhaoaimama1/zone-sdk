package com.zone.banner_zonelib.indicator.type;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.zone.banner_zonelib.indicator.type.abstarct.AbstractIndicator;

/**
 * Created by Administrator on 2016/1/27.
 */
public class CircleIndicator extends AbstractIndicator {
    private  Paint paint =null;
    private Bitmap defaultBitmap,selectedBitmap;
    private float radius;
//    private CircleEntity defaultBitmapEntity,selectedBitmapEntity;



    private CircleIndicator(int width, int height) {
        super(2*width, 2*height);
        this.radius=1F*width;
        paint =new Paint();
        createDefalutBitmap();
        createSelectedBitmap();
    }
    private  void initPaint(){
        paint.reset();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(true);
    }
    public CircleIndicator(int radius) {
        this(radius, radius);
    }

    public class CircleEntity{
       public float stroke;
       public int strokeColor;
       public int fillColor=-1;//等于-1就是noFill
    }
    private void createDefalutBitmap(){
        defaultBitmap=Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_4444);
        Canvas canvas=new Canvas(defaultBitmap);
//        canvas.drawColor(Color.TRANSPARENT);
        initPaint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
//        paint.setStrokeWidth(3);
//        if(defaultBitmapEntity.fillColor!=-1){
//            paint.setStyle(Paint.Style.FILL_AND_STROKE);
//        }else{
//            paint.setStyle(Paint.Style.STROKE);
//        }
        canvas.drawCircle(radius,radius,radius,paint);
    }
    private void createSelectedBitmap(){
        selectedBitmap=Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_4444);
        Canvas canvas=new Canvas(selectedBitmap);
//        canvas.drawColor(Color.TRANSPARENT);
        initPaint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
//        paint.setStrokeWidth(3);
//        if(defaultBitmapEntity.fillColor!=-1){
//            paint.setStyle(Paint.Style.FILL_AND_STROKE);
//        }else{
//            paint.setStyle(Paint.Style.STROKE);
//        }
        canvas.drawCircle(radius,radius,radius,paint);

    }
    @Override
    public Bitmap getDefaultBitmap(int position) {
//        return null;
        return defaultBitmap;
    }

    @Override
    public Bitmap getSelectedBitmap(int position) {
//        return null;
        return selectedBitmap;
    }

//    public CircleEntity getDefaultBitmapEntity() {
//        return defaultBitmapEntity;
//    }
//
//    public void setDefaultBitmapEntity(CircleEntity defaultBitmapEntity) {
//        this.defaultBitmapEntity = defaultBitmapEntity;
//    }
//
//    public CircleEntity getSelectedBitmapEntity() {
//        return selectedBitmapEntity;
//    }
//
//    public void setSelectedBitmapEntity(CircleEntity selectedBitmapEntity) {
//        this.selectedBitmapEntity = selectedBitmapEntity;
//    }
}
