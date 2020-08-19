package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.zone.lib.utils.view.DrawUtils;


/**
 *  tvMusicNoteNum.setTranslationY(tvMusicNoteNum.getPaint().getFontMetrics().descent);
 *  includePadding不要设置  不然线会飘
 */
public class DTextView extends AppCompatTextView {
    public DTextView(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public DTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    public DTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
    }

    Paint paint = DrawUtils.getStrokePaint(Paint.Style.STROKE, 0.5f);
    Paint.FontMetrics fontMetrics = new Paint.FontMetrics();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getPaint().getFontMetrics(fontMetrics);
        float offset = -fontMetrics.top;
        fontMetrics.bottom += offset;
        fontMetrics.top += offset;
        fontMetrics.ascent += offset;
        fontMetrics.descent += offset;

        paint.setColor(Color.RED);
        canvas.drawLine(0, fontMetrics.top, getWidth(), fontMetrics.top, paint);
        paint.setColor(Color.BLUE);
        canvas.drawLine(0, fontMetrics.ascent, getWidth(), fontMetrics.ascent, paint);
        paint.setColor(Color.CYAN);
        canvas.drawLine(0, offset, getWidth(), offset, paint);
        paint.setColor(Color.YELLOW);
        canvas.drawLine(0, fontMetrics.descent, getWidth(), fontMetrics.descent, paint);
        paint.setColor(Color.GREEN);
        canvas.drawLine(0, fontMetrics.bottom, getWidth(), fontMetrics.bottom, paint);
    }
}
