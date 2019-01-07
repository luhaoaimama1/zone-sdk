package view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.SpannedString;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * [2018] by Zone
 */
@SuppressLint("AppCompatCustomView")
public class ExpandTextView extends TextView {

    public final String EXPAND = "... 展开";
    private int maxLine = -1;

    public ExpandTextView(Context context) {
        super(context);
    }

    public ExpandTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    //找到这行的最后一个字符 然后计算宽度 与EXPAND 是否大于自身的宽度
    public void setMaxLine(int maxLine) {
        this.maxLine = maxLine;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!calExpand)
            setExpand();
    }

    boolean calExpand;

    /**
     * 得到最多行那个 最后一个字符的位置，与第一个位置 测出宽度 与 扩展字符 相加 如果大于行宽， 就把endPos--，知道小于。
     * 最后 截取字符  0-endPos 然后拼接  扩展字符 去显示即可！
     */
    private void setExpand() {
        calExpand = true;
        Layout layout = getLayout();
        int endPos = layout.getLineEnd(maxLine - 1);
        int startPos = layout.getLineStart(maxLine - 1);
        TextPaint pt = getPaint();

        //如果宽度>测量宽度 就继续 --
        while (Layout.getDesiredWidth(getText(), startPos, endPos, pt) + Layout.getDesiredWidth(new SpannedString(EXPAND), pt) > layout.getWidth()
                && --endPos > startPos) {
        }
        SpannableStringBuilder nowSb = new SpannableStringBuilder(getText().subSequence(0, endPos)).append(EXPAND);
        setText(nowSb);
    }

}
