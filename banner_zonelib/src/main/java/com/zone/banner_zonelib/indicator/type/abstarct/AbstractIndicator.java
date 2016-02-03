package com.zone.banner_zonelib.indicator.type.abstarct;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.zone.banner_zonelib.indicator.IndicatorView;

/**
 * Created by Zone on 2016/1/28.
 */
public abstract class AbstractIndicator  implements ViewPager.OnPageChangeListener{
    protected int width,height;
    protected int betweenMargin;
    protected IndicatorView indicatorView;
    protected ImageView ivTop;
    public AbstractIndicator(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    public int getBetweenMargin() {
        return betweenMargin;
    }

    public abstract Bitmap getDefaultBitmap(int position);

    public void setIndicatorView(IndicatorView indicatorView){
        this.indicatorView=indicatorView;
    }
    public void setIvTop(ImageView ivTop){
        this.ivTop=ivTop;
    }
    public AbstractIndicator setBetweenMargin(int betweenMargin) {
        this.betweenMargin = betweenMargin;
        return this;
    }

    protected boolean writeLog = false;
    //    protected boolean writeLog=true;
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (writeLog) {
            String s = String.format("onPageScrolled====position:%d /tpositionOffset:%f /tpositionOffsetPixels:%d /t", position, positionOffset, positionOffsetPixels);
            System.out.println(s);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (writeLog) {
            String s = String.format("onPageSelected====position:%d /t", position);
            System.out.println(s);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (writeLog) {
            String s = String.format("onPageScrollStateChanged====state:%d /t", state);
            System.out.println(s);
        }
    }

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
