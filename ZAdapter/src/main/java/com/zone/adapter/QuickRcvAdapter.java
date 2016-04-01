package com.zone.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.zone.adapter.Helper.ViewHolderWithRecHelper;
import com.zone.adapter.callback.IAdapter;
import com.zone.adapter.loadmore.RecyclerOnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/26.
 */
public abstract class QuickRcvAdapter<T> extends RecyclerView.Adapter<ViewHolderWithRecHelper> implements IAdapter<T> {
    protected final List<T> data;
    protected final Context context;

    private boolean ani = false;
    protected IAdapter.OnItemClickListener onItemClickListener;
    protected IAdapter.OnItemLongClickListener onItemLongClickListener;

    private static  int HEADER_TYPE = -1000;
    private static  int FOOTER_TYPE = -2000;
    //限定一千个
    private List<View> mHeaderViews = new ArrayList<>();
    private List<View> mFooterViews = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager manager;



    public QuickRcvAdapter(Context context, List<T> data) {
        this(context, data, 1);

    }

    //第三个数虽然 没用 但是为了和quickadapter对外方法一致你懂得
    public QuickRcvAdapter(Context context, List<T> data, int viewTypeCount) {
        if (data == null)
            throw new IllegalArgumentException("data must be not null!");
        this.context = context;
        this.data = data;
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
            return ViewHolderWithRecHelper.newInstance(context, parent, viewType, this);
        }

    }
    private View setFullspan(View itemView){
        if(manager!=null&& manager instanceof  StaggeredGridLayoutManager){
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
            T item = data.get(getDataPosition(position));
            boolean itemChanged = (holder.baseAdapterHelperRcv.getAssociatedObject() == null || !holder.baseAdapterHelperRcv.getAssociatedObject().equals(item));
            //用之前关联 position object  保持数据的准确性
            holder.baseAdapterHelperRcv.setAssociatedObject(item, getDataPosition(position));
            fillData(holder.baseAdapterHelperRcv, item, itemChanged, getItemViewType(getDataPosition(position)));
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + mHeaderViews.size() + mFooterViews.size();
    }

    /**
     * 返回布局id
     */
    @Override
    public int getItemViewType(int position) {
        if(mHeaderViews.size()>1000||mFooterViews.size()>1000)
            throw new RuntimeException("header or footer  max size is 1000!");

        System.out.println("1");
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

    private int getDataPosition(int mapPosition) {
        return mapPosition - mHeaderViews.size();
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

    public int getHeaderViewsCount() {
        return mHeaderViews.size();
    }

    public int getFooterViewsCount() {
        return mFooterViews.size();
    }

    public Context getContext() {
        return context;
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

    public void setOnLoadMoreListener(RecyclerOnLoadMoreListener mRecyclerOnScrollListener){
        mRecyclerView.addOnScrollListener(mRecyclerOnScrollListener);
        mRecyclerOnScrollListener.associatedAdapter(this);
    }


    public IAdapter ani() {
        ani = true;
        return this;
    }

    private void closeAni() {
        ani = false;
    }

    public void add(T elem) {
        data.add(elem);
        if (ani)
            notifyItemInserted(data.size());
        else
            notifyDataSetChanged();
        closeAni();
    }

    public void add(int index, T elem) {
        data.add(index, elem);
        if (ani)
            notifyItemInserted(index);
        else
            notifyDataSetChanged();
        closeAni();
    }

    public void addAll(List<T> elem) {
        int index = data.size();
        data.addAll(elem);
        if (ani)
            notifyItemRangeInserted(index, elem.size());
        else
            notifyDataSetChanged();
        closeAni();
    }

    public void reverse(int fromPosition, int toPosition) {
        T temp = data.get(fromPosition);
        data.remove(fromPosition);
        data.set(toPosition, temp);
        if (ani)
            notifyItemMoved(fromPosition, toPosition);
        else
            notifyDataSetChanged();
        closeAni();
    }

    public void set(int index, T elem) {
        data.set(index, elem);
        if (ani)
            notifyItemChanged(index);
        else
            notifyDataSetChanged();
        closeAni();
    }

    public void set(T oldElem, T newElem) {
        int index = data.indexOf(oldElem);
        set(index, newElem);
        closeAni();
    }

    public void remove(T elem) {
        int index = data.indexOf(elem);
        data.remove(elem);
        if (ani)
            notifyItemRemoved(index);
        else
            notifyDataSetChanged();
        closeAni();
    }

    public void remove(int index) {
        data.remove(index);
        if (ani)
            notifyItemRemoved(index);
        else
            notifyDataSetChanged();
        closeAni();
    }

    public void replaceAll(List<T> elem) {
        data.clear();
        data.addAll(elem);
        notifyDataSetChanged();
        closeAni();
    }

    public boolean contains(T elem) {
        return data.contains(elem);
    }

    /**
     * Clear data list
     */
    public void clear() {
        int count = data.size();
        data.clear();
        if (ani)
            notifyItemRangeRemoved(0, count);
        else
            notifyDataSetChanged();
        closeAni();
    }

    @Override
    public void setOnItemClickListener(IAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void setOnItemLongClickListener(IAdapter.OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    @Override
    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }
}
