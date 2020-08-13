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

import com.zone.lib.utils.data.convert.DensityUtils;
import com.zone.lib.utils.view.DrawUtils;

import java.util.ArrayList;
import java.util.List;
//todo zone

/**
 *
 qq音乐
 切换状态的时候
 先计算终结状态显示的文字。然后做缩放

 得到当前滚动值，计算到top的值

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
        String str = "Hi are you wake\n" +
                "\n" +
                "Hi you wake up\n" +
                "\n" +
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
                "\n" +
                "It's gonna light up\n" +
                "\n" +
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
        lineSpace = DensityUtils.dp2px(getContext(), 30);

        mScroller = new Scroller(getContext());

        backAnimator.setDuration(500);
        backAnimator.addUpdateListener(animation -> {
            fraction = (float) animation.getAnimatedValue();
            postInvalidate();
        });
        post(runnable);
    }

    float fraction;
    float lineSpace, lineInnerSpace;
    Paint bigPaint = DrawUtils.getBtPaint();
    Paint smallPaint = DrawUtils.getBtPaint();
    ValueAnimator backAnimator = ValueAnimator.ofFloat(0f, 1f);

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            update();
            paragraphList.clear();
            for (int i = 0; i < list.size(); i++) {
                String lineNote = list.get(i);

                Paragraph paragraph = new Paragraph();
                paragraph.str = lineNote;

                int beginIndex = 0;
                int endIndex = lineNote.length();

                while (beginIndex != endIndex - 1) {
                    //判断剩下的是否超过一行
                    if (bigPaint.measureText(lineNote, beginIndex, endIndex) > getWidth()) {
                        do {
                            endIndex--;
                        } while (bigPaint.measureText(lineNote, beginIndex, endIndex) > getWidth());

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

    List<Paragraph> paragraphList = new ArrayList<>();

    class Paragraph {
        String str;
        List<Line> list = new ArrayList<>();
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
        int save = canvas.save();
        canvas.translate(0, 50);
        for (Paragraph paragraph : paragraphList) {
            for (Line line : paragraph.list) {
                canvas.drawText(paragraph.str, line.begin, line.end, 0, 0, bigPaint);
                bigPaint.getTextBounds(paragraph.str, line.begin, line.end, rect);
                canvas.translate(0, rect.height());
                canvas.translate(0, lineInnerSpace);
            }
            canvas.translate(0, lineSpace);
        }
        canvas.restoreToCount(save);
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
