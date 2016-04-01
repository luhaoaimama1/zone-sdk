package com.zone.adapter.loadmore;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import com.zone.adapter.QuickConfig;
import com.zone.adapter.QuickRcvAdapter;
import com.zone.adapter.loadmore.callback.ILoadMoreFrameLayout;
import com.zone.adapter.loadmore.callback.OnLoadMoreListener;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class RecyclerOnLoadMoreListener extends RecyclerView.OnScrollListener  {

    private final OnLoadMoreListener listener;
    /**
     * 当前RecyclerView类型
     */
    protected LayoutManagerType layoutManagerType;

    /**
     * 最后一个的位置
     */
    private int[] lastPositions;

    /**
     * 最后一个可见的item的位置
     */
    private int lastVisibleItemPosition;

    /**
     * 当前滑动的状态
     */
    private int currentScrollState = 0;

    /**
     * 判断方向 是否向下
     */
    private int dy=0;
    private boolean fullScreen=false;

    /**
     * 三种状态： 刷新中，完成，失败
     */
    private LoadingType mLoadingType=LoadingType.COMPLETE;//默认完成的
    private QuickRcvAdapter mQuickRcvAdapter;
    private View iLoadFooterView;
    private RecyclerView recyclerView;

    public RecyclerOnLoadMoreListener(OnLoadMoreListener listener) {
        this.listener = listener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        this.dy=dy;
        if(dy!=0)
            fullScreen=true;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if (layoutManagerType == null) {
            if (layoutManager instanceof LinearLayoutManager) {
                layoutManagerType = LayoutManagerType.LinearLayout;
            } else if (layoutManager instanceof GridLayoutManager) {
                layoutManagerType = LayoutManagerType.GridLayout;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                layoutManagerType = LayoutManagerType.StaggeredGridLayout;
            } else {
                throw new RuntimeException("Unsupported LayoutManager used. Valid ones " +
                        "are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
            }
        }

        switch (layoutManagerType) {
            case LinearLayout:
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case GridLayout:
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case StaggeredGridLayout:
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                if (lastPositions == null) {
                    lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                lastVisibleItemPosition = findMax(lastPositions);
                break;
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        this.recyclerView=recyclerView;
        currentScrollState = newState;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        if (mLoadingType!=LoadingType.LOADING
                && currentScrollState == RecyclerView.SCROLL_STATE_IDLE
                &&fullScreen&&dy>=0 &&visibleItemCount > 0
                && (lastVisibleItemPosition) >= totalItemCount - 1){
            //如果不是加载中   滑动停止   占满一屏幕  滑动方向向下 可见的数量大于0  最后可见的位置是最后一个
            onLoadMore();
        }
    }

    /**
     * 加载失败的时候重新加载的时候会调用
     */
    protected void onLoadMore(){
        mLoadingType=LoadingType.LOADING;
        if (listener!=null) {
            if (iLoadFooterView !=null) {
                iLoadFooterView.setVisibility(View.VISIBLE);
                ((ILoadMoreFrameLayout)iLoadFooterView).loading();
                mQuickRcvAdapter.addFooterView(iLoadFooterView);
                recyclerView.scrollToPosition(recyclerView.getLayoutManager().getItemCount() - 1);
            }
            listener.onLoadMoreListener();
        }
    }
    public void onloadMoreComplete(){
        mLoadingType=LoadingType.COMPLETE;
        iLoadFooterView.setVisibility(View.GONE);
    }
    public void onloadMoreFail(){
        mLoadingType=LoadingType.FAIL;
        iLoadFooterView.setVisibility(View.VISIBLE);
        ((ILoadMoreFrameLayout)iLoadFooterView).fail();
    }

    /**
     * 取数组中最大值
     *
     * @param lastPositions
     * @return
     */
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }


    public  enum LayoutManagerType {
        LinearLayout,StaggeredGridLayout,GridLayout;
    }
    public  enum LoadingType {
        LOADING,COMPLETE,FAIL;
    }
    public void associatedAdapter(QuickRcvAdapter mQuickRcvAdapter){
        this.mQuickRcvAdapter=mQuickRcvAdapter;
        //有参数构造器的初始化
        try {
            Constructor<?> conSun = QuickConfig.iLoadMoreFrameLayoutClass.getDeclaredConstructor(Context.class,RecyclerOnLoadMoreListener.class);
            Object temp = conSun.newInstance(mQuickRcvAdapter.getContext(),this);
            if(temp instanceof View &&ILoadMoreFrameLayout.class.isAssignableFrom(temp.getClass())){
                iLoadFooterView =(View)temp;
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
