package com.zone.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.zone.adapter.Helper.ViewHolderWithRecHelper;
import com.zone.adapter.base.BaseQuickRcvAdapter;
import com.zone.adapter.base.Header2FooterRcvAdapter;
import com.zone.adapter.callback.IAdapter;
import com.zone.adapter.loadmore.RecyclerOnLoadMoreListener;
import com.zone.adapter.loadmore.callback.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/26.
 */
public abstract class QuickRcvAdapter<T> extends Header2FooterRcvAdapter<T> {

    public QuickRcvAdapter(Context context, List<T> data) {
        super(context, data, 1);

    }

    //第三个数虽然 没用 但是为了和quickadapter对外方法一致你懂得
    public QuickRcvAdapter(Context context, List<T> data, int viewTypeCount) {
        super(context, data, viewTypeCount);
    }

    @Override
    public void onBindContentViewHolder(ViewHolderWithRecHelper holder, int position) {
            T item = data.get(position);
            boolean itemChanged = (holder.baseAdapterHelperRcv.getData() == null || !holder.baseAdapterHelperRcv.getData().equals(item));
            //用之前关联 position object  保持数据的准确性
            holder.baseAdapterHelperRcv.setData(item, position);
            fillData(holder.baseAdapterHelperRcv, item, itemChanged, getItemViewType(position));
    }

    @Override
    public ViewHolderWithRecHelper onCreateContentView(ViewGroup parent, int viewType) {
        return ViewHolderWithRecHelper.newInstance(context, parent, viewType, this);
    }
}
