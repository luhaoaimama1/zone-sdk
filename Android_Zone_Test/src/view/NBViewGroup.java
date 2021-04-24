package view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.example.mylib_test.LogApp;
import com.zone.lib.utils.image.BitmapUtils;
import com.zone.lib.utils.view.DrawUtils;

/**
 * Created by fuzhipeng on 16/9/29.
 */

public class NBViewGroup extends LinearLayout {

    private final Context context;

    public NBViewGroup(Context context) {
        this(context, null);
    }

    public NBViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NBViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
//        setWillNotDraw(false);
    }

    Paint paint = DrawUtils.getBtPaint();
    LinearGradient multiGradient;


    int[] colors = {
            GradientView.Colors.alphaColor(Color.BLUE, 0),
            GradientView.Colors.alphaColor(Color.BLUE, 255),
            GradientView.Colors.alphaColor(Color.BLUE, 255),
    };
    float[] pos = {0f, 0.5f, 0.1F,};

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        LogApp.INSTANCE.d("onLayout:");
        multiGradient = new LinearGradient(0, 0, 0,
                getHeight(), colors, pos, Shader.TileMode.CLAMP);
    }

    //自己绘制方法理解1
    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (multiGradient != null) {
            canvas.saveLayer(0, 0, getWidth(), getHeight(), paint, Canvas.ALL_SAVE_FLAG);
            super.dispatchDraw(canvas);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            paint.setShader(multiGradient);
            canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
            paint.setXfermode(null);
            canvas.restore();
        } else {
            super.dispatchDraw(canvas);
        }
    }

//自己绘制方法理解2
//    @Override
//    protected void dispatchDraw(Canvas canvas) {
//        //canvas 在这里面绘的图才可以在界面显示出来
//
//        Bitmap  maskBitmap= BitmapUtils.createBitmapAndGcIfNecessary(getWidth(),getHeight());
//        canvas.save();
//        canvas.translate(300,0);
//        super.dispatchDraw(new Canvas(maskBitmap));//这部只是为了 子布局的绘制 绘制在bitmap上
//
//        //然后bitmap  会知道canvas上
//        Bitmap maskBitmapBlur = BitmapUtils.fastblur(context, maskBitmap, 20);
//        canvas.drawBitmap(maskBitmapBlur,0,0,new Paint());
//        canvas.restore();
//
//        super.dispatchDraw(canvas);
//
//        //单独绘制
//        if (getChildAt(1)!=null) {
//            canvas.save();
//            canvas.translate(0,500);
//            boolean drawChild=drawChild(canvas, getChildAt(1), getDrawingTime());
//            System.out.println("成功？"+drawChild+"绘制a==>" + getDrawingTime());
//            canvas.restore();
//        }
//
//    }


    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        LogApp.INSTANCE.d("onWindowVisibilityChanged:"+visibility);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        LogApp.INSTANCE.d("onAttachedToWindow:");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LogApp.INSTANCE.d("onDetachedFromWindow:");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogApp.INSTANCE.d("onMeasure:");
    }
}
