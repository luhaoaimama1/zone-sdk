package view;


import java.util.ArrayList;
import java.util.List;
import com.example.mylib_test.R;
import com.zone.view.label.util.Attr_Styleable_Utils;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 *
 * <strong>此控件中心view放最下面</strong> 这样看起来才能是 <strong>最上面    </strong>
 * <br>中心控件左间距 上间距随便弄   而且可以用相对布局去包裹  
 * 
 * @author Zone
 *
 */
public class ArcMenu_Zone extends RelativeLayout implements OnClickListener{
	
	//半径
	private int mRadius = 100;
	//现在没用了
	private int position = 0;
	// 第一个旋转控件和中心点成0
	private Float start_angle = 0F;
	//展开角度
	private Float spreadAngel = 90F;
	//排序   顺时针Or逆时针
	private Rorate_Mode rorate_mode=Rorate_Mode.DIRECTION_CCW;
	//初始状态是  关闭
	private ToggleState toggleState=ToggleState.CLOSE;
	//记录中心的view
	private View centerView_Click;
	private Point centerPoint;
	
	//把外围View 中心点 显示范围都记录下来
	private List<Rect> outter_viewRectList;
	private List<View> outter_viewList;
	private List<Point> outter_viewCenterPointList;
	//动画持续时间  毫秒
	private static final int duration=300;
	private static final int maxDelay_Duration=100;
	private   onItemlistener listener;
	
	{
		outter_viewRectList=new ArrayList<Rect>();
		outter_viewList=new ArrayList<View>();
		outter_viewCenterPointList=new ArrayList<Point>();
	}
	private enum ToggleState{
		OPEN,CLOSE
	}
	private enum Rorate_Mode{
		/**
		 * 顺时针
		 */
		DIRECTION_CW,
		/**
		 * 逆时针
		 */
		DIRECTION_CCW
	}

	public ArcMenu_Zone(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		Attr_Styleable_Utils attr_utils = new Attr_Styleable_Utils(context,
				attrs, defStyle, R.styleable.ArcMenu_Zone, this);
		mRadius = attr_utils.get_attr_demen_toPix(
				R.styleable.ArcMenu_Zone_radius, mRadius);
		position = attr_utils.get_attr_enum_to_int(
				R.styleable.ArcMenu_position, position);
		start_angle = attr_utils.get_attr_Float(
				R.styleable.ArcMenu_Zone_startAngel, start_angle);
		spreadAngel = attr_utils.get_attr_Float(
				R.styleable.ArcMenu_Zone_startAngel, spreadAngel);
		attr_utils.recycle();

	}

	public ArcMenu_Zone(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ArcMenu_Zone(Context context) {
		this(context, null);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		measureChildren(widthMeasureSpec, heightMeasureSpec);
	}

	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		outter_viewRectList.clear();
		outter_viewList.clear();
		outter_viewCenterPointList.clear();
		
		if(getChildCount()>0){
			//设置中心点控件的布局
			centerView_Click = getChildAt(getChildCount()-1);
			//给中心按钮 设置点击监听
			centerView_Click.setOnClickListener(this);
			RelativeLayout.LayoutParams st = (RelativeLayout.LayoutParams) centerView_Click.getLayoutParams();
			centerView_Click.layout(st.leftMargin, st.topMargin, st.leftMargin+ centerView_Click.getMeasuredWidth(),
					st.topMargin + centerView_Click.getMeasuredHeight());
//			Log.d("centerPoint", "Pingmu:left_"+st.leftMargin+" \eventType right_"+
//			st.leftMargin+ centerView.getMeasuredWidth()+" \eventType top_"+st.topMargin
//					+"\eventType bottom"+st.topMargin + centerView.getMeasuredHeight());
			centerPoint=new Point(st.leftMargin+centerView_Click.getMeasuredWidth()/2,st.topMargin + centerView_Click.getMeasuredHeight()/2);
			//给其他的控件进行 布局  从1开始
			if(getChildCount()>1){
				switch (rorate_mode) {
				case DIRECTION_CW:
					calculate_Rect_Right(centerPoint,1);
					break;
				case DIRECTION_CCW:
					calculate_Rect_Right(centerPoint,-1);
					break;

				default:
					break;
				}
				//设置布局
				
			}
		}
		Log.d("gaga", "Pingmu:left_0 \t right_"+getMeasuredWidth()+" \t top_0 \t bottom"+getMeasuredHeight());
	}

