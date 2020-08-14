package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;

public class GradientView extends View {
    public GradientView(Context context) {
        super(context);
    }

    public GradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }
    public static class Colors {

        @ColorInt
        public static int alphaColor(@ColorInt int color, @FloatRange(from = 0, to = 1) float alpha) {
            return alphaColor(color, (int) (alpha * 255));
        }

        @ColorInt
        public static int alphaColor(@ColorInt int color, @IntRange(from = 0, to = 255) int alpha) {
            return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
        }
    }

    int[] colors = {
            Colors.alphaColor(Color.parseColor("#EEEEEE"),70),
            Colors.alphaColor(  Color.parseColor("#ECECEC"),0),
            Colors.alphaColor(  Color.parseColor("#E2E2E2"),0),
            Colors.alphaColor( Color.parseColor("#D8D8D8"),85)
    };
    float[] pos = {0f, 0.33f, 0.66f, 1f,};

    Paint paint = new Paint();
    LinearGradient multiGradient;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        multiGradient = new LinearGradient(0, 0, 0,
                getHeight(), colors, pos, Shader.TileMode.CLAMP);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setShader(multiGradient);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
    }
}
