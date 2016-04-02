package com.zone.adapter.Helper;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zone.adapter.QuickConfig;
import com.zone.adapter.QuickAdapter;

public class BaseAdapterHelper<T> extends AbHelper<T> {

    public BaseAdapterHelper(Context context, View convertView, final ViewGroup parent, int layoutId, final QuickAdapter adapter) {
        super(context, convertView,parent, layoutId);
        convertView.setTag(this);
        //todo　position假的  这回貌似是真的了 到时候测验就好了
        if(adapter.getOnItemClickListener()!=null)
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (adapter.getOnItemClickListener() != null){
                        QuickConfig.d("OnItemClick: position" + position);
                        adapter.getOnItemClickListener().onItemClick(parent, v, position, -1);
                    }

                }
            });
        if( adapter.getOnItemLongClickListener()!=null)
            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if ( adapter.getOnItemLongClickListener() != null) {
                        QuickConfig.d("OnItemLongClick: position" + position);
                        return adapter.getOnItemLongClickListener().onItemLongClick(parent, v, position, -1);
                    }
                    return false;
                }
            });
    }

    /** This method is package private and should only be used by QuickAdapter. */
    public static BaseAdapterHelper get(Context context, View convertView, final ViewGroup parent, int layoutId, final QuickAdapter adapter) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            BaseAdapterHelper result = new BaseAdapterHelper(context,convertView, parent, layoutId,adapter);
            return result;
        }
        // Retrieve the existing helper and update its position
        BaseAdapterHelper existingHelper = (BaseAdapterHelper) convertView.getTag();
        return existingHelper;
    }

    /**
     * Retrieve the overall position of the data in the list.
     * @throws IllegalArgumentException If the position hasn't been set at the construction of the this helper.
     */
    public int getPosition() {
        if (position == -1)
            throw new IllegalStateException("Use Helper constructor " +
                    "with position if you need to retrieve the position.");
        return position;
    }



}
