package com.zone.adapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import com.zone.adapter.Helper.BaseAdapterHelper;
import com.zone.adapter.callback.IAdapter;
import com.zone.adapter.loadmore.ListOnLoadMoreListener;
import com.zone.adapter.loadmore.callback.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;
/**
 * Abstraction class of a BaseAdapter in which you only need
 * to provide the fillData() implementation.<br/>
 * Using the provided BaseAdapterHelper, your code is minimalist.
 * @param <T> The type of the items in the list.
 */
public abstract class QuickAdapter<T> extends BaseAdapter implements IAdapter<T> {
    protected final List<T> data;
    protected final int viewTypeCount;
    protected static final String TAG = QuickAdapter.class.getSimpleName();
    protected final Context context;

    protected IAdapter.OnItemClickListener  onItemClickListener;
    protected IAdapter.OnItemLongClickListener onItemLongClickListener;
    private int firstLayoutId=-1;
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
    public QuickAdapter(Context context, List<T> data) {
        this(context,data,1);
    }
    public QuickAdapter(Context context, List<T> data,int viewTypeCount) {
        if(data==null)
            throw new IllegalArgumentException("data must be not null!");
        this.data = data ;
        this.context = context;
        this.viewTypeCount = viewTypeCount;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int position) {
        if (position >= data.size()) return null;
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        final BaseAdapterHelper<T> helper =new BaseAdapterHelper(context,convertView,parent, getItemViewType(position), this);
        BaseAdapterHelper<T> helper =BaseAdapterHelper.get(context, convertView, parent, getItemViewType(position), this);
        T item = getItem(position);
        boolean itemChanged =(helper.getData() == null || !helper.getData().equals(item));
        //用之前关联 position object  保持数据的准确性
        helper.setData(item, position);
        //多布局怎么办
        fillData(helper, item, itemChanged, getItemViewType(position));
        return helper.getView();
    }

    /**
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        int resultId=getItemLayoutId(data.get(position), position);
        if(firstLayoutId==-1)
            firstLayoutId=resultId;
        else
            if(firstLayoutId!=resultId)
                throw new IllegalStateException("must be set viewTypeCount");
        return resultId;
    }

    @Override
    public int getViewTypeCount() {
        //毕竟默认值就是1
        return viewTypeCount;
    }

    /**
     * 如果列表的一项item是separator（充当分隔项目，跟其他item项一样，也可以不一样，但是无法进行点击），返回true，
     * 也就是可以点击，并接收响应事件。如果此时position处的item是separator的话，返回false，也就无法响应点击或触摸事件，
     * 此项目是不可以点击的，表现形式为点了没任何反应，可以充当一个列表中的分隔，当然可以自定义这个分隔项的布局
     * @param position
     * @return
     */
    @Override
    public boolean isEnabled(int position) {
        return position < data.size();
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
