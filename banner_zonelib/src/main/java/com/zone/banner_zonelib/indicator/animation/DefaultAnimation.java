package com.zone.banner_zonelib.indicator.animation;

import android.widget.ImageView;

import com.nineoldandroids.view.ViewHelper;
import com.zone.banner_zonelib.indicator.IndicatorView;
import com.zone.banner_zonelib.indicator.animation.abstarct.AbstractAnimation;

/**
 * Created by Zone on 2016/1/28.
 */
public class DefaultAnimation extends AbstractAnimation {


    public DefaultAnimation(ImageView iv_top, int itemLength, int childCount) {
        super(iv_top, itemLength, childCount);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        String s = String.format("onPageScrolled====position:%d /tpositionOffset:%f /tpositionOffsetPixels:%d /t", position, positionOffset, positionOffsetPixels);
//        System.out.println(s);
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        ViewHelper.setX(iv_top, itemLength * position);
    }
}
