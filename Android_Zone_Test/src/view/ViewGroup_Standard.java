package view;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

//此view是ViewGroup而不是 onDraw那种自定义
public abstract class ViewGroup_Standard extends LinearLayout {
	private int mMeasureWith, mMeasureHeight, offsetX, offsetY;
	private List<ViewAttr> childList = new ArrayList<ViewAttr>();
	private void onMeasureReset() {
		childList.clear();
		mMeasureWith = 0;
		mMeasureHeight = 0;
		offsetX=0; 
		offsetY=0;
	};
	public  class ViewAttr {
		public View view;// 收集view
		public Point location=new Point();// view在布局中左上角的点

		//这里是对View显隐性  的support
		public void layoutSupporVisibilty() {
			if (view.getVisibility()==View.VISIBLE) {
				int realX = location.x + offsetX;
				int realY = location.y + offsetY;
				view.layout(realX, realY, realX + view.getMeasuredWidth(),
						realY + view.getMeasuredHeight());
			}
		}
	}

	public ViewGroup_Standard(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ViewGroup_Standard(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ViewGroup_Standard(Context context) {
		this(context, null);
	}

	/**
	 * 通过测量孩子的大小 来真正测量自己需要多大 如果不考虑 wrap_content 则可以不写此方法,直接在布局里写逻辑！！！
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		onMeasureReset();

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		//这里widthSize heightSize 是最大值
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getMode(heightMeasureSpec);

		measureChildren(widthMeasureSpec, heightMeasureSpec);

		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			ViewAttr viewAttr=new ViewAttr();
			View view = getChildAt(i);
			viewAttr.view=view;
			if (view.getVisibility()!=View.GONE) {
				viewAttr.location=getViewLocation(view);
			}
			childList.add(viewAttr);
		}
		
		Point sizePoint = makeSureSize();
		mMeasureWith=sizePoint.x;
		mMeasureHeight=sizePoint.y;
		
		//在这之前确定  mMeasureWith  mMeasureHeight的值
		supportPadding(widthMode, heightMode,widthSize,heightSize);
		/**
		 * 如果是wrap_content设置为我们计算的值 否则：直接设置为父容器计算的值
		 */
		setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize
				: mMeasureWith, heightMode == MeasureSpec.EXACTLY ? heightSize
				: mMeasureHeight);
	}

	public abstract Point makeSureSize();

	/**
	 * 如何迭代这个位置 然后让viewAttr.location 有值  是业务逻辑   记得支持margin 
	 * @param view 
	 * @return  返回的这个点是不包含padding的业务逻辑  因为padding已经内部支持了
	 */
	public abstract Point getViewLocation(View view);

	private void supportPadding(int widthMode, int heightMode, int widthSize, int heightSize) {
		if (widthMode == MeasureSpec.EXACTLY) {
			offsetX = getPaddingLeft();
		} else {
			mMeasureWith += getPaddingLeft() + getPaddingRight();
			//如果这个widthSize<0 则代表无界限 view的宽最大值为widthSize 
			if(widthSize>0&&mMeasureWith>widthSize)
				mMeasureWith=widthSize;
		}

		if (heightMode == MeasureSpec.EXACTLY) {
			offsetY = getPaddingTop();
		} else {
			mMeasureHeight += getPaddingTop() + getPaddingBottom();
			if(heightSize>0&&mMeasureHeight>heightSize)
				mMeasureHeight=heightSize;
		}
	}

	/**
	 * 知道自己多大后 然后给孩子布局
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		for (ViewAttr item : childList) {
			item.layoutSupporVisibilty();
		}
	}
	@Override
	protected void onDraw(Canvas canvas) {
		//在回调父类方法之前    实现自己的逻辑
		super.onDraw(canvas);//此方法来实现原生控件的功能
		//在回调父类方法之后    实现自己的逻辑
	}

}
