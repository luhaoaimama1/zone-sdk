package com.zone.banner_zonelib.indicator.animation;

import android.widget.ImageView;

import com.nineoldandroids.view.ViewHelper;
import com.zone.banner_zonelib.indicator.animation.abstarct.AbstractAnimation;

/**
 * Created by Zone on 2016/1/28.
 */
public class MoveAnimation extends AbstractAnimation {

    public MoveAnimation(ImageView iv, int itemLength, int childCount) {
        super(iv, itemLength, childCount);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        String s = String.format("onPageScrolled====position:%d /tpositionOffset:%f /tpositionOffsetPixels:%d /t", position, positionOffset, positionOffsetPixels);
//        System.out.println(s);
        if(position!=childCount-1){
            //最后一个之前的操作
            ViewHelper.setX(iv,itemLength * (position + positionOffset));
        }
        if(position==childCount-1&&positionOffset>0.5){
            //最后一个 跳转到第一个的操作
            ViewHelper.setX(iv,itemLength * (0));
        }
    }
}
