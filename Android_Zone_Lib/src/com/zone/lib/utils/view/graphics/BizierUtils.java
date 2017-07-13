package com.zone.lib.utils.view.graphics;

import android.graphics.Matrix;
import android.graphics.Path;

import com.zone.lib.utils.view.graphics.basic.Circle;
import com.zone.lib.utils.view.graphics.basic.ZPointF;

/**
 * Created by fuzhipeng on 16/8/5.
 */
public class BizierUtils {
    //c1固定 是固定的圆   angel(0,180)之间的值
    public static Path getTwoCircle_CutLineMergePath(Circle c1, Circle c2, double expandAngel) {
        float startAngel = GeometryUtils.getP2AngleByX(c2.center, c1.center);
        Matrix m = new Matrix();
        float[] src = new float[]{c2.center.x, c2.center.y};
        float[] dst = new float[2];//代替了c2.center的点
        m.postRotate(-startAngel, c1.center.x, c1.center.y); //现在都在右边了
        m.mapPoints(dst, src);
        //现在为止   就是 c1,c2 x点都是相同了 并且 c2在c1右边
        ZPointF pA, pB, pC, pD;//AD 在c1,  BC在c2上
        ZPointF pApDControl, pBpCControl;//AD 在c1,  BC在c2上

        pA = new ZPointF((float) Math.cos(Math.toRadians(expandAngel / 2)) * c1.r, -(float) Math.sin(Math.toRadians(expandAngel / 2)) * c1.r);
        pA.offset(c1.center);

        pD = new ZPointF((float) Math.cos(Math.toRadians(expandAngel / 2)) * c1.r, (float) Math.sin(Math.toRadians(expandAngel / 2)) * c1.r);
        pD.offset(c1.center);

        pApDControl = new ZPointF((float) (c1.r / Math.cos(Math.toRadians(expandAngel / 2))), 0);
        pApDControl.offset(c1.center);

        pB = new ZPointF(-(float) Math.cos(Math.toRadians(expandAngel / 2)) * c2.r, -(float) Math.sin(Math.toRadians(expandAngel / 2)) * c2.r);
        pB.offset(dst[0], dst[1]);

        pC = new ZPointF(-(float) Math.cos(Math.toRadians(expandAngel / 2)) * c2.r, (float) Math.sin(Math.toRadians(expandAngel / 2)) * c2.r);
        pC.offset(dst[0], dst[1]);

        pBpCControl = new ZPointF(-(float) (c2.r / Math.cos(Math.toRadians(expandAngel / 2))), 0);
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

    //二阶  没上边的好
    public static Path getTwoCircle_RectangleMergePath(Circle c1, Circle c2, double expandAngel) {
        float startAngel = GeometryUtils.getP2AngleByX(c2.center, c1.center);
        Matrix m = new Matrix();
        float[] src = new float[]{c2.center.x, c2.center.y};
        float[] dst = new float[2];//代替了c2.center的点
        m.postRotate(-startAngel, c1.center.x, c1.center.y); //现在都在右边了
        m.mapPoints(dst, src);
        //现在为止   就是 c1,c2 x点都是相同了 并且 c2在c1右边
        ZPointF pA, pB, pC, pD;//AD 在c1,  BC在c2上
        ZPointF pControl;//AD 在c1,  BC在c2上

        pA = new ZPointF((float) Math.cos(Math.toRadians(expandAngel / 2)) * c1.r, -(float) Math.sin(Math.toRadians(expandAngel / 2)) * c1.r);
        pA.offset(c1.center);

        pD = new ZPointF((float) Math.cos(Math.toRadians(expandAngel / 2)) * c1.r, (float) Math.sin(Math.toRadians(expandAngel / 2)) * c1.r);
        pD.offset(c1.center);


        pB = new ZPointF(-(float) Math.cos(Math.toRadians(expandAngel / 2)) * c2.r, -(float) Math.sin(Math.toRadians(expandAngel / 2)) * c2.r);
        pB.offset(dst[0], dst[1]);

        pC = new ZPointF(-(float) Math.cos(Math.toRadians(expandAngel / 2)) * c2.r, (float) Math.sin(Math.toRadians(expandAngel / 2)) * c2.r);
        pC.offset(dst[0], dst[1]);

        pControl = GeometryUtils.getMidPoint(c1.center, new ZPointF(dst[0], dst[1]));

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
}
