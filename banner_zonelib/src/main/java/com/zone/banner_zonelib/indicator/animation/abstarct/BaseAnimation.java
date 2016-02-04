package com.zone.banner_zonelib.indicator.animation.abstarct;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.zone.banner_zonelib.indicator.IndicatorView;

/**
 * Created by Zone on 2016/1/28.
 */
public abstract class BaseAnimation implements ViewPager.OnPageChangeListener{

    protected  IndicatorView indicatorView;
    protected int itemLength;
    protected int state;
    protected  ImageView iv_top;
    protected  int childCount;

    public BaseAnimation(IndicatorView indicatorView, int itemLength){
        this.itemLength=itemLength;
        this.indicatorView=indicatorView;
    }
//    public AbstractAnimation(ImageView iv_top, int itemLength, int childCount) {
//        this.iv_top=iv_top;
//        this.itemLength=itemLength;
//        this.childCount=childCount;
//    }


    protected boolean writeLog=false;
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
        this.state=state;
        if (writeLog) {
            String s = String.format("onPageScrollStateChanged====state:%d /t", state);
            System.out.println(s);
        }
    }
}
