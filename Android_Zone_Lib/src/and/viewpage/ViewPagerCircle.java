package and.viewpage;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class ViewPagerCircle extends ViewPager {
	private long delayMillis=3000;
	private boolean isTimeDelay=false;
	private int initCircle=200;
	private Handler handler;
	private PagerAdapterCycle adapter;
	private OnPageChangeListener mListener;
	public ViewPagerCircle(Context context, AttributeSet attrs) {
		super(context, attrs);
		handler=new Handler();
	}

	public ViewPagerCircle(Context context) {
		this(context, null);
	}
	/**
	 * @param adapter
	 */
	public void setAdapter(PagerAdapterCycle adapter) {
		setAdapter(adapter, 0);
	}
	/**
	 * @param adapter
	 */
	public void setAdapter(PagerAdapterCycle adapter,int offset) {
		super.setAdapter(adapter);
		this.adapter=adapter;
		setCurrentItem(adapter.getSize() * initCircle + offset);
		setOnPageChangeListener(null);
	}

	public void nextPage() {
		setCurrentItem(getCurrentItem() + 1);
	}

	public void previousPage() {
		setCurrentItem(getCurrentItem() - 1);
	}
	/**
	 * 在adapter中调用
	 */
	private void againTiming(){
		if (isTimeDelay) {
			handler.removeCallbacks(run);
			handler.postDelayed(run, delayMillis);
		}
	}
	private Runnable run=new Runnable() {
		@Override
		public void run() {
			nextPage();
		}
	};

	@Override
	public void setOnPageChangeListener(final OnPageChangeListener listener) {
		mListener=listener;
		if (adapter==null)
			throw new IllegalStateException("setadapter must be use before setOnPageChangeListener!");
			OnPageChangeListener listenerSet = new OnPageChangeListener() {
				@Override
				public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
					int reallyPosition = position % adapter.getSize();
					if (mListener!=null)
						mListener.onPageScrolled(reallyPosition, positionOffset, positionOffsetPixels);
				}

				@Override
				public void onPageSelected(int position) {
					againTiming();
					int reallyPosition = position % adapter.getSize();
					if (mListener!=null)
						mListener.onPageSelected(reallyPosition);
				}

				@Override
				public void onPageScrollStateChanged(int state) {
					if (mListener!=null)
						mListener.onPageScrollStateChanged(state);
				}
		};
		super.setOnPageChangeListener(listenerSet);
	}

	public long getDelayMillis() {
		return delayMillis;
	}

	public void setDelayMillis(long delayMillis) {
		this.delayMillis = delayMillis;
	}

	/**
	 * 开启轮播  并设置轮播时间
	 * @param delayMillis
	 */
	public void openTimeCircle(long delayMillis) {
		if (-1!=delayMillis)
			this.delayMillis = delayMillis;
		isTimeDelay=true;
		againTiming();
	}
	/**
	 * 开启轮播  轮播时间为默认时间
	 */
	public void openTimeCircle() {
		openTimeCircle(-1);
	}

}
