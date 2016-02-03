package com.example.mylib_test.activity.custom_view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.three_place.Images;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zone.banner_zonelib.FixedSpeedScroller;
import com.zone.banner_zonelib.ViewPagerCircle;
import com.zone.banner_zonelib.indicator.IndicatorView;
import com.zone.banner_zonelib.indicator.type.CircleIndicator;
import com.zone.banner_zonelib.indicator.type.ImageIndicator;
import com.zone.banner_zonelib.indicator.type.LineIndicator;
import com.zone.banner_zonelib.indicator.type.abstarct.AbstractIndicator;
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
    PagerAdapterCircle_Image mviewPagerNoCircle;
    private CircleIndicator circleIndicator;
    private LineIndicator lineIndicator;
    private ImageIndicator imageIndicator;

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
        for (int i = 0; i < 4; i++) {
            list.add(Images.imageThumbUrls[i]);
        }
        mviewPager = new PagerAdapterCircle_Image(this, list, true) {
            @Override
            public void setImage(ImageView iv, int position) {
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ImageLoader.getInstance().displayImage(list.get(position), iv);
            }
        };
        mviewPagerNoCircle = new PagerAdapterCircle_Image(this, list, false) {
            @Override
            public void setImage(ImageView iv, int position) {
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ImageLoader.getInstance().displayImage(list.get(position), iv);
            }
        };

        pager.setAdapter(mviewPager, 2);
        pager.setPageTransformer(true, new TestAnime());
        new FixedSpeedScroller(this).setViewPager(pager);
        indicatorView.setViewPager(pager);
        circleIndicator = new CircleIndicator(20);
        indicatorView.setIndicator(circleIndicator);


        circleIndicator = new CircleIndicator(20).setCircleEntity
                (new AbstractIndicator.ShapeEntity().setStrokeWidthHalf(5).setStrokeColor(Color.WHITE).setHaveFillColor(false),
                        new AbstractIndicator.ShapeEntity().setStrokeWidthHalf(5).setFillColor(Color.RED).setHaveStrokeColor(false));
        lineIndicator=new LineIndicator(50,30).setShapeEntity
                (new AbstractIndicator.ShapeEntity().setStrokeWidthHalf(2.5F).setStrokeColor(Color.BLACK).setHaveFillColor(false),
                        new AbstractIndicator.ShapeEntity().setStrokeWidthHalf(2.5F).setFillColor(Color.RED).setHaveStrokeColor(false));


        imageIndicator=new ImageIndicator(100,100);
         List<Bitmap> defaultBitmaps= new  ArrayList<Bitmap>();
        defaultBitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.perm_group_calendar_normal));
        defaultBitmaps.add(BitmapFactory.decodeResource(getResources(),R.drawable.perm_group_camera_normal));
        defaultBitmaps.add(BitmapFactory.decodeResource(getResources(),R.drawable.perm_group_device_alarms_normal));
        defaultBitmaps.add(BitmapFactory.decodeResource(getResources(),R.drawable.perm_group_location_normal));
        List<Bitmap> selectBitmaps= new  ArrayList<Bitmap>();
        selectBitmaps.add(BitmapFactory.decodeResource(getResources(),R.drawable.perm_group_calendar_selected));
        selectBitmaps.add(BitmapFactory.decodeResource(getResources(),R.drawable.perm_group_camera_selected));
        selectBitmaps.add(BitmapFactory.decodeResource(getResources(),R.drawable.perm_group_device_alarms_selected));
        selectBitmaps.add(BitmapFactory.decodeResource(getResources(),R.drawable.perm_group_location_selected));
        imageIndicator.setDefaultBitmaps(defaultBitmaps);
        imageIndicator.setSelectBitmaps(selectBitmaps);

    }

    @Override
    protected void onDestroy() {
        pager.closeTimeCircle();
        super.onDestroy();
    }

    @Override
    public void setListener() {
        //如果我们要对ViewPager设置监听，用indicator设置就行了
//        indicatorView.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            @Override
//            public void onPageSelected(int position) {
////                System.out.println("position");
////                System.out.printf("ViewPagerRepeatWriteActivity onPageSelected====position:%d /t", position);
//            }
//
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
////                System.out.printf("ViewPagerRepeatWriteActivity onPageScrolled====position:%d /tpositionOffset:%f /tpositionOffsetPixels:%d /t", position, positionOffset, positionOffsetPixels);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
////                System.out.printf("ViewPagerRepeatWriteActivity onPageScrollStateChanged====state:%d /t", state);
//
//            }
//        });
    }

    boolean isDefault=true;
    boolean isOpenTime =false;
    boolean isCircle=true;
    Shape shape=Shape.circle;
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
            case R.id.togShape:
                switch (shape) {
                    case circle:
                        indicatorView.setIndicator(circleIndicator);
                        ToastUtils.showLong(ViewPagerRepeatWriteActivity.this, "变成方形");
                        shape=Shape.line;
                        break;
                    case line:
                        indicatorView.setIndicator(lineIndicator);
                        ToastUtils.showLong(ViewPagerRepeatWriteActivity.this, "变成方形");
                        shape=Shape.image;
                        break;
                    case image:
                        indicatorView.setIndicator(imageIndicator);
                        ToastUtils.showLong(ViewPagerRepeatWriteActivity.this, "变成图片");
                        shape=Shape.circle;
                        break;
                }
                if (isDefault)
                    indicatorView.setSnap(false);
                else
                    indicatorView.setSnap(true);
                if (isOpenTime)
                    pager.openTimeCircle();
                else
                    pager.closeTimeCircle();
                break;
            case R.id.togCircle:
                if (isCircle) {
                    pager.setAdapter(mviewPagerNoCircle, 2);
                    isCircle =false;
                    indicatorView.setViewPager(pager);
                    indicatorView.setIndicator(circleIndicator);
                    ToastUtils.showLong(ViewPagerRepeatWriteActivity.this, "关闭循环");
                }else{
                    pager.setAdapter(mviewPager, 2);
                    isCircle =true;
                    indicatorView.setViewPager(pager);
                    indicatorView.setIndicator(circleIndicator);
                    ToastUtils.showLong(ViewPagerRepeatWriteActivity.this,"开启循环");
                }
                if (isDefault)
                    indicatorView.setSnap(false);
                else
                     indicatorView.setSnap(true);
                if (isOpenTime)
                    pager.openTimeCircle();
                else
                    pager.closeTimeCircle();
                break;
        }
    }
    enum Shape{
        circle,line,image;
    }
}
