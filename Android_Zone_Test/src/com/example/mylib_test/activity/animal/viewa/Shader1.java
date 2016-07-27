package com.example.mylib_test.activity.animal.viewa;

import com.example.mylib_test.R;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.View;

public class Shader1 extends View {
    Paint paint = new Paint();
    private Context context;

    public Shader1(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setShader(new BitmapShader(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher),
                TileMode.REPEAT, TileMode.REPEAT));
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 200, paint);
    }
}
