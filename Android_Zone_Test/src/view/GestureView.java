package view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.zone.lib.utils.view.DrawUtils;
import com.zone.lib.utils.view.gesture.RotationGestureDetector;

/**
 * Created by Administrator on 2016/6/19.
 */
public class GestureView extends View {
    private final Paint paint;
    private final Context context;

    private  GestureDetectorCompat ges;
    private Path move=new Path();

    private final ScaleGestureDetector scale;
    float cx, cy, radius,firstRadius;

    EventType eventType =EventType.Move;
    public enum EventType{
      Move,Scale,Ratotion;
    }

    private final RotationGestureDetector ratotion;
    public float ratotationAngle;
    public float ratotationAngleFirst;
    public GestureView(Context context) {
        this(context,null);
    }

    public GestureView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    public GestureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;

        paint=DrawUtils.getStrokePaint(Paint.Style.STROKE,4);
        //位移
        ges=new GestureDetectorCompat(context,listener );

        //旋转
        ratotion=new RotationGestureDetector(new RotationGestureDetector.OnRotationGestureListener() {

            @Override
            public boolean onRotation(RotationGestureDetector rotationDetector) {
                ratotationAngle=rotationDetector.getMoveAngle();
                ratotationAngleFirst=rotationDetector.getmStartAngle();
                System.out.println("ratotationAngle:"+ratotationAngle);
                return true;
            }
        });

        //缩放
        scale=new ScaleGestureDetector(context, new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                System.out.println("onScale:"+detector.toString());
                radius=detector.getCurrentSpan()/2;
//               不对 detector.getScaleFactor()  返回从前一个伸缩事件至当前伸缩事件的伸缩比率。该值定义为  (getCurrentSpan()  / getPreviousSpan())。
                return true;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                eventType =EventType.Scale;
                System.out.println("onScaleBegin:"+detector.toString());
                cx=detector.getFocusX();
                cy=detector.getFocusY();
                radius=detector.getCurrentSpan()/2;
                firstRadius=detector.getCurrentSpan()/2;
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
                System.out.println("onScaleEnd:"+detector.toString());
                radius=0;
                eventType =EventType.Move;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//
//            break;
//        }
        boolean scaleResult = scale.onTouchEvent(event);
        boolean ratotionResult = ratotion.onTouchEvent(event);
        boolean result = ges.onTouchEvent(event);
        invalidate();
//        return super.onTouchEvent(event);
        return scaleResult||result||ratotionResult;
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
