package com.zone.banner_zonelib.viewpage_anime;

import android.support.v4.view.ViewPager;
import android.view.View;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by Zone on 2016/1/28.
 */
public class TestAnime implements ViewPager.PageTransformer   {
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
