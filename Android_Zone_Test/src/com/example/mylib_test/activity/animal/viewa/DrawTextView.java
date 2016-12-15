package com.example.mylib_test.activity.animal.viewa;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import and.utils.view.DrawUtils;

/**
 * Created by fuzhipeng on 2016/12/14.
 */
public class DrawTextView extends RelativeLayout {

    private static String introduce = "从[下到上]Align格式";
    private final TextView tv;

    private String content = "google?";
    private int TextSize = 100;
    private int Margin = 100;
    Paint paintStokeBlue = DrawUtils.getStrokePaint(Paint.Style.STROKE, 1);
    Paint paintStokeLine = DrawUtils.getStrokePaint(Paint.Style.STROKE, 1);
    private Paint.FontMetricsInt fm;

    public DrawTextView(Context context) {
        this(context, null);
    }

    public DrawTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);

        addView(tv = new TextView(context), new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tv.setTextSize(17);

        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
        lp.addRule(ALIGN_PARENT_BOTTOM);
        EditText ed;
        addView(ed = new EditText(context), lp);
        ed.setText(content);
        ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                content = s.toString();
                System.out.println("heihei:" + content);
                invalidate();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paintStokeLine.setColor(Color.BLACK);
        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), paintStokeLine);
        //键盘监听 开启 输入字符串
        Paint paint = DrawUtils.getStrokePaint(Paint.Style.FILL);
        paint.setTextSize(TextSize);
        paint.setColor(Color.BLACK);

        //必须有textSize不然测不出来
        fm = paint.getFontMetricsInt();
        System.out.println("heihei____FontMetricsInt:" + fm.toString());

        Rect bounds = new Rect();
        paint.getTextBounds(content, 0, content.length(), bounds);


        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Paint.Align align : Paint.Align.values()) {
            sb.append("—" + align.name());
            paint.setTextAlign(align);
            drawText_(canvas, paint, i);
            i++;
        }
        sb.append("\n top:BLACK value:" + fm.top);
        sb.append("\n ascent:MAGENTA value:" + fm.ascent);
        sb.append("\n descent:GREEN value:" + fm.descent);
        sb.append("\n bottom:RED value:" + fm.bottom);
        sb.append("\n baseLine:BLUE 上边的值都是基于baseLine的值");
        sb.append("\n bounds:" + bounds.toString());
        System.out.println("heihei__bounds：" + bounds.toString());
        sb.append("\n textSize:" + TextSize);
        sb.append("\n 推断出:textSize=descent+bounds.top");
        tv.setText(introduce + sb.toString());
        //封装工具测试
//        DrawUtils.Text.drawTextTopOfPoint(canvas,"lucky!",getWidth()/2,200,0,paint, Paint.Align.LEFT);
//        canvas.drawLine(0,200,getWidth(),200,paint);
    }

    // FIXME: 2016/12/15  这里不要用canvas位移和还原会出问题的。。。。大概viewgroup里就会出问题吧；
    private void drawText_(Canvas canvas, Paint paint, int i) {

        int baseLineY = (getHeight() - 100 - Margin) - (TextSize + Margin) * i;
        canvas.drawText(content, getWidth() / 2, baseLineY, paint);
        //baseLine
        drawLine(canvas, baseLineY, Color.BLUE);
        //fm
        drawLine(canvas, baseLineY + fm.top, Color.BLACK);
        drawLine(canvas, baseLineY + fm.ascent, Color.MAGENTA);
        drawLine(canvas, baseLineY + fm.descent, Color.GREEN);
        drawLine(canvas, baseLineY + fm.bottom, Color.RED);
//        drawLine(canvas,baseLineY+fm.leading,Color.MAGENTA);

        int width = (int) paint.measureText(content);
        Rect bounds = new Rect();
        paint.getTextBounds(content, 0, content.length(), bounds);
        switch (paint.getTextAlign()) {
            case LEFT:
                bounds.offset(getWidth() / 2, baseLineY);
                break;
            case RIGHT:
                bounds.offset(getWidth() / 2 - width, baseLineY);
                break;
            case CENTER:
                bounds.offset((getWidth() - width) / 2, baseLineY);
                break;
        }

        canvas.drawRect(bounds, paintStokeBlue);


    }

    private void drawLine(Canvas canvas, int baseLineY, int color) {
        paintStokeLine.setColor(color);
        canvas.drawLine(0, baseLineY, getWidth(), baseLineY, paintStokeLine);
    }
}
