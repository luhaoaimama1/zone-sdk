package com.zone.adapter.loadmore;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import com.zone.adapter.QuickConfig;
import com.zone.adapter.base.Header2FooterRcvAdapter;
import com.zone.adapter.loadmore.callback.ILoadMoreFrameLayout;
import com.zone.adapter.loadmore.callback.OnLoadMoreListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class RecyclerOnLoadMoreListener extends RecyclerView.OnScrollListener  {

    public  enum LayoutManagerType {
        LinearLayout,StaggeredGridLayout,GridLayout;
    }
    public  enum LoadingType {
        LOADING,COMPLETE,FAIL;
    }
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
    private Header2FooterRcvAdapter mQuickRcvAdapter;
    private View iLoadFooterView;
    private RecyclerView recyclerView;
    private boolean isReady=false;

    public RecyclerOnLoadMoreListener(OnLoadMoreListener listener) {
        this.listener = listener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        this.dy=dy;
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

        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        QuickConfig.eLoad("onScrolled=================isReady:" + isReady + "\t fullScreen:" + fullScreen + "\t mLoadingType:" + mLoadingType + "\t visibleItemCount != totalItemCount：" + (visibleItemCount != totalItemCount)
                + "\t dy:" + dy + "\t visibleItemCount:" + visibleItemCount + "\t (lastVisibleItemPosition) >= totalItemCount - 1:"
                + ((lastVisibleItemPosition) >= totalItemCount - 1) + "\t lastVisibleItemPosition:" + lastVisibleItemPosition + "\t totalItemCount:" + totalItemCount);
        if (!QuickConfig.SCROLL_STATE_IDLE_OnloadMore_Mode) {
            if (mLoadingType!=LoadingType.LOADING
                    &&fullScreen&&dy>=0 &&visibleItemCount > 0
                    && (lastVisibleItemPosition) >= totalItemCount - 1){
                //如果不是加载中   滑动停止   占满一屏幕  滑动方向向下 可见的数量大于0  最后可见的位置是最后一个
                onLoadMore();
            }
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        QuickConfig.eLoad("newState:" + newState);
        if (newState==RecyclerView.SCROLL_STATE_DRAGGING)
            isReady=true;
        this.recyclerView=recyclerView;
        currentScrollState = newState;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        fullScreen=visibleItemCount!=totalItemCount?true:false;
        QuickConfig.eLoad("onScrollStateChanged================= isReady:" + isReady + "\t fullScreen:" + fullScreen + "\t mLoadingType:" + mLoadingType + "\t visibleItemCount != totalItemCount：" + (visibleItemCount != totalItemCount)
                + "\t dy:" + dy + "\t visibleItemCount:" + visibleItemCount + "\t (lastVisibleItemPosition) >= totalItemCount - 1:"
                + ((lastVisibleItemPosition) >= totalItemCount - 1) + "\t lastVisibleItemPosition:" + lastVisibleItemPosition + "\t totalItemCount:" + totalItemCount);
        if (QuickConfig.SCROLL_STATE_IDLE_OnloadMore_Mode) {
            if (isReady&mLoadingType!=LoadingType.LOADING
                    && currentScrollState == RecyclerView.SCROLL_STATE_IDLE
                    &&fullScreen&&dy>=0 &&visibleItemCount > 0
                    && (lastVisibleItemPosition) >= totalItemCount - 1){
                //如果不是加载中   滑动停止   占满一屏幕  滑动方向向下 可见的数量大于0  最后可见的位置是最后一个
                onLoadMore();
            }
        }else{
            if (isReady&mLoadingType==LoadingType.FAIL
                    &&fullScreen&&dy>=0 &&visibleItemCount > 0
                    && (lastVisibleItemPosition) >= totalItemCount - 1){
                //如果不是加载中   滑动停止   占满一屏幕  滑动方向向下 可见的数量大于0  最后可见的位置是最后一个
                onLoadMore();
            }
        }
    }

    /**
     * 加载失败的时候重新加载的时候会调用
     */
    public void onLoadMore(){
        isReady=false;
        mLoadingType=LoadingType.LOADING;
        QuickConfig.eLoad("onLoadMore");
        if (listener!=null) {
            if (iLoadFooterView !=null) {
                iLoadFooterView.setVisibility(View.VISIBLE);
                ((ILoadMoreFrameLayout)iLoadFooterView).loading();
                mQuickRcvAdapter.addFooterView(iLoadFooterView);
                recyclerView.scrollToPosition(recyclerView.getLayoutManager().getItemCount() - 1);
            }
            listener.onLoadMore();
        }
    }
    public void onLoadMoreComplete(){
        mLoadingType=LoadingType.COMPLETE;
        iLoadFooterView.setVisibility(View.GONE);
    }
    public void onLoadMoreComplete2RemoveFooterView(){
        mLoadingType=LoadingType.COMPLETE;
        mQuickRcvAdapter.removeFooterView(iLoadFooterView);
    }
    public void onLoadMoreFail(){
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



    public void associatedAdapter(Header2FooterRcvAdapter mQuickRcvAdapter){
        this.mQuickRcvAdapter=mQuickRcvAdapter;
        //有参数构造器的初始化
        try {
            Constructor<?> conSun = QuickConfig.iLoadMoreFrameLayoutClass.getDeclaredConstructor(Context.class,Object.class);
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
