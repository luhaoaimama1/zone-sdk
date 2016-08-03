package com.zone.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import and.utils.draw.DrawBind;
import and.utils.draw.DrawUtils;

/**
 * Created by fuzhipeng on 16/7/29.
 */
public class QQBizierView extends View {
    private DrawBind mDrawBind;
    private Paint paint;
    float sr = 50, br = 100;//r1 固定小圆50 暂时固定的大圆 100
    private float[] anotherCenter;
    private float[] center;
    private Model model;

    int deaufltDegrees = 50;
    PointF pointA = null, pointC, pointB, pointD = null;

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
    float bCenter[]=new float[]{0,0};

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
        return super.onTouchEvent(event);
    }


    //    sr=smallRadius br=bigRadius  BD点在sr上  AC点在br上
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.RED);

        //1静态图, 两个圆 一个path
        center = mDrawBind.center();
        canvas.drawCircle(center[0], center[1], sr, paint);

//        anotherCenter = new float[]{center[0] - 200, center[1] -200};
        anotherCenter = new float[]{bCenter[0], bCenter[1]};
//        anotherCenter = new float[]{center[0] - 200, center[1] - 200};
        canvas.drawCircle(anotherCenter[0], anotherCenter[1], br, paint);


//        quadTo(canvas);//矩形拟合方式;
//        cubicTo(canvas);//切线拟合  三阶贝塞尔
        custom(canvas);
    }

    private void custom(Canvas canvas) {
        MathUtils.Circle c1 = new MathUtils.Circle(new PointF(center[0], center[1]), sr);
        MathUtils.Circle c2 = new MathUtils.Circle(new PointF(anotherCenter[0], anotherCenter[1]), br);
        Path resultPath = MathUtils.getTwoCircleMergePath(c1, c2, 50);
        paint.setColor(Color.YELLOW);
        canvas.drawPath(resultPath, paint);

    }

    private void cubicTo(Canvas canvas) {
        //判断校园在大圆的那个位置?  这样  AC  BD这四个点 都有不同的变化
        boolean bigIsLeft = (anotherCenter[0] - center[0]) > 0 ? false : true;
        boolean bigIsUp = (anotherCenter[1] - center[1]) > 0 ? false : true;
        model = Model.getModel(bigIsLeft, bigIsUp);

        float k = Math.abs(MathUtils.getP2Angle(new PointF(anotherCenter[0], anotherCenter[1]), new PointF(center[0], center[1])));
        float kReal = MathUtils.getP2Angle(new PointF(anotherCenter[0], anotherCenter[1]), new PointF(center[0], center[1]));

        switch (model) {
            case LEFT_UP:
                left_up(k);
                break;
            case LEFT_DOWN:
                left_down(k);
                break;
            case RIGHT_UP:
                right_up(k);
                break;
            case RIGHT_DOWN:
                right_down(k);
                break;
        }

        float aControl_bCenter = (float) (br / Math.cos(Math.toRadians(deaufltDegrees)));
        float bCenter_sCenter = (float) Math.pow(Math.pow(anotherCenter[0] - center[0], 2) + Math.pow(anotherCenter[1] - center[1], 2), 0.5);

        float[] controlAc = new float[]{(float) (Math.cos(Math.toRadians(kReal)) * (bCenter_sCenter - aControl_bCenter)) + center[0],
                (float) (Math.sin(Math.toRadians(kReal)) * (bCenter_sCenter - aControl_bCenter)) + center[1]};

        float bControl_sCenter = (float) (sr / Math.cos(Math.toRadians(deaufltDegrees)));

        float[] controlBD = new float[]{(float) (Math.cos(Math.toRadians(kReal)) * bControl_sCenter) + center[0],
                (float) (Math.sin(Math.toRadians(kReal)) * bControl_sCenter) + center[1]};

        paint.setColor(Color.YELLOW);
        Path resultPath = new Path();
        resultPath.moveTo(pointA.x, pointA.y);
        resultPath.cubicTo(controlAc[0], controlAc[1], controlBD[0], controlBD[1], pointB.x, pointB.y);
        resultPath.lineTo(pointD.x, pointD.y);
        resultPath.cubicTo(controlBD[0], controlBD[1], controlAc[0], controlAc[1], pointC.x, pointC.y);
        resultPath.close();
        canvas.drawPath(resultPath, paint);

        print("A", pointA);
        print("B", pointB);
        print("C", pointC);
        print("D", pointD);


        paint.setColor(Color.BLUE);
        drawPoint(canvas, pointA, "A");
        drawPoint(canvas, pointB, "B");
        drawPoint(canvas, pointC, "C");
        drawPoint(canvas, pointD, "D");


        paint.setColor(Color.BLACK);
        canvas.drawPoint(controlAc[0], controlAc[1], paint);
        canvas.drawPoint(controlBD[0], controlBD[1], paint);

    }

    private void left_up(float k) {
        pointA = new PointF();
        pointA.set(-(float) Math.cos(Math.toRadians(deaufltDegrees - k)) * br,
                -(float) Math.sin(Math.toRadians(deaufltDegrees - k)) * br);

        pointA.offset(anotherCenter[0], anotherCenter[1]);

        pointC = new PointF();
        pointC.set(-(float) Math.sin(Math.toRadians(deaufltDegrees - k)) * br, (float) Math.cos(Math.toRadians(deaufltDegrees - k)) * br);
        pointC.offset(anotherCenter[0], anotherCenter[1]);


        pointB = new PointF();
        pointB.set((float) Math.sin(Math.toRadians(deaufltDegrees - k)) * sr, -(float) Math.cos(Math.toRadians(deaufltDegrees - k)) * sr);
        pointB.offset(center[0], center[1]);

        pointD = new PointF();
        pointD.set((float) Math.cos(Math.toRadians(deaufltDegrees - k)) * sr, (float) Math.sin(Math.toRadians(deaufltDegrees - k)) * sr);
        pointD.offset(center[0], center[1]);
    }

    private void left_down(float k) {
        pointA = new PointF();
        pointA.set(-(float) Math.sin(Math.toRadians(deaufltDegrees - k)) * br,
                -(float) Math.cos(Math.toRadians(deaufltDegrees - k)) * br);

        pointA.offset(anotherCenter[0], anotherCenter[1]);

        pointC = new PointF();
        pointC.set(-(float) Math.cos(Math.toRadians(deaufltDegrees - k)) * br, (float) Math.sin(Math.toRadians(deaufltDegrees - k)) * br);
        pointC.offset(anotherCenter[0], anotherCenter[1]);


        pointB = new PointF();
        pointB.set((float) Math.cos(Math.toRadians(deaufltDegrees - k)) * sr, -(float) Math.sin(Math.toRadians(deaufltDegrees - k)) * sr);
        pointB.offset(center[0], center[1]);

        pointD = new PointF();
        pointD.set((float) Math.sin(Math.toRadians(deaufltDegrees - k)) * sr, (float) Math.cos(Math.toRadians(deaufltDegrees - k)) * sr);
        pointD.offset(center[0], center[1]);

    }

    private void right_up(float k) {
        pointA = new PointF();
        pointA.set(-(float) Math.cos(Math.toRadians(deaufltDegrees - k)) * br,
                (float) Math.sin(Math.toRadians(deaufltDegrees - k)) * br);

        pointA.offset(anotherCenter[0], anotherCenter[1]);

        pointC = new PointF();
        pointC.set(-(float) Math.sin(Math.toRadians(deaufltDegrees - k)) * br, (float) Math.cos(Math.toRadians(deaufltDegrees - k)) * br);
        pointC.offset(anotherCenter[0], anotherCenter[1]);


        pointB = new PointF();
        pointB.set((float) Math.sin(Math.toRadians(deaufltDegrees - k)) * sr, -(float) Math.cos(Math.toRadians(deaufltDegrees - k)) * sr);
        pointB.offset(center[0], center[1]);

        pointD = new PointF();
        pointD.set((float) Math.cos(Math.toRadians(deaufltDegrees - k)) * sr, -(float) Math.sin(Math.toRadians(deaufltDegrees - k)) * sr);
        pointD.offset(center[0], center[1]);
    }

    private void right_down(float k) {
        pointA = new PointF();
        pointA.set((float) Math.sin(Math.toRadians(deaufltDegrees - k)) * br,
                -(float) Math.cos(Math.toRadians(deaufltDegrees - k)) * br);

        pointA.offset(anotherCenter[0], anotherCenter[1]);

        pointC = new PointF();
        pointC.set(-(float) Math.cos(Math.toRadians(deaufltDegrees - k)) * br, (float) Math.sin(Math.toRadians(deaufltDegrees - k)) * br);
        pointC.offset(anotherCenter[0], anotherCenter[1]);


        pointB = new PointF();
        pointB.set((float) Math.cos(Math.toRadians(deaufltDegrees - k)) * sr, -(float) Math.sin(Math.toRadians(deaufltDegrees - k)) * sr);
        pointB.offset(center[0], center[1]);

        pointD = new PointF();
        pointD.set(-(float) Math.sin(Math.toRadians(deaufltDegrees - k)) * sr, (float) Math.cos(Math.toRadians(deaufltDegrees - k)) * sr);
        pointD.offset(center[0], center[1]);
    }

    private void drawPoint(Canvas canvas, PointF ponit, String str) {
        paint.setTextSize(20);
        paint.setStrokeWidth(1);
        canvas.drawText(str, ponit.x, ponit.y - 20, paint);
        canvas.drawPoint(ponit.x, ponit.y, paint);
    }

    public void print(String str, PointF pointF) {
        System.out.println(str + "点信息是:" + pointF.toString());
    }

    private void quadTo(Canvas canvas) {


        //tan?=(r1+r2)/两个中心点的距离 ,在用?这个角度 tan?=r1/center到控制点的距离
        //controlLegth=center到控制点的距离   k=两个圆心的角度  正的;
        // x=controlLegth*cos(k) ,y=controlLegth*sin(k)  最后加上小圆的高度

        double k = Math.abs(
                Math.toDegrees(
                        Math.atan2(anotherCenter[1] - center[1], anotherCenter[0] - center[0])));

        double circleLength = sr * (Math.pow(Math.pow(anotherCenter[0] - center[0], 2) + Math.pow(anotherCenter[1] - center[1], 2), 0.5))
                / (sr + br);

        float[] control = new float[]{(float) (Math.cos(Math.toRadians(k)) * circleLength) + center[0],
                (float) (Math.sin(Math.toRadians(k)) * circleLength) + center[1]};

        //ac(b) ,bd(s)  四个点   x=sin(k) y=cos(k)
        PointF pointA = new PointF();
        pointA.set((float) Math.sin(Math.toRadians(k)) * br, -(float) Math.cos(Math.toRadians(k)) * br);
        pointA.offset(anotherCenter[0], anotherCenter[1]);
        PointF pointC = new PointF();
        pointC.set(-(float) Math.sin(Math.toRadians(k)) * br, (float) Math.cos(Math.toRadians(k)) * br);
        pointC.offset(anotherCenter[0], anotherCenter[1]);

        PointF pointB = new PointF();
        pointB.set((float) Math.sin(Math.toRadians(k)) * sr, -(float) Math.cos(Math.toRadians(k)) * sr);
        pointB.offset(center[0], center[1]);

        PointF pointD = new PointF();
        pointD.set(-(float) Math.sin(Math.toRadians(k)) * sr, (float) Math.cos(Math.toRadians(k)) * sr);
        pointD.offset(center[0], center[1]);


        Path resultPath = new Path();
        resultPath.moveTo(pointA.x, pointA.y);
        resultPath.quadTo(control[0], control[1], pointB.x, pointB.y);
        resultPath.lineTo(pointD.x, pointD.y);
        resultPath.quadTo(control[0], control[1], pointC.x, pointC.y);
        resultPath.close();
        canvas.drawPath(resultPath, paint);


        paint.setColor(Color.BLUE);
        drawPoint(canvas, pointA);
        drawPoint(canvas, pointB);
        drawPoint(canvas, pointC);
        drawPoint(canvas, pointD);

        canvas.drawLine(pointA.x, pointA.y, pointD.x, pointD.y, paint);
        canvas.drawLine(pointB.x, pointB.y, pointC.x, pointC.y, paint);

        paint.setColor(Color.BLACK);
        canvas.drawPoint(control[0], control[1], paint);

    }

    private void drawPoint(Canvas canvas, PointF ponit) {
        canvas.drawPoint(ponit.x, ponit.y, paint);
    }

    public enum Model {
        LEFT_UP, LEFT_DOWN, RIGHT_UP, RIGHT_DOWN;

        public static Model getModel(boolean bigIsLeft, boolean bigIsUp) {
            if (bigIsLeft && bigIsUp)
                return LEFT_UP;
            if (bigIsLeft && !bigIsUp)
                return LEFT_DOWN;
            if (!bigIsLeft && bigIsUp)
                return RIGHT_UP;
            if (!bigIsLeft && !bigIsUp)
                return RIGHT_DOWN;
            return null;
        }

        ;
    }
}
