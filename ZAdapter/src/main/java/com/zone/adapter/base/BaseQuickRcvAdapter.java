package com.zone.adapter.base;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import com.zone.adapter.Helper.ViewHolderWithRecHelper;
import com.zone.adapter.callback.IAdapter;
import java.util.List;

/**
 * Created by Administrator on 2016/4/3.
 */
public abstract class BaseQuickRcvAdapter<T>   extends RecyclerView.Adapter<ViewHolderWithRecHelper> implements IAdapter<T> {
    protected final List<T> data;
    protected final Context context;
    private boolean ani = false;
    protected IAdapter.OnItemClickListener onItemClickListener;
    protected IAdapter.OnItemLongClickListener onItemLongClickListener;

    public BaseQuickRcvAdapter(Context context, List<T> data) {
        this(context, data, 1);

    }

    //第三个数虽然 没用 但是为了和quickadapter对外方法一致你懂得
    public BaseQuickRcvAdapter(Context context, List<T> data, int viewTypeCount) {
        if (data == null)
            throw new IllegalArgumentException("data must be not null!");
        this.context = context;
        this.data = data;
    }


    @Override
    public IAdapter ani() {
        ani = true;
        return this;
    }

    private void closeAni() {
        ani = false;
    }
    @Override
    public void add(T elem) {
        data.add(elem);
        if (ani)
            notifyItemInserted(data.size());
        else
            notifyDataSetChanged();
        closeAni();
    }
    @Override
    public void add(int index, T elem) {
        data.add(index, elem);
        if (ani)
            notifyItemInserted(index);
        else
            notifyDataSetChanged();
        closeAni();
    }
    @Override
    public void addAll(List<T> elem) {
        int index = data.size();
        data.addAll(elem);
        if (ani)
            notifyItemRangeInserted(index, elem.size());
        else
            notifyDataSetChanged();
        closeAni();
    }
    @Override
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
    @Override
    public void set(int index, T elem) {
        data.set(index, elem);
        if (ani)
            notifyItemChanged(index);
        else
            notifyDataSetChanged();
        closeAni();
    }
    @Override
    public void set(T oldElem, T newElem) {
        int index = data.indexOf(oldElem);
        set(index, newElem);
        closeAni();
    }
    @Override
    public void remove(T elem) {
        int index = data.indexOf(elem);
        data.remove(elem);
        if (ani)
            notifyItemRemoved(index);
        else
            notifyDataSetChanged();
        closeAni();
    }
    @Override
    public void remove(int index) {
        data.remove(index);
        if (ani)
            notifyItemRemoved(index);
        else
            notifyDataSetChanged();
        closeAni();
    }
    @Override
    public void replaceAll(List<T> elem) {
        data.clear();
        data.addAll(elem);
        notifyDataSetChanged();
        closeAni();
    }
    @Override
    public boolean contains(T elem) {
        return data.contains(elem);
    }

    /**
     * Clear data list
     */
    @Override
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
    public Context getContext() {
        return context;
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
