package and.utils.view.graphics;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.View;

/**
 * Created by fuzhipeng on 16/8/11.
 */
public class MatteUtils {

    public static Bitmap setMatte(Bitmap source, Bitmap matte, Matrix matteMatrix, PorterDuff.Mode xfermode) {
        Canvas canvas = new Canvas(source);
        Paint paint = DrawUtils.getBtPaint();
        paint.setXfermode(new PorterDuffXfermode(xfermode));
        if (matteMatrix != null)
            canvas.drawBitmap(matte, matteMatrix, paint);
        else
            canvas.drawBitmap(matte, 0, 0, paint);
        paint.setXfermode(null);
        return source;
    }

    //跟Canvas 当前的layer(new Canvas 默认当做layer) 的透明度与不透明度  进行叠加模式;
    public static Bitmap setMatte(Bitmap source, Bitmap matte, PorterDuff.Mode xfermode) {
        return setMatte(source, matte, null, xfermode);
    }

    //跟Canvas 当前的layer(new Canvas 默认当做layer) 的透明度与不透明度  进行叠加模式;
    public static void setMatte(Canvas canvas, Bitmap matte, Matrix matteMatrix, Paint paint, PorterDuff.Mode xfermode) {
        canvas.save();
        paint.setXfermode(new PorterDuffXfermode(xfermode));
        if (matteMatrix != null)
            canvas.drawBitmap(matte, matteMatrix, paint);
        else
            canvas.drawBitmap(matte, 0, 0, paint);
        paint.setXfermode(null);
        canvas.restore();
    }

    public static void setMatte(Canvas canvas, Bitmap matte, Paint paint, PorterDuff.Mode xfermode) {
        setMatte(canvas, matte, null, paint, xfermode);
    }

    //跟Canvas 当前的layer(new Canvas 默认当做layer) 的透明度与不透明度  进行叠加模式;
    public static void setMatte(Canvas canvas, Path path, Matrix matteMatrix, Paint paint, PorterDuff.Mode xfermode) {
        canvas.save();
        paint.setXfermode(new PorterDuffXfermode(xfermode));
        if (matteMatrix != null)
            canvas.concat(matteMatrix);
        canvas.drawPath(path, paint);
        paint.setXfermode(null);
        canvas.restore();
    }

    //跟Canvas 当前的layer(new Canvas 默认当做layer) 的透明度与不透明度  进行叠加模式;
    public static void setMatte(Canvas canvas, Path path, Paint paint, PorterDuff.Mode xfermode) {
        setMatte(canvas, path, null, paint, xfermode);
    }

    /**
     * 结束别忘记   canvas.restore();//画布提交  对应上面的 保存图层,不然会显示不出来 saveLayer的那层画面;
     *
     * @param canvas
     * @param view
     * @param paint
     */
    public static void saveLayerAllFlag(Canvas canvas, View view, Paint paint) {
        canvas.saveLayer(0, 0, view.getWidth(), view.getHeight(), paint, Canvas.ALL_SAVE_FLAG);
    }


}
