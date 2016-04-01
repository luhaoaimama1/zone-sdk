package com.zone.http2rflist.impl.rflist;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.zone.http2rflist.BasePullView;
import com.zone.zrflist.UltraControl;
import java.util.List;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2016/3/31.
 */
public abstract class UltraPullView<E, A> extends BasePullView<PtrFrameLayout,ListView, BaseAdapter,E, A> {
    boolean loadMoreOk=true;
    public UltraPullView(PtrFrameLayout pullView, ListView listView, BaseAdapter adapter, List<E> data) {
        super(pullView, listView, adapter, data);
        pullViewSetListener();
    }
    private void pullViewSetListener() {
        UltraControl.init(baseNetworkQuest.context, pullView, new UltraControl.OnRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                baseNetworkQuest.firstPage();
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            int scrollState;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.scrollState = scrollState;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (scrollState != AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
                        if (loadMoreOk) {
                            System.out.println("上啦加载了~~~");
                            baseNetworkQuest.nextPage();
                            loadMoreOk = false;
                        }
                    }
                }
            }
        });
    }
    @Override
    public void onRefreshComplete() {
        pullView.refreshComplete();
    }

    @Override
    public void onloadMoreComplete() {
        loadMoreOk=true;
    }

    @Override
    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

}
