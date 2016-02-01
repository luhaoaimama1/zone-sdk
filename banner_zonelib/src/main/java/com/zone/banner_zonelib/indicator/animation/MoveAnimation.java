package com.zone.banner_zonelib.indicator.animation;

import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.nineoldandroids.view.ViewHelper;
import com.zone.banner_zonelib.indicator.animation.abstarct.AbstractAnimation;

/**
 * Created by Zone on 2016/1/28.
 */
public class MoveAnimation extends AbstractAnimation {

    private int scrolledPosition=-1;
    public MoveAnimation(ImageView iv, int itemLength, int childCount) {
        super(iv, itemLength, childCount);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        super.onPageScrolled(position,positionOffset,positionOffsetPixels);
        scrolledPosition=position;
        if(position!=childCount-1){
            //最后一个之前的操作
            ViewHelper.setX(iv,itemLength * (position + positionOffset));
        }
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        //1->0  last-2->last-1的时候 不需要设置因为设置了话会和onPageScrolled 相互影响而造成闪烁的可能
        if (!((scrolledPosition==0&&position==0)||(scrolledPosition==childCount-2&&position==childCount-1))) {
            if(position==0||position==childCount-1){
                //当0 last-1 的时候才设置select 因为这个时候没有move
                ViewHelper.setX(iv,itemLength * position);
            }
        }
    }
}
