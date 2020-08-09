package android.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.zone.lib.utils.view.DrawUtils;

public class BGTextView extends AppCompatEditText {
    public BGTextView(Context context) {
        super(context);
    }

    public BGTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BGTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    Paint paint2 = DrawUtils.getStrokePaint(Paint.Style.STROKE, 3);
    int[] colorArry = new int[]{Color.CYAN, Color.GREEN, Color.YELLOW};
    Rect rect = new Rect();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), (int) (getMeasuredHeight() + getLineSpacingExtra()));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float lineSpacingExtra = getLineSpacingExtra();
        try {
            Layout layout = getLayout();
            int lineCount = layout.getLineCount();
            for (int i = 0; i < lineCount; i++) {
                paint2.setColor(colorArry[i % colorArry.length]);
                layout.getLineBounds(i, rect);
                if (i == lineCount - 1) {
                    rect.bottom += lineSpacingExtra;
                }
                canvas.drawRect(rect, paint2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
