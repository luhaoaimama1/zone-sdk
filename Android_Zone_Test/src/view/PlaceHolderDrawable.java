//package view;
//
//import android.graphics.Canvas;
//import android.graphics.ColorFilter;
//import android.graphics.Paint;
//import android.graphics.Path;
//import android.graphics.PixelFormat;
//import android.graphics.PorterDuff;
//import android.graphics.PorterDuffXfermode;
//import android.graphics.Rect;
//import android.graphics.drawable.Drawable;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//
//import com.kuaiyin.player.R;
//import com.kuaiyin.player.v2.utils.Apps;
//
//public class PlaceHolderDrawable extends Drawable {
//
//    int width, height;
//    float r;
//    Paint paint = getStrokePaint();
//    Drawable drawable;
//    Path mClipPath;
//    Path rectPath;
//    PorterDuffXfermode xfermode;
//
//    public PlaceHolderDrawable() {
//    }
//
//    public PlaceHolderDrawable(int resId) {
//        if (resId != 0) {
//            drawable = Apps.getApplication().getDrawable(resId);
//            mClipPath = new Path();
//            rectPath = new Path();
//            xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
//        }
//    }
//
//
//    @Override
//    public void draw(@NonNull Canvas canvas) {
//        if (r > 0) {
//            if (drawable == null) {
//                canvas.drawCircle(width * 1f / 2, height * 1f / 2, r, paint);
//            } else {
//                canvas.saveLayer(0, 0, width, height, paint, Canvas.ALL_SAVE_FLAG);
//                drawable.draw(canvas);
//                paint.setXfermode(xfermode);
//                rectPath.op(mClipPath, Path.Op.DIFFERENCE);
//                canvas.drawPath(rectPath, paint);
//                paint.setXfermode(null);
//                canvas.restore();
//            }
//        }
//    }
//
//    @Override
//    public void setBounds(@NonNull Rect bounds) {
//        super.setBounds(bounds);
//        width = getBounds().width();
//        height = getBounds().height();
//        r = Math.min(width, height) * 1f / 2;
//        if (drawable != null) {
//            drawable.setBounds(bounds);
//            mClipPath.reset();
//            mClipPath.addCircle(width * 1f / 2, height * 1f / 2, r, Path.Direction.CW);
//
//            rectPath.reset();
//            rectPath.addRect(0, 0, width, height, Path.Direction.CW);
//        }
//    }
//
//    @Override
//    public void setAlpha(int alpha) {
//
//    }
//
//    @Override
//    public void setColorFilter(@Nullable ColorFilter colorFilter) {
//
//    }
//
//    @Override
//    public int getOpacity() {
//        return PixelFormat.TRANSLUCENT;
//    }
//
//    public Paint getStrokePaint() {
//        Paint paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setStyle(Paint.Style.FILL);
//        paint.setStrokeWidth(0);
//        paint.setColor(Apps.getApplication().getResources().getColor(R.color.colorffD9D9D9));
//        return paint;
//    }
//}
