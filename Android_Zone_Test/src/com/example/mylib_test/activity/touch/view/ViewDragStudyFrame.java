package com.example.mylib_test.activity.touch.view;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.example.mylib_test.R;

/**
 * Created by Zone on 2016/1/29.
 */
public class ViewDragStudyFrame extends FrameLayout {
    private final ViewDragHelper mViewDragHelper;
    private View mMenuView,mMainView,backView,edgeView;

    private Point sourcePoint=new Point();
    public ViewDragStudyFrame(Context context) {
        this(context, null);
    }

    public ViewDragStudyFrame(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewDragStudyFrame(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mViewDragHelper=ViewDragHelper.create(this,mCallback);
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
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
        mMenuView=getChildAt(0);
        mMainView=getChildAt(1);
//        backView=getChildAt(2);
        backView=this.findViewById(R.id.tv_back);
        edgeView=getChildAt(3);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        sourcePoint.x=backView.getLeft();
        sourcePoint.y=backView.getTop();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private  ViewDragHelper.Callback mCallback=new  ViewDragHelper.Callback(){

    // tryCaptureView如何返回ture则表示可以捕获该view的事件 在  ViewDragHelper.Callback中
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return mMainView==child||child==backView;
        }

        //横向滑动
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //通过条件 控制 在什么范围滑动
            if (mMainView==child) {
                int valueM=500;
                if(left<=valueM&&left>0){
                    return left;
                }else if(left<=0){
                    return 0;
                }else{
                    return valueM;
                }
            }
            if(child==backView||child==edgeView){
                return left;
            }
            return 0;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            if(child==backView||child==edgeView){
                return top;
            }
            return 0;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            //手指释放后  检查mMainView的getLeft
            if (releasedChild==mMainView) {
                if(mMainView.getLeft()<500/2){
                    //关闭
                    mViewDragHelper.smoothSlideViewTo(mMainView,0,0);
                    ViewCompat.postInvalidateOnAnimation(ViewDragStudyFrame.this);//兼容刷新的
                    open =false;
                }else{
                    //开启
                    mViewDragHelper.smoothSlideViewTo(mMainView,500,0);
                    //TODO 需要注意这里是布局刷新而不是mMainView
                    ViewCompat.postInvalidateOnAnimation(ViewDragStudyFrame.this);
                    open =true;
                }
            }
            if (releasedChild==backView) {
                mViewDragHelper.settleCapturedViewAt(sourcePoint.x, sourcePoint.y);
                ViewCompat.postInvalidateOnAnimation(ViewDragStudyFrame.this);
            }

        }


        // 而在判断的过程中会去判断另外两个回调的方法：getViewHorizontalDragRange和getViewVerticalDragRange，只有这两个方法返回大于0的值才能正常的捕获。
        @Override
        public int getViewHorizontalDragRange(View child) {
            //TODO  他拦截的距离  是内部的 mTouchSlop
            return 1;
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
//            mViewDragHelper.captureChildView(edgeView,pointerId);
            System.out.println("onEdgeDragStarted");
            mViewDragHelper.captureChildView(edgeView, pointerId);
        }
    };

    @Override
    public void computeScroll() {
        //用到  mViewDragHelper.smoothSlideViewTo 即里面封装的scroller 即需要这样集成
        if(mViewDragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this);//兼容刷新的
        }
    }
    boolean open =false;
    public void toggle(){
        if(open){
            open =false;
            mViewDragHelper.smoothSlideViewTo(mMainView,0,0);
            ViewCompat.postInvalidateOnAnimation(this);//兼容刷新的
        }else{
            open =true;
            mViewDragHelper.smoothSlideViewTo(mMainView,500,0);
            ViewCompat.postInvalidateOnAnimation(this);//兼容刷新的
        }


    }
}
