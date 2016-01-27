package com.zone.banner_zonelib.indicator;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zone.banner_zonelib.PagerAdapterCycle;
import com.zone.banner_zonelib.R;

/**
 * Created by Administrator on 2016/1/27.
 */
public class IndicatorView extends RelativeLayout  implements ViewPager.OnPageChangeListener {
    private  Context context;
    private ViewPager mViewPager;
    private OnCustomViewListener customViewlistener;
    private GravityType gravityType;
    private LinearLayout ll_1,ll_2;
    private int betweenMargin;
    private ViewPager.OnPageChangeListener pageChangeListener;
    private int childCount;

    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
    }

    public void setViewPager(ViewPager mViewPager) {
        this.mViewPager = mViewPager;
        if(mViewPager.getAdapter() instanceof PagerAdapterCycle)
            childCount=((PagerAdapterCycle) mViewPager.getAdapter()).getSize();
        else
            childCount=mViewPager.getAdapter().getCount();
        mViewPager.setOnPageChangeListener(this);
    }
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener pageChangeListener ){
        this.pageChangeListener=pageChangeListener;
    }

    public void setOnCustomViewListener(OnCustomViewListener customViewlistener, GravityType gravityType,int betweenMargin) {
        this.customViewlistener = customViewlistener;
        this.betweenMargin=betweenMargin;
        removeAllViews();
//        RelativeLayout.LayoutParams a = (LayoutParams) this.getLayoutParams();
//        a.setMargins();
        initGravityType(gravityType);
        initView();
    }

    private void initGravityType(GravityType gravityType) {
        switch (gravityType) {
            case Center:
                setGravity(Gravity.CENTER);
                break;
            case Left:
                setGravity(Gravity.LEFT);
                break;
            case Right:
                setGravity(Gravity.RIGHT);
                break;
        }
    }


    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        ll_1 = (LinearLayout) inflater.inflate(R.layout.core2, null, false);
        ll_2 = (LinearLayout) inflater.inflate(R.layout.core2, null, false);
        addView(ll_1);
        addView(ll_2);
//        addView();
        //TODO 添加几个view   每个view变换的时候都会监听到
        for (int i = 0; i < childCount; i++) {
//            ll_1.addView(customViewlistener.getView(context,1.0,true));
        }
//        ll_2.addView(customViewlistener.getView(context,1.0,true));

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(pageChangeListener!=null)
            pageChangeListener.onPageScrolled(position,positionOffset,positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        if(pageChangeListener!=null)
            pageChangeListener.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if(pageChangeListener!=null)
            pageChangeListener.onPageScrollStateChanged(state);
    }

    public enum GravityType {
        Left, Right, Center;
        public int margin;

        public int getMargin() {
            return margin;
        }

        public void setMargin(int margin) {
            this.margin = margin;
        }
    }

//    public class ViewWrapper extends View{
//
//    }

    public interface OnCustomViewListener {
        //TODO 考虑初始化 未选中状态
        public View getView_NoSelect(Context context, int position);
        //TODO 考虑 子 1与2 同时做动画的时候呢
        public void onPageScrolled(ViewGroup viewGroup,int position, float positionOffset);
    }

}
