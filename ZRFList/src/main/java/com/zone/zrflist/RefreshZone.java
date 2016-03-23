package com.zone.zrflist;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.TextView;
public class RefreshZone extends ListView implements OnScrollListener {
	static final int NONE=0,PULL=1,RELESE=2,REFLASHING=3;
	private View header=null;
	private int headerHeight=0;
	private boolean haveHeight=false;
	private int length=0;
	private int firstVisibleItem;
	private int scrollState;
	private float startY=0F;
	private int Pull_State=NONE;
	private boolean isRefresh=false;
	private TextView tip;
	public RefreshZone(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RefreshZone(Context context) {
		this(context, null);
	}

	public RefreshZone(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		header=LayoutInflater.from(context).inflate(R.layout.header_layout, null);
		tip=(TextView) header.findViewById(R.id.tip);
		addHeaderView(header);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		this.scrollState=scrollState;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.firstVisibleItem=firstVisibleItem;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		System.err.println("宽高："+header.getHeight());
		if(header.getHeight()!=0&&!haveHeight){
			System.err.println("走了");
			headerHeight=header.getHeight();
			haveHeight=true;
			topPadding(-header.getHeight());
		}
	
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if(firstVisibleItem==0&&Pull_State==NONE){
				startY=ev.getY();
				isRefresh=true;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			pull(ev);
			break;
		case MotionEvent.ACTION_UP:
			refreshAdjustPosition(ev);
			isRefresh=false;
			break;

		default:
			break;
		}
		return super.onTouchEvent(ev);
	}

	private void pull(MotionEvent ev) {
		if (isRefresh) {
			length=(int)(ev.getY() - startY);
			switch (Pull_State) {
			case NONE:
				if (ev.getY() - startY> 0) {
					Pull_State = PULL;
					//TODO 切换PULL状态
				}
				break;
			case PULL:
				if (ev.getY() - startY >= (headerHeight + 30)) {
					Pull_State = RELESE;
					//TODO 切换RELESE动画
				}
				if(ev.getY() - startY <=0){
					Pull_State = NONE;
					//TODO 切换NONE状态
				}
				break;
			case RELESE:
				if (ev.getY() - startY < (headerHeight + 30)) {
					Pull_State = PULL;
					//TODO 切换PULL动画	
				}
				break;

			default:
				break;
			}
			stateAni();
		}
	}

	private void stateAni() {
		switch (Pull_State) {
		case NONE:
			topPadding(-headerHeight);
			break;
		case PULL:
			topPadding(length-headerHeight);
			tip.setText("下拉可以刷新！");
			break;
		case RELESE:
			topPadding(length-headerHeight);
			//TODO 开启动画
			tip.setText("释放刷新！");
			break;
		case REFLASHING:
			topPadding(0);
			tip.setText("正在刷新！");
			postDelayed(new Runnable() {
				@Override
				public void run() {
					refreshCompele();
				}
			},1000);
			break;

		default:
			break;
		}
	}

	private void refreshAdjustPosition(MotionEvent ev) {
		length=(int)(ev.getY() - startY);
		if(Pull_State==RELESE){
		
			//TODO 阻力恢复到view的位置
			
			//TODO 然后做动画  把状态弄成这个
			Pull_State=REFLASHING;
			stateAni();
			
		}
		if(Pull_State==PULL){
			Pull_State = NONE;
			//TODO 切换NONE状态
			stateAni();
		}
	}
	public void refreshCompele(){
		Pull_State = NONE;
		stateAni();
	}

	/**
	 * 设置header 布局 上边距；
	 * 
	 * @param topPadding
	 */
	private void topPadding(int topPadding) {
		header.setPadding(header.getPaddingLeft(), topPadding,header.getPaddingRight(), header.getPaddingBottom());
		header.invalidate();
	}

}
