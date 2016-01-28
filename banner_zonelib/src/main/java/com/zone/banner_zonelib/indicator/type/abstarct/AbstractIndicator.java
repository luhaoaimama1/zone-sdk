package com.zone.banner_zonelib.indicator.type.abstarct;

import android.graphics.Bitmap;

/**
 * Created by Zone on 2016/1/28.
 */
public abstract class AbstractIndicator {
    protected int width,height;
    protected int betweenMargin;

    public AbstractIndicator(int width, int height) {
        this.width = width;
        this.height = height;
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

    public void setBetweenMargin(int betweenMargin) {
        this.betweenMargin = betweenMargin;
    }
}
