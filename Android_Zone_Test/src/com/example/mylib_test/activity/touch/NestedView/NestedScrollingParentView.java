package com.example.mylib_test.activity.touch.NestedView;
import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import com.example.mylib_test.R;

import com.zone.lib.utils.reflect.Reflect;

/**
 * HongYang  demo 借鉴过来的；
 */
public class NestedScrollingParentView extends LinearLayout implements NestedScrollingParent {
    private static final String TAG = "StickyNavLayout";
    private final NestedScrollingParentHelper mNestedScrollingParentHelper;

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        mNestedScrollingParentHelper.onNestedScrollAccepted(child,target,nestedScrollAxes);

    }

    @Override
    public int getNestedScrollAxes() {
        return mNestedScrollingParentHelper.getNestedScrollAxes();
    }

    @Override
    public void onStopNestedScroll(View target) {
        mNestedScrollingParentHelper.onStopNestedScroll(target);

    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        Log.e(TAG, "onStartNestedScroll");
        return nestedScrollAxes== ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.e(TAG, "onNestedScroll");
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        Log.e(TAG, "onNestedPreScroll");
        //父控件消耗的两种情况：
        //1.下啦 显示头部  (里面不能下拉了，并且方向向上，并且 头部漏出)
        if (!ViewCompat.canScrollVertically(target, -1) && dy < 0 && getScrollY() > 0) {
            consumed[1] = dy;
            scrollBy(0, dy);
        }
        //2.上啦 隐藏头部
        if (dy > 0 && getScrollY() < mTopViewHeight) {
            int length = mTopViewHeight - getScrollY();
            consumed[1] = dy < length ? dy : length;
            scrollBy(0, dy);
        }

        Log.e(TAG,
                String.format("dx:%d \tdy:%d \tcanScrollVertically 1:%b \tcanScrollVertically -1:%b " +
                        "自己:\tcanScrollVertically 1:%b \tcanScrollVertically -1:%b"
                ,dx,dy,ViewCompat.canScrollVertically(target, 1),ViewCompat.canScrollVertically(target, -1),
                        ViewCompat.canScrollVertically(this, 1),ViewCompat.canScrollVertically(this, -1)));

    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.e(TAG, "onNestedFling");
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.e(TAG, "onNestedPreFling");
        //有很多种方案： 一下两种 为例可自行随意扩展
        //1.子滑动的时候 父亲也滑动；这样return false 子就继续滑动了。参考：CoordinatorLayout

        mScroller.fling(0, getScrollY(), 0, (int) velocityY, 0, 0, 0, mTopViewHeight);
        invalidate();
        return false;

        //2.头部显示和隐藏 会 切断 子滑动的继续；所以到那时会有个停顿，然后你在滑动就是了；

        //必须有=因为 没有等于就是一直 true 子布局的滑动会被此方法影响 导致不能正常；
//        if (getScrollY() >= mTopViewHeight) return false;
//        //最后为啥不用 getHeight 而用mTopViewHeight 因为我fling能看到头部的时候就想到哪挺住；
////        mScroller.fling(0, getScrollY(), 0, (int) velocityY,0, 0, 0, mTopViewHeight);
////        invalidate();
//
//        return true;
    }

    private View mTop;
    private View recyclerView;
    private int mTopViewHeight;
    private View indicatorView;
    private OverScroller mScroller;

    public NestedScrollingParentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        mScroller = new OverScroller(context);
        mNestedScrollingParentHelper=new NestedScrollingParentHelper(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTop = findViewById(R.id.id_stickynavlayout_topview);
        recyclerView =  findViewById(R.id.recyclerView);
        indicatorView =  findViewById(R.id.indicatorView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //不限制顶部的高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //这里主要 诠释了UNSPECIFIED的含义；
        //AT_MOST:/System.out: 顶部高度：0
        //EXACTLY:/System.out: 顶部高度：0
        //UNSPECIFIED:/System.out: 顶部高度：512
//        getChildAt(0).measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        //AT_MOST:/System.out: 顶部高度：512
        Reflect.on(getChildAt(0)).call("measureChildren"
                , widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.AT_MOST));
//        ((ViewGroup)getChildAt(0)).measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        System.out.println("顶部高度：" + mTop.getMeasuredHeight());


        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
//        这行代码不需要set就可以了？  有点吊 为啥这么做呢因为他想高度 mViewPager+mNav正好等于 本view的高度；
        //本身的matchParent不可以的 因为他会随着 上面的view被占有后大小会变小的；
        params.height = getMeasuredHeight()-indicatorView.getMeasuredHeight() ;

        //设置要的高度 正好是 top的高度 加上 原来本身的高度；
        setMeasuredDimension(getMeasuredWidth(), mTop.getMeasuredHeight() +  getMeasuredHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopViewHeight = mTop.getMeasuredHeight();
    }


    @Override
    public void scrollTo(int x, int y) {
        //防止滑动出界； 滑动多了也不怕
        if (y < 0)
            y = 0;
//        if (y > mTopViewHeight)
//            y = mTopViewHeight;
        super.scrollTo(x, y);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //拦截 则不会走  nest嵌套
        Log.i(TAG, "onInterceptTouchEvent");
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN :
                Log.i(TAG, "onInterceptTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "onInterceptTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "onInterceptTouchEvent ACTION_UP");
                break;

            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
//        return true;
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i(TAG, "onTouchEvent");
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN :
                Log.i(TAG, "onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "onTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "onTouchEvent ACTION_UP");
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean canScrollVertically(int direction) {
        if (direction < 0) {
            return getScrollY() == 0;
        } else {
            return getScrollY()==mTopViewHeight;
        }
//        return super.canScrollVertically(direction);
    }
}
