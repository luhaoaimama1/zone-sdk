package com.zone.adapter.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zone.adapter.QuickRcvAdapter;

/**
 * Created by Administrator on 2016/3/27.
 */
public class ViewHolderWithRecHelper extends  android.support.v7.widget.RecyclerView.ViewHolder {
    public BaseAdapterHelperRcv baseAdapterHelperRcv;
    private ViewHolderWithRecHelper(View convertView) {
        super(convertView);
    }

    public static  ViewHolderWithRecHelper newInstance(Context context,ViewGroup parent, int viewType,QuickRcvAdapter adapter){
        View convertView = LayoutInflater.from(context).inflate(viewType, parent, false);
        ViewHolderWithRecHelper viewHolder = new ViewHolderWithRecHelper(convertView);
        viewHolder.baseAdapterHelperRcv=new BaseAdapterHelperRcv(context,convertView,parent,viewType,adapter,viewHolder);
        return viewHolder;
    }
}
