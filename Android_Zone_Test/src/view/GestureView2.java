package view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import and.utils.view.DrawUtils;
import and.utils.view.gesture.RotationGestureDetector;
import and.utils.view.gesture.ZGestrueDetector;

/**
 * Created by Administrator on 2016/6/19.
 */
public class GestureView2 extends View {
    private final Paint paint;
    private final Context context;
    private final ZGestrueDetector zGestrueDetector;

    private GestureDetectorCompat ges;
    private Path move=new Path();

    float cx, cy, radius,firstRadius;

    EventType eventType =EventType.Move;
    public enum EventType{
        Move,Scale,Ratotion;
    }

    public float ratotationAngle;
    public float ratotationAngleFirst;
    public GestureView2(Context context) {
        this(context,null);
    }

    public GestureView2(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    public GestureView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        paint= DrawUtils.getStrokePaint(Paint.Style.STROKE,4);
        zGestrueDetector=new ZGestrueDetector.Builder(context).moveListener(listener).rotationListener(new ZGestrueDetector.OnRotationGestureListener() {
            @Override
            public boolean onRotation(RotationGestureDetector rotationDetector, float StartAngle, float sweepAngle) {
                ratotationAngle=rotationDetector.getMoveAngle();
                ratotationAngleFirst=rotationDetector.getmStartAngle();
                System.out.println("ratotationAngle:"+ratotationAngle);
                return true;
            }
        }).scaleListener(new ZGestrueDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector, float scaleRatio) {
                radius=detector.getCurrentSpan()/2;
                return true;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector, PointF centerPoint) {
                eventType =EventType.Scale;
                System.out.println("onScaleBegin:"+detector.toString());
                cx=centerPoint.x;
                cy=centerPoint.y;
                firstRadius=detector.getCurrentSpan()/2;
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
                System.out.println("onScaleEnd:"+detector.toString());
                radius=0;
                eventType =EventType.Move;
            }
        }).build();


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result=zGestrueDetector.onTouchEvent(event);
        invalidate();
        return result;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (  eventType ==EventType.Move) {
            canvas.drawPath(move,paint);
        }
        if(radius!=0){
            canvas.drawCircle(cx,cy,radius,paint);
        }
        System.out.println("ratotationAngleFirst:"+ratotationAngleFirst+"________ratotationAngle:"+ratotationAngle);
        canvas.drawArc(cx-radius,cy-radius,cx+radius,cy+radius,ratotationAngleFirst,ratotationAngle,true,paint);

    }
    GestureDetector.OnGestureListener listener=new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            move=new Path();
            move.moveTo(e.getX(),e.getY());
            System.out.println("onDown:"+e.toString());
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            System.out.println("onSingleTapUp");
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            System.out.println("distanceX:"+distanceX+"______________distanceY:"+distanceY);
            if (  eventType ==EventType.Move) {
                move.rLineTo(-distanceX,-distanceY);
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            System.out.println("onFling: " + e1.toString()+e2.toString());
            return true;
        }
    };
}