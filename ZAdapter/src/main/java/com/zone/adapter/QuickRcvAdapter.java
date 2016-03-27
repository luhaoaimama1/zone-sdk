package com.zone.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.zone.adapter.Helper.ViewHolderWithRecHelper;
import com.zone.adapter.callback.IAdapter;
import java.util.List;

/**
 * Created by Administrator on 2016/3/26.
 */
public abstract class QuickRcvAdapter<T> extends RecyclerView.Adapter<ViewHolderWithRecHelper> implements IAdapter<T> {
    protected final List<T> data;
    protected final Context context;

    private boolean ani =false;
    protected IAdapter.OnItemClickListener onItemClickListener;
    protected IAdapter.OnItemLongClickListener onItemLongClickListener;

    public QuickRcvAdapter(Context context,List<T> data) {
        this(context,data,1);
    }
    //第三个数虽然 没用 但是为了和quickadapter对外方法一致你懂得
    public QuickRcvAdapter(Context context,List<T> data,int viewTypeCount) {
        if(data==null)
            throw new IllegalArgumentException("data must be not null!");
        this.context = context;
        this.data = data;
    }
    @Override
    public ViewHolderWithRecHelper onCreateViewHolder(final ViewGroup parent, int viewType) {
        //viewType就是  getItemViewType(int position)的id了
        return   ViewHolderWithRecHelper.newInstance(context,parent,viewType,this);
    }

    @Override
    public void onBindViewHolder(ViewHolderWithRecHelper holder, int position) {
        T item=data.get(position);
        boolean itemChanged =(holder.baseAdapterHelperRcv.getAssociatedObject() == null || !holder.baseAdapterHelperRcv.getAssociatedObject().equals(item));
        //用之前关联 position object  保持数据的准确性
        holder.baseAdapterHelperRcv.setAssociatedObject(item,position);
        fillData(holder.baseAdapterHelperRcv, item, itemChanged, getItemViewType(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * 返回布局id
     */
    @Override
    public int getItemViewType(int position) {
//         super.getItemViewType(position);
        return getItemLayoutId(data.get(position), position);
    }

    @Override
    public void relatedList(Object listView) {
        if(listView instanceof RecyclerView)
           ((RecyclerView) listView).setAdapter(this);
        else
            throw new IllegalArgumentException("listView must be RecyclerView!");
    }
    public IAdapter ani(){
        ani =true;
        return this;
    }

    private  void closeAni(){
        ani =false;
    }

    public void add(T elem) {
        data.add(elem);
        if (ani)
            notifyItemInserted(data.size());
        else
            notifyDataSetChanged();
        closeAni();
    }
    public void add(int index,T elem) {
        data.add(index,elem);
        if (ani)
            notifyItemInserted(index);
        else
            notifyDataSetChanged();
        closeAni();
    }
    public void addAll(List<T> elem) {
        int index=data.size();
        data.addAll(elem);
        if (ani)
            notifyItemRangeInserted(index, elem.size());
        else
            notifyDataSetChanged();
        closeAni();
    }
    public void reverse(int fromPosition,int toPosition){
        T temp=data.get(fromPosition);
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
        int index=data.indexOf(oldElem);
        set(index, newElem);
        closeAni();
    }
    public void remove(T elem) {
        int index=data.indexOf(elem);
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
        int count=data.size();
        data.clear();
        if (ani)
            notifyItemRangeRemoved(0, count);
        else
            notifyDataSetChanged();
        closeAni();
    }

    @Override
    public void setOnItemClickListener(IAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener=onItemClickListener;
    }

    @Override
    public void setOnItemLongClickListener(IAdapter.OnItemLongClickListener onItemLongClickListener) {
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
}
