package view;

import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
//参考：https://stackoverflow.com/questions/1734745/how-to-create-circle-with-b%C3%A9zier-curves
public class Circle2 {
    /**
     * 圆形偏移比例
     */
    private static final float c = (float) (4 * Math.tan(Math.PI / 8) / 3);

    private PointF center = new PointF();
    ;
    private float r;
    private PointF left = new PointF(), right = new PointF(), top = new PointF(), bottom = new PointF();

    private PointF topLeftContrl = new PointF();
    private PointF topRightContrl = new PointF();

    private PointF bottomLeftContrl = new PointF();
    private PointF bottomRightContrl = new PointF();

    private PointF leftTopContrl = new PointF();
    private PointF leftBottomContrl = new PointF();

    private PointF rightTopContrl = new PointF();
    private PointF rightBottomContrl = new PointF();

    private float offsetX, offsetY;

    public Circle2() {//为了克隆而用
    }

    public Circle2(Circle2 circle) {
        this(circle.center.x, circle.center.y, circle.r);
    }


    public Circle2(PointF center, float r) {
        this(center.x, center.y, r);
    }

    public Circle2(float centerX, float centerY, float r) {
        center.offset(centerX, centerY);
        resetR(r);
    }

    private void resetR(float r) {
        this.r = r;
        resetPointByR(left, -r, 0F);
        resetPointByR(right, r, 0F);
        resetPointByR(top, 0F, r);
        resetPointByR(bottom, 0F, -r);
    }


    private void resetPointByR(PointF pointF, float x, float y) {
        pointF.set(center.x + x, center.y + y);
    }

    public Circle2 reset(float centerX, float centerY, float r) {
        center.set(centerX, centerY);
        resetR(r);
        return this;
    }

    public Circle2 offset(float dx, float dy) {
        this.offsetX = dx;
        this.offsetY = dy;
        return this;
    }

    /**
     * 得到与此圆相切的矩形;
     *
     * @return
     */
    public RectF getIntersectRect() {
        RectF rectF = new RectF(center.x - r, center.y - r, center.x + r, center.y + r);
        rectF.offset(offsetX, offsetY);
        return rectF;
    }

    PathInner circlePath = new PathInner();

    public Path getPath() {
        circlePath.reset();
        initControlPoint();
        //把控制点  和 都绘制起来;
        //编辑Path
        circlePath.moveTo(top);
        circlePath.cubicTo(topLeftContrl, leftTopContrl, left);
        circlePath.cubicTo(leftBottomContrl, bottomLeftContrl, bottom);
        circlePath.cubicTo(bottomRightContrl, rightBottomContrl, right);
        circlePath.cubicTo(rightTopContrl, topRightContrl, top);

        circlePath.offset(offsetX, offsetY);
        return circlePath;
    }

    private static class PathInner extends Path {
        public void moveTo(PointF point) {
            super.moveTo(point.x, point.y);
        }

        public void cubicTo(PointF control1, PointF control2, PointF point) {
            super.cubicTo(control1.x, control1.y, control2.x, control2.y, point.x, point.y);
        }
    }


    private void initControlPoint() {
        float controlLength = r * c;

        setControlRelativePointF(topLeftContrl, -controlLength, 0, top);
        setControlRelativePointF(topRightContrl, controlLength, 0, top);

        setControlRelativePointF(bottomLeftContrl, -controlLength, 0, bottom);
        setControlRelativePointF(bottomRightContrl, controlLength, 0, bottom);

        setControlRelativePointF(leftTopContrl, 0, controlLength, left);
        setControlRelativePointF(leftBottomContrl, 0, -controlLength, left);

        setControlRelativePointF(rightTopContrl, 0, controlLength, right);
        setControlRelativePointF(rightBottomContrl, 0, -controlLength, right);
    }

    private void setControlRelativePointF(PointF pointF1, float rx, float ry, PointF pointF) {
        pointF1.set(pointF);
        pointF1.offset(rx, ry);
    }
}