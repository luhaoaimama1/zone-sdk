package view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mylib_test.LogApp;
import com.zone.lib.utils.data.convert.DensityUtils;
import com.zone.lib.utils.view.DrawUtils;
import com.zone.lib.utils.view.graphics.MathUtils;

import java.util.ArrayList;
import java.util.List;
//todo zone

/**
 * qq音乐
 * 切换状态的时候
 * 先计算终结状态显示的文字。然后做缩放
 * <p>
 * 得到当前滚动值，计算到top的值
 */
public class LrcView extends FrameLayout {
    List<String> list = new ArrayList<>();

    int select = 0;
    int unSelect = -1;

    public LrcView(@NonNull Context context) {
        super(context);
        init();
    }

    public LrcView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LrcView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {

        setWillNotDraw(false);
        String str = "Hi are you wakeHi are you wakeHi are you wakeHi are you wakeHi are you wakeHi are you wakeHi are you wakeHi are you wake\n" +
                "\n" +
                "Hi you wake up\n" +
                "It's gonna light ueHi are you wakeHi are you wakeHi are you wakeHi are you wakeHi are you wakeHieHi are you wakeHi are you wakeHi are you wakeHi are you wakeHi are you wakeHieHi are you wakeHi are you wakeHi are you wakeHi are you wakeHi are you wakeHieHi are you wakeHi are you wakeHi are you wakeHi are you wakeHi are you wakeHip\n" +
                "En it's gonna light up\n" +
                "\n" +
                "Are you wake\n" +
                "\n" +
                "En it's gonna light up\n" +
                "\n" +
                "Hi are you wake hi are you wake hi are you wake\n" +
                "\n" +
                "Hi are you wake hi hi are you wake\n" +
                "\n" +
                "Hi it's gonna light up\n" +
                "It's gonna light up\n" +
                "\n" +
                "It's gonna light up\n" +
                "\n" +
                "Hi are you wake\n" +
                "\n" +
                "Hi are you wake\n" +
                "\n" +
                "Hi with me\n" +
                "\n" +
                "Hi come through\n" +
                "\n" +
                "Hi with me hi come through\n" +
                "\n" +
                "Just hold my hand go through the dark\n" +
                "\n" +
                "Just hold my hand go through the dark\n" +
                "\n" +
                "Hi are you hi\n" +
                "\n" +
                "Hi are you hi\n" +
                "\n" +
                "It's gonna light up\n" +
                "\n" +
                "It's gonna light up\n" +
                "\n" +
                "It's gonna light up it's gonna light up\n" +
                "\n" +
                "Hi are you wake hi are you wake\n" +
                "\n" +
                "Hi are you wake hi\n" +
                "\n" +
                "Hi are you wake\n" +
                "\n" +
                "Hi you wake up\n" +
                "\n" +
                "Hi with me hi with me\n" +
                "\n" +
                "Hi with me hi with me";
        String[] split = str.split("\n");
        for (int i = 0; i < split.length; i++) {
            if (!TextUtils.isEmpty(split[i])) {
                list.add(split[i]);
            }
        }

        bigPaint.setTextSize(DensityUtils.dp2px(getContext(), 24));
        bigPaint.setColor(Color.BLACK);
        smallPaint.setTextSize(DensityUtils.dp2px(getContext(), 14));
        smallPaint.setColor(Color.BLACK);

        lineInnerSpace = DensityUtils.dp2px(getContext(), 4);
        lineSpace = DensityUtils.dp2px(getContext(), 10);
        lineSpacePaint.setColor(Color.BLUE);
        lineInnerSpacePaint.setColor(Color.GREEN);

        mScroller = new Scroller(getContext());

        backAnimator.setDuration(2000);
        backAnimator.addUpdateListener(animation -> {
            fraction = (float) animation.getAnimatedValue();
            LogApp.INSTANCE.d("fraction:" + fraction);
            postInvalidate();
        });
        post(runnable);
    }

    float fraction;
    float lineSpace, lineInnerSpace;
    Paint bigPaint = DrawUtils.getBtPaint();
    Paint smallPaint = DrawUtils.getBtPaint();
    float bigPaintSize, smallPaintSize;

    Paint lineInnerSpacePaint = DrawUtils.getStrokePaint(Paint.Style.STROKE);
    Paint lineSpacePaint = DrawUtils.getStrokePaint(Paint.Style.STROKE);
    ValueAnimator backAnimator = ValueAnimator.ofFloat(0f, 1f);


