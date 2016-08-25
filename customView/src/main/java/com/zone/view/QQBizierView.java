package com.zone.view;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import and.utils.view.graphics.BizierUtils;
import and.utils.view.graphics.basic.Circle;
import and.utils.view.graphics.basic.DrawBind;
import and.utils.view.graphics.DrawUtils;
import and.utils.view.graphics.basic.ZPointF;

/**
 * Created by fuzhipeng on 16/7/29.
 */
public class QQBizierView extends View {
    private DrawBind mDrawBind;
    private Paint paint;
    float sr = 50, br = 100;//r1 固定小圆50 暂时固定的大圆 100
    private float[] anotherCenter;
    private float[] center;
    float bCenter[]=new float[]{0,0};

    public QQBizierView(Context context) {
        this(context, null);
    }

    public QQBizierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQBizierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mDrawBind = new DrawBind();
        mDrawBind.bingView(this);
        paint = DrawUtils.getStrokePaint(Paint.Style.FILL);
        paint.setColor(Color.RED);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                bCenter[0]=event.getX();
                bCenter[1]=event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                bCenter[0]=event.getX();
                bCenter[1]=event.getY();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate();
        return true;
    }
    //    sr=smallRadius br=bigRadius  BD点在sr上  AC点在br上
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.RED);

        //1静态图, 两个圆 一个path
        center = mDrawBind.center();
        canvas.drawCircle(center[0], center[1], sr, paint);

        anotherCenter = new float[]{bCenter[0], bCenter[1]};
        canvas.drawCircle(anotherCenter[0], anotherCenter[1], br, paint);
        Circle c1 = new Circle(new ZPointF(center[0], center[1]), sr);
        Circle c2 = new Circle(new ZPointF(anotherCenter[0], anotherCenter[1]), br);
        paint.setColor(Color.YELLOW);
        customCutLine(canvas,c1,c2);
//        customSquare(canvas,c1,c2);
    }

    private void customCutLine(Canvas canvas, Circle c1, Circle c2) {
        Path resultPath = BizierUtils.getTwoCircle_CutLineMergePath(c1, c2, 50);
        canvas.drawPath(resultPath, paint);
    }
    private void customSquare(Canvas canvas,Circle c1, Circle c2) {
        Path resultPath = BizierUtils.getTwoCircle_RectangleMergePath(c1, c2, 50);
        canvas.drawPath(resultPath, paint);
    }


}
