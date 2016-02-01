package com.zone.banner_zonelib.indicator.type.abstarct;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

/**
 * Created by Zone on 2016/1/28.
 */
public abstract class AbstractIndicator  implements ViewPager.OnPageChangeListener{
    protected int width,height;
    protected ImageView iv_Top;


    protected int betweenMargin;

    public AbstractIndicator(int width, int height) {
        this.width = width;
        this.height = height;
    }
    public void setIv_Top(ImageView iv_Top){
        this.iv_Top= iv_Top;
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

    public AbstractIndicator setBetweenMargin(int betweenMargin) {
        this.betweenMargin = betweenMargin;
        return this;
    }


    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
