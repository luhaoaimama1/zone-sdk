package com.zone.banner_zonelib.indicator.ani;

import android.widget.ImageView;

import com.zone.banner_zonelib.indicator.ani.abstarct.AbstractAni;

/**
 * Created by Zone on 2016/1/28.
 */
public class MoveAni extends AbstractAni{
    public MoveAni(ImageView iv, int itemLength) {
        super(iv, itemLength);
    }

    //TODO 最后一个 用pageSlected动画即可 其他走动
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        String s = String.format("onPageScrolled====position:%d /tpositionOffset:%f /tpositionOffsetPixels:%d /t", position, positionOffset, positionOffsetPixels);
        System.out.println(s);
        refreshParams();
        params.setMargins((int)(itemLength * (position + positionOffset)), 0, 0, 0);
        iv.setLayoutParams(params);
    }

    @Override
    public void onPageSelected(int position) {
        String s = String.format("onPageSelected====position:%d /t", position);
        System.out.println(s);
        refreshParams();
        params.setMargins(itemLength * position,0,0,0);
        iv.setLayoutParams(params);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        String s = String.format("onPageScrollStateChanged====state:%d /t", state);
        System.out.println(s);
    }
}
