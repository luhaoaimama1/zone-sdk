package com.zone.view.ninegridview;

import android.content.Context;
import android.view.View;
import java.util.List;
public abstract class ZGridViewAdapter<T> {

    public final List<T> list;
    protected ZGridView gvz;

    public ZGridViewAdapter(List<T> list) {
        this.list = list;
    }

    public void onItemImageClick(Context context, int index, T data) {
    };

    public void onItemImageLongClick(Context context, int index, T data) {
    };

    public abstract View getView(Context context, int index);

    public void setGridViewZone(ZGridView gvz) {
        this.gvz = gvz;
    }

    public void notifyDataSetChanged() {
        if (gvz != null)
            gvz.setAdapter(this);
    }
}