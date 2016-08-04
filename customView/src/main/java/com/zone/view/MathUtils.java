package com.zone.view;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by fuzhipeng on 16/8/3.
 */
public class MathUtils {
    public static float getLength(ZPointF p1, ZPointF p2) {
        return (float) Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
    }

    public static ZPointF getMid_Point(ZPointF p1, ZPointF p2) {
        return new ZPointF((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
    }

    //这个三个点形成一个三角形
    public static float getP2Angle(ZPointF p1, ZPointF p2, ZPointF p3) {
        return (float) (Math.toDegrees(Math.atan2(p1.y - p2.y, p1.x - p2.x)) + Math.toDegrees(Math.atan2(p3.y - p2.y, p3.x - p2.x)));
    }

    //这个三个点形成一个三角形  并且默认第三个点是直角  , 得到的角度是和x轴正方向 的夹角; x轴顺时针 既正,x轴逆时针 既负;
    public static float getP2AngleByX(ZPointF p1, ZPointF p2) {
        return (float) Math.toDegrees(Math.atan2(p1.y - p2.y, p1.x - p2.x));
    }

    //c1固定 是固定的圆   angel(0,180)之间的值
    public static Path getTwoCircle_CutLineMergePath(Circle c1, Circle c2, double expandAngel) {
        float startAngel = getP2AngleByX(c2.center, c1.center);
        Matrix m = new Matrix();
        float[] src = new float[]{c2.center.x, c2.center.y};
        float[] dst = new float[2];//代替了c2.center的点
        m.postRotate(-startAngel, c1.center.x, c1.center.y); //现在都在右边了
        m.mapPoints(dst, src);
        //现在为止   就是 c1,c2 x点都是相同了 并且 c2在c1右边
        ZPointF pA, pB, pC, pD;//AD 在c1,  BC在c2上
        ZPointF pApDControl, pBpCControl;//AD 在c1,  BC在c2上

        pA = new ZPointF();
        pA.set((float) Math.cos(Math.toRadians(expandAngel / 2)) * c1.r, -(float) Math.sin(Math.toRadians(expandAngel / 2)) * c1.r);
        pA.offset(c1.center.x, c1.center.y);

        pD = new ZPointF();
        pD.set((float) Math.cos(Math.toRadians(expandAngel / 2)) * c1.r, (float) Math.sin(Math.toRadians(expandAngel / 2)) * c1.r);
        pD.offset(c1.center.x, c1.center.y);

        pApDControl = new ZPointF();
        pApDControl.set((float) (c1.r / Math.cos(Math.toRadians(expandAngel / 2))), 0);
        pApDControl.offset(c1.center.x, c1.center.y);

        pB = new ZPointF();
        pB.set(-(float) Math.cos(Math.toRadians(expandAngel / 2)) * c2.r, -(float) Math.sin(Math.toRadians(expandAngel / 2)) * c2.r);
        pB.offset(dst[0], dst[1]);

        pC = new ZPointF();
        pC.set(-(float) Math.cos(Math.toRadians(expandAngel / 2)) * c2.r, (float) Math.sin(Math.toRadians(expandAngel / 2)) * c2.r);
        pC.offset(dst[0], dst[1]);

        pBpCControl = new ZPointF();
        pBpCControl.set(-(float) (c2.r / Math.cos(Math.toRadians(expandAngel / 2))), 0);
        pBpCControl.offset(dst[0], dst[1]);


        Path resultPath = new Path();
        resultPath.moveTo(pA.x, pA.y);
        resultPath.cubicTo(pApDControl.x, pApDControl.y, pBpCControl.x, pBpCControl.y, pB.x, pB.y);
        resultPath.lineTo(pC.x, pC.y);
        resultPath.cubicTo(pBpCControl.x, pBpCControl.y, pApDControl.x, pApDControl.y, pD.x, pD.y);
        resultPath.close();

        Matrix m2 = new Matrix();
        m2.postRotate(startAngel, c1.center.x, c1.center.y);
        resultPath.transform(m2);
        return resultPath;
    }

    public static Path getTwoCircle_RectangleMergePath(Circle c1, Circle c2, double expandAngel) {
        float startAngel = getP2AngleByX(c2.center, c1.center);
        Matrix m = new Matrix();
        float[] src = new float[]{c2.center.x, c2.center.y};
        float[] dst = new float[2];//代替了c2.center的点
        m.postRotate(-startAngel, c1.center.x, c1.center.y); //现在都在右边了
        m.mapPoints(dst, src);
        //现在为止   就是 c1,c2 x点都是相同了 并且 c2在c1右边
        ZPointF pA, pB, pC, pD;//AD 在c1,  BC在c2上
        ZPointF pControl;//AD 在c1,  BC在c2上

        pA = new ZPointF();
        pA.set((float) Math.cos(Math.toRadians(expandAngel / 2)) * c1.r, -(float) Math.sin(Math.toRadians(expandAngel / 2)) * c1.r);
        pA.offset(c1.center.x, c1.center.y);

        pD = new ZPointF();
        pD.set((float) Math.cos(Math.toRadians(expandAngel / 2)) * c1.r, (float) Math.sin(Math.toRadians(expandAngel / 2)) * c1.r);
        pD.offset(c1.center.x, c1.center.y);


        pB = new ZPointF();
        pB.set(-(float) Math.cos(Math.toRadians(expandAngel / 2)) * c2.r, -(float) Math.sin(Math.toRadians(expandAngel / 2)) * c2.r);
        pB.offset(dst[0], dst[1]);

        pC = new ZPointF();
        pC.set(-(float) Math.cos(Math.toRadians(expandAngel / 2)) * c2.r, (float) Math.sin(Math.toRadians(expandAngel / 2)) * c2.r);
        pC.offset(dst[0], dst[1]);

        pControl = getMid_Point(c1.center, new ZPointF(dst[0], dst[1]));

        Path resultPath = new Path();
        resultPath.moveTo(pA.x, pA.y);
        resultPath.quadTo(pControl.x, pControl.y, pB.x, pB.y);
        resultPath.lineTo(pC.x, pC.y);
        resultPath.quadTo(pControl.x, pControl.y, pD.x, pD.y);
        resultPath.close();

        Matrix m2 = new Matrix();
        m2.postRotate(startAngel, c1.center.x, c1.center.y);
        resultPath.transform(m2);
        return resultPath;
    }

    public static class Circle {
        /**
         * 圆形偏移比例
         */
        private float c = (float) (4 * Math.tan(Math.PI / 8) / 3);//
        public ZPointF center;
        public float r;
        public ZPointF left, right, top, bottom;
        public ZPointF topLeftContrl, topRightContrl, bottomLeftContrl, bottomRightContrl,
                leftTopContrl, leftBottomContrl, rightTopContrl, rightBottomContrl;


        public Circle(ZPointF center, float r) {
            this.center = center;
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
            topLeftContrl = new ZPointF();
            topLeftContrl.set(-controlLength, 0);
            topLeftContrl.offset(top.x, top.y);//跟随top移动

            topRightContrl = new ZPointF();
            topRightContrl.set(controlLength, 0);
            topRightContrl.offset(top.x, top.y);

            bottomLeftContrl = new ZPointF();
            bottomLeftContrl.set(-controlLength, 0);
            bottomLeftContrl.offset(bottom.x, bottom.y);

            bottomRightContrl = new ZPointF();
            bottomRightContrl.set(controlLength, 0);
            bottomRightContrl.offset(bottom.x, bottom.y);


            leftTopContrl = new ZPointF();
            leftTopContrl.set(0, controlLength);
            leftTopContrl.offset(left.x, left.y);


            leftBottomContrl = new ZPointF();
            leftBottomContrl.set(0, -controlLength);
            leftBottomContrl.offset(left.x, left.y);


            rightTopContrl = new ZPointF();
            rightTopContrl.set(0, controlLength);
            rightTopContrl.offset(right.x, right.y);

            rightBottomContrl = new ZPointF();
            rightBottomContrl.set(0, -controlLength);
            rightBottomContrl.offset(right.x, right.y);

            //绑定父子关系   父亲移动了孩子就跟着移动
            top.addChildren(topLeftContrl, topRightContrl);
            left.addChildren(leftBottomContrl, leftTopContrl);
            bottom.addChildren(bottomLeftContrl, bottomRightContrl);
            right.addChildren(rightBottomContrl, rightTopContrl);
        }
    }
}
