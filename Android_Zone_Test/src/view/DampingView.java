package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import and.utils.view.graphics.DampingUitls;
import and.utils.view.DrawUtils;
import and.utils.view.graphics.basic.DrawBind;

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

    private float down;
    private float length;
    private DownCallback mDownCallback;

    public void setDownCallback(DownCallback mDownCallback){
        this.mDownCallback=mDownCallback;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(mDownCallback!=null)
                mDownCallback.down();
                down = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                length = event.getY() - down;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate();
        return true;
    }

    private float maxValue = 500, dampingRadio = 0.3F;

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
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
        for (int i = 0; i <= length; i++) {
            float x = center[0] + i;
            float y = center[1] - DampingUitls.damping(i, maxValue, dampingRadio) * 300;
            if (i == 0)
                path.moveTo(x, y);
            else
                path.lineTo(x, y);

        }
        canvas.drawPath(path, paint);
    }
    public  interface DownCallback{
        void down();
    }


}
