package com.zone.adapter.base;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.zone.adapter.Helper.ViewHolderWithRecHelper;
import com.zone.adapter.QuickConfig;
import com.zone.adapter.loadmore.RecyclerOnLoadMoreListener;
import com.zone.adapter.loadmore.callback.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/3.
 */
public abstract class Header2FooterRcvAdapter<T> extends  BaseQuickRcvAdapter<T>{
    private static  int HEADER_TYPE = -1000;
    private static  int FOOTER_TYPE = -2000;
    //限定一千个
    private List<View> mHeaderViews = new ArrayList<>();
    private List<View> mFooterViews = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager manager;
    private RecyclerOnLoadMoreListener mRecyclerOnLoadMoreListener;

    public Header2FooterRcvAdapter(Context context, List<T> data) {
        super(context, data);
    }

    public Header2FooterRcvAdapter(Context context, List<T> data, int viewTypeCount) {
        super(context, data, viewTypeCount);
    }

    @Override
    public ViewHolderWithRecHelper onCreateViewHolder(final ViewGroup parent, int viewType) {
        if (viewType <=HEADER_TYPE&&viewType > HEADER_TYPE-1000){
            QuickConfig.e("position 4:" + viewType);
            return new ViewHolderWithRecHelper(setFullspan(mHeaderViews.get(HEADER_TYPE - viewType)));
        }else if (viewType <= FOOTER_TYPE && viewType > FOOTER_TYPE-1000){
            QuickConfig.e("position 5:" + viewType);
            return new ViewHolderWithRecHelper(setFullspan(mFooterViews.get(FOOTER_TYPE - viewType)));
        }else{
            //viewType就是  getItemViewType(int position)的id了
            QuickConfig.e("position 6:" + viewType + " \t HEADER_TYPE-1000" + (HEADER_TYPE - 1000) + " \t HEADER_TYPE" + HEADER_TYPE);
            return  onCreateContentView(parent, viewType);
        }

    }
    private View setFullspan(View itemView){
        if(manager!=null&& manager instanceof StaggeredGridLayoutManager){
            StaggeredGridLayoutManager.LayoutParams params = new StaggeredGridLayoutManager.LayoutParams(
                    StaggeredGridLayoutManager.LayoutParams.MATCH_PARENT,
                    StaggeredGridLayoutManager.LayoutParams.MATCH_PARENT);
            params.setFullSpan(true);
            itemView.setLayoutParams(params);
        }
        if(manager!=null&& manager instanceof LinearLayoutManager){
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            itemView.setLayoutParams(params);
        }
        return itemView;
    }

    @Override
    public void onBindViewHolder(ViewHolderWithRecHelper holder, int position) {
        if (position >= mHeaderViews.size() && position < mHeaderViews.size() + data.size()) {
            onBindContentViewHolder(holder, getDataPosition(position));
        }
    }
    private int getDataPosition(int mapPosition) {
        return mapPosition - mHeaderViews.size();
    }

    @Override
    public int getItemCount() {
        return data.size()+ mHeaderViews.size() + mFooterViews.size();
    }
        /**
     * 返回布局id
     */
    @Override
    public int getItemViewType(int position) {
        if(mHeaderViews.size()>1000||mFooterViews.size()>1000)
            throw new RuntimeException("header or footer  max size is 1000!");

        if (position < getHeaderViewsCount()){
            QuickConfig.e("position 1:" + position);
            return HEADER_TYPE - position;
        } else if (position >= getHeaderViewsCount() && position < getHeaderViewsCount() + data.size()){
            QuickConfig.e("position 2:" + position);
            return getItemLayoutId(data.get(getDataPosition(position)), getDataPosition(position));
        }else{
            QuickConfig.e("position 3:" + position);
            return FOOTER_TYPE - (position-getHeaderViewsCount()-data.size());
        }
    }


    @Override
    public void relatedList(Object listView) {
        if (listView instanceof RecyclerView){
            mRecyclerView=(RecyclerView) listView;
            //todo 添加footerView的时候也需要调用这个  因为我那个是一直添加移除的过程   那个obser
            gridSetLookup();
            mRecyclerView.setAdapter(this);
        }
        else
            throw new IllegalArgumentException("listView must be RecyclerView!");
    }
    private void gridSetLookup(){
        if(mRecyclerView==null)
            throw new RuntimeException("method :relatedList is used after  addHeaderView or addFooterView");
        manager = mRecyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager){
            ((GridLayoutManager) manager).setSpanSizeLookup(new  GridLayoutManager.SpanSizeLookup(){
                @Override
                public int getSpanSize(int position) {
                    if(position >= getHeaderViewsCount() && position < getHeaderViewsCount() + data.size())
                        return 1;
                    else
                        return ((GridLayoutManager) manager).getSpanCount();
                }
            });
            mRecyclerView.setLayoutManager(manager);
        }
    }


    @Override
    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        if (listener!=null)
            if(mRecyclerView!=null){
                mRecyclerView.addOnScrollListener(mRecyclerOnLoadMoreListener=new RecyclerOnLoadMoreListener(listener));
                mRecyclerOnLoadMoreListener.associatedAdapter(this);
            }else
                throw new IllegalStateException(" must  use method :relatedList before this method ");
    }
    @Override
    public void onLoadMoreComplete() {
        if (mRecyclerOnLoadMoreListener!=null)
            mRecyclerOnLoadMoreListener.onLoadMoreComplete();
    }


    @Override
    public void removeOnLoadMoreListener() {
        if(mRecyclerOnLoadMoreListener!=null)
            mRecyclerOnLoadMoreListener.onLoadMoreComplete2RemoveFooterView();
        mRecyclerView.removeOnScrollListener(mRecyclerOnLoadMoreListener);
    }
    @Override
    public void onLoadMoreFail() {
        if (mRecyclerOnLoadMoreListener!=null)
            mRecyclerOnLoadMoreListener.onLoadMoreFail();
    }

    @Override
    public void addHeaderView(View header) {
        if (header == null)
            throw new RuntimeException("header is null");
        if (!mHeaderViews.contains(header)) {
            mHeaderViews.add(header);
            this.notifyDataSetChanged();
        }
    }

    @Override
    public void addFooterView(View footer) {
        if (footer == null)
            throw new RuntimeException("footer is null");
        if (!mFooterViews.contains(footer)) {
            mFooterViews.add(footer);
            this.notifyDataSetChanged();
        }
    }

    @Override
    public void removeHeaderView(View view) {
        mHeaderViews.remove(view);
        this.notifyDataSetChanged();
    }

    @Override
    public void removeFooterView(View view) {
        mFooterViews.remove(view);
        this.notifyDataSetChanged();
    }

    @Override
    public int getHeaderViewsCount() {
        return mHeaderViews.size();
    }
    @Override
    public int getFooterViewsCount() {
        return mFooterViews.size();
    }

    public abstract ViewHolderWithRecHelper onCreateContentView(ViewGroup parent,int viewType);//创建中间内容View

    public abstract void onBindContentViewHolder(ViewHolderWithRecHelper holder, int position) ;

}
