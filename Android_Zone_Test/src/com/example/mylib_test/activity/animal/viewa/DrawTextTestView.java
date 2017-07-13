package com.example.mylib_test.activity.animal.viewa;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.zone.lib.utils.view.DrawUtils;

/**
 * Created by fuzhipeng on 2016/12/16.
 */

public class DrawTextTestView extends View {
    Paint paintBounds = DrawUtils.getStrokePaint(Paint.Style.FILL);
    Paint paint = DrawUtils.getStrokePaint(Paint.Style.FILL);
    Paint paintLine
            = DrawUtils.getStrokePaint(Paint.Style.FILL);
    public static final String content = "lucky!";

    public DrawTextTestView(Context context) {
        super(context);
    }

    {
        paint.setColor(Color.BLACK);
        paint.setTextSize(65);

        paintBounds.setColor(Color.YELLOW);

        paintLine.setStrokeWidth(5);
        paintLine.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();

        for (DrawUtils.Text.ShowType showType : DrawUtils.Text.ShowType.values()) {
            canvas.translate(0, 200);
            int y = 200;
            canvas.save();
            int i = 0;
            for (Paint.Align align : Paint.Align.values()) {
                if (i != 0)
                    canvas.translate(paint.measureText(content) * 2+5, 0);
                DrawUtils.Text.with(canvas, content, 0, y, paint)
                        .align(align)
                        .drawBound(paintBounds)
                        .show(showType);
                canvas.drawPoint(0, 200, paintLine);
                i++;
            }
            canvas.restore();
        }
        canvas.restore();
    }
}
