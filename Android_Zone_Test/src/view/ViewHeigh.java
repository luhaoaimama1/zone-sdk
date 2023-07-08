package view;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * 广告：过长的时候 底对齐，不过长 居中。
 * 而正常的父布局会受到 自身父布局大小的控制。导致布局长度无法用子布局的高度。所以用UNSPECIFIED 把父布局的限制打破
 */
public class ViewHeigh  extends ConstraintLayout {
    public ViewHeigh(@NonNull Context context) {
        super(context);
    }

    public ViewHeigh(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewHeigh(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
    }
}
