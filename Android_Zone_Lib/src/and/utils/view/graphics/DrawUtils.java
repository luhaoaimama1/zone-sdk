package and.utils.view.graphics;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Administrator on 2016/4/14.
 * custom view  put  工具类
 */
public class DrawUtils {
    //  把bt摆放到 view的中心
    public static void drawBtAtCenter(View view, Canvas canvas,Bitmap bt,Paint paint) {
        canvas.drawBitmap(bt,(view.getWidth() - bt.getWidth()) / 2, (view.getHeight() - bt.getHeight()) / 2,paint);
    };

    public static Paint getBtPaint(){
        Paint paint=new Paint();
        paint.setDither(true);
        paint.setAntiAlias(true);
        return paint;
    }

    public static Paint getStrokePaint(Paint.Style style){
        Paint  mPaint2 = new Paint();
        mPaint2.setAntiAlias(true);
        mPaint2.setStyle(style);
        mPaint2.setStrokeWidth(3);
        return mPaint2;
    }
    public static Paint getStrokePaint(Paint.Style style,float strokeWidth){
        Paint  mPaint2 = new Paint();
        mPaint2.setAntiAlias(true);
        mPaint2.setStyle(style);
        mPaint2.setStrokeWidth(strokeWidth);
        return mPaint2;
    }
}
