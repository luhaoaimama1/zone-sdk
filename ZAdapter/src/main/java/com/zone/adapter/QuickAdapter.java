package com.zone.adapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.zone.adapter.Helper.BaseAdapterHelper;
import com.zone.adapter.base.BaseQuickAdapter;
import java.util.List;
/**
 * Abstraction class of a BaseAdapter in which you only need
 * to provide the fillData() implementation.<br/>
 * Using the provided BaseAdapterHelper, your code is minimalist.
 * @param <T> The type of the items in the list.
 */
public abstract class QuickAdapter<T> extends BaseQuickAdapter<T> {
    protected static final String TAG = QuickAdapter.class.getSimpleName();
    private int firstLayoutId=-1;

    public QuickAdapter(Context context, List<T> data) {
        super(context, data, 1);
    }
    public QuickAdapter(Context context, List<T> data,int viewTypeCount) {
        super(context,data,viewTypeCount);
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
}
