package com.zone.adapter.loadmore.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.zone.adapter.loadmore.ListOnLoadMoreListener;
import com.zone.adapter.loadmore.RecyclerOnLoadMoreListener;
import com.zone.adapter.loadmore.callback.ILoadMoreFrameLayout;

/**
 * Created by Administrator on 2016/4/1.
 */
public abstract class BaseLoadMoreFrameLayout extends FrameLayout implements ILoadMoreFrameLayout {
    public final LayoutInflater inflater;
    public final Object listener;

    /**
     * 构造器必须两个参数 因为我通过反射来完成的
     * @param context
     * @param listener
     */
    public BaseLoadMoreFrameLayout(Context context, Object listener) {
        super(context);
        inflater=LayoutInflater.from(context);
        if(listener instanceof ListOnLoadMoreListener ||listener instanceof RecyclerOnLoadMoreListener)
            this.listener=listener;
        else
            throw new IllegalArgumentException("listener must be ListOnLoadMoreListener or RecyclerOnLoadMoreListener");
    }

    @Override
    public void loading() {
        removeAllViews();
        inflater.inflate(getLoadingLayoutID(),this);

    }
    public abstract int getLoadingLayoutID();
    public abstract int getFailLayoutID();
    public abstract boolean getFailClickable();
    @Override
    public void fail() {
        removeAllViews();
        inflater.inflate(getFailLayoutID(), this);
        if (getFailClickable()) {
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener instanceof ListOnLoadMoreListener )
                        ((ListOnLoadMoreListener)listener).onLoadMore();
                    if(listener instanceof RecyclerOnLoadMoreListener )
                        ((RecyclerOnLoadMoreListener)listener).onLoadMore();
                }
            });
        }
    }
}
