package view;
import java.util.ArrayList;
import java.util.List;

import com.example.mylib_test.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class FlowLayout_Zone extends LinearLayout{
	private boolean log=true;
	private static final String TAG="FlowLayout_Zone";
	public FlowLayout_Zone(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	//line属性： lineWidthlist lineHeightList
	private List<Integer> lineWidthlist;
	private List<Integer> lineHeightList;
	private List<List<View>> lineAllView;

	{
		lineWidthlist=new ArrayList<Integer>();
		lineHeightList=new ArrayList<Integer>();
		lineAllView=new ArrayList<List<View>>();

	}
	/**
	 * 通过测量孩子的大小  来真正测量自己需要多大
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if(log)
			System.out.println("onMeasure");
		//当请求测量的时候 把缓存值清空
		lineWidthlist.clear();
		lineHeightList.clear();
		lineAllView.clear();
		
		int widthMode=MeasureSpec.getMode(widthMeasureSpec);
		int heightMode=MeasureSpec.getMode(heightMeasureSpec);
		int widthSize=MeasureSpec.getSize(widthMeasureSpec);
		int heightSize=MeasureSpec.getMode(heightMeasureSpec);
		measureChildren(widthMeasureSpec, heightMeasureSpec);
		//自己测量的宽高   主要用于 AT_MOST
		int width=0;
		int height=0;
		
		//lineMaxLength     lineTotallength
		int lineWidth=0;//行宽
		int lineHeight=0;//行高
		
		//行view
		List<View> lineView=new ArrayList<View>();
		
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child=getChildAt(i);
			Point point=getRealWidth_Height(child);
			int cRealWidth=point.x;
			int cRealHeight=point.y;
			
				//计算宽度
				if (lineWidth + cRealWidth > widthSize) {
					/** 如果增加一个发先 比最大的宽度大 */
					//则把没增加的宽度记录为line宽度
					lineWidthlist.add(lineWidth);
					//当换行的时候 高度也记录下来
					lineHeightList.add(lineHeight);
					//把行view添加进去
					lineAllView.add(lineView);
					
					/**
					 * 把 view添加到第二行
					 */
					//把这个view高赋值给行高度
					lineHeight = cRealHeight;
					//把这个控件赋值给行宽度  
					lineWidth = cRealWidth;
					lineView=new ArrayList<View>();;
					lineView.add(child);
					/**
					 * 换行后 并且是最后一个  那么把此行也给提交了
					 */
					if(i==count-1){
						//则把没增加的宽度记录为line宽度
						lineWidthlist.add(lineWidth);
						//当换行的时候 高度也记录下来
						lineHeightList.add(lineHeight);
						lineAllView.add(lineView);
					}
				} else {
					/** 没超过最大的宽度 */
					if (i!=count-1) {
						/** 如果_不是_最后一个 */
						//当前行的宽度增加
						lineWidth += cRealWidth;
						//记录当前行最大的高度
						lineHeight = Math.max(lineHeight, cRealHeight);
						lineView.add(child);
					}else{
						/** 如果是最后一个 */
						//当前行的宽度增加
						lineWidth += cRealWidth;
						//记录当前行最大的高度
						lineHeight = Math.max(lineHeight, cRealHeight);
						
						//则把没增加的宽度记录为line宽度
						lineWidthlist.add(lineWidth);
						//当换行的时候 高度也记录下来
						lineHeightList.add(lineHeight);
						//把行view添加进去
						lineView.add(child);
						lineAllView.add(lineView);
					}
				}
				
		}
		//计算完成后  累计高和算出最大长度
		for (Integer item : lineHeightList) {
			height+=item;
		}
		for (Integer item : lineWidthlist) {
			width=Math.max(width, item);
		}
		/**
		 * 如果是wrap_content设置为我们计算的值
		 * 否则：直接设置为父容器计算的值
		 */
		setMeasuredDimension(widthMode==MeasureSpec.EXACTLY?widthSize:width, 
				heightMode==MeasureSpec.EXACTLY?heightSize:height);
	}
	/**
	 * 知道自己多大后   然后给孩子布局
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if(log)
			System.out.println("onLayout");
		int left=0;
		int top=0;
		int right=0;
		int bottom=0;
		for (List<View> line : lineAllView) {
			int lineIndex=lineAllView.indexOf(line);
			bottom+=lineHeightList.get(lineIndex);
			for (View view : line) {
				int viewIndex=line.indexOf(view);
				
				Point point=getRealWidth_Height(view);
				int cRealWidth=point.x;
				int cRealHeight=point.y;
				LayoutParams lp =  (LayoutParams) view.getLayoutParams();
				
				right+=cRealWidth;
				
				int view_Top_Gap=(lineHeightList.get(lineIndex)-cRealHeight)/2;
				
				view.layout(left+lp.leftMargin, top+lp.topMargin+view_Top_Gap, right-lp.rightMargin, bottom-lp.bottomMargin-view_Top_Gap);
				if(log)
				Log.i("lineIndex:"+lineIndex+"_viewIndex:"+viewIndex+"", "left:"+left+"__top:"+top+"__right："+right+"__bottom:"+bottom);
				//布局以后的逻辑
				left+=cRealWidth;
			}
			//view换行操作
			top+=lineHeightList.get(lineIndex);
			left=0;
			right=0;
		}
	}

	private Point getRealWidth_Height(View view) {
		int cWidth = view.getMeasuredWidth();
		int cHeight = view.getMeasuredHeight();
		LayoutParams lp = (LayoutParams) view.getLayoutParams();
		int cRealWidth = cWidth + lp.leftMargin + lp.rightMargin;
		int cRealHeight = cHeight + lp.topMargin + lp.bottomMargin;
		Point point = new Point(cRealWidth, cRealHeight);
		return point;
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(log)
			System.out.println("onDraw");
	}
	
}
