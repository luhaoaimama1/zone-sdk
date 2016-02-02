package com.zone.banner_zonelib.indicator;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nineoldandroids.view.ViewHelper;
import com.zone.banner_zonelib.PagerAdapterCycle;
import com.zone.banner_zonelib.R;
import com.zone.banner_zonelib.ViewPagerCircle;
import com.zone.banner_zonelib.indicator.animation.DefaultAnimation;
import com.zone.banner_zonelib.indicator.animation.MoveAnimation;
import com.zone.banner_zonelib.indicator.animation.abstarct.AbstractAnimation;
import com.zone.banner_zonelib.indicator.type.abstarct.AbstractIndicator;
import com.zone.banner_zonelib.viewpager.ViewPagerCompat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Administrator on 2016/1/27.
 */
public class IndicatorView extends RelativeLayout implements ViewPager.OnPageChangeListener {
    private Context context;
    private ViewPagerCompat mViewPager;
    private LinearLayout ll_bottom;
    private FrameLayout fl_top;
    private ViewPager.OnPageChangeListener pageChangeListener;
    private int childCount, startIndex = 0, betweenMargin = 0;
    private AbstractAnimation animation;

    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void setViewPager(ViewPagerCompat mViewPager) {
        if (mViewPager.getAdapter() instanceof PagerAdapterCycle){
            childCount = ((PagerAdapterCycle) mViewPager.getAdapter()).getSize();
            startIndex=((ViewPagerCircle)mViewPager).getCurrentItem();
        }
        else{
            childCount = mViewPager.getAdapter().getCount();
            startIndex=mViewPager.getCurrentItem();
        }
        mViewPager.setOnPageChangeListener(this);
        this.mViewPager = mViewPager;
        removeAllViews();
        initView();
    }


    public void setOnPageChangeListener(ViewPager.OnPageChangeListener pageChangeListener) {
        this.pageChangeListener = pageChangeListener;
    }


    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        ll_bottom = (LinearLayout) inflater.inflate(R.layout.core2_linear, null, false);
        fl_top = (FrameLayout) inflater.inflate(R.layout.core2_frame, null, false);
        addView(ll_bottom);
        addView(fl_top);
    }

    private AbstractIndicator indicator;
    private ImageView iv_Top;

    public void setIndicator(AbstractIndicator indicator) {
        this.indicator = indicator;
        this.betweenMargin = indicator.getBetweenMargin();

        ll_bottom.removeAllViews();
        fl_top.removeAllViews();
        //初始化 ll_bottom
        int ll_bottom_sumWidth = initLl_bottom();
        //初始化 fl_top
        initFl_top(ll_bottom_sumWidth);
        indicator.setIv_Top(iv_Top);
        //设置动画
        animation = new DefaultAnimation(iv_Top, betweenMargin + indicator.getWidth(), childCount);
    }

    private int initLl_bottom() {
        int ll_bottom_sumWidth = 0;
        for (int i = 0; i < childCount; i++) {
            ImageView iv = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(indicator.getWidth(), indicator.getHeight());
            ll_bottom_sumWidth += indicator.getWidth();
            if (i != childCount - 1) {
                params.rightMargin = betweenMargin;
                ll_bottom_sumWidth += betweenMargin;
            }
            iv.setLayoutParams(params);
            iv.setImageBitmap(indicator.getDefaultBitmap(i));
            ll_bottom.addView(iv);
        }
        return ll_bottom_sumWidth;
    }

    private void initFl_top(int ll_bottom_sumWidth) {
        iv_Top = new ImageView(context);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(indicator.getWidth(), indicator.getHeight());
        iv_Top.setLayoutParams(params2);
        fl_top.addView(iv_Top);
        ViewGroup.LayoutParams params_ll_2 = fl_top.getLayoutParams();
        params_ll_2.width = ll_bottom_sumWidth;
        fl_top.setLayoutParams(params_ll_2);
        //iv_Top把位置初始化 好
        ViewHelper.setX(iv_Top, startIndex *(betweenMargin + indicator.getWidth()));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (indicator != null)
            indicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
        if (animation != null)
            animation.onPageScrolled(position, positionOffset, positionOffsetPixels);
        if (pageChangeListener != null)
            pageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        if (indicator != null)
            indicator.onPageSelected(position);
        if (animation != null)
            animation.onPageSelected(position);
        if (pageChangeListener != null)
            pageChangeListener.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (indicator != null)
            indicator.onPageScrollStateChanged(state);
        if (animation != null)
            animation.onPageScrollStateChanged(state);
        if (pageChangeListener != null)
            pageChangeListener.onPageScrollStateChanged(state);
    }
    public void setSnap(boolean snap){
        if (snap)
            animation=new MoveAnimation(iv_Top, betweenMargin + indicator.getWidth(), childCount);
        else
            animation=new DefaultAnimation(iv_Top, betweenMargin + indicator.getWidth(), childCount);
    }
    //TODO 如果多动画了会 把这个方法在启用的
//    public void setAnimation(Class<? extends AbstractAnimation> aniClass) {
//        try {
//            Constructor<? extends AbstractAnimation> method = aniClass.getDeclaredConstructor(ImageView.class, int.class, int.class);
//            animation = method.newInstance(iv_Top, betweenMargin + indicator.getWidth(), childCount);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }

}
