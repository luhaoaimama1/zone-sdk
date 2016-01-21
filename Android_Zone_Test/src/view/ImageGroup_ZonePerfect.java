package view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class ImageGroup_ZonePerfect extends LinearLayout {
	private  int  marginPix=20;
	private  int  lineNum=1;
	private  int  lineViewNum=4;

	private ShowStatue showStatue = null;

	public enum ShowStatue {
		ALL_VISIBLE, FIRST_VISIBLE
	}
	public ImageGroup_ZonePerfect(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public ImageGroup_ZonePerfect(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public ImageGroup_ZonePerfect(Context context) {
		this(context,null);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
	}

}
