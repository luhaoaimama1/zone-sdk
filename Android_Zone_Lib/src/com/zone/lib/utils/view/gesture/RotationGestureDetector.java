package com.zone.lib.utils.view.gesture;

import android.view.MotionEvent;

import androidx.annotation.NonNull;

public class RotationGestureDetector {

    private static final int INVALID_POINTER_INDEX = -1;

    private float fX, fY, sX, sY;

    private int mPointerIndex1, mPointerIndex2;
    private float MoveAngle;


    private float mStartAngle;
    private boolean mIsFirstTouch;

    private OnRotationGestureListener mListener;

    public RotationGestureDetector(OnRotationGestureListener listener) {
        mListener = listener;
        mPointerIndex1 = INVALID_POINTER_INDEX;
        mPointerIndex2 = INVALID_POINTER_INDEX;
    }

    public float getMoveAngle() {
        return MoveAngle;
    }
    public float getmStartAngle() {
        return mStartAngle;
    }


    public boolean onTouchEvent(@NonNull MotionEvent event) {
        switch (event.getAction()&MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                sX = event.getX();
                sY = event.getY();
                mPointerIndex1 = event.findPointerIndex(event.getPointerId(0));
                MoveAngle = 0;
                mIsFirstTouch = true;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                fX = event.getX(1);
                fY = event.getY(1);
                mPointerIndex2 = event.findPointerIndex(event.getPointerId(event.getActionIndex()));
                MoveAngle = 0;
                mIsFirstTouch = true;
                mStartAngle=(float) Math.toDegrees( Math.atan2(fY-sY, fX-sX));
//                System.out.println("mlgb:"+mStartAngle);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mPointerIndex1 != INVALID_POINTER_INDEX && mPointerIndex2 != INVALID_POINTER_INDEX && event.getPointerCount() > mPointerIndex2) {
                    float nfX, nfY, nsX, nsY;

                    nsX = event.getX(mPointerIndex1);
                    nsY = event.getY(mPointerIndex1);
                    nfX = event.getX(mPointerIndex2);
                    nfY = event.getY(mPointerIndex2);

                    if (mIsFirstTouch) {
                        MoveAngle = 0;
                        mIsFirstTouch = false;
                    } else {
                        calculateAngleBetweenLines( nfX, nfY, nsX, nsY);
                    }

                    if (mListener != null) {
                        mListener.onRotation(this);
                    }
                    fX = nfX;
                    fY = nfY;
                    sX = nsX;
                    sY = nsY;
                }
                break;
            case MotionEvent.ACTION_UP:
                mPointerIndex1 = INVALID_POINTER_INDEX;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mPointerIndex2 = INVALID_POINTER_INDEX;
                break;
        }
        return true;
    }

    private float calculateAngleBetweenLines(
                                             float sx1, float sy1, float sx2, float sy2) {
        return calculateAngleDelta((float) Math.toDegrees( Math.atan2((sy1 - sy2), (sx1 - sx2))));
    }

    private float calculateAngleDelta(float angleTo) {
        MoveAngle = angleTo - mStartAngle ;
//        System.out.println("mlgb:"+MoveAngle+"__________angleTo  "+angleTo +"_______angleFrom"+ mStartAngle);
        return MoveAngle;
    }

    public static class SimpleOnRotationGestureListener implements OnRotationGestureListener {

        @Override
        public boolean onRotation(RotationGestureDetector rotationDetector) {
            return false;
        }
    }

    public interface OnRotationGestureListener {

        boolean onRotation(RotationGestureDetector rotationDetector);
    }

}