package view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.mylib_test.R;
import and.utils.view.graphics.DrawUtils;

import and.utils.image.compress2sample.SampleUtils;

/**
 * Created by Administrator on 2016/4/14.
 */
public class ChengView extends View {
    private final Bitmap line;
    Paint paint = new Paint();
    private final Bitmap centerFrame;
    float start, offset;

    public ChengView(Context context) {
        this(context, null);
    }

    public ChengView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChengView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        centerFrame = SampleUtils.load(context,R.drawable.aaaaaaaaaaaab).bitmap();
//        centerFrame = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_4444);
        line = SampleUtils.load(context,R.drawable.ic_launcher).bitmap();

        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        onDrawTest(canvas);
        canvas.drawBitmap(centerFrame, (getWidth() - centerFrame.getWidth()) / 2, (getHeight() - centerFrame.getHeight()) / 2, paint);
        paint.setAlpha(100);
        canvas.drawRect(0, 0, getWidth(), (getHeight() - centerFrame.getHeight()) / 2, paint);//最上边的矩形
        canvas.drawRect(0, (getHeight() - centerFrame.getHeight()) / 2, (getWidth() - centerFrame.getWidth()) / 2, (getHeight() + centerFrame.getHeight()) / 2, paint);//左侧矩形
        canvas.drawRect((getWidth() + centerFrame.getWidth()) / 2, (getHeight() - centerFrame.getHeight()) / 2, getWidth(), (getHeight() + centerFrame.getHeight()) / 2, paint);//右侧矩形
        canvas.drawRect(0, (getHeight() + centerFrame.getHeight()) / 2, getWidth(), getHeight(), paint);//最下边的矩形

        start = (getHeight() - centerFrame.getHeight()) / 2 - line.getHeight() / 2;
        if (offset >= centerFrame.getHeight())
            offset = 0;
        else
            offset += 5;
        canvas.drawBitmap(line, (getWidth() - line.getWidth()) / 2, start + offset, paint);
        postInvalidateDelayed(10, (getWidth() - centerFrame.getWidth()) / 2, (getHeight() + centerFrame.getHeight()-line.getHeight()) / 2,
                (getWidth() + centerFrame.getWidth()) / 2, (getHeight() + centerFrame.getHeight()+line.getHeight()) / 2);


//只刷新扫描框的内容，其他地方不刷新  记住如果 二维码扫描的那个例子 又个位图 一半冒出了这个范围 这个位图也不刷新了吧
//        postInvalidateDelayed(10, 100,100,getWidth()-100,getHeight()-100);
//        postInvalidateDelayed(10);
    }

    private void onDrawTest(Canvas canvas) {
        DrawUtils.drawBtAtCenter(this,canvas,centerFrame,paint);
    }
}
