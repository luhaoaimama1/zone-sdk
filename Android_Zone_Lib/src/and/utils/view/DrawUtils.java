package and.utils.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by Administrator on 2016/4/14.
 * custom view  put  工具类
 */
public class DrawUtils {
    //  把bt摆放到 view的中心
    public static void drawBtAtCenter(View view, Canvas canvas, Bitmap bt, Paint paint) {
        canvas.drawBitmap(bt, (view.getWidth() - bt.getWidth()) / 2, (view.getHeight() - bt.getHeight()) / 2, paint);
    }

    ;

    public static Paint getBtPaint() {
        Paint paint = new Paint();
        paint.setDither(true);
        paint.setAntiAlias(true);
        return paint;
    }

    public static Paint getStrokePaint(Paint.Style style) {
        Paint mPaint2 = new Paint();
        mPaint2.setAntiAlias(true);
        mPaint2.setStyle(style);
        mPaint2.setStrokeWidth(3);
        return mPaint2;
    }

    public static Paint getStrokePaint(Paint.Style style, float strokeWidth) {
        Paint mPaint2 = new Paint();
        mPaint2.setAntiAlias(true);
        mPaint2.setStyle(style);
        mPaint2.setStrokeWidth(strokeWidth);
        return mPaint2;
    }

    /**
     * 仅仅是提示，可以查看paint关注更多API
     * 关于drawText的文章：
     * https://github.com/luhaoaimama1/AndroidNote-Zone/blob/master/Android的drawText.md
     */
    public static class Text {

        public static float getTextHeight(Paint paint) {
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            return fontMetrics.top - fontMetrics.bottom;
        }

        /**
         * 仅仅是提示，可以查看paint关注更多API
         *
         * @param paint
         * @param str
         * @return
         */
        public static float getTextWidth(Paint paint, String str) {
            float width = paint.measureText(str);
            return width;
        }

        /**
         *
         * @param canvas
         * @param content
         * @param x
         * @param y
         * @param degrees todo 缩放和他类似  搞canvas即可；
         * @param paint
         * @param align
         */
        public static void drawTextTopOfPoint(Canvas canvas, String content, float x, float y, float degrees, @NonNull Paint paint, Paint.Align align) {
            if (align != null)
                paint.setTextAlign(align);
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            if (degrees != 0)
                canvas.rotate(degrees, x, y);
            canvas.drawText(content, x, y - fontMetrics.bottom, paint);
            if (degrees != 0)
                canvas.restore();
        }

        public static void drawTextTopOfPoint(Canvas canvas, String content, float x, float y, @NonNull Paint paint) {
            drawTextTopOfPoint(canvas, content, x, y, 0, paint, null);
        }

        public static void drawTextTopOfPoint(Canvas canvas, String content, float x, float y, float degrees, @NonNull Paint paint) {
            drawTextTopOfPoint(canvas, content, x, y, degrees, paint, null);
        }

        public static void drawTextTopOfPoint(Canvas canvas, String content, float x, float y, @NonNull Paint paint, Paint.Align align) {
            drawTextTopOfPoint(canvas, content, x, y, 0, paint, align);
        }

    }
}
