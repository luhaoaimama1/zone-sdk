package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.zone.lib.utils.view.DrawUtils;

public class CornerImageView extends androidx.appcompat.widget.AppCompatImageView {
    public CornerImageView(Context context) {
        super(context);
    }

    public CornerImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CornerImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    Path mClipPath = new Path();
    Path path2 = new Path();

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int min = Math.min(getWidth(), getHeight());
        mClipPath.reset();
        mClipPath.addCircle(getWidth() * 1f / 2, getHeight() * 1f / 2, min * 1f / 2, Path.Direction.CW);
        mClipPath.moveTo(0,0);
        mClipPath.lineTo(getWidth(),0);
        mClipPath.lineTo(getWidth(),getHeight());
        mClipPath.lineTo(0,getHeight());

        path2.reset();
        path2.addRect(0, 0, getWidth(), getHeight(), Path.Direction.CW);
    }

    Paint paint = DrawUtils.getStrokePaint(Paint.Style.FILL);
    //由于边界问题 path的边界无法作用所有的 path外面的效果所以DST_in的方式先绘制canvas会看不出效果
    PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.saveLayer(0, 0, getWidth(), getHeight(), paint, Canvas.ALL_SAVE_FLAG);
        super.onDraw(canvas);

//        paint.setXfermode(xfermode);
//        canvas.saveLayer(0, 0, getWidth(), getHeight(), paint, Canvas.ALL_SAVE_FLAG);
//        paint.setXfermode(null);
//        paint.setColor(Color.BLACK);
//        canvas.drawPath(path, paint);
//        canvas.restore();

//        paint.setXfermode(xfermode);
//        path2.op(mClipPath, Path.Op.DIFFERENCE);
//        canvas.drawPath(path2, paint);
//        paint.setXfermode(null);
        canvas.restore();
    }
}
