package com.zone.view;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import com.zone.customview.ninegridview.R;

public class SideBar extends View {

	public static String[] chars = { "A", "B", "C", "D", "E", "F", "G", "H",
			"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
			"V", "W", "X", "Y", "Z", "#" };
	private int chooseIndex; // 被选中的字母下标
	private Paint paint = new Paint();// 画笔
	private TextView mTextView;
	
	private OnLetterSelectedListener letterSelectedListener;

	public OnLetterSelectedListener getLetterSelectedListener() {
		return letterSelectedListener;
	}

	public void setLetterSelectedListener(
			OnLetterSelectedListener letterSelectedListener) {
		this.letterSelectedListener = letterSelectedListener;
	}

	public TextView getmTextView() {
		return mTextView;
	}

	public void setmTextView(TextView mTextView) {
		this.mTextView = mTextView;
	}

	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 绘制视图
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 获取控件的宽高
		int width = getWidth();
		int height = getHeight();
		// 每个字母的高度
		int singleHeight = height / chars.length;
		for (int i = 0; i < chars.length; i++) {
			paint.setColor(Color.rgb(33, 66, 99));
			paint.setTypeface(Typeface.DEFAULT_BOLD);// 设置粗体
			paint.setAntiAlias(true); // 抗锯齿
			paint.setTextSize(20);
			if (chooseIndex == i) {
				paint.setColor(Color.parseColor("#3399ff"));
			}
			// 绘制文本
			float xPos = (width - paint.measureText(chars[i])) / 2;
			float yPos= singleHeight*i+singleHeight;	// 每个字母的Y坐标
			canvas.drawText(chars[i], xPos, yPos, paint);
			paint.reset();	// 重置画笔
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		int action=event.getAction(); // 手指动作
		float y=event.getY();	// 手指Y坐标
		int oldChoode=chooseIndex;
		int c=(int) (y/this.getHeight()*chars.length);
		switch (action) {
		case MotionEvent.ACTION_UP:
			setBackgroundDrawable(new ColorDrawable(0x00000000));	//设置背景透明
			chooseIndex=-1;
			invalidate(); // 重绘
			if (null != mTextView) {
				mTextView.setVisibility(View.INVISIBLE);
			}
			break;

		default:
			setBackgroundResource(R.drawable.side_bar_bg); // 设置红色背景
			// 根据y坐标获取点到的字母
			if (oldChoode != c) {
				if (c>=0 && c<=chars.length) {
					if (letterSelectedListener != null) {
						letterSelectedListener.onLetterSelected(chars[c]);
					}
					if (null != mTextView) {
						mTextView.setVisibility(View.VISIBLE);
						mTextView.setText(chars[c]);// 设置被选中的字母
					}
				}
				chooseIndex=c;
				invalidate();
			}
			break;
		}
		return true;
	}
	
	public interface OnLetterSelectedListener{
		 void onLetterSelected(String s);
	}
}
