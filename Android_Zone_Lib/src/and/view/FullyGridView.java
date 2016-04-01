package and.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

//ScrollView嵌套GridView取消滚动/防止显示不全
public class FullyGridView extends GridView {
	
	public FullyGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public FullyGridView(Context context) {
		super(context);
	}

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, mExpandSpec);
	}
}
