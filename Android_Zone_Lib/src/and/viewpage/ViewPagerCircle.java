package and.viewpage;
import and.viewpage.ViewPager_CycleAdapter_Zone;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class ViewPagerCircle extends ViewPager {
	private Handler handler;
	private long delayMillis=5000;
	public ViewPagerCircle(Context context, AttributeSet attrs) {
		super(context, attrs);
		handler=new Handler();
	}

	public ViewPagerCircle(Context context) {
		this(context, null);
	}
	/**
	 * 
	 * @param adapter
	 * @param delayMillis  轮播 的切换时间
	 */
	public void setAdapter(PagerAdapter adapter,long delayMillis) {
		super.setAdapter(adapter);
		
		this.delayMillis=delayMillis;
		againTiming();
		if(ViewPager_CycleAdapter_Zone.class.isInstance(adapter)){
			ViewPager_CycleAdapter_Zone lin = (ViewPager_CycleAdapter_Zone)adapter;
			int listSize=lin.getSize();
			this.setCurrentItem(listSize*400);
		}
	}
	private Runnable run=new Runnable() {
		@Override
		public void run() {
				nextPage();
			}
		};
	public void nextPage(){
		setCurrentItem(getCurrentItem()+1);
	}
	public void previousPage(){
		setCurrentItem(getCurrentItem()-1);
	}
	/**
	 * 在adapter中调用
	 */
	public void againTiming(){
		handler.removeCallbacks(run);
		handler.postDelayed(run,delayMillis);
	}

	public long getDelayMillis() {
		return delayMillis;
	}

	public void setDelayMillis(long delayMillis) {
		this.delayMillis = delayMillis;
	}

}
