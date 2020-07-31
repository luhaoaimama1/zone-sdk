package com.example.mylib_test.activity.touch.view;

import android.content.Context;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;


/**
 * Created by Zone on 2016/1/29.
 */
public class ViewDragFrame extends FrameLayout {
    private final ViewDragHelper mViewDragHelper;
    private View mMenuView, mMainView;
    private int bgColor = 0xb0000000;
    private int MaxMoveLength = 500;
    boolean isEnableMove = true;
    int moveType=ViewDragHelper.EDGE_RIGHT;

    private ProgressCallback progressCallback;
    public ViewDragFrame(Context context) {
        this(context, null);
    }

    public ViewDragFrame(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewDragFrame(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mViewDragHelper = ViewDragHelper.create(this, mCallback);
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_RIGHT);
    }

    public int getMoveType() {
        return moveType;
    }

    public void setMoveType(int moveType) {
        if(moveType!=ViewDragHelper.EDGE_LEFT&&moveType!=ViewDragHelper.EDGE_RIGHT)
            throw new IllegalArgumentException("参数异常!");
        mViewDragHelper.setEdgeTrackingEnabled(moveType);
        this.moveType = moveType;

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    //加载xml完事后 执行
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMenuView = getChildAt(0);
        mMainView = getChildAt(1);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }


    private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {

        // tryCaptureView如何返回ture则表示可以捕获该view的事件 在  ViewDragHelper.Callback中
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return mMainView == child;
        }

        //横向滑动
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {

            int returnValue = 0;
            //通过条件 控制 在什么范围滑动
            if (mMainView == child) {
                log("left:" + left + "\t dx:" + dx);
                switch (moveType) {
                    case ViewDragHelper.EDGE_RIGHT:
                        if (left >= -MaxMoveLength && left < 0) {
                            returnValue = left;
                        } else if (left >= 0) {
                            returnValue = 0;
                        } else {
                            returnValue = -MaxMoveLength;
                        }
                        break;
                    case ViewDragHelper.EDGE_LEFT:
                        if (left <= MaxMoveLength && left > 0) {
                            returnValue = left;
                        } else if (left <= 0) {
                            returnValue = 0;
                        } else {
                            returnValue = MaxMoveLength;
                        }
                        break;
                }
            }
            return returnValue;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if (changedView == mMainView) {
                progress(left);
            }
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return 0;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            //手指释放后  检查mMainView的getLeft
            if (releasedChild == mMainView) {
                if (Math.abs(mMainView.getLeft()) < MaxMoveLength / 2) {
                    //关闭
                    close();
                } else {
                    //开启
                    open();
                }
            }

        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            log(state + "");
        }

        // 而在判断的过程中会去判断另外两个回调的方法：getViewHorizontalDragRange和getViewVerticalDragRange，只有这两个方法返回大于0的值才能正常的捕获。
        @Override
        public int getViewHorizontalDragRange(View child) {
            log("getViewHorizontalDragRange");
            //TODO  他拦截的距离  是内部的 mTouchSlop
            if (isEnableMove)
                return 1;
            else
                return -1;
        }

        //小于0就不拦截
        @Override
        public int getViewVerticalDragRange(View child) {
            return -1;
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            //captureChildView  这个方法会让  此view被捕捉了  但是没有tryCaptureView每次都可以捕捉
            //这个仅仅是 onEdgeDragStarted 的时候才可以被捕捉到
            log("onEdgeDragStarted");
            if (isEnableMove)
                mViewDragHelper.captureChildView(mMainView, pointerId);
        }
    };

    @Override
    public void computeScroll() {
        //用到  mViewDragHelper.smoothSlideViewTo 即里面封装的scroller 即需要这样集成
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);//兼容刷新的
        }
    }

    boolean open = false;

    public void close() {
        open = false;
        mViewDragHelper.smoothSlideViewTo(mMainView, 0, 0);
        ViewCompat.postInvalidateOnAnimation(this);//兼容刷新的
    }

    public void open() {
        open = true;
        switch (moveType) {
            case ViewDragHelper.EDGE_RIGHT:
                mViewDragHelper.smoothSlideViewTo(mMainView, -MaxMoveLength, 0);
                break;
            case ViewDragHelper.EDGE_LEFT:
                mViewDragHelper.smoothSlideViewTo(mMainView, MaxMoveLength, 0);
                break;
        }

        ViewCompat.postInvalidateOnAnimation(this);//兼容刷新的
    }


    public void toggle() {
        if (open) {
            close();
        } else {
            open();
        }
    }

    public boolean isEnableMove() {
        return isEnableMove;
    }

    public void setEnableMove(boolean enableMove) {
        isEnableMove = enableMove;
    }

    public int getMaxMoveLength() {
        return MaxMoveLength;
    }

    public void setMaxMoveLength(int maxMoveLength) {
        MaxMoveLength = maxMoveLength;
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public void progress(float slideOffset) {
        log("slideOffset:" + slideOffset);
        if (progressCallback != null)
            progressCallback.progress(slideOffset);
    }


    public interface ProgressCallback {
        void progress(float slideOffset);
    }

    public ProgressCallback getProgressCallback() {
        return progressCallback;
    }

    public void setProgressCallback(ProgressCallback progressCallback) {
        this.progressCallback = progressCallback;
    }

    private void log(String str) {
        Log.v("ViewDragFrame", str);
    }
}
