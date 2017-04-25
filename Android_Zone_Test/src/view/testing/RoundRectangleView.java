package view.testing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class RoundRectangleView extends View {
    private final float rx=300;
    private Paint paint;

    public RoundRectangleView(Context context) {
        this(context,null);
    }
    public RoundRectangleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
        paint.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()), rx, rx, paint);
    }
}