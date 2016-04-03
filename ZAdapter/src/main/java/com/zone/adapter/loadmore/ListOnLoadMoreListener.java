package com.zone.adapter.loadmore;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import com.zone.adapter.QuickAdapter;
import com.zone.adapter.QuickConfig;
import com.zone.adapter.base.BaseQuickAdapter;
import com.zone.adapter.loadmore.callback.ILoadMoreFrameLayout;
import com.zone.adapter.loadmore.callback.OnLoadMoreListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Administrator on 2016/4/2.
 */
public class ListOnLoadMoreListener implements AbsListView.OnScrollListener {
    public  enum LoadingType {
        LOADING,COMPLETE,FAIL;
    }
    public final OnLoadMoreListener listener;
    /**
     * 三种状态： 刷新中，完成，失败
     */
    private LoadingType mLoadingType=LoadingType.COMPLETE;//默认完成的
    private int touchItem;
    private boolean moveDown;
    private BaseQuickAdapter mQuickAdapter;
    private View iLoadFooterView;
    private ListView listView;

    private  int firstVisibleItem;
    private  int visibleItemCount;
    private  int totalItemCount;

    public ListOnLoadMoreListener(OnLoadMoreListener listener) {
        this.listener = listener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState==SCROLL_STATE_TOUCH_SCROLL) {
            touchItem =firstVisibleItem;
        }
        if (scrollState==SCROLL_STATE_IDLE) {
            moveDown =(firstVisibleItem-touchItem)>0?true:false;
        }

//        if (scrollState == SCROLL_STATE_IDLE) {
//            QuickConfig.e("visibleItemCount != totalItemCount："+(visibleItemCount != totalItemCount)
//                    +"\t moveDown:"+moveDown+"\t visibleItemCount:"+visibleItemCount+"\t (firstVisibleItem + visibleItemCount) >= totalItemCount:"
//                    +((firstVisibleItem + visibleItemCount) >= totalItemCount));
//        }
        //判断可视Item是否能在当前页面完全显示 visibleItemCount != totalItemCount
        if (mLoadingType!=LoadingType.LOADING
                &&scrollState ==SCROLL_STATE_IDLE
                && visibleItemCount != totalItemCount&&moveDown&& visibleItemCount > 0
                && (firstVisibleItem + visibleItemCount) >= totalItemCount) {
            onLoadMore();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if (view instanceof ListView) {
            listView=(ListView)view;
            this.totalItemCount=totalItemCount;
            this.visibleItemCount=visibleItemCount;
            this.firstVisibleItem=firstVisibleItem;
        }
    }

    /**
     * 加载失败的时候重新加载的时候会调用
     */
    public void onLoadMore(){
        mLoadingType=LoadingType.LOADING;
        if (listener!=null) {
            if (iLoadFooterView !=null) {
                iLoadFooterView.setVisibility(View.VISIBLE);
                ((ILoadMoreFrameLayout)iLoadFooterView).loading();
                mQuickAdapter.addFooterView(iLoadFooterView);
                listView.smoothScrollToPosition(listView.getAdapter().getCount() - 1);
            }
            listener.onLoadMore();
        }
    }
    public void onLoadMoreComplete(){
        mLoadingType=LoadingType.COMPLETE;
        iLoadFooterView.setVisibility(View.GONE);
    }
    public void onLoadMoreFail(){
        mLoadingType=LoadingType.FAIL;
        iLoadFooterView.setVisibility(View.VISIBLE);
        ((ILoadMoreFrameLayout)iLoadFooterView).fail();
    }

    public void associatedAdapter(BaseQuickAdapter mQuickAdapter){
        this.mQuickAdapter=mQuickAdapter;
        //有参数构造器的初始化
        try {
            Constructor<?> conSun = QuickConfig.iLoadMoreFrameLayoutClass.getDeclaredConstructor(Context.class,Object.class);
            Object temp = conSun.newInstance(mQuickAdapter.getContext(),this);
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
