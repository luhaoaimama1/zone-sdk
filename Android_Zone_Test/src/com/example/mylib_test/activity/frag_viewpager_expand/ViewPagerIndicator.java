package com.example.mylib_test.activity.frag_viewpager_expand;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class ViewPagerIndicator extends ImageView  {

    private Drawable chatDrawable;

    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (chatDrawable != null) {
            chatDrawable.setBounds(startpoint, 0, startpoint + kuand, getHeight());
            chatDrawable.draw(canvas);
        }
    }


    public void setViewPagerWithItemView(ViewPager vp, View... views) {
        if (vp.getAdapter().getCount() != views.length)
            throw new IllegalStateException("长度不一样!");


        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (state == -1) {
                    //绘制 rects[position]
                    //第一此的setposition
                    vp.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rects = new int[views.length][2];
                            for (int i = 0; i < rects.length; i++)
                                //postDelay 因为有些机器需要延时才能得到正确的值 不然值特别奇怪
                                //而且延时200都不一定获得正确的值... 如果有人知道更好的办法请告诉我~
                                views[i].getLocationOnScreen(rects[i]);
                            startpoint = rects[position][0];
                            kuand = views[position].getWidth();
                            invalidate();
                        }
                    }, 500);
                } else {
                    if (position + 1 < views.length) {//防止数组越界
                        startpoint = (int) (rects[position][0]
                                + (rects[position + 1][0] - rects[position][0]) * positionOffset);
                        kuand = (int) (views[position].getWidth()
                                + (views[position + 1].getWidth() - views[position].getWidth()) * positionOffset);
                        invalidate();
                    }
                }
                log("onPageScrolled:" + position +
                        "\t positionOffset:" + positionOffset + "\t positionOffsetPixels:" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                log("onPageSelected:" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                log("state:" + state);
                ViewPagerIndicator.this.state = state;
            }
        });
    }

    private int[][] rects;
    private int state = -1;
    private int startpoint = 0;
    private int kuand = 0;

    public void setDrawRes(int resId) {
        chatDrawable = getResources().getDrawable(resId);
    }

    private void log(String str) {
        Log.i("MyImageView2", str);
    }

}
