package com.zone.banner_zonelib.indicator.ani.abstarct;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zone.banner_zonelib.indicator.IndicatorView;

/**
 * Created by Zone on 2016/1/28.
 */
public abstract class AbstractAni  implements ViewPager.OnPageChangeListener{
    protected  LinearLayout.LayoutParams params;
    protected ImageView iv;
    protected int itemLength;
    public  AbstractAni(ImageView iv,int itemLength){
        this.iv=iv;
        this.itemLength=itemLength;
    }

    public void refreshParams() {
      params= (LinearLayout.LayoutParams) iv.getLayoutParams();
    }
}