    public void setPaintDp(float bigDp, float smallDp, float lineInnerMargin, float lineMargin) {
        bigPaintSize = DensityUtils.dp2px(getContext(), bigDp);
        bigPaint.setTextSize(bigPaintSize);

        smallPaintSize = DensityUtils.dp2px(getContext(), smallDp);
        smallPaint.setTextSize(smallPaintSize);

        lineSpace = DensityUtils.dp2px(getContext(), lineMargin);
        lineInnerSpace = DensityUtils.dp2px(getContext(), lineInnerMargin);
        postInvalidate();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            update();
            paragraphList.clear();
            for (int i = 0; i < list.size(); i++) {
                String lineNote = list.get(i);

                Paragraph paragraph = new Paragraph();
                paragraph.str = lineNote;

                if (i == select) {
                    paragraph.isSelect = SelectState.SELECT;
                } else if (i == unSelect) {
                    paragraph.isSelect = SelectState.UNSELECT;
                } else {
                    paragraph.isSelect = SelectState.INIT;
                }

                int beginIndex = 0;
                int endIndex = lineNote.length();
                Paint measurePaint = getDrawPaint(paragraph);

                while (beginIndex != endIndex - 1) {

                    //判断剩下的是否超过一行
                    if (measurePaint.measureText(lineNote, beginIndex, endIndex) > getWidth()) {
                        do {
                            endIndex--;
                        } while (measurePaint.measureText(lineNote, beginIndex, endIndex) > getWidth());

                        paragraph.list.add(new Line(beginIndex, endIndex));
                        //找到小于的了
                        beginIndex = endIndex;
                        endIndex = lineNote.length();//
                    } else {
                        paragraph.list.add(new Line(beginIndex, endIndex));
                        break;
                    }
                }
                paragraphList.add(paragraph);
            }

            postInvalidate();
            postDelayed(runnable, 4000);
        }
    };

    private Paint getDrawPaint(Paragraph paragraph) {
        return paragraph.isSelect == SelectState.SELECT ? bigPaint : smallPaint;
    }

    List<Paragraph> paragraphList = new ArrayList<>();

    class Paragraph {
        String str;
        SelectState isSelect = SelectState.INIT;
        List<Line> list = new ArrayList<>();
    }

    enum SelectState {
        INIT, SELECT, UNSELECT;
    }

    class Line {
        public Line() {
        }

        public Line(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        int begin;
        int end;//he ending index, exclusive.
    }


    private void update() {
        if (select == list.size() - 1) return;
        select++;
        unSelect++;
        if (backAnimator.isRunning()) {
            backAnimator.end();
        }
        backAnimator.start();
    }

    Rect rect = new Rect();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        getScrollY();
        float height = 0;//50显示防止看到
        for (int j = 0; j < paragraphList.size(); j++) {
            Paragraph paragraph = paragraphList.get(j);
            boolean isLastParagraph = j == paragraphList.size() - 1;

            Paint drawPaint = getDrawPaint(paragraph);
            float paragrahHeight = 0;

            int saveLine = canvas.save();
            canvas.translate(0, height);
            float scale = 1F;
            switch (paragraph.isSelect) {
                case SELECT:
                    scale = MathUtils.linear(fraction, 0f, 1f, smallPaintSize * 1f / bigPaintSize, 1F, MathUtils.Linear.OverMax);
                    LogApp.INSTANCE.d(" SELECT fraction: " + fraction + "\t scale:" + scale);
                    break;
                case UNSELECT:
                    scale = MathUtils.linear(fraction, 0f, 1f, bigPaintSize / smallPaintSize, 1F, MathUtils.Linear.OverMax);
                    LogApp.INSTANCE.d(" UNSELECT fraction: " + fraction + "\t scale:" + scale);
                    break;
            }
            canvas.scale(scale, scale);
            for (int i = 0; i < paragraph.list.size(); i++) {

                Line line = paragraph.list.get(i);

                float top = drawPaint.getFontMetrics().top;
                canvas.drawLine(0, 0, getWidth(), 0, lineInnerSpacePaint);
                canvas.translate(0, top * -1);//top 是负的值
                //绘制开始的点事底部
                canvas.drawText(paragraph.str, line.begin, line.end, 0, 0, drawPaint);
                float lineHeightReal = drawPaint.getFontMetrics().bottom - top;

                boolean isLastLine = i == paragraph.list.size() - 1;
                float lineHeightWithMargin = lineHeightReal + (isLastLine ? 0F : lineInnerSpace);
                canvas.translate(0, drawPaint.getFontMetrics().bottom);//top 是负的值
                canvas.drawLine(0, 0, getWidth(), 0, lineSpacePaint);

                canvas.translate(0, lineInnerSpace);

                paragrahHeight += lineHeightWithMargin * scale;
            }

            float paragrahHeightFinal = paragrahHeight + (isLastParagraph ? 0F : lineSpace) * scale;
            canvas.translate(0, paragrahHeightFinal);
            canvas.restoreToCount(saveLine);
            height += paragrahHeightFinal;
        }


    }

    private Scroller mScroller;

    //调用此方法滚动到目标位置
    public void smoothScrollTo(int fx, int fy) {
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        smoothScrollBy(dx, dy);
    }

    //调用此方法设置滚动的相对偏移
    public void smoothScrollBy(int dx, int dy) {

        //设置mScroller的滚动偏移量
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    @Override
    public void computeScroll() {
        //先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {
            //这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
        super.computeScroll();
    }
}
