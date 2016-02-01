package com.example.mylib_test.activity.custom_view;

import android.view.View;
import android.widget.ImageView;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.three_place.Images;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zone.banner_zonelib.FixedSpeedScroller;
import com.zone.banner_zonelib.ViewPagerCircle;
import com.zone.banner_zonelib.indicator.IndicatorView;
import com.zone.banner_zonelib.indicator.animation.DefaultAnimation;
import com.zone.banner_zonelib.indicator.animation.MoveAnimation;
import com.zone.banner_zonelib.indicator.type.CircleIndicator;
import com.zone.banner_zonelib.simpleadapter.PagerAdapterCircle_Image;
import com.zone.banner_zonelib.viewpage_anime.TestAnime;

import java.util.ArrayList;
import java.util.List;

import and.abstractclass.BaseActvity;
import and.log.ToastUtils;

/**
 * Created by Zone on 2016/1/27.
 */
public class ViewPagerRepeatWriteActivity extends BaseActvity {
    private ViewPagerCircle pager;
    private IndicatorView indicatorView;
    PagerAdapterCircle_Image mviewPager;

    @Override
    public void setContentView() {
        setContentView(R.layout.a_viewpager_circle_indicator);
    }

    @Override
    public void findIDs() {
        pager = (ViewPagerCircle) findViewById(R.id.pager);
        indicatorView = (IndicatorView) findViewById(R.id.indicatorView);
    }

    @Override
    public void initData() {

        final List<String> list = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            list.add(Images.imageThumbUrls[i]);
        }
        mviewPager = new PagerAdapterCircle_Image(this, list) {
            @Override
            public void setImage(ImageView iv, int position) {
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ImageLoader.getInstance().displayImage(list.get(position), iv);
            }
        };
        pager.setAdapter(mviewPager, 2);
        pager.setPageTransformer(true, new TestAnime());
        indicatorView.setViewPager(pager);
        indicatorView.setIndicator(new CircleIndicator(20));
        new FixedSpeedScroller(this).setViewPager(pager);
    }

    @Override
    protected void onDestroy() {
        pager.closeTimeCircle();
        super.onDestroy();
    }

    @Override
    public void setListener() {
        //如果我们要对ViewPager设置监听，用indicator设置就行了
//        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            @Override
//            public void onPageSelected(int position) {
//                System.out.println("position");
//                System.out.printf("onPageSelected====position:%d /t", position);
//            }
//
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                System.out.printf("onPageScrolled====position:%d /tpositionOffset:%f /tpositionOffsetPixels:%d /t", position, positionOffset, positionOffsetPixels);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                System.out.printf("onPageScrollStateChanged====state:%d /t", state);
//
//            }
//        });
    }

    boolean isDefault=true;
    boolean isOpenTime =false;
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bt_toggle:
                if (isDefault) {
                    indicatorView.setSnap(true);
                    isDefault=false;
                    ToastUtils.showLong(ViewPagerRepeatWriteActivity.this,"变成move");
                }else{
                    indicatorView.setSnap(false);
                    isDefault=true;
                    ToastUtils.showLong(ViewPagerRepeatWriteActivity.this,"变成Default");
                }
                break;
            case R.id.bt_toggleTime:
                if (isOpenTime) {
                    pager.closeTimeCircle();
                    isOpenTime =false;
                    ToastUtils.showLong(ViewPagerRepeatWriteActivity.this,"手动轮播");
                }else{
                    pager.openTimeCircle();
                    isOpenTime =true;
                    ToastUtils.showLong(ViewPagerRepeatWriteActivity.this,"定时轮播");
                }
                break;
        }
    }
}