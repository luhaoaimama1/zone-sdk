package view.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;

import com.zone.lib.utils.activity_fragment_ui.handler.HandlerUiUtil;

public class ZGestrueDetector {
    private final Context context;
    public static final int GESTURE_STATUS_INIT = 0;
    public static final int GESTURE_STATUS_SCALE = 1;
    public static final int GESTURE_STATUS_FLING = 2;
    public static final int GESTURE_STATUS_ONCLICK = 3;

    private final OnGestureListener mListener;

    ScaleGestureDetector scaleGesture;
    GestureDetectorCompat moveGesture;
    int mGestureStatus = GESTURE_STATUS_INIT;

    public ZGestrueDetector(Context context, OnGestureListener mListener) {
        this.context = context;
        this.mListener = mListener;
        if (mListener != null) {
            moveGesture = new GestureDetectorCompat(context, mListenerInner);
            scaleGesture = new ScaleGestureDetector(context, mScaleListenerInner);
        }
    }

    //双手的时候 move手势 传递了 但是未传递到我设置的监听
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        boolean scaleResult = scaleGesture.onTouchEvent(event);
        boolean moveResult = moveGesture.onTouchEvent(event);
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_UP:
                onUp();
                break;
        }
        return scaleResult || moveResult;
    }


    float distanceYOffset = 0f;

    private void onUp() {
        distanceYOffset = 0f;
        mGestureStatus = GESTURE_STATUS_INIT;
        Log("onUp");
        HandlerUiUtil.INSTANCE.removeCallbacks(longPerformClick);
        System.out.println("触发长恩-移除");
    }

    Runnable longPerformClick=new Runnable() {
        @Override
        public void run() {
            System.out.println("触发长恩-触发");
        }
    };

    private GestureDetector.OnGestureListener mListenerInner = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            HandlerUiUtil.INSTANCE.postDelayed(longPerformClick,1500);
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
        }

        @Override
        public void onLongPress(MotionEvent e) {
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (mGestureStatus == GESTURE_STATUS_SCALE) {
                return true;
            }
            System.out.println("触发长恩-click");
            mGestureStatus = GESTURE_STATUS_ONCLICK;
            mListener.onClick();
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return true;
        }


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log("onFling: " + e1.toString() + e2.toString());
            if(Math.abs(velocityX)>Math.abs(velocityY)){
                System.out.println("ScaleImageView onFling 横向屏蔽");
                return true;
            }
            if (mGestureStatus == GESTURE_STATUS_SCALE) {
                return true;
            }

            mGestureStatus = GESTURE_STATUS_FLING;
            if (velocityY > 0) {//下滑
                mListener.onDownSilde();
                System.out.println("ScaleImageView onFling velocityY>0:" + velocityY);
            } else {
                mListener.onUpSilde();
                System.out.println("ScaleImageView onFling velocityY<0:" + velocityY);
            }
            return true;
        }
    };

    private ScaleGestureDetector.OnScaleGestureListener mScaleListenerInner = new ScaleGestureDetector.OnScaleGestureListener() {
        private float beginLength = 0;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            Log("onScaleBegin:" + detector.toString());
            beginLength = detector.getCurrentSpan();
            mGestureStatus = GESTURE_STATUS_SCALE;
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            Log("onScaleEnd:" + detector.toString());
            if (detector.getCurrentSpan() - beginLength > 0) {
                mListener.onZoomIn();
            } else {
                mListener.onKeading();
            }
            beginLength = 0;
        }
    };


    public interface OnGestureListener {
        public void onZoomIn();

        public void onKeading();

        public void onUpSilde();

        public void onDownSilde();

        public void onClick();
    }

    private void Log(String str) {
        System.out.println("ZGestrueDetector----->" + str);
    }
}
