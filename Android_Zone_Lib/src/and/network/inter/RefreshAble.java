package and.network.inter;

import android.view.View;

public interface RefreshAble {
	//完成刷新监听 与加载更多监听的 注册
	public abstract void init();
	public abstract void loadMore(View v);
	public abstract void onRefresh(View v);
	public abstract void onRefreshComplete();
	public abstract void loadMoreComplete();
}
