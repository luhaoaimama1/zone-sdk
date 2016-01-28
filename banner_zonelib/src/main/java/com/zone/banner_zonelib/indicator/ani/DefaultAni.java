package com.zone.banner_zonelib.indicator.ani;

import android.widget.ImageView;
import com.zone.banner_zonelib.indicator.ani.abstarct.AbstractAni;

/**
 * Created by Zone on 2016/1/28.
 */
public class DefaultAni extends AbstractAni{
    public DefaultAni(ImageView iv, int itemLength) {
        super(iv, itemLength);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        refreshParams();
        params.setMargins(itemLength * position, 0, 0, 0);
        iv.setLayoutParams(params);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
