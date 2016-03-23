package com.zone.zbanner.indicator.animation;
import com.nineoldandroids.view.ViewHelper;
import com.zone.zbanner.indicator.IndicatorView;
import com.zone.zbanner.indicator.animation.abstarct.BaseAnimation;

/**
 * Created by Zone on 2016/1/28.
 */
public class MoveAnimation extends BaseAnimation {

    private int scrolledPosition=-1;

    public MoveAnimation(IndicatorView indicatorView, int itemLength) {
        super(indicatorView, itemLength);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        super.onPageScrolled(position,positionOffset,positionOffsetPixels);
        scrolledPosition=position;
        if(position!=indicatorView.getIndeciatorCount()-1){
            //最后一个之前的操作
            ViewHelper.setX(indicatorView.getIv_Top(),itemLength * (position + positionOffset));
        }
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        //1->0  last-2->last-1的时候 不需要设置因为设置了话会和onPageScrolled 相互影响而造成闪烁的可能
        if (!((scrolledPosition==0&&position==0)||(scrolledPosition==indicatorView.getIndeciatorCount()-2&&position==indicatorView.getIndeciatorCount()-1))) {
            if(position==0||position==indicatorView.getIndeciatorCount()-1){
                //当0 last-1 的时候才设置select 因为这个时候没有move
                ViewHelper.setX(indicatorView.getIv_Top(),itemLength * position);
            }
        }
    }
}
