package com.zone.adapter.Helper;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.zone.adapter.LogConfig;
import com.zone.adapter.QuickRcvAdapter;

public class BaseAdapterHelperRcv extends AbHelper  {
    //获取位置有用
    private final ViewHolderWithRecHelper viewHolderWithRecHelper;

    public BaseAdapterHelperRcv(Context context, View convertView, final ViewGroup parent, final int layoutId, final QuickRcvAdapter adapter, final ViewHolderWithRecHelper viewHolderWithRecHelper) {
        super(context,convertView,parent,layoutId);
        this.viewHolderWithRecHelper=viewHolderWithRecHelper;
        if(adapter.getOnItemClickListener()!=null)
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (adapter.getOnItemClickListener() != null){
                        LogConfig.d("OnItemClick: position" + viewHolderWithRecHelper.getPosition());
                        adapter.getOnItemClickListener().onItemClick(parent, v, viewHolderWithRecHelper.getPosition(), -1);
                    }
                }
            });
        if(adapter.getOnItemLongClickListener()!=null)
            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (adapter.getOnItemLongClickListener() != null) {
                        LogConfig.d("OnItemLongClick: position" + viewHolderWithRecHelper.getPosition());
                        return adapter.getOnItemLongClickListener().onItemLongClick(parent, v, viewHolderWithRecHelper.getPosition(), -1);
                    }
                    return false;
                }
            });
    }

    //todo 这个是真的位置吧
    @Override
    public int getPosition() {
        return viewHolderWithRecHelper.getPosition();
    }
}
