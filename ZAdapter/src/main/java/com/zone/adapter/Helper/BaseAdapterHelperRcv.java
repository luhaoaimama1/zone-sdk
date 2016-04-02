package com.zone.adapter.Helper;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.zone.adapter.QuickConfig;
import com.zone.adapter.QuickRcvAdapter;

public class BaseAdapterHelperRcv<T> extends AbHelper<T>  {
    //获取位置有用
    private final ViewHolderWithRecHelper viewHolderWithRecHelper;

    private final QuickRcvAdapter adapter;
    public BaseAdapterHelperRcv(Context context, View convertView, final ViewGroup parent, final int layoutId, final QuickRcvAdapter adapter, final ViewHolderWithRecHelper viewHolderWithRecHelper) {
        super(context,convertView,parent,layoutId);
        this.viewHolderWithRecHelper=viewHolderWithRecHelper;
        this.adapter=adapter;
        if(adapter.getOnItemClickListener()!=null)
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (adapter.getOnItemClickListener() != null){
                        QuickConfig.d("OnItemClick: position" + (viewHolderWithRecHelper.getLayoutPosition() - adapter.getHeaderViewsCount()));
                        adapter.getOnItemClickListener().onItemClick(parent, v, viewHolderWithRecHelper.getLayoutPosition()-adapter.getHeaderViewsCount(), -1);
                    }
                }
            });
        if(adapter.getOnItemLongClickListener()!=null)
            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (adapter.getOnItemLongClickListener() != null) {
                        QuickConfig.d("OnItemLongClick: position" + (viewHolderWithRecHelper.getLayoutPosition() - adapter.getHeaderViewsCount()));
                        return adapter.getOnItemLongClickListener().onItemLongClick(parent, v, viewHolderWithRecHelper.getLayoutPosition()-adapter.getHeaderViewsCount(), -1);
                    }
                    return false;
                }
            });
    }

    @Override
    public int getPosition() {
        return viewHolderWithRecHelper.getLayoutPosition()-adapter.getHeaderViewsCount();
    }
}
