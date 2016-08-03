package com.zone.view;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;

/**
 * Created by fuzhipeng on 16/8/3.
 */
public class MathUtils {
    public static PointF getMid_Point(PointF p1, PointF p2){
        return new PointF((p1.x+p2.x)/2,(p1.y+p2.y)/2);
    }
    //这个三个点形成一个三角形
    public static  float getP2Angle(PointF p1, PointF p2,PointF p3){
       return (float) (Math.toDegrees(Math.atan2(p1.y-p2.y,p1.x-p2.x))+Math.toDegrees(Math.atan2(p3.y-p2.y,p3.x-p2.x)));
    }

    //这个三个点形成一个三角形  并且默认第三个点是直角  , 得到的角度是和x轴正方向 的夹角; x轴顺时针 既正,x轴逆时针 既负;
    public static  float getP2Angle(PointF p1, PointF p2){
       return (float) Math.toDegrees( Math.atan2(p1.y-p2.y, p1.x-p2.x));
    }

    //c1固定 是固定的圆   angel(0,180)之间的值
    public static  Path getTwoCircleMergePath(Circle c1,Circle c2,double expandAngel){
        float startAngel = getP2Angle(c2.center, c1.center);
        Matrix m=new Matrix();
        float[] src=new float[]{c2.center.x,c2.center.y};
        float[] dst=new float[2];//代替了c2.center的点
        m.postRotate(-startAngel,c1.center.x,c1.center.y); //现在都在右边了
        m.mapPoints(dst,src);
        //现在为止   就是 c1,c2 x点都是相同了 并且 c2在c1右边
        PointF pA,pB,pC, pD;//AD 在c1,  BC在c2上
        PointF pApDControl,pBpCControl;//AD 在c1,  BC在c2上

        pA =new PointF();
        pA.set((float)Math.cos(Math.toRadians(expandAngel/2))*c1.r,-(float)Math.sin(Math.toRadians(expandAngel/2))*c1.r);
        pA.offset(c1.center.x,c1.center.y);

        pD =new PointF();
        pD.set((float)Math.cos(Math.toRadians(expandAngel/2))*c1.r,(float)Math.sin(Math.toRadians(expandAngel/2))*c1.r);
        pD.offset(c1.center.x,c1.center.y);

        pApDControl=new PointF();
        pApDControl.set((float) (c1.r/Math.cos(Math.toRadians(expandAngel/2))),0);
        pApDControl.offset(c1.center.x,c1.center.y);

        pB =new PointF();
        pB.set(-(float)Math.cos(Math.toRadians(expandAngel/2))*c2.r,-(float)Math.sin(Math.toRadians(expandAngel/2))*c2.r);
        pB.offset(dst[0],dst[1]);

        pC =new PointF();
        pC.set(-(float)Math.cos(Math.toRadians(expandAngel/2))*c2.r,(float)Math.sin(Math.toRadians(expandAngel/2))*c2.r);
        pC.offset(dst[0],dst[1]);

        pBpCControl=new PointF();
        pBpCControl.set(-(float) (c2.r/Math.cos(Math.toRadians(expandAngel/2))),0);
        pBpCControl.offset(dst[0],dst[1]);


        Path resultPath = new Path();
        resultPath.moveTo(pA.x, pA.y);
        resultPath.cubicTo(pApDControl.x, pApDControl.y, pBpCControl.x, pBpCControl.y, pB.x, pB.y);
        resultPath.lineTo(pC.x, pC.y);
        resultPath.cubicTo( pBpCControl.x, pBpCControl.y,pApDControl.x, pApDControl.y, pD.x, pD.y);
        resultPath.close();

        Matrix m2=new Matrix();
        m2.postRotate(startAngel,c1.center.x,c1.center.y);
        resultPath.transform(m2);
        return resultPath;
    }

    public static class Circle{
        public Circle(PointF center, float r) {
            this.center = center;
            this.r = r;
        }

        public PointF center;
        public float r;
    }
}
