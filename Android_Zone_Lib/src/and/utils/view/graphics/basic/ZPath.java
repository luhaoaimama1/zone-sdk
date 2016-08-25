package and.utils.view.graphics.basic;

import android.graphics.Path;

/**
 * Created by fuzhipeng on 16/8/4.
 * 兼容PonitF
 */
public class ZPath extends Path {

    public void moveTo(ZPointF point) {
        super.moveTo(point.x, point.y);
    }

    public void rMoveTo(ZPointF point) {
        super.rMoveTo(point.x, point.y);
    }

    public void lineTo(ZPointF point) {
        super.lineTo(point.x, point.y);
    }

    public void rLineTo(ZPointF point) {
        super.rLineTo(point.x, point.y);
    }

    public void quadTo(ZPointF control, ZPointF point) {
        super.quadTo(control.x, control.y, point.x, point.y);
    }

    public void rQuadTo(ZPointF control, ZPointF point) {
        super.rQuadTo(control.x, control.y, point.x, point.y);
    }

    public void cubicTo(ZPointF control1, ZPointF control2, ZPointF point) {
        super.cubicTo(control1.x, control1.y, control2.x, control2.y, point.x, point.y);
    }

    public void rCubicTo(ZPointF control1, ZPointF control2, ZPointF point) {
        super.rCubicTo(control1.x, control1.y, control2.x, control2.y, point.x, point.y);
    }


}