	private void calculate_Rect_Right(Point centerPoint,int directionMode) {
		Log.d("vie"+0+"中心点", centerPoint.x+":"+centerPoint.y);
		if(directionMode!=1&&directionMode!=-1){
			throw new IllegalArgumentException("参数：directionMode 必须是1 或者-1！");
		}
		//正角度 计算
		int count=getChildCount();
		//减1是为了 把中心那个view去掉
		for (int i = 0; i <count-1 ; i++) {
			View v=getChildAt(i);
			int viewWidth=v.getMeasuredWidth();
			int viewHeight=v.getMeasuredHeight();
			int outterViewIndex=i;
			
			Float averageAngel=spreadAngel/(count-2);
			Float viewAngel=averageAngel*outterViewIndex*directionMode+start_angle*directionMode;
			//sin cos是弧度  2PI  是360°
			double rad = viewAngel*(2*Math.PI)/360;
			int cosX=(int)(Math.cos(rad)*mRadius);
			int cosY=(int)(Math.sin(rad)*mRadius);
			int index_Center_Width=cosX+centerPoint.x;
			int index_Center_Height=cosY+centerPoint.y;
			//记录外围View 中心点 显示范围
			outter_viewCenterPointList.add(new Point(index_Center_Width,index_Center_Height));
			outter_viewList.add(v);
			Rect rectShow = new Rect(index_Center_Width-viewWidth/2, index_Center_Height-viewHeight/2, 
					index_Center_Width+viewWidth/2, index_Center_Height+viewHeight/2);
			outter_viewRectList.add(rectShow);
			
			v.layout(index_Center_Width-viewWidth/2, index_Center_Height-viewHeight/2, 
					index_Center_Width+viewWidth/2, index_Center_Height+viewHeight/2);
			final int position_Outter=outterViewIndex;
			v.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					menuItemAnim(v);
					if (listener!=null) {
						listener.onItemlistener(v);
					}
					System.out.println("点击的是第几个："+position_Outter);
				}
			});
			v.setVisibility(INVISIBLE);
			Log.d("vie"+i+"中心点", index_Center_Width+":"+index_Center_Height);
		}
		
	}

	@Override
	public void onClick(View v) {
		if(v.equals(centerView_Click)){
			centerView_rotate(v);
			toggle();
		}
		
	}

	private void toggle() {

		int count=outter_viewList.size();
		for (int i = 0; i <count ; i++) {
			AnimationSet animset = new AnimationSet(true);
			final View view=outter_viewList.get(i);
			view.setVisibility(VISIBLE);

			Log.d("ani_outterView_"+i, "x："+ centerPoint.x+"-->"+ outter_viewCenterPointList.get(i).x+"\ty:"+centerPoint.y
					+"-->"+outter_viewCenterPointList.get(i).y);
			Animation translateAnim = null;
			switch (toggleState) {
			case OPEN:
				//toClose
				translateAnim = new TranslateAnimation(0L, centerPoint.x- outter_viewCenterPointList.get(i).x,
						0L,centerPoint.y - outter_viewCenterPointList.get(i).y);
				break;
			case CLOSE:
				//toOpen   最大延迟100秒
				animset.setInterpolator(new OvershootInterpolator(2F));
				translateAnim = new TranslateAnimation(centerPoint.x- outter_viewCenterPointList.get(i).x, 0L,
						centerPoint.y - outter_viewCenterPointList.get(i).y, 0L);
				translateAnim.setStartOffset((i * maxDelay_Duration) / (count - 1));
				break;

			default:
				break;
			}
		
			translateAnim.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation animation) {
				}
				
				@Override
				public void onAnimationRepeat(Animation animation) {
				}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					System.out.println("onAnimationEnd");
					//动画结束显示控件
					switch (toggleState) {
					case OPEN:
						//打开
						view.setClickable(true);
						
						break;
					case CLOSE:
						//关闭
						view.setClickable(false);
						view.setVisibility(INVISIBLE);
						break;
					default:
						break;
					}
				}
			});
			
			
			translateAnim.setDuration(duration);  
			translateAnim.setFillAfter(true);
			Animation rotateAnim = new RotateAnimation(0, 720,Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f); 
			rotateAnim.setFillAfter(true);
			rotateAnim.setDuration(duration);
			//顺序不能乱 乱了 你可以是试试
			animset.addAnimation(rotateAnim);
			animset.addAnimation(translateAnim);
			
			view.startAnimation(animset);
		}
		//动画完毕更改状态
		switch (toggleState) {
		case OPEN:
			toggleState=ToggleState.CLOSE;
			break;
		case CLOSE:
			toggleState=ToggleState.OPEN;
			break;
		default:
			break;
		}
		
	}
	/**
	 * 开始菜单动画，点击的MenuItem放大消失，其他的缩小消失
	 * @param v
	 */
	private void menuItemAnim(View v){
		for (View item : outter_viewList) {
			AnimationSet animationSet=new AnimationSet(true);
			if(v.equals(item)){
				//放大动画
				Animation quitscale=new ScaleAnimation(1F, 4.0F,1F, 4.0F, Animation.RELATIVE_TO_SELF,
						0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
				Animation quitalpha=new AlphaAnimation(1F, 0F);
				quitscale.setDuration(duration);
				quitalpha.setDuration(duration);
				animationSet.addAnimation(quitscale);
				animationSet.addAnimation(quitalpha);
				
			}else{
				//缩小动画
				Animation quitscale=new ScaleAnimation(1F,0F, 1F, 0F, Animation.RELATIVE_TO_SELF,
						0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
				Animation quitalpha=new AlphaAnimation(1F, 0F);
				quitscale.setDuration(duration);
				quitalpha.setDuration(duration);
				animationSet.addAnimation(quitscale);
				animationSet.addAnimation(quitalpha);
			}
			animationSet.setFillAfter(true);
			item.startAnimation(animationSet);
			item.setVisibility(INVISIBLE);
			item.setClickable(false);
			toggleState=ToggleState.CLOSE;
		}
		
	}
	private void centerView_rotate(View v) {
		RotateAnimation animation=new RotateAnimation(0F, 270F,
				Animation.RELATIVE_TO_SELF, 0.5F,Animation.RELATIVE_TO_SELF, 0.5F);
		animation.setDuration(duration);
		animation.setFillAfter(true);
		v.startAnimation(animation);
	}
	public interface onItemlistener{
		public abstract void onItemlistener(View v);
	}
	public void setOnItemlistener(onItemlistener listener){
		this.listener=listener;
	}

}
