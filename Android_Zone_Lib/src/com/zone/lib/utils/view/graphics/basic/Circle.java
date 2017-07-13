package com.zone.lib.utils.view.graphics.basic;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class Circle {
    /**
     * 圆形偏移比例
     */
    private static final float c = (float) (4 * Math.tan(Math.PI / 8) / 3);

    public ZPointF center;
    public float r;
    public ZPointF left, right, top, bottom;
    public ZPointF topLeftContrl, topRightContrl, bottomLeftContrl, bottomRightContrl,
            leftTopContrl, leftBottomContrl, rightTopContrl, rightBottomContrl;


    public Circle() {//为了克隆而用
    }

    public Circle(Circle circle) {
        this(circle.center, circle.r);
    }

    public Circle(float centerX, float centerY, float r) {
        center = new ZPointF(centerX, centerY);
        init(r);
    }

    public Circle(ZPointF center, float r) {
        this.center = center;
        init(r);
    }

    private void init(float r) {
        this.r = r;
        left = offset(-r, 0F);
        right = offset(r, 0F);
        top = offset(0F, r);
        bottom = offset(0F, -r);
    }


    private ZPointF offset(float x, float y) {
        return new ZPointF(center.x + x, center.y + y);
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawCircle(center.x, center.y, r, paint);
    }

    /**
     * 得到与此圆相切的矩形;
     * @return
     */
    public RectF getIntersectRect(){
        return new RectF(center.x-r, center.y-r,center.x+r, center.y+r);
    }
    public ZPath getPath() {
        initControlPoint();
        //把控制点  和 都绘制起来;
        //编辑Path
        ZPath circlePath = new ZPath();
        circlePath.moveTo(top);
        circlePath.cubicTo(topLeftContrl, leftTopContrl, left);
        circlePath.cubicTo(leftBottomContrl, bottomLeftContrl, bottom);
        circlePath.cubicTo(bottomRightContrl, rightBottomContrl, right);
        circlePath.cubicTo(rightTopContrl, topRightContrl, top);
        return circlePath;
    }

    private void initControlPoint() {
        float controlLength = r * c;

        topLeftContrl = new ZPointF(-controlLength, 0, top);
        topRightContrl = new ZPointF(controlLength, 0, top);

        bottomLeftContrl = new ZPointF(-controlLength, 0, bottom);
        bottomRightContrl = new ZPointF(controlLength, 0, bottom);

        leftTopContrl = new ZPointF(0, controlLength, left);
        leftBottomContrl = new ZPointF(0, -controlLength, left);

        rightTopContrl = new ZPointF(0, controlLength, right);
        rightBottomContrl = new ZPointF(0, -controlLength, right);
    }
}