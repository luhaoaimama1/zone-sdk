package com.zone.adapter;

import android.view.View;

import com.zone.adapter.callback.IAdapter;
import com.zone.adapter.loadmore.callback.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/3.
 */
public class QuickManager {
    private List<View> mHeaderViews = new ArrayList<>();
    private List<View> mFooterViews = new ArrayList<>();
    private Object listView;
    private OnLoadMoreListener mOnLoadMoreListener;
    private IAdapter.OnItemClickListener mOnItemClickListener;
    private IAdapter.OnItemLongClickListener mOnItemLongClickListener;
    private IAdapter mIAdapter;

    private QuickManager() {
    }
      
    public static QuickManager with(IAdapter mIAdapter,Object listView){
        if(mIAdapter==null||listView==null)
            throw new IllegalArgumentException("mIAdapter or listView must be not null!");
        return new QuickManager().setIAdapter2ListView(mIAdapter, listView);
    }
    private QuickManager setIAdapter2ListView(IAdapter mIAdapter,Object listView){
        this.mIAdapter=mIAdapter;
        this.listView=listView;
        return this;
    }
    public QuickManager addHeaderView(View headerView){
        if (!mHeaderViews.contains(headerView))
            mHeaderViews.add(headerView);
        return this;
    }
    public QuickManager addFooterView(View footerView){
        if (!mFooterViews.contains(footerView))
            mFooterViews.add(footerView);
        return this;
    }
    public QuickManager setOnItemClickListener(IAdapter.OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener=mOnItemClickListener;
        return this;
    }
    public QuickManager setOnItemLongClickListener(IAdapter.OnItemLongClickListener mOnItemLongClickListener){
        this.mOnItemLongClickListener=mOnItemLongClickListener;
        return this;
    }
    public QuickManager setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener){
        this.mOnLoadMoreListener=mOnLoadMoreListener;
        return this;
    }
    //书写顺序
    public QuickManager build(){
        mIAdapter.relatedList(listView);

        for (View mHeaderView : mHeaderViews)
            mIAdapter.addHeaderView(mHeaderView);
        for (View mFooterView : mFooterViews)
            mIAdapter.addFooterView(mFooterView);

        mIAdapter.setOnLoadMoreListener(mOnLoadMoreListener);
        mIAdapter.setOnItemClickListener(mOnItemClickListener);
        mIAdapter.setOnItemLongClickListener(mOnItemLongClickListener);
        return new QuickManager();
    }
}
