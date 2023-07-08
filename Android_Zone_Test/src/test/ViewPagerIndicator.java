package test;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewPagerIndicator extends ImageView {
    public interface Callback {
        void onCallback(Rect bounds);
    }

    public interface SelectCallback {
        void onCallback(int oldShouldSelectPosi, int shouldSelectPosi);
    }

    private final Rect bounds = new Rect();
    private Drawable chatDrawable;
    private Callback fixedWithCallback;
    private SelectCallback selectCallback;
    private int shouldSelectPosi = 0;
    private List<int[]> rects;
    private int startPoint = 0;
    private int selectWidth = 0;
    private boolean initComplete = false;
    @Nullable
    private List<View> views;
    @Nullable
    private ViewPager vp;

    public ViewPagerIndicator(Context context) {
        super(context);
    }

    public ViewPagerIndicator(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPagerIndicator(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Drawable chatDrawable = this.chatDrawable;
        if (chatDrawable == null) {
            return;
        }
        bounds.set(startPoint, 0, startPoint + selectWidth, getHeight());
        if (fixedWithCallback != null) {
            fixedWithCallback.onCallback(bounds);
        }
        chatDrawable.setBounds(bounds);
        chatDrawable.draw(canvas);
    }

    void init(int position) {
        List<View> views = this.views;
        if (views == null) return;
        //绘制 rects[position] 第一此的setposition
        ArrayList<int[]> rects = new ArrayList<>();
        for (int i = 0; i < views.size(); i++) {
            rects.add(new int[2]);
        }
        this.rects = rects;
        for (int i = 0; i < rects.size(); i++) {
            views.get(i).getLocationOnScreen(rects.get(i));
        }
        startPoint = rects.get(position)[0];
        selectWidth = views.get(position).getWidth();
        invalidate();
        initComplete = true;
    }

    public  void setViewPagerWithItemView(ViewPager viewPager,View... views) {
        setViewPagerWithItemView(viewPager, Arrays.asList(views));
    }
    public void setViewPagerWithItemView(ViewPager viewPager, List<View> views) {
        initComplete = false;
        this.vp = viewPager;
        this.views = new ArrayList<>(views);
        if (vp != null && vp.getAdapter() != null && vp.getAdapter().getCount() != views.size()) {
            throw new IllegalStateException("长度不一样!");
        }
        if (!views.isEmpty()) {
            getViewTreeObserver().addOnPreDrawListener(() -> {
                if (vp != null) {
                    init(vp.getCurrentItem());
                    return false;
                }
                return true;
            });
            vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (initComplete) {
                        if (rects == null) return;
                        int nextIndex = position + 1 < views.size() ? position + 1 : position;
                        startPoint = (int) (rects.get(position)[0] + (rects.get(nextIndex)[0] - rects.get(position)[0]) * positionOffset);
                        selectWidth = (int) (views.get(position).getWidth() + (views.get(nextIndex).getWidth() - views.get(position).getWidth()) * positionOffset);
                        invalidate();

                        int nowShouldSelectPosi = position + Math.round(positionOffset);
                        if (shouldSelectPosi != nowShouldSelectPosi) {
                            if (selectCallback != null) {
                                selectCallback.onCallback(shouldSelectPosi, nowShouldSelectPosi);
                            }
                            shouldSelectPosi = nowShouldSelectPosi;
                        }
                    }
                    log("onPageScrolled:" + position +
                            "\t positionOffset:" + positionOffset + "\t positionOffsetPixels:" + positionOffsetPixels);

                }

                @Override
                public void onPageSelected(int position) {
                    log("onPageSelected:$position");
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    log("state:$state");
                }
            });
        }
    }


    private void log(String str) {
        Log.d("ViewPagerIndicator", str);
    }

    public void setFixedWithCallback(Callback fixedWithCallback) {
        this.fixedWithCallback = fixedWithCallback;
    }

    public void setSelectCallback(SelectCallback selectCallback) {
        this.selectCallback = selectCallback;
    }

    void setDrawRes(@DrawableRes int resId) {
        if (getContext() == null) return;
        chatDrawable = getContext().getResources().getDrawable(resId);
    }
}
