package com.zone.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

//宽是定值,高是warp 则使用 宽,高同理;
//如果都是wrap,或者都是定制; 都取最小值最为边长;


public class SquareImageView2 extends ImageView {
    private boolean isBothWrap;

    public SquareImageView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SquareImageView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareImageView2(Context context) {
        this(context, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //这个super不要注释  因为这里测量图片
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int result;
        if (widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY)
            //仅仅宽是 定值
            result = widthSize;
        else if (widthMode != MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY)
            //仅仅高是 定值
            result = heightSize;
        else
            //都不是定值,或者都是定值的时候;
            result = Math.min(widthSize, heightSize);
        System.out.println("widthSize:" + widthSize + "_widthMode:" + (widthMode == MeasureSpec.EXACTLY ? "精确的" : "wrap") + "_heightSize:"
                + heightSize + "——heightMode:" + (heightMode == MeasureSpec.EXACTLY ? "精确的" : "wrap") + "_result:" + result);

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST)
            isBothWrap = true;
        else
            setMeasuredDimension(result, result);
    }

    //都是warp的特殊情况在这里弄
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (isBothWrap && w != h) {
            int result = Math.min(w, h);
            ViewGroup.LayoutParams params = getLayoutParams();
            params.height = result;
            params.width = result;
            //注意 这里  setMeasuredDimension 方法已经无用,要用    setLayoutParams
            setLayoutParams(params);
        }
    }
}
