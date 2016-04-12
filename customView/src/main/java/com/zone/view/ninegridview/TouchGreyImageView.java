package com.zone.view.ninegridview;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;


/**
 * Created by Pan_ on 2015/2/2.
 */
public class TouchGreyImageView extends ImageView {

    public TouchGreyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchGreyImageView(Context context) {
        super(context);
    }


    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!isClickable())
            setClickable(true);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Drawable drawable=getDrawable();
                if(drawable!=null) {
                    drawable.mutate().setColorFilter(Color.GRAY,
                            PorterDuff.Mode.MULTIPLY);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                Drawable drawableUp=getDrawable();
                if(drawableUp!=null) {
                    drawableUp.mutate().clearColorFilter();
                }
                break;
        }

        return super.onTouchEvent(event);
    }


    @Override
    public void onDetachedFromWindow() {
        setImageBitmap(null);
        super.onDetachedFromWindow();
    }
}
