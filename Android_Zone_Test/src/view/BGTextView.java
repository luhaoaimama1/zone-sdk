package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import com.zone.lib.utils.data.convert.DensityUtils;
import com.zone.lib.utils.view.DrawUtils;

public class BGTextView extends AppCompatEditText {
    public BGTextView(Context context) {
        super(context);
        init();
    }

    public BGTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BGTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    int lineHeight = 1;

    private void init() {
        lineHeight = DensityUtils.dp2px(getContext(), 40);
        final int fontHeight = getPaint().getFontMetricsInt(null);
        // Make sure we don't setLineSpacing if it's not needed to avoid unnecessary redraw.
        // Set lineSpacingExtra by the difference of lineSpacing with lineHeight
        int lineExtra = lineHeight - fontHeight;
        setLineSpacing(lineExtra, 1f);
        setPadding(0, lineExtra / 2, 0, 0);
    }

    Paint paint2 = DrawUtils.getStrokePaint(Paint.Style.STROKE, 3);
    int[] colorArry = new int[]{Color.CYAN, Color.GREEN, Color.YELLOW};

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //滚动问题
        int drawlineCount = (int) ((getHeight() + getScrollY()) / lineHeight);
        int beginCount = (int) getScrollY() / lineHeight;
        for (int i = beginCount; i <= drawlineCount; i++) {
            paint2.setColor(colorArry[i % colorArry.length]);
            float lineY = lineHeight * i;
            canvas.drawLine(0, lineY, getWidth(), lineY, paint2);
        }
    }
}
