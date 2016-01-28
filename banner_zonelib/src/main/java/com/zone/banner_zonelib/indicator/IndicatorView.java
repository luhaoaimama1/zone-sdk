package com.zone.banner_zonelib.indicator;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zone.banner_zonelib.PagerAdapterCycle;
import com.zone.banner_zonelib.R;
import com.zone.banner_zonelib.indicator.ani.DefaultAni;
import com.zone.banner_zonelib.indicator.ani.abstarct.AbstractAni;
import com.zone.banner_zonelib.indicator.type.abstarct.AbstractIndicator;
import com.zone.banner_zonelib.viewpager.ViewPagerCompat;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Administrator on 2016/1/27.
 */
public class IndicatorView extends RelativeLayout  implements ViewPager.OnPageChangeListener {
    private  Context context;
    private ViewPagerCompat mViewPager;
    private GravityType gravityType=GravityType.Center;
    private LinearLayout ll_1,ll_2;
    private ViewPager.OnPageChangeListener pageChangeListener;
    private int childCount, startIndex =0;
    private AbstractAni ani;

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

    public void setViewPager(ViewPagerCompat mViewPager,int startIndex) {
        if(mViewPager.getAdapter() instanceof PagerAdapterCycle)
            childCount=((PagerAdapterCycle) mViewPager.getAdapter()).getSize();
        else
            childCount=mViewPager.getAdapter().getCount();
        mViewPager.setOnPageChangeListener(this);
        if(startIndex >=childCount)
            throw new IllegalArgumentException("startIndex must be < mViewPager's childCount");
        this.startIndex = startIndex;
        this.mViewPager = mViewPager;
        //设置默认重心
        setGravity(gravityType);
    }
    public void setViewPager(ViewPagerCompat mViewPager) {
        setViewPager(mViewPager,0);
    }
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener pageChangeListener ){
        this.pageChangeListener=pageChangeListener;
    }

    public void setGravity(GravityType gravityType) {
        if (gravityType!=null) {
            this.gravityType=gravityType;
        }
        removeAllViews();
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
    }
    private AbstractIndicator indicator;
    private ImageView iv_Top;



    public void setIndicator(AbstractIndicator indicator){
        ll_1.removeAllViews();
        ll_2.removeAllViews();
        this.indicator=indicator;
        for (int i = 0; i < childCount; i++) {
            ImageView iv=new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(indicator.getWidth(), indicator.getHeight());
            if (i!=0||i!=childCount-1) {
                params.rightMargin=indicator.getBetweenMargin();
            }
            iv.setLayoutParams(params);
            iv.setImageBitmap(indicator.getDefaultBitmap(i));
            ll_1.addView(iv);
        }
        iv_Top =new ImageView(context);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(indicator.getWidth(), indicator.getHeight());
        iv_Top.setLayoutParams(params2);
        iv_Top.setImageBitmap(indicator.getSelectedBitmap(startIndex));
        ll_2.addView(iv_Top);
        //设置动画
        ani=new DefaultAni(iv_Top,indicator.getBetweenMargin()+indicator.getWidth());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (ani!=null)
            ani.onPageScrolled(position, positionOffset, positionOffsetPixels);
        if(pageChangeListener!=null)
            pageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        if (ani!=null)
            ani.onPageSelected(position);
        if(pageChangeListener!=null)
            pageChangeListener.onPageSelected(position);
        iv_Top.setImageBitmap(indicator.getSelectedBitmap(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (ani!=null)
            ani.onPageScrollStateChanged(state);
        if(pageChangeListener!=null)
            pageChangeListener.onPageScrollStateChanged(state);
    }
    public void setAni(Class<? extends AbstractAni>  aniClass) {
        try {
            Constructor<? extends AbstractAni> method = aniClass.getDeclaredConstructor(ImageView.class, int.class);
            ani=method.newInstance(iv_Top,indicator.getBetweenMargin()+indicator.getWidth());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public enum GravityType {
        Left, Right, Center;

    }
}
