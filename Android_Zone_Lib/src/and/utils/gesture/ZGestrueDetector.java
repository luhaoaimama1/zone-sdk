package and.utils.gesture;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PointF;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

/**
 * Created by Administrator on 2016/6/19.
 * todo 注意 onScroll 里面的 距离是反向的
 */
public class ZGestrueDetector {
    private final  OnRotationGestureListener rotationListener;
    private final  OnScaleGestureListener scaleListener;
    private final  GestureDetector.OnGestureListener moveListener;
    private final Context context;

    private  ScaleGestureDetector scaleGesture;
    private  RotationGestureDetector ratotionGesture;
    private  GestureDetectorCompat moveGesture;
    private EventType eventType =EventType.Move;
    public enum EventType{
        Move,Other;
    }
    public static boolean printLog=false;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private ZGestrueDetector(Context context, OnRotationGestureListener rotationListener, OnScaleGestureListener scaleListener, GestureDetector.OnGestureListener moveListener) {
        this.context = context;
        this.rotationListener = rotationListener;
        this.scaleListener = scaleListener;
        this.moveListener = moveListener;
        if (moveListener!=null)
            moveGesture =new GestureDetectorCompat(context,zMoveListener);
        if (moveListener!=null)
            scaleGesture =new ScaleGestureDetector(context,zScaleListener);
        if (rotationListener!=null)
            ratotionGesture =new RotationGestureDetector(zRotationListener);
    }

    //双手的时候 move手势 传递了 但是未传递到我设置的监听
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        switch (event.getAction()&MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                Log("ACTION_DOWN:"+event.toString());
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                eventType=EventType.Other;
                Log("ACTION_POINTER_DOWN222:"+event.toString());
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                Log("ACTION_UP:"+event.toString());
                clearHistory();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                eventType=EventType.Move;
                Log("ACTION_POINTER_UP2222:"+event.toString());
                break;
        }
        boolean scaleResult = scaleGesture.onTouchEvent(event);
        boolean ratotionResult = ratotionGesture.onTouchEvent(event);
        boolean moveResult =moveGesture.onTouchEvent(event);
        return scaleResult||ratotionResult||moveResult;
    }

    private void clearHistory() {

    }



    private GestureDetector.OnGestureListener zMoveListener=new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            Log("onDown:"+e.toString());
            if(eventType==EventType.Other)
                return false;
            return moveListener.onDown(e);
        }

        @Override
        public void onShowPress(MotionEvent e) {
            if(eventType==EventType.Move)
                moveListener.onShowPress(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log("onSingleTapUp");
            if(eventType==EventType.Other)
                return false;
            return   moveListener.onSingleTapUp(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log("distanceX:"+distanceX+"____________distanceY:"+distanceY);
            if(eventType==EventType.Other)
                return false;
            return moveListener.onScroll(e1,e2,distanceX,distanceY);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            if(eventType==EventType.Move)
                moveListener.onLongPress(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log("onFling: " + e1.toString()+e2.toString());
            if(eventType==EventType.Other)
                return false;
            return moveListener.onFling(e1,e2,velocityX,velocityY);
        }
    };

    public static  class  Builder {
        private final Context context;
        OnRotationGestureListener rotationListener;
        OnScaleGestureListener scaleListener;
        GestureDetector.OnGestureListener moveListener;

        public Builder(Context context) {
            this.context=context;
        }


        public Builder rotationListener(OnRotationGestureListener rotationListener) {
            this.rotationListener = rotationListener;
            return this;
        }


        public Builder scaleListener(OnScaleGestureListener scaleListener) {
            this.scaleListener = scaleListener;
            return this;
        }


        public Builder moveListener(GestureDetector.OnGestureListener moveListener) {
            this.moveListener = moveListener;
            return this;
        }

        public ZGestrueDetector build() {
            return new ZGestrueDetector(context,rotationListener,scaleListener,moveListener);
        };
    }

    private ScaleGestureDetector.OnScaleGestureListener zScaleListener = new ScaleGestureDetector.OnScaleGestureListener() {
        private PointF centerPoint;
        private float scaleRatio=-1;
        private float beginLength, currentLength =-1;
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            Log("onScale:"+detector.toString());
            currentLength =detector.getCurrentSpan();
//        不对 detector.getScaleFactor()  返回从前一个伸缩事件至当前伸缩事件的伸缩比率。该值定义为  (getCurrentSpan()  / getPreviousSpan())。
            scaleRatio= currentLength /beginLength;
            return scaleListener.onScale(detector,scaleRatio);
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            Log("onScaleBegin:"+detector.toString());
            centerPoint=new PointF(detector.getFocusX(),detector.getFocusY());
            beginLength=detector.getCurrentSpan();
            return  scaleListener.onScaleBegin(detector,centerPoint);
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            Log("onScaleEnd:"+detector.toString());
            centerPoint=null;
            scaleRatio=-1;
            scaleListener.onScaleEnd(detector);
        }
    };

    public interface OnScaleGestureListener {
        boolean onScale(ScaleGestureDetector detector, float scaleRatio);

        boolean onScaleBegin(ScaleGestureDetector detector, PointF centerPoint);

        void onScaleEnd(ScaleGestureDetector detector);
    }

    private RotationGestureDetector.OnRotationGestureListener zRotationListener = new RotationGestureDetector.OnRotationGestureListener() {

        @Override
        public boolean onRotation(RotationGestureDetector rotationDetector) {
            return rotationListener.onRotation(rotationDetector, rotationDetector.getmStartAngle(), rotationDetector.getMoveAngle());
        }
    };
   public interface OnRotationGestureListener {
       boolean onRotation(RotationGestureDetector rotationDetector, float StartAngle, float sweepAngle);
   }
    private void Log(String str){
        if (printLog) {
            System.out.println("ZGestrueDetector----->"+str);
        }
    }
}
