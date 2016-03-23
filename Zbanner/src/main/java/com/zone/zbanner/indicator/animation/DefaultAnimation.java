package com.zone.zbanner.indicator.animation;

import com.nineoldandroids.view.ViewHelper;
import com.zone.zbanner.indicator.IndicatorView;
import com.zone.zbanner.indicator.animation.abstarct.BaseAnimation;

/**
 * Created by Zone on 2016/1/28.
 */
public class DefaultAnimation extends BaseAnimation {


    public DefaultAnimation(IndicatorView indicatorView, int itemLength) {
        super(indicatorView, itemLength);
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        String s = String.format("onPageScrolled====position:%d /tpositionOffset:%f /tpositionOffsetPixels:%d /t", position, positionOffset, positionOffsetPixels);
//        System.out.println(s);
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        ViewHelper.setX(indicatorView.getIv_Top() , itemLength * position);
    }
}
