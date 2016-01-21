package view;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
/**
 * 
 * 宽高模式：  宽不能为充斥内容 即明确长度
 * 		        高必须是 充斥内容 因为是正方形长度 已经在宽的时候 算出来了
 * @author Zone
 *
 */
public class ImageGroup_Zone extends LinearLayout {
	private  int  marginPix=20;
	private  int  lineNum=1;
	private  int  lineViewNum=4;
	private int viewWidth;
	private Context context=null;
	private ShowStatue showStatue=ShowStatue.ALL_VISIBLE;
	private List<ChildPro> childs=new ArrayList<ChildPro>();
	private MarginShowStatue marginShowStatue=MarginShowStatue.NONE_HIDE;
	public enum MarginShowStatue{
		LEFT_HIDE,RIGHT_HIDE,NONE_HIDE
	}
	public void setMarginShowStatue(MarginShowStatue marginShowStatue){
		this.marginShowStatue=marginShowStatue;
		requestLayout();
		invalidate();
	}
	public List<ChildPro> getChilds() {
		return childs;
	}
	public void setChilds(List<ChildPro> childs) {
		this.childs = childs;
	}
	public ChildPro getChild_ChildPro(int index){
		return childs.get(index);
	}
	public void moveOrderImageView(int index){
		ChildPro temp = childs.get(index);
		childs.remove(index);
		childs.add(temp);
		requestLayout();
		invalidate();
	}
	public class  ChildPro{
		public ImageView iv;
		public VisibleStatue vs;
		public ChildPro(ImageView iv, VisibleStatue vs) {
			this.iv=iv;
			this.vs=vs;
		}
		public void setGone(){
			vs=VisibleStatue.GONE;
			requestLayout();
			invalidate();
		}
		public void setVisiable(){
			vs=VisibleStatue.VISIBLE;
			requestLayout();
			invalidate();
		}
		
	}
	
	public enum VisibleStatue{
		VISIBLE,GONE
	}
	public enum ShowStatue{
		ALL_VISIBLE,FIRST_VISIBLE
	}
	
	public ImageGroup_Zone(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		updateProperty(marginPix, lineNum, lineViewNum,showStatue);
	}
	public ImageGroup_Zone(Context context,int marginPix,int lineNum,int lineViewNum) {
		this(context,null);
		updateProperty(marginPix, lineNum, lineViewNum,showStatue);
	}
	public void updateProperty(int marginPix,int lineNum,int lineViewNum,ShowStatue showStatue){
		this.marginPix=marginPix;
		this.lineNum=lineNum;
		this.lineViewNum=lineViewNum;
		this.showStatue=showStatue;
		int size = lineNum*lineViewNum;
		removeAllViews();
		childs.clear();
		
		for (int i = 0; i <size ; i++) {
			ImageView iv=new ImageView(context);
			addView(iv);
			switch (showStatue) {
			case ALL_VISIBLE:
				childs.add(new ChildPro(iv, VisibleStatue.VISIBLE));
				break;
			case FIRST_VISIBLE:
				if(i!=0)
					childs.add(new ChildPro(iv, VisibleStatue.GONE));
				else
					childs.add(new ChildPro(iv, VisibleStatue.VISIBLE));
				break;
			default:
				break;
			};
		}
		requestLayout();
		invalidate();
	}
	/**
	 * 测量不考虑 gone  如果考虑 那么 难道宽度 会有变化 
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		System.out.println("onMeasure走了");
		int widthMode=MeasureSpec.getMode(widthMeasureSpec);
		int heightMode=MeasureSpec.getMode(heightMeasureSpec);
		int widthSize=MeasureSpec.getSize(widthMeasureSpec);
//		int heightSize=MeasureSpec.getMode(heightMeasureSpec);
//		measureChildren(widthMeasureSpec, heightMeasureSpec);
		
		switch (marginShowStatue) {
		case LEFT_HIDE:
			viewWidth=(widthSize-marginPix*(lineViewNum))/lineViewNum;
			break;
		case RIGHT_HIDE:
			viewWidth=(widthSize-marginPix*(lineViewNum))/lineViewNum;
			break;
		case NONE_HIDE:
			viewWidth=(widthSize-marginPix*(lineViewNum+1))/lineViewNum;
			break;

		default:
			break;
		}
		int height;
		if(showStatue==ShowStatue.ALL_VISIBLE)
			height= marginPix*(lineNum+1)+viewWidth*lineNum;
		else{
			int showToIndex=-1;
			for (int i = 0; i < childs.size(); i++) {
				if(childs.get(i).vs==VisibleStatue.GONE){
					showToIndex=i-1;
					break;
				}
			}
			if(showToIndex==-1)
				showToIndex=childs.size()-1;
			int lineNumTemp=showToIndex/lineViewNum+1;
			
			height= marginPix*(lineNumTemp+1)+viewWidth*lineNumTemp;
			System.out.println("Height:____"+height+"\tlineNumTemp"+lineNumTemp+"\t showToIndex:gone_"+showToIndex);
		}
	
		
		if(widthMode!=MeasureSpec.EXACTLY)
			throw new  IllegalStateException("widthMode can not be  wrap_content");
		
		if(heightMode==MeasureSpec.EXACTLY)
			throw new  IllegalStateException("heightMode must be  wrap_content");
		
		/**
		 * 如果是wrap_content设置为我们计算的值
		 * 否则：直接设置为父容器计算的值
		 */
		setMeasuredDimension(widthSize,height);
	}

	/**
	 * 布局考虑 gone
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		System.out.println("onLayout走了");
		int width = getWidth();
		int height = getHeight();
		System.err.println("width:"+width+"\theight:"+height);
		int size = lineNum*lineViewNum;
		for (int i = 0; i < size; i++) {
			LinearLayout.LayoutParams params = new LayoutParams(viewWidth,viewWidth);
			ImageView child = (ImageView)childs.get(i).iv;
			child.setLayoutParams(params);
			int lineViewIndex=i%lineViewNum;
			int childLeft=0;
			switch (marginShowStatue) {
			case LEFT_HIDE:
				childLeft=lineViewIndex*viewWidth+marginPix*(lineViewIndex);
				break;
			case RIGHT_HIDE:
				childLeft=lineViewIndex*viewWidth+marginPix*(lineViewIndex+1);
				break;
			case NONE_HIDE:
				childLeft=lineViewIndex*viewWidth+marginPix*(lineViewIndex+1);
				break;

			default:
				break;
			}
			int childRight=childLeft+viewWidth;
			
			int lineIndex=i/lineViewNum;
			int childHeight=(lineIndex+1)*marginPix+lineIndex*viewWidth;
			int childBottom=childHeight+viewWidth;
			
			child.layout(childLeft, childHeight,childRight, childBottom);
			
			if(childs.get(i).vs==VisibleStatue.GONE)
				child.setVisibility(View.GONE);
			else
				child.setVisibility(View.VISIBLE);
		}
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		System.out.println("onDraw");
	}

}
