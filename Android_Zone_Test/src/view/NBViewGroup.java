package view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import and.utils.image.BitmapUtils;

/**
 * Created by fuzhipeng on 16/9/29.
 */

public class NBViewGroup extends LinearLayout {

    private final Context context;

    public NBViewGroup(Context context) {
        this(context,null);
    }

    public NBViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NBViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
//        setWillNotDraw(false);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        //canvas 在这里面绘的图才可以在界面显示出来

        Bitmap  maskBitmap= BitmapUtils.createBitmapAndGcIfNecessary(getWidth(),getHeight());
        canvas.save();
        canvas.translate(300,0);
        super.dispatchDraw(new Canvas(maskBitmap));//这部只是为了 子布局的绘制 绘制在bitmap上

        //然后bitmap  会知道canvas上
        Bitmap maskBitmapBlur = BitmapUtils.fastblur(context, maskBitmap, 20);
        canvas.drawBitmap(maskBitmapBlur,0,0,new Paint());
        canvas.restore();

        super.dispatchDraw(canvas);
    }

}
