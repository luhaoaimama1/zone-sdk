package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.Layout;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ReplacementSpan;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mylib_test.LogApp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BackGroundTextView extends androidx.appcompat.widget.AppCompatEditText {
    Layout layout;

    public BackGroundTextView(Context context) {
        this(context, null);
    }

    public BackGroundTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public BackGroundTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addTextChangedListener(watcher);
    }

    Rect rect = new Rect();
    int singleLineHeight = 0;
    int offset = 50;

    class Paragraph {

    }

    TextWatcher watcher = new TextWatcher() {
        //s是已经变化之前的值 getText也是之前的值
        //start 改变内容的起始位置，count表示 原始内容变动的长度  after:表示新添加的长度
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            System.out.println();
            LogApp.INSTANCE.d("删除的字符：" + s.subSequence(start, start + count));

        }

        //s是已经变化后的值 getText也是之后的值
        //start 改变内容的起始位置，count表示 原始内容变动的长度  after:表示新添加的长度
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //todo 删除的需要管 ，替换操作不管删除的而已，只看变化的内容
            //todo 检测 /n的后一个字符 与/n的前一个字符
            //

            Type type = Type.getType(before, count);
            if (type != Type.NONE) {
                resetSpan();
            }
            switch (type) {
                case ADD:
                    break;
                case REPLACE:
                    break;
                case DELETE:
                    break;
                default:
                    break;

            }
            //todo

            LogApp.INSTANCE.d("添加的字符：" + s.subSequence(start, start + count));
            System.out.println();
        }

        @Override
        public void afterTextChanged(Editable s) {
            System.out.println();
        }
    };


    enum Type {
        REPLACE, ADD, DELETE, NONE;

        static Type getType(int before, int count) {
            if (before > 0 && count > 0) {
                return REPLACE;
            } else if (before > 0 && count == 0) {
                return DELETE;
            } else if (count > 0 && before == 0) {
                return DELETE;
            } else {
                return NONE;
            }
        }
    }

    Pattern p = Pattern.compile(".*\n");

    void resetSpan() {
        Editable text = getText();
        if (text != null) {
            text.clearSpans();

            Matcher matcher = p.matcher(text);
            int start = 0;
            while (matcher.find()) {
                int end = matcher.end();
                LogApp.INSTANCE.d("matcher start:" + matcher.start() + "\t start:" + start + "\t end:" + end);
                //substring 包括start 不包括end
                CharSequence substring = text.subSequence(matcher.start(), end);
                if (text.length() < start + 1) {
                    text.setSpan(new MaginSpan(start == 0 ? 50 : 100, true),
                            start, start + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                LogApp.INSTANCE.d(substring.toString().replaceAll("\n", "!空格!"));
                start = end;
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeTextChangedListener(watcher);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        layout = getLayout();
        if (singleLineHeight == 0) {
            singleLineHeight = (int) (offset * 2 + Layout.getDesiredWidth("h", 0, 1, getPaint()));
        }

        int lineCount = layout.getLineCount();
        for (int i = 0; i < lineCount; i++) {
            int lineStart = layout.getLineStart(i);
            int lineEnd = layout.getLineEnd(i);
            CharSequence charSequence = getText().subSequence(lineStart, lineEnd);
            if (charSequence.toString().contains("/n")) {

            }
        }
        layout.getLineBounds(0, rect);
//rect
//        layout.getLineCount()
//layout.getLineWidth(1)


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    class MaginSpan extends ReplacementSpan {
        int offset;
        boolean isTopMargin;

        public MaginSpan(int offset, boolean isTopMargin) {
            if (offset < 0) throw new IllegalStateException("offset不能小于0");
            this.offset = offset;
            this.isTopMargin = isTopMargin;
        }

        @Override
        public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, @Nullable Paint.FontMetricsInt fm) {
            if (fm != null) {
                int offset = isTopMargin ? this.offset * -1 : this.offset;
                fm.ascent += offset;
                fm.descent += offset;

                fm.top += offset;
                fm.bottom += offset;
            }

            return (int) paint.measureText(text, start, end);
        }

        @Override
        public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
            try {
                canvas.drawText(text.toString(), start, end, x, y, paint);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
