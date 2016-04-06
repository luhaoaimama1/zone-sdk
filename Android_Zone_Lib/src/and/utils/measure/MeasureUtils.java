package and.utils.measure;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
/**
 * @author Zone
 */
public class MeasureUtils {
	public enum ListenerState {
		MEASURE_REMOVE, MEASURE_CONTINUE
	}
	public interface OnMeasureListener{
		/**
		 * 绘画完毕  即布局已经画好  什么值都可以得到了
		 * @param v
		 * @param viewWidth
		 * @param viewHeight
		 */
		public void measureResult(View v,int viewWidth,int viewHeight);
	}
	/**
	 * <br>调用时机：可见性 或者 布局状态改变的时候 如果view背景换了 那么一定invalited那么布局状态一定改变了
	 * <br>Register a callback to be invoked when the global layout state or the visibility of views within the view tree changes
	 * @param v
	 * @param gs  枚举表示 测量后是否移除此监听 目的是：为了防止被多次调用 (用完就移除     除非特殊一直想监听)
	 */
	public static void measure(final View v, final ListenerState gs, final OnMeasureListener oml){
		ViewTreeObserver  vto = v.getViewTreeObserver(); 
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				int height = v.getMeasuredHeight();
				int width = v.getMeasuredWidth();
				if(oml!=null){
					oml.measureResult(v,width, height);
				}
				switch (gs) {
				case MEASURE_REMOVE:
					v.getViewTreeObserver().removeGlobalOnLayoutListener(this);
					break;
				case MEASURE_CONTINUE:
					break;
				default:
					break;
				}
			}
		});
	}
}
