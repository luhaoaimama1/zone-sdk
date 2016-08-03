package com.zone.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import and.utils.draw.DrawBind;
import and.utils.draw.DrawUtils;

/**
 * Created by fuzhipeng on 16/7/28.
 */
public class WaveView2 extends View {
    private static final float DEFAULT_AMPLITUDE_RATIO = 0.1f;
    private static final float DEFAULT_WATER_LEVEL_RATIO = 0.5f;
    private static final float DEFAULT_WAVE_LENGTH_RATIO = 1.0f;
    private static final float DEFAULT_WAVE_SHIFT_RATIO = 0.0f;

    private  float waterLevelRadio=0.2F;//waterLevel=waterLevelRadio*View.height;
    private  float amplitudeRatio=0.2F;//amplitude=amplitudeRatio*View.height;
    private float waveLengthRadio=1F;//waveLength=waveLengthRadio*View.width;
    private float waveOffsetRadioY=0.02F;//水平的偏移量  代表波浪的动态移动
    private float waveOffsetRadioX=0.08F;//竖直的偏移量  代表起伏
    //todo  想让其动  后再说 通过接口
    private int progress=0;
    private static final int MAX_PROGRESS=100;
    private Paint shaderPaint;
    private BitmapShader shader;
    private Matrix matrix;
    private DrawBind bind;

    /**
     * +------------------------+
     * |<--wave length->        |______
     * |   /\          |   /\   |  |
     * |  /  \         |  /  \  | amplitude
     * | /    \        | /    \ |  |
     * |/      \       |/      \|__|____
     * |        \      /        |  |
     * |         \    /         |  |
     * |          \  /          |  |
     * |           \/           | water level
     * |                        |  |
     * |                        |  |
     * +------------------------+__|____
     */
    public WaveView2(Context context) {
        this(context,null);
    }

    public WaveView2(Context context, AttributeSet attrs) {
        this(context, null,0);
    }

    public WaveView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs ,defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bind = new DrawBind();
        bind.bingView(WaveView2.this);
        initShaderPaint();
        initWaveShader();
    }

    private void initWaveShader() {
        Bitmap waveBm = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas waveCanvas=new Canvas(waveBm);
        Path wavePath = new Path();
        wavePath.moveTo(0,getHeight()+1);
        float waveLength = DEFAULT_WAVE_LENGTH_RATIO * getWidth();
        float amplitude = DEFAULT_AMPLITUDE_RATIO*getHeight();
        for (int x = 0; x <= getWidth(); x++) {
            double y=Math.sin(x*2*Math.PI/waveLength)*amplitude+getHeight()/2-1;//-1是让那个一像素  复制出来
            wavePath.lineTo(x, (float) y);
        }
        wavePath.lineTo(getWidth(),getHeight()+1);
        wavePath.close();
        waveCanvas.drawPath(wavePath,shaderPaint);
        shaderPaint.setShader(shader=new BitmapShader(waveBm,
                Shader.TileMode.REPEAT, Shader.TileMode.CLAMP));
    }

    private void initShaderPaint() {
        shaderPaint= DrawUtils.getStrokePaint(Paint.Style.FILL);
        shaderPaint.setColor(Color.BLUE);
    }


    int i=0;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (matrix==null) {
            matrix = new Matrix();
        }
        matrix.reset();
        matrix.setScale(0.5F,0.5F,0,getHeight()/2);
        matrix.postTranslate(waveOffsetRadioX*getWidth()*i,
                 ((float) ((DEFAULT_AMPLITUDE_RATIO+0.5) * getHeight())-((float)(i%100)/100)*(DEFAULT_AMPLITUDE_RATIO+1) * getHeight()));
        shader.setLocalMatrix(matrix);
        canvas.drawRect(bind.getRect(),shaderPaint);
        postInvalidateDelayed(300);
        i++;
    }
}
