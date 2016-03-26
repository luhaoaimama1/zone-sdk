package and.base;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import and.utils.UiUtils;

public abstract class Pop_Zone extends PopupWindow {
	protected Activity activity;
	protected View mMenuView;
	private int layoutid,dismissViewId,showAtLocationViewId;
	private boolean bgVisibility=true;
	private int bgColor=0xb0000000;
	private Mode mode=null;
	public enum Mode{
		Fill,Wrap;
	}
	/**
	 * 
	 * @param layoutid
	 * @param mode 
	 * @param dismissViewId  pop布局中控件id viewrect范围之外 点击 即可dissming
	 */
	public void setPopContentView(int layoutid,Mode mode,int dismissViewId){
		this.layoutid=layoutid;
		this.dismissViewId=dismissViewId;
		if(mode==null)
			throw new NullPointerException("setMode may be null!");
		this.mode=mode;
	}
	/**
	 * 仅仅调用show()即可  
	 * <br>默认颜色　　是浅黑色</br>
	 * <br>没有showAtLocationViewId setLocation的view就是传入布局的</br>
	 * @param activity  在那个activity 弹出pop
	 */
	public Pop_Zone(Activity activity) {
		this(activity,-1);
	}
	/**
	 * 仅仅调用show()即可
	 * <br>默认颜色　　是浅黑色
	 * @param activity  在那个activity 弹出pop
	 * @param showAtLocationViewId
	 */
	public Pop_Zone(Activity activity, int showAtLocationViewId) {
		super(activity);
		this.activity = activity;
		this.showAtLocationViewId=showAtLocationViewId;
	}
	/**
	 * 动态的调用initPop(R.layout.alert_dialog, R.id.pop_layout); 
	 * @param layout 加载的自定义布局
	 * @param dismissViewId 在那个控件id rect范围之外 点击 即可dissming 
	 * <br>-1 则表示不需要此功能
	 */
	private void initPop(int layout, final int dismissViewId) {
		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(layout, null);
		// 设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		switch (mode) {
		case Fill:
			// 设置SelectPicPopupWindow弹出窗体的宽
			this.setWidth(LayoutParams.FILL_PARENT);
			// 设置SelectPicPopupWindow弹出窗体的高
			this.setHeight(LayoutParams.FILL_PARENT);
			break;
		case Wrap:
			// 设置SelectPicPopupWindow弹出窗体的宽
			this.setWidth(LayoutParams.WRAP_CONTENT);
			// 设置SelectPicPopupWindow弹出窗体的高
			this.setHeight(LayoutParams.WRAP_CONTENT);
			bgVisibility=false;
			break;

		default:
			break;
		}
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setTouchable(true);
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		if (bgVisibility) {
			// 设置SelectPicPopupWindow弹出窗体动画效果this.setAnimationStyle(R.style.AnimBottom);
			// 实例化一个ColorDrawable颜色为半透明
			ColorDrawable dw = new ColorDrawable(bgColor);
			// 设置SelectPicPopupWindow弹出窗体的背景
			setBackgroundDrawable(dw);
			// 设置layout在PopupWindow中显示的位置
		}else{
			//这样能让 pop 返回键 dimiss
			setBackgroundDrawable(new BitmapDrawable()); 
		}
		if(dismissViewId!=-1){
			// mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
			mMenuView.setOnTouchListener(new OnTouchListener() {

				public boolean onTouch(View v, MotionEvent event) {
					
					if(event.getAction() == MotionEvent.ACTION_DOWN){
						int x = (int) event.getX();
						int y = (int) event.getY();
						View view=mMenuView.findViewById(dismissViewId);
							int left=view.getLeft();
							int right=view.getRight();
							int top=view.getTop();
							int bottom=view.getBottom();
							Rect rect=new Rect(left,top,right,bottom);
							if(!rect.contains(x, y)){
								dismiss();
							}
					}
					return true;
				}
			});
		}
	}
	/**
	 * 这样就制定规范了
	 */
	public void show(){
		initPop(layoutid, dismissViewId);
		findView(mMenuView);
		initData();
		setListener();
		View view = null;
		if(showAtLocationViewId!=-1)
			view = activity.findViewById(showAtLocationViewId);
		else
			view= UiUtils.getRootView(activity);
		setLocation(view);
	}
	/**
	 * 通过父类中的mMenuView找到pop内的控件
	 * <br>例如：tv_pop_cancel=(TextView) mMenuView.findViewById(R.id.tv_pop_cancel);
	 * @param mMenuView 
	 */
	protected abstract  void findView(View mMenuView) ;
	protected abstract  void initData() ;
	protected abstract  void setListener() ;
	/**
	 * <br>也可以加动画 this.setAnimationStyle(R.style.PopSelectPicAnimation);
	 * <br>例子：this.showAtLocation(context.findViewById(R.id.main), Gravity.BOTTOM	| Gravity.CENTER_HORIZONTAL, 0, 0);
	 * <br>并可以更改pop的其他设置 
	 * @param view 
	 */
	protected abstract void setLocation(View view);
	
	
	
	/**
	 * 例子：构造器中调用     super下面即可  默认可见性为true
	 * setBgVisibility(false);
	 * @param bgVisibility
	 */
	protected void setBgVisibility(boolean bgVisibility) {
		this.bgVisibility=bgVisibility;
	}
	/**
	 * 同链接的一样   设置颜色即可   默认可见性为true
	 * {@link #setBgVisibility}
	 * @param bgColor
	 */
	protected void setBgColor(int bgColor) {
		this.bgColor=bgColor;
	}
}
