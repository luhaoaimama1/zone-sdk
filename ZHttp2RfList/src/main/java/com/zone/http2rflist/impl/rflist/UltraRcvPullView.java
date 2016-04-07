package com.zone.http2rflist.impl.rflist;

import android.content.Context;
import android.widget.ListView;

import com.zone.adapter.QuickRcvAdapter;
import com.zone.adapter.loadmore.callback.OnLoadMoreListener;
import com.zone.http2rflist.base.BasePullView;
import com.zone.zrflist.UltraControl;

import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2016/3/31.
 */
public abstract class UltraRcvPullView<E, A> extends BasePullView<PtrFrameLayout,ListView, QuickRcvAdapter,E, A> {
    private boolean loadMoreFail=false;
    public UltraRcvPullView(Context context,PtrFrameLayout pullView, ListView listView, QuickRcvAdapter adapter, List<E> data) {
        super(context,pullView, listView, adapter, data);
        pullViewSetListener();
    }
    private void pullViewSetListener() {
        UltraControl.init(context, pullView, new UltraControl.OnRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                baseNetworkQuest.firstPage();
                //因为到头  我会吧 加载监听移除 所以  首页还的弄回来
                adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        if (loadMoreFail)
                            baseNetworkQuest.start();//因为翻页了已经所以  保持原状即可
                        else
                            baseNetworkQuest.nextPage();
                    }
                });
            }
        });

    }
    @Override
    public void onRefreshComplete() {
        pullView.refreshComplete();
    }
    @Override
    public void onLoadMoreFail() {
        adapter.onLoadMoreFail();
        loadMoreFail=true;
    }

    @Override
    public void onLoadMoreComplete() {
        adapter.onLoadMoreComplete();
        loadMoreFail=false;
    }
    @Override
    public void lastPageRemoveOnLoadListener() {
        adapter.removeOnLoadMoreListener();
    }
    @Override
    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }
}
