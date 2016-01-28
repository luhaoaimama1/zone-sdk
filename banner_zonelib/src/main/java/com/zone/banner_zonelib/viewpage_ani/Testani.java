package com.zone.banner_zonelib.viewpage_ani;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by Zone on 2016/1/28.
 * //TODO 首先了解 nineoldandroids 他 看demo  自己做个小范例
 * //TODO 其次setTranslationX 这个的时候  是会在linearLayout里进行移动 与margin 等没关系？
 */
public class Testani  implements ViewPager.PageTransformer   {
    @Override
    public void transformPage(View page, float position) {
        //简单的透明度把
//        结合
//        A页的position变化就是( 0, -1] 1-0
//        B页的position变化就是[ 1 , 0 ]0-1
        if(position<=0&&position>=-1){
            ViewHelper.setAlpha(page,position+1);
            ViewHelper.setTranslationX(page, 0);
        }
        if(position>0&&position<=1){
            ViewHelper.setAlpha(page, 1 - position);
            ViewHelper.setTranslationX(page, page.getWidth() * -position);
//            ViewHelper.animate(page).setDuration(2000).rotationYBy(720).x(100).y(100);
        }

    }
}
