//package and.network.pullview.utils;
//
//import android.view.View;
//import android.widget.BaseAdapter;
//import android.widget.ListView;
//
//import com.handmark.pulltorefresh.library.PullToRefreshBase;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
//import com.handmark.pulltorefresh.library.PullToRefreshListView;
//import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
///**
// * 如果需要加载的时候　显示footview即可以像list一样添加footview 也可以　添加　headerView 想公司用的一样　头部和list一起刷新
// * @author 123
// */
//public class RefreshUtils {
//	/**
//	 * 下拉到头即加载 如果没到头 则不可
//	 * @param listView
//	 * @param adapter
//	 * @param refreshListener
//	 * @param loadMoreListener
//	 */
//	public static  void initPullToRefreshListView(final PullToRefreshListView listView, BaseAdapter adapter,final PullToRefreshListener listener) {
//		listView.setAdapter(adapter);
//		listView.setMode(Mode.PULL_FROM_START);
//		if(listener==null)
//			throw new IllegalArgumentException("arg:PullToRefreshListener maybe　null!");
//		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
//
//			@Override
//			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//					listener.onRefresh(listView);
//			}
//		});
//		listView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
//
//			@Override
//			public void onLastItemVisible() {
//				listener.loadMore(listView);
//			}
//		});
//	}
//	public interface PullToRefreshListener{
//		public abstract void loadMore(View v);
//		public abstract void onRefresh(View v);
//
//	}
//}
