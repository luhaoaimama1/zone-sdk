package com.zone.banner_zonelib.indicator.animation.abstarct;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

/**
 * Created by Zone on 2016/1/28.
 */
public abstract class AbstractAnimation implements ViewPager.OnPageChangeListener{
    protected  int childCount;
    protected ImageView iv;
    protected int itemLength;
    public AbstractAnimation(ImageView iv, int itemLength, int childCount){
        this.iv=iv;
        this.itemLength=itemLength;
        this.childCount=childCount;
    }


    @Override
    public void onPageSelected(int position) {
        String s = String.format("onPageSelected====position:%d /t", position);
        System.out.println(s);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        String s = String.format("onPageScrollStateChanged====state:%d /t", state);
        System.out.println(s);
    }
}
