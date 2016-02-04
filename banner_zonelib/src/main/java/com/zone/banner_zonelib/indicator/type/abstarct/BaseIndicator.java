package com.zone.banner_zonelib.indicator.type.abstarct;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.view.ViewPager;

import com.zone.banner_zonelib.indicator.IndicatorView;

/**
 * Created by Zone on 2016/1/28.
 */
public abstract class BaseIndicator implements ViewPager.OnPageChangeListener {
    protected int width, height;
    protected int betweenMargin;
    protected IndicatorView indicatorView;
    protected boolean writeLog = false;
    //    protected boolean writeLog=true;
    public BaseIndicator(int width, int height) {
        this.width = width;
        this.height = height;
    }

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

    public abstract Bitmap getDefaultBitmap(int position);
    public abstract Bitmap getSelectedBitmap(int position);

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

    public void setIndicatorView(IndicatorView indicatorView) {
        this.indicatorView = indicatorView;
    }

    public BaseIndicator setBetweenMargin(int betweenMargin) {
        this.betweenMargin = betweenMargin;
        return this;
    }



}
