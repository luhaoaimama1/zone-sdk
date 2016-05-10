package view.testing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/5/10.
 */
public class DrawView extends View {
    private Paint mPaint2;
    public DrawView(Context context) {
        super(context);
        init();
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint2 = new Paint();
        mPaint2.setAntiAlias(true);
        mPaint2.setColor(Color.RED);
        mPaint2.setStyle(Paint.Style.STROKE);
        mPaint2.setStrokeWidth(3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float f=30;
        canvas.drawCircle(getWidth()/2,getHeight()/2,getWidth()/2-f,mPaint2);
        canvas.drawCircle(getWidth()/2,getHeight()/2,f,mPaint2);

        //大 |
        for (int i = 0; i <12 ; i++) {
            canvas.save();
            canvas.rotate(360/12*i,getWidth()/2,getHeight()/2);
            canvas.drawLine(getWidth()/2,f,getWidth()/2,f-20,mPaint2);
            canvas.restore();
        }
        //小 |
        for (int i = 0; i <60 ; i++) {
            canvas.save();
            canvas.rotate(360/60*i,getWidth()/2,getHeight()/2);
            canvas.drawLine(getWidth()/2,f,getWidth()/2,f-14,mPaint2);
            canvas.restore();
        }

        canvas.save();
        canvas.rotate(-140,getWidth()/2,getHeight()/2);
        canvas.drawLine(getWidth()/2,getHeight()/2,getWidth()/2,getHeight()/2+getWidth()/2-f,mPaint2);
        canvas.restore();
    }
}
