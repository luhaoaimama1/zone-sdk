package and.viewpage;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;

public abstract class ViewPager_CycleAdapter_Zone<T> extends PagerAdapter implements OnPageChangeListener {
	private List<T> data = null;
	private ViewPager viewPager;
	private OnPageChangeListener_Zone listener;
	

	public ViewPager_CycleAdapter_Zone(List<T> data,ViewPager viewPager,  OnPageChangeListener_Zone listener) {
		this.data = data;
		this.viewPager=viewPager;
		if(listener!=null){
			this.listener=listener;
			this.viewPager.setOnPageChangeListener(this);
		}
	}
	public ViewPager_CycleAdapter_Zone(List<T> data,ViewPager viewPager) {
		this(data,viewPager,null);
	}
	

	public int getSize(){
		
		return data.size();
	}
	/**
	 * 定义ViewPager的总长度的.
	 */
	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	/**
	 * 判断是否使用缓存, 如果true, 使用缓存 arg0 就是拖动的对象 arg1 进来的对象
	 */
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	/**
	 * 销毁对象 position 就是被销毁的对象的索引
	 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// 取得在集合中真正的位置.
		int reallyPosition = position % data.size();
		// 从viewpager中移除当前索引的对象
		viewPager.removeView(getViewByList(reallyPosition));
	}

	/**
	 * 加载item position 被加载的item的索引
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		int reallyPosition = position % data.size();
		View view=getViewByList(reallyPosition);
		container.addView(view);
		return view;
	}

	/**
	 * 通过list获得view
	 * @param position
	 * @return
	 */
	public abstract View getViewByList(int position);
	public interface OnPageChangeListener_Zone {
		public void onPageScrollStateChanged(int state);
		public void onPageScrolled(int position,float positionOffset, int positionOffsetPixels);
		public void onPageSelected(int position);
	}

	/**
	 * 当页面的滚动状态改变时回调 
	 * 0:代表状态滚动动画完成
	 */
	@Override
	public void onPageScrollStateChanged(int state) {
		listener.onPageScrollStateChanged(state);
	}

	/**
	 * 当滚动时回调
	 */
	@Override
	public void onPageScrolled(int position ,float positionOffset, int positionOffsetPixels) {
		int reallyPosition = position % data.size();
		listener.onPageScrolled(reallyPosition, positionOffset, positionOffsetPixels);
	}

	/**
	 * 当页面被选中时回调
	 */
	@Override
	public void onPageSelected(int position) {
		if(ViewPagerCircle.class.isInstance(viewPager)){
			//当翻页的时候 记得定时刷新
			((ViewPagerCircle)viewPager).againTiming();
		}
		int reallyPosition = position % data.size();
		listener.onPageSelected(reallyPosition);
	}
}
