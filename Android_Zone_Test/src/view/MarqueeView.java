package view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import androidx.annotation.Nullable;

public class MarqueeView extends androidx.appcompat.widget.AppCompatTextView {
    public MarqueeView(Context context) {
        this(context,null,-1);
    }

    public MarqueeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public MarqueeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setMarqueeRepeatLimit(-1);
        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        setGravity(Gravity.CENTER_VERTICAL);
    }

//    @Override
//    public boolean isFocused() {
//        return true;
//    }

    @Override
    public boolean isSelected() {
        return true;
    }
}
