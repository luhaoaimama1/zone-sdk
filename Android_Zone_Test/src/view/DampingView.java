package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.zone.lib.utils.view.graphics.DampingUtils;
import com.zone.lib.utils.view.DrawUtils;
import com.zone.lib.utils.view.graphics.basic.DrawBind;

/**
 * Created by fuzhipeng on 16/7/29.
 */
public class DampingView extends View {
    private DrawBind mDrawBind;
    private Paint paint;
    private float[] center;


    public DampingView(Context context) {
        this(context, null);
    }

    public DampingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DampingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mDrawBind = DrawBind.bingView(this);
        paint = DrawUtils.getStrokePaint(Paint.Style.STROKE, 5);
        paint.setColor(Color.RED);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        center = mDrawBind.center();
    }

    private float maxX = 500, dampingRadio = 1F, maxY = 500;

    public void setMaxX(float maxX) {
        this.maxX = maxX;
        invalidate();
    }

    public void setMaxY(float maxY) {
        this.maxY = maxY;
        invalidate();
    }

    public void setDampingRadio(float dampingRadio) {
        this.dampingRadio = dampingRadio;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //x100 y300
        Path path = new Path();
        for (int i = 0; i <= 2000; i++) {
            float x = i;
            float y = getHeight()- DampingUtils.INSTANCE.damping(i, maxX, maxY, dampingRadio);
            if (i == 0)
                path.moveTo(x, y);
            else
                path.lineTo(x, y);

        }
        canvas.drawPath(path, paint);
    }

}
