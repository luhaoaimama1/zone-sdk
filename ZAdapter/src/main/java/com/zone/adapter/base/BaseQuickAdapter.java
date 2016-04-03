package com.zone.adapter.base;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.zone.adapter.callback.IAdapter;
import com.zone.adapter.loadmore.ListOnLoadMoreListener;
import com.zone.adapter.loadmore.callback.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/3.
 */
public abstract class BaseQuickAdapter<T>  extends BaseAdapter implements IAdapter<T> {

    protected final List<T> data;
    protected final int viewTypeCount;
    protected final Context context;

    protected IAdapter.OnItemClickListener  onItemClickListener;
    protected IAdapter.OnItemLongClickListener onItemLongClickListener;
    private ListView listView;
    //限定一千个
    private List<View> mHeaderViews = new ArrayList<>();
    private List<View> mFooterViews = new ArrayList<>();
    private ListOnLoadMoreListener mListOnLoadMoreListener;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     * @param context     The context.
     * @param data        A new list is created out of this one to avoid mutable list
     */
    public BaseQuickAdapter(Context context, List<T> data) {
        this(context,data,1);
    }
    public BaseQuickAdapter(Context context, List<T> data,int viewTypeCount) {
        if(data==null)
            throw new IllegalArgumentException("data must be not null!");
        this.data = data ;
        this.context = context;
        this.viewTypeCount = viewTypeCount;
    }
    //todo  不支持~
    @Override
    public IAdapter ani(){
        return this;
    }

    @Override
    public void add(T elem) {
        data.add(elem);
        notifyDataSetChanged();
    }
    @Override
    public void add(int index,T elem) {
        data.add(index, elem);
        notifyDataSetChanged();
    }
    @Override
    public void addAll(List<T> elem) {
        data.addAll(elem);
        notifyDataSetChanged();
    }
    @Override
    public void reverse(int fromPosition,int toPosition){
        T temp=data.get(fromPosition);
        data.remove(fromPosition);
        data.set(toPosition, temp);
        notifyDataSetChanged();
    }
    @Override
    public void set(T oldElem, T newElem) {
        set(data.indexOf(oldElem), newElem);
    }
    @Override
    public void set(int index, T elem) {
        data.set(index, elem);
        notifyDataSetChanged();
    }
    @Override
    public void remove(T elem) {
        data.remove(elem);
        notifyDataSetChanged();
    }
    @Override
    public void remove(int index) {
        data.remove(index);
        notifyDataSetChanged();
    }


    @Override
    public void replaceAll(List<T> elem) {
        data.clear();
        data.addAll(elem);
        notifyDataSetChanged();
    }
    @Override
    public boolean contains(T elem) {
        return data.contains(elem);
    }

    /** Clear data list */
    @Override
    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    @Override
    public void notifyItemChanged(int position) {
        //todo 暂时不支持~
        notifyDataSetChanged();
    }


    @Override
    public void setOnItemClickListener(IAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener=onItemClickListener;
    }

    @Override
    public void setOnItemLongClickListener(IAdapter.OnItemLongClickListener  onItemLongClickListener) {
        this.onItemLongClickListener=onItemLongClickListener;
    }

    @Override
    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    @Override
    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    @Override
    public void relatedList(Object listView) {
        if(listView instanceof ListView){
            this.listView=((ListView) listView);
            this.listView.setAdapter(this);
        }else if(listView instanceof GridView){
            ((GridView)listView).setAdapter(this);
        }else
            throw new IllegalArgumentException("listView must be ListView!");
    }

    @Override
    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        if (listener!=null)
            if (listView!=null) {
                listView.setOnScrollListener(mListOnLoadMoreListener=new ListOnLoadMoreListener(listener));
                mListOnLoadMoreListener.associatedAdapter(this);
            }else
                throw new IllegalStateException(" must  use method :relatedList before this method ");
    }

    @Override
    public void onLoadMoreComplete() {
        if(mListOnLoadMoreListener!=null)
            mListOnLoadMoreListener.onLoadMoreComplete();
    }

    @Override
    public void removeOnLoadMoreListener() {
        if(mListOnLoadMoreListener!=null)
            mListOnLoadMoreListener.onLoadMoreComplete2RemoveFooterView();
        listView.setOnScrollListener(null);
    }

    @Override
    public void onLoadMoreFail() {
        if(mListOnLoadMoreListener!=null)
            mListOnLoadMoreListener.onLoadMoreFail();
    }
    @Override
    public Context getContext() {
        return context;
    }


    @Override
    public void addHeaderView(View header) {
        if (!mHeaderViews.contains(header)) {
            listView.addHeaderView(header);
            mHeaderViews.add(header);
        }
    }

    @Override
    public void addFooterView(View footer) {
        if (!mFooterViews.contains(footer)) {
            listView.addFooterView(footer);
            mFooterViews.add(footer);
        }
    }
    @Override
    public int getHeaderViewsCount() {
        return mHeaderViews.size();
    }

    @Override
    public int getFooterViewsCount() {
        return mFooterViews.size();
    }
    @Override
    public void removeHeaderView(View header) {
        listView.removeHeaderView(header);
    }

    @Override
    public void removeFooterView(View footer) {
        listView.removeFooterView(footer);
    }
}
