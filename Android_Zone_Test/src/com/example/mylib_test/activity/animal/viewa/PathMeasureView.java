package com.example.mylib_test.activity.animal.viewa;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.mylib_test.R;
import com.nineoldandroids.animation.ValueAnimator;

import and.utils.image.compress2sample.SampleUtils;
import and.utils.view.DrawUtils;
import zone.com.zanimate.value.ValueAnimatorProxy;

/**
 * Created by fuzhipeng on 2016/11/23.
 */

public class PathMeasureView extends View {
    private final Bitmap arraw;
    int mViewWidth, mViewHeight;
    private Paint mDeafultPaint = DrawUtils.getStrokePaint(Paint.Style.STROKE, 3);
    private Float currentValue;

    public PathMeasureView(Context context) {
        this(context, null);


    }

    public PathMeasureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        arraw = SampleUtils.load(context, R.drawable.arraw).overrideW(100).bitmap();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        Region globalRegion = new Region();
    }

    public enum State {
       None, Constructor, NextContour, GetSegmentTrue, GetSegmentFalse, GetMatrix;
    }

    State state = State.None;
    ValueAnimator animator = null;
    public void setState(State state) {
        this.state = state;
        if (state == State.GetMatrix) {
            if (animator == null)
                animator = ValueAnimatorProxy.ofFloat(0, 1).setInterpolator(new LinearInterpolator()).setDuration(3000).setRepeatCount(-1)
                        .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                currentValue = (Float) animation.getAnimatedValue();
                                invalidate();
                            }
                        }).source();

            animator.start();
        } else if (animator != null)
            animator.cancel();

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(mViewWidth / 2, mViewHeight / 2);      // 平移坐标系
        //证明PathMeasure测量的path是 深拷贝；
        switch (state) {
            case None:
                testArc(canvas);
                break;
            case Constructor:
                testConstructor(canvas);
                break;
            case NextContour:
                testNextContour(canvas);
                break;
            case GetSegmentTrue:
                testGetSegment(canvas, true);
                break;
            case GetSegmentFalse:
                testGetSegment(canvas, false);
                break;
            case GetMatrix:
                testGetMatrix(canvas);
                break;
        }
        canvas.restore();
    }

    private void testArc(Canvas canvas) {
        Path path=new Path();
        int minWidth=400;
        int br = minWidth / 2;
        RectF bigCircle = new RectF(-br, -br, br, br);

        int sr = minWidth / 4;
        RectF smallCircle = new RectF(-sr, -sr, sr, sr);

        float bigSweepAngle = 84;
        float smallSweepAngle = -80;
        path.addArc(bigCircle, 50, bigSweepAngle);
        path.arcTo(smallCircle, 130, smallSweepAngle);
        path.close();
        canvas.drawPath(path, mDeafultPaint);

        logRegion();


    }

    private void logRegion() {
        // ▼在屏幕中间添加一个圆
        Path circlePath = new Path();
        circlePath.addCircle(mViewWidth/2, mViewHeight/2, 300, Path.Direction.CW);
        // ▼将剪裁边界设置为视图大小
        Region globalRegion = new Region(0, 0, mViewWidth, mViewHeight/2);

        // ▼将 Path 添加到 Region 中
        Region circleRegion=new Region();
        circleRegion.setPath(circlePath, globalRegion);
        System.err.println("circleRegion"+(circleRegion.contains(mViewWidth/2,mViewHeight/2-100)?"包含":"不包含")+"圆心h-100");
        System.err.println("circleRegion"+(circleRegion.contains(mViewWidth/2,mViewHeight/2+100)?"包含":"不包含")+"圆心h+100");
        System.err.println("circleRegion"+(circleRegion.contains(mViewWidth/2,mViewHeight/2-400)?"包含":"不包含")+"圆心h-400");
    }

    private void testGetMatrix(Canvas canvas) {

        Path path = new Path();

        path.addCircle(0, 0, 200, Path.Direction.CW);  // 添加大矩形


        PathMeasure measure = new PathMeasure(path, false);
        Matrix matrix = new Matrix();
        measure.getMatrix(measure.getLength() * currentValue, matrix, PathMeasure.TANGENT_MATRIX_FLAG | PathMeasure.POSITION_MATRIX_FLAG);
        //考虑的时候 把角度先放到0度在考虑； 图片默认在左上角移动到该点 应该在该点之下；
        matrix.preTranslate(-arraw.getWidth(), -arraw.getHeight() / 2);
        canvas.drawBitmap(arraw, matrix, null);

        canvas.drawPath(path, mDeafultPaint);                    // 绘制 Path
    }

    private void testNextContour(Canvas canvas) {

        Path path = new Path();

        path.addRect(-100, -100, 100, 100, Path.Direction.CW);  // 添加小矩形
        path.addRect(-200, -200, 200, 200, Path.Direction.CW);  // 添加大矩形

        canvas.drawPath(path, mDeafultPaint);                    // 绘制 Path

        PathMeasure measure = new PathMeasure(path, false);     // 将Path与PathMeasure关联

        float len1 = measure.getLength();                       // 获得第一条路径的长度

        measure.nextContour();                                  // 跳转到下一条路径

        float len2 = measure.getLength();                       // 获得第二条路径的长度

        Log.i("LEN", "len1=" + len1);                              // 输出两条路径的长度
        Log.i("LEN", "len2=" + len2);
    }

    private void testGetSegment(Canvas canvas, boolean startWithMoveTo) {

        Path path = new Path();                                     // 创建Path并添加了一个矩形
        path.addRect(-200, -200, 200, 200, Path.Direction.CW);

        Path dst = new Path();                                      // 创建用于存储截取后内容的 Path
        dst.lineTo(-300, -300);                                     // <--- 在 dst 中添加一条线段

        PathMeasure measure = new PathMeasure(path, false);         // 将 Path 与 PathMeasure 关联

        measure.getSegment(200, 600, dst, startWithMoveTo);                   // 截取一部分 并使用 moveTo 保持截取得到的 Path 第一个点的位置不变

        canvas.drawPath(dst, mDeafultPaint);
    }

    private void testConstructor(Canvas canvas) {

        Path path = new Path();

        path.lineTo(0, 200);
        path.lineTo(200, 200);
        path.lineTo(200, 0);

        PathMeasure measure1 = new PathMeasure(path, false);
        PathMeasure measure2 = new PathMeasure(path, true);

        Log.e("TAG", "forceClosed=false---->" + measure1.getLength());
        Log.e("TAG", "forceClosed=true----->" + measure2.getLength());

        canvas.drawPath(path, mDeafultPaint);
    }
}
