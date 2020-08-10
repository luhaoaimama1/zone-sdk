package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class CornerImageView extends androidx.appcompat.widget.AppCompatImageView {
    public CornerImageView(Context context) {
        super(context);
    }

    public CornerImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CornerImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    Circle2 circle = new Circle2();

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int min = Math.min(getWidth(), getHeight());
        circle.reset(getWidth() * 1f / 2, getHeight() * 1f / 2, min * 1f / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.clipPath(circle.getPath());
        super.onDraw(canvas);
        canvas.restore();
    }
}
