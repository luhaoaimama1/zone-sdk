package com.zone.adapter.callback;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author Jack Tony
 * @date 2015/11/29
 * 通用的adapter必须实现的接口，作为方法名统一的一个规范
 */
public interface IAdapter<T> {

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     * @param itemChanged Whether or not the helper was bound to another object before.
     * @param layoutId 布局的id可以switch  0是默认值 单布局的可以不考虑
     */
     void fillData(Helper helper, T item, boolean itemChanged, int layoutId);

    /**
     * @param t list中的一条数据
     * @return mustbe返回布局id
     */
    int getItemLayoutId(T t, int position);

    void relatedList(Object listView);

    IAdapter ani();

    void add(T elem);

    void add(int index,T elem);

    void addAll(List<T> elem);

    void reverse(int fromPosition,int toPosition);

    void set(int index, T elem);

    void set(T oldElem, T newElem);

    void remove(T elem);

    void remove(int index);

    void replaceAll(List<T> elem);

    boolean contains(T elem);

    void clear();

    void notifyDataSetChanged();

    void notifyItemChanged(int position);


    void setOnItemClickListener(OnItemClickListener listener);

    void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener);

    OnItemClickListener getOnItemClickListener();

    OnItemLongClickListener getOnItemLongClickListener();

    interface OnItemClickListener {
        void onItemClick(ViewGroup parent, View view, int position, long id);
    }
    interface OnItemLongClickListener {
        boolean onItemLongClick(ViewGroup parent, View view, int position, long id);
    }


    //todo
    void addHeaderView(View v);
    void addFooterView(View v);

    boolean removeHeaderView(View v);
    boolean removeFooterView(View v);
}