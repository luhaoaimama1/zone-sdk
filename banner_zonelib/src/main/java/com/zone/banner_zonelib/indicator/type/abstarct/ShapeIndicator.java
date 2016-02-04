package com.zone.banner_zonelib.indicator.type.abstarct;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Zone on 2016/2/3.
 */
public abstract class ShapeIndicator extends BaseIndicator {
    protected Paint paint = null;
    protected ShapeEntity defaultShapeStyle, topShapeStyle;
    protected Bitmap defaultBitmap, selectedBitmap;

    public ShapeIndicator(int width, int height) {
        super(width, height);
        paint = new Paint();
        setShapeEntity(new ShapeEntity().setFillColor(Color.WHITE), new ShapeEntity().setFillColor(Color.RED));
    }

    protected void initPaint() {
        paint.reset();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(true);
    }

    public ShapeIndicator setShapeEntity(ShapeEntity defaultCircleStyle, ShapeEntity topCircleStyle) {
        this.defaultShapeStyle = defaultCircleStyle;
        this.topShapeStyle = topCircleStyle;
        createDefalutBitmap();
        createSelectedBitmap();
        return this;
    }


    private void createDefalutBitmap() {
        defaultBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        getBitmap(defaultBitmap, defaultShapeStyle);
    }

    private void createSelectedBitmap() {
        selectedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        getBitmap(selectedBitmap, topShapeStyle);
    }

    protected abstract void getBitmap(Bitmap bitmap, ShapeEntity shapeStyle);

    //fillRadius=radius-strokeWidthHalf;
    public static class ShapeEntity {
        //解释为什么是一半  因为不一半  就会用到除法  就会精度损失 然后显示的时候 可能会有缝
        private float strokeWidthHalf;
        private int strokeColor = Color.WHITE;
        private boolean haveStrokeColor = true;
        private int fillColor = Color.WHITE;
        private boolean haveFillColor = true;

        public boolean isHaveStrokeColor() {
            return haveStrokeColor;
        }

        public ShapeEntity setHaveStrokeColor(boolean haveStrokeColor) {
            this.haveStrokeColor = haveStrokeColor;
            return this;
        }

        public boolean isHaveFillColor() {
            return haveFillColor;
        }

        public ShapeEntity setHaveFillColor(boolean haveFillColor) {
            this.haveFillColor = haveFillColor;
            return this;
        }

        public int getFillColor() {
            return fillColor;
        }

        public ShapeEntity setFillColor(int fillColor) {
            this.fillColor = fillColor;
            return this;
        }

        public int getStrokeColor() {
            return strokeColor;
        }

        public ShapeEntity setStrokeColor(int strokeColor) {
            this.strokeColor = strokeColor;
            return this;
        }

        public float getStrokeWidthHalf() {
            return strokeWidthHalf;
        }

        public ShapeEntity setStrokeWidthHalf(float strokeWidthHalf) {
            this.strokeWidthHalf = strokeWidthHalf;
            return this;
        }
    }
}
