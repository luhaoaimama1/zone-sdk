package view.testing;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyClipTextView extends TextView {

    private Paint mPaint1, mPaint2;

    public MyClipTextView(Context context) {
        super(context);
        initView();
    }

    public MyClipTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyClipTextView(Context context, AttributeSet attrs,
                          int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint1 = new Paint();
        mPaint1.setColor(getResources().getColor(
                android.R.color.holo_blue_light));
        mPaint1.setStyle(Paint.Style.FILL);
        mPaint2 = new Paint();
        mPaint2.setColor(Color.YELLOW);
        mPaint2.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制外层矩形
        canvas.drawRect( 0, 0,getMeasuredWidth(), getMeasuredHeight(), mPaint1);

        canvas.save();
        canvas.clipRect(0,0,getMeasuredWidth()/2,getMeasuredHeight());
        // 绘制外层矩形
        canvas.drawRect( 0, 0,getMeasuredWidth(), getMeasuredHeight(), mPaint2);
        canvas.restore();

        canvas.save();
        // 绘制文字前平移10像素  这个叼！！！！
        canvas.translate(10, 0);
        // 父类完成的方法，即绘制文本
        super.onDraw(canvas);
        canvas.restore();
    }
}
