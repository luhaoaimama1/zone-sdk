package view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

import and.utils.image.WaveHelper;

/**
 * Created by fuzhipeng on 16/7/28.
 */
public class WaveView2 extends View {

    private WaveHelper wave;

    public WaveView2(Context context) {
        this(context, null);
    }

    public WaveView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        wave = new WaveHelper(w, h, new WaveHelper.RefreshCallback() {
            @Override
            public void refresh(Bitmap bt) {
                WaveView2.this.invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Matrix matrix = new Matrix();
        //方向相反 运动
        matrix.postScale(-1, 1, getWidth() / 2, getHeight() / 2);
        canvas.drawBitmap(wave.getBitmap(), matrix, null);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        wave.cancel();
    }

    public void setLevel(float level) {
        wave.setLevelProgress(level);
    }

    public void setWaveAmplitude(int waveAmplitude) {
        wave.setAmplitude(waveAmplitude);
    }

    public void setWaveLength(int waveLength) {
        wave.setLength(waveLength);
    }

    public void setWaveSpeed(int waveSpeed) {
        System.out.println("waveSpeed:"+waveSpeed);
        wave.setSpeed(waveSpeed);
    }

    public void setOffsetXRadioOfLength(float offsetXRadioOfLength) {
        wave.setOffsetXRadioOfLength(offsetXRadioOfLength);
    }


}
