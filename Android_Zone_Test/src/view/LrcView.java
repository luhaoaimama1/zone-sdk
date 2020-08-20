package view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
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
public class LrcView extends View {

    //>0
    public int maxLineNumber = 3;
    public static final String END_PREFIX = "...";

    boolean debugDrawLineGuide = false;
    boolean debugDrawScrollGuide = false;

    public static final int DURATION = 500;
    private List<String> datas = new ArrayList<>();
    private Scroller mScroller;
    private List<Paragraph> paragraphList = new ArrayList<>();

    float fraction;
    float paragraphSpace, lineInnerSpaceSmall, lineInnerSpaceBig;
    Paint bigPaint = DrawUtils.getBtPaint();
    Paint smallPaint = DrawUtils.getBtPaint();
    float bigPaintSize, smallPaintSize;

    Paint lineInnerSpacePaint = DrawUtils.getStrokePaint(Paint.Style.STROKE);
    Paint lineSpacePaint = DrawUtils.getStrokePaint(Paint.Style.STROKE);
    ValueAnimator backAnimator = ValueAnimator.ofFloat(0f, 1f);

    float scrolllOffsetTop = 500;//滚动距离头部的高度

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
        setBackgroundColor(Color.parseColor("#EB000000"));
        setWillNotDraw(false);

        String str =
//                "Hi are you wakeHi are you wakeHi are you wakeHi are you wakeHi are you wakeHou wakere you wakeHi are you wakeHi are you wakeHoure you wakeHi are you wakeHi are you wakeHoure you wakeHi are you wakeHi are you wakeHoure you wakeHi are you wakeHi are you wakeHoure you wakeHi are you wakeHi are you wakeHoure you wakeHi are you wakeHi are you wakeHou\n" +
//                        "\n" +
//                        "Hi you wake up\n" +
//                        "It's gonna light ueHi arere you wakeHi are you wakeHi are you wakeHoure you wakeHi are you wakeHi are you wakeHoure you wakeHi are you wakeHi are you wakeHoure you wakeHi are you wakeHi are you wakeHoure you wakeHi are you wakeHi are you wakeHoure you wakeHi are you wakeHi are you wakeHoure you wakeHi are you wakeHi are you wakeHoure you wakeHi are you wakeHi are you wakeHoure you wakeHi are you wakeHi are you wakeHoure you wakeHi are you wakeHi are you wakeHou you wakeHi are you wakeHi are \n" +
//                        "En it's gonna light up\n" +
//                        "\n" +
//                        "Are you wake\n" +
//                        "\n" +
//                        "En it's gonna light up\n" +
//                        "\n" +
//                        "Hi are you wake hi are you wake hi are you wake\n" +
//                        "\n" +
//                        "Hi are you wake hi hi are you wa are you wa are you wa are you wa are you wake\n" +
//                        "wa are you wa are yowa are you wa are yowa are you wa are yowa are you wa are yowa are you wa are yowa are you wa are yowa are you wa are yowa are you wa are you wake\n" +
//                        "\n" +
//                        "Hi it's gonna light up\n" +
//                        "It's gonna light up\n" +
                "\n" +
//                        "It's gonna light up\n" +
//                        "\n" +
//                        "Hi are you wake\n" +
//                        "\n" +
//                        "Hi are you wake\n" +
//                        "\n" +
//                        "Hi with me\n" +
//                        "\n" +
//                        "Hi come through\n" +
//                "\n" +
//                        "Hi with me hi come through\n" +
//                        "\n" +
//                        "Just hold my hand go through the dark\n" +
//                        "\n" +
                        "Just hold my hand go through the dark\n" +
                        "\n" +
                        "Hi are you hi\n" +
                        "\n" +
                        "Hi are you hi\n" +
                        "\n" +
                        "It's gonna light up\n" +
                        "\n" +
                        "Hi are you wake hi\n" +
                        "\n" +
                        "Hi are you wake\n" +
                        "\n" +
                        "Hi you wake up\n" +
                        "\n" +
//                        "Hi with me hi with me\n" +
//                "\n" +
                        "Hi with me hi with medafkjdlksajflajfdja;jfaljdkdaasajflajfdja;jfasajflajfdja;jfasajflajfdja;jfasajflajfdja;jfasajflajfdja;jfa";
        String[] split = str.split("\n");
        for (int i = 0; i < split.length; i++) {
            if (!TextUtils.isEmpty(split[i])) {
                datas.add(split[i]);
            }
        }


