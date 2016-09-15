package and.utils.view.graphics;

import and.utils.view.graphics.basic.ZPointF;

/**
 * Created by fuzhipeng on 16/8/3.
 */
public class GeometryUtils {
    public static float getLength(ZPointF p1, ZPointF p2) {
        return (float) Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
    }

    public static ZPointF getMidPoint(ZPointF p1, ZPointF p2) {
        return new ZPointF((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
    }

    //这个三个点形成一个三角形
    public static float getP2Angle(ZPointF p1, ZPointF p2, ZPointF p3) {
        return (float) (Math.toDegrees(Math.atan2(p1.y - p2.y, p1.x - p2.x)) + Math.toDegrees(Math.atan2(p3.y - p2.y, p3.x - p2.x)));
    }

    //这个三个点形成一个三角形  并且默认第三个点是直角  , 得到的角度是和x轴正方向 的夹角;
    // x轴顺时针 既正,x轴逆时针 既负;
    public static float getP2AngleByX(ZPointF p1, ZPointF p2) {
        return (float) Math.toDegrees(Math.atan2(p1.y - p2.y, p1.x - p2.x));
    }




}
