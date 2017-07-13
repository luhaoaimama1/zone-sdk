package com.zone.lib.utils.view.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

import com.zone.lib.utils.view.DrawUtils;

/**
 * Created by fuzhipeng on 16/8/11.
 * todo 请先明白saveLayer的含义在使用此工具类 不然会出现奇怪的错误 参考 https://github.com/luhaoaimama1/zone-sdk/blob/master/README-Xfermode.md
 */
public class MatteUtils {

    public static Bitmap setMatte(Bitmap source, Bitmap matte, Matrix matteMatrix, PorterDuff.Mode xfermode) {
        //注意：这里我尝试用bitmap.Copy去获取可以更改的位图,但是有坑透明度没有
        Bitmap canvasBitmap = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(canvasBitmap);
        Paint paint = DrawUtils.getBtPaint();
        canvas.drawBitmap(source,0,0,null);
        paint.setXfermode(new PorterDuffXfermode(xfermode));
        if (matteMatrix != null)
            canvas.drawBitmap(matte, matteMatrix, paint);
        else
            canvas.drawBitmap(matte, 0, 0, paint);
        paint.setXfermode(null);
        return canvasBitmap;
    }

    public static Bitmap setMatte(Bitmap source, Bitmap matte, PorterDuff.Mode xfermode) {
        return setMatte(source, matte, null, xfermode);
    }

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

    public static void setMatte(Canvas canvas, Path path, Matrix matteMatrix, Paint paint, PorterDuff.Mode xfermode) {
        canvas.save();
        paint.setXfermode(new PorterDuffXfermode(xfermode));
        if (matteMatrix != null)
            canvas.concat(matteMatrix);
        canvas.drawPath(path, paint);
        paint.setXfermode(null);
        canvas.restore();
    }

    public static void setMatte(Canvas canvas, Path path, Paint paint, PorterDuff.Mode xfermode) {
        setMatte(canvas, path, null, paint, xfermode);
    }

    public static void setMatte(Canvas canvas, RectF rectF, Matrix matteMatrix, Paint paint, PorterDuff.Mode xfermode) {
        canvas.save();
        paint.setXfermode(new PorterDuffXfermode(xfermode));
        if (matteMatrix != null)
            canvas.concat(matteMatrix);
        canvas.drawRect(rectF, paint);
        paint.setXfermode(null);
        canvas.restore();
    }

    public static void setMatte(Canvas canvas, RectF rectF, Paint paint, PorterDuff.Mode xfermode) {
        setMatte(canvas, rectF, null, paint, xfermode);
    }


}