        mScroller = new Scroller(getContext(), new AccelerateDecelerateInterpolator());
        backAnimator.setDuration(DURATION);
        backAnimator.addUpdateListener(animation -> {
            fraction = (float) animation.getAnimatedValue();
            postInvalidate();
        });
        backAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        post(runnable);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (selectIndex == datas.size()) return;
            selectIndex++;
            unSelectIndex++;
            refreshLogic(selectIndex, unSelectIndex);
            postInvalidate();
            postDelayed(runnable, 4000);
        }
    };

    private Paint getDrawPaint(Paragraph paragraph) {
        return paragraph.selectState == SelectState.SELECT ? bigPaint : smallPaint;
    }


    public void reset() {
        datas.clear();
        paragraphList.clear();
        selectIndex = -1;
        unSelectIndex = -2;
        postInvalidate();
    }

    int bigPaintColor = Color.parseColor("#FFFFFFFF");
    int smallPaintColor = Color.parseColor("#99FFFFFF");

    public void setPaintDp(int bigPaintColor, int smallPaintColor) {
        this.bigPaintColor = bigPaintColor;
        this.smallPaintColor = smallPaintColor;
        postInvalidate();
    }

    public void setPaintDp(float bigDp, float smallDp, float lineInnerSpaceSmall, float lineInnerSpaceBig, float paragraphSpace, float scrollTopMargin) {
        this.scrolllOffsetTop = DensityUtils.dp2px(getContext(), scrollTopMargin);
        bigPaintSize = DensityUtils.dp2px(getContext(), bigDp);
        bigPaint.setTextSize(bigPaintSize);
        bigPaint.setColor(bigPaintColor);
        bigPaint.setFakeBoldText(true);

        smallPaintSize = DensityUtils.dp2px(getContext(), smallDp);
        smallPaint.setTextSize(smallPaintSize);
        smallPaint.setColor(smallPaintColor);
        smallPaint.setFakeBoldText(false);

        this.paragraphSpace = DensityUtils.dp2px(getContext(), paragraphSpace);
        this.lineInnerSpaceSmall = DensityUtils.dp2px(getContext(), lineInnerSpaceSmall);
        this.lineInnerSpaceBig = DensityUtils.dp2px(getContext(), lineInnerSpaceBig);

        lineSpacePaint.setColor(Color.BLUE);
        lineInnerSpacePaint.setColor(Color.GREEN);

        postInvalidate();
    }

    int selectIndex = -1, unSelectIndex = -2;

    public void refreshLogic(int selectIndex, int unSelectIndex) {
        this.unSelectIndex = unSelectIndex;
        this.selectIndex = selectIndex;


        if (backAnimator.isRunning()) {
            backAnimator.end();
        }
        preDealParagraph();
        LogApp.INSTANCE.d(
                "selectIndex:" + selectIndex +
                        "\t unSelectIndex:" + unSelectIndex
        );
        scrollLogic();

        backAnimator.start();
        postInvalidate();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (backAnimator.isRunning()) {
            backAnimator.cancel();
        }
    }

    private void scrollLogic() {
        caculateSelectTop(scrollValues);
        int sy = 0;
        //logic 只会在不满 或者满屏幕的两个逻辑走不回交叉变换 所以动画不会有问题
        if (scrollValues[1] + scrolllOffsetTop < getHeight()) {
            LogApp.INSTANCE.d("内容不满屏幕，可以滚动到 头部偏移值");
            sy = (int) (-scrolllOffsetTop);
        } else { //内容满一屏幕的话
            float shouldScrollY = scrollValues[0] - scrolllOffsetTop;
            float screenBottom = shouldScrollY + getHeight();
            if (screenBottom < scrollValues[1]) {
                //滚动后的屏幕底部未超出内容底部， 可以继续滚动
                sy = (int) shouldScrollY;
                LogApp.INSTANCE.d("滚动后的屏幕底部 未超出内容底部， 可以继续滚动");
            } else {
                //滚动后的屏幕底部 超出内容底部， view与内容底对齐
                sy = (int) (scrollValues[1] - getHeight());
                LogApp.INSTANCE.d("滚动后的屏幕底部 超出内容底部， view与内容底对齐");
            }
        }

        if (sy != getScrollY()) {
            smoothScrollTo(0, sy);
        }
    }

    /**
     * 需要 预处理成 打字的格式，去确定每行的字数
     */
    private void preDealParagraph() {
        paragraphList.clear();
        for (int i = 0; i < datas.size(); i++) {
            String lineStr = datas.get(i);

            Paragraph paragraph = new Paragraph();
            paragraph.str = lineStr;

            if (i == selectIndex) {
                paragraph.selectState = SelectState.SELECT;
            } else if (i == unSelectIndex) {
                paragraph.selectState = SelectState.UNSELECT;
            } else {
                paragraph.selectState = SelectState.INIT;
            }

            int beginIndex = 0;
            int endIndex = lineStr.length();
            Paint measurePaint = bigPaint;

            int lineNumber = 0;
            while (beginIndex != endIndex - 1) {
                //判断剩下的是否超过一行
                if (measurePaint.measureText(lineStr, beginIndex, endIndex) > getWidth()) {
                    do {
                        endIndex--;
                    } while (measurePaint.measureText(lineStr, beginIndex, endIndex) > getWidth());
                    lineNumber++;

                    if (lineNumber >= maxLineNumber) {
                        endIndex = lineStr.length();
                        do {
                            endIndex--;
                        } while (measurePaint.measureText(lineStr, beginIndex, endIndex)
                                + measurePaint.measureText(END_PREFIX, 0, END_PREFIX.length() - 1) > getWidth());
                        paragraph.list.add(new Line(beginIndex, endIndex).setHasEndPrefix(true));
                        break;
                    }

                    paragraph.list.add(new Line(beginIndex, endIndex));
                    //找到小于的了
                    beginIndex = endIndex;
                    endIndex = lineStr.length();//

                } else {
                    paragraph.list.add(new Line(beginIndex, endIndex));
                    lineNumber++;
                    break;
                }
            }
            paragraphList.add(paragraph);
        }
    }

    //scrollValues[0]选中歌词的 top, scrollValues[1] 歌词底部
    float[] scrollValues = new float[2];

    /**
     * 根据动画结束后状态,去计算
     * <p>
     * 【0】 计算选中的位置头部的高度
     * 【1】 计算文字整体的高度
     */
    private void caculateSelectTop(float[] scrollFloat) {
        scrollFloat[0] = 0;
        scrollFloat[1] = 0;

        float height = 0;
        for (int j = 0; j < paragraphList.size(); j++) {
            Paragraph paragraph = paragraphList.get(j);
            boolean isLastParagraph = j == paragraphList.size() - 1;

            Paint drawPaint = getDrawPaint(paragraph);
            float paragrahHeight = 0;

            float lineInnerSpace;
            switch (paragraph.selectState) {
                case SELECT:
                    scrollFloat[0] = height;
                    lineInnerSpace = lineInnerSpaceBig;
                    break;
                case INIT:
                case UNSELECT:
                default:
                    lineInnerSpace = lineInnerSpaceSmall;
                    break;
            }

            for (int i = 0; i < paragraph.list.size(); i++) {
                float top = drawPaint.getFontMetrics().top;
                float lineHeightReal = drawPaint.getFontMetrics().bottom - top;

                boolean isLastLine = i == paragraph.list.size() - 1;
                float lineHeightWithMargin = lineHeightReal + (isLastLine ? 0F : lineInnerSpace);
                paragrahHeight += lineHeightWithMargin;
            }

            float paragrahHeightFinal = paragrahHeight + (isLastParagraph ? 0F : paragraphSpace);
            height += paragrahHeightFinal;
        }
        scrollFloat[1] = height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float height = 0;
        for (int j = 0; j < paragraphList.size(); j++) {
            Paragraph paragraph = paragraphList.get(j);
            boolean isLastParagraph = j == paragraphList.size() - 1;

            Paint drawPaint = getDrawPaint(paragraph);
            float paragrahHeight = 0;

            //绘制段落
            int saveLine = canvas.save();
            canvas.translate(0, height);
            float scale = 1F;
            float lineInnerSpace;

            switch (paragraph.selectState) {
                case SELECT:
                    scale = MathUtils.linear(fraction, 0f, 1f, smallPaintSize * 1f / bigPaintSize, 1F, MathUtils.Linear.OverMax);
                    lineInnerSpace = lineInnerSpaceBig;
                    break;
                case UNSELECT:
                    lineInnerSpace = lineInnerSpaceSmall;
                    scale = MathUtils.linear(fraction, 0f, 1f, bigPaintSize / smallPaintSize, 1F, MathUtils.Linear.OverMax);
                    break;
                default:
                    lineInnerSpace = lineInnerSpaceSmall;
                    drawPaint.setColor(smallPaintColor);
                    break;
            }

            //这里是左上角缩放
            canvas.scale(scale, scale, 0, 0);

            for (int i = 0; i < paragraph.list.size(); i++) {

                //绘制行
                Line line = paragraph.list.get(i);

                float top = drawPaint.getFontMetrics().top;
                float lineHeightReal = drawPaint.getFontMetrics().bottom - top;

                if (debugDrawLineGuide) {
                    canvas.drawLine(0, 0, getWidth(), 0, lineInnerSpacePaint);
                }
                canvas.translate(0, top * -1);//top 是负的值
                //绘制开始的点事底部
                if (line.hasEndPrefix) {
                    canvas.drawText(paragraph.str, line.begin, line.end, 0, 0, drawPaint);
                    float measureWidth = drawPaint.measureText(paragraph.str, line.begin, line.end);
                    canvas.drawText(END_PREFIX, 0, END_PREFIX.length(), measureWidth, 0, drawPaint);

//                    String text = paragraph.str.substring(line.begin, line.end) + END_PREFIX;
//                    canvas.drawText(text, 0, text.length(), 0, 0, drawPaint);
                } else {
                    canvas.drawText(paragraph.str, line.begin, line.end, 0, 0, drawPaint);
                }

                boolean isLastLine = i == paragraph.list.size() - 1;
                float lineHeightWithMargin = lineHeightReal + (isLastLine ? 0F : lineInnerSpace);
                canvas.translate(0, drawPaint.getFontMetrics().bottom);//top 是负的值
                if (debugDrawLineGuide) {
                    canvas.drawLine(0, 0, getWidth(), 0, lineSpacePaint);
                }
                canvas.translate(0, lineInnerSpace);

                paragrahHeight += lineHeightWithMargin * scale;
            }

            float paragrahHeightFinal = paragrahHeight + (isLastParagraph ? 0F : paragraphSpace);
            canvas.translate(0, paragrahHeightFinal);
            canvas.restoreToCount(saveLine);
            height += paragrahHeightFinal;
        }

        if (debugDrawScrollGuide) {
            canvas.drawLine(0, scrollValues[0] - scrolllOffsetTop, getWidth(), scrollValues[0] - scrolllOffsetTop, lineInnerSpacePaint);
            canvas.drawLine(0, scrollValues[1], getWidth(), scrollValues[1], lineSpacePaint);
        }
    }

    //调用此方法滚动到目标位置
    public void smoothScrollTo(int fx, int fy) {
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        smoothScrollBy(dx, dy);
    }

    //调用此方法设置滚动的相对偏移
    public void smoothScrollBy(int dx, int dy) {

        //设置mScroller的滚动偏移量
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy, DURATION);
        postInvalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
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


    static class Paragraph {
        String str;
        SelectState selectState = SelectState.INIT;
        List<Line> list = new ArrayList<>();
    }

    static class Line {
        public Line() {
        }

        public Line(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        int begin;
        int end;//he ending index, exclusive.
        boolean hasEndPrefix;

        public boolean isHasEndPrefix() {
            return hasEndPrefix;
        }

        public Line setHasEndPrefix(boolean hasEndPrefix) {
            this.hasEndPrefix = hasEndPrefix;
            return this;
        }
    }

    enum SelectState {
        INIT, SELECT, UNSELECT;
    }

}
