package com.zone.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @deprecated  Use {@link SquareImageView2 } instead.
 */
public class SquareImageView extends ImageView {

	public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SquareImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SquareImageView(Context context) {
		this(context, null);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int height = getMeasuredWidth();
		setMeasuredDimension(height, height);
	}

}
