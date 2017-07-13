package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.zone.lib.utils.view.DrawUtils;
import com.zone.lib.utils.view.graphics.animation.FlexibleBallAnimation;
import com.zone.lib.utils.view.graphics.basic.Circle;
import com.zone.lib.utils.view.graphics.basic.ZPath;
import com.zone.lib.utils.view.graphics.basic.ZPointF;

/**
 * Created by fuzhipeng on 16/8/4.
 */
public class FlexibleBall extends View {


    private Paint paint;
    private Circle circle;
    private ZPath mPath, mPathProgress;
    private FlexibleBallAnimation flexibleBallAnimation;
    private FlexibleBallAnimation flexibleBallAnimationProgress;

    public FlexibleBall(Context context) {
        this(context,null);
    }

    public FlexibleBall(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FlexibleBall(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = DrawUtils.getStrokePaint(Paint.Style.FILL);

    }


    private  float ex,ey;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                ex=event.getX();
                ey=event.getY();
                stop();
                start();
                break;
        }
        return true;
    }

    private void stop() {
        if(flexibleBallAnimation!=null)
            flexibleBallAnimation.stop();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        if (mPathProgress!=null) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.CYAN);
            canvas.drawPath(mPathProgress,paint);
        }
        if (mPath!=null) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.RED);
            canvas.drawPath(mPath,paint);
        }

        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        canvas.drawCircle(getWidth()/2-100,getHeight()/2,50,paint);
        canvas.drawCircle(ex,ey,50,paint);
    }
    public void start(){
        circle=new Circle(new ZPointF(getWidth()/2-100,getHeight()/2),50);
        flexibleBallAnimation=new FlexibleBallAnimation(circle, new ZPointF(ex,ey),new FlexibleBallAnimation.Listener() {
            @Override
            public void update(ZPath path) {
                mPath=path;
                postInvalidate();
            }
        });
        flexibleBallAnimation.start();
        flexibleBallAnimationProgress=new FlexibleBallAnimation(circle, new ZPointF(ex,ey),new FlexibleBallAnimation.Listener() {


            @Override
            public void update(ZPath path) {
                mPathProgress =path;
                postInvalidate();
            }
        });
        flexibleBallAnimationProgress.setProgress(0.5F);
    }
}
