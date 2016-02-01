package com.zone.banner_zonelib.indicator.animation;

import android.widget.ImageView;

import com.nineoldandroids.view.ViewHelper;
import com.zone.banner_zonelib.indicator.animation.abstarct.AbstractAnimation;

/**
 * Created by Zone on 2016/1/28.
 */
public class DefaultAnimation extends AbstractAnimation {

    public DefaultAnimation(ImageView iv, int itemLength, int childCount) {
        super(iv, itemLength, childCount);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset == 0) {
            ViewHelper.setX(iv, itemLength * (position + positionOffset));
        }
    }

}
