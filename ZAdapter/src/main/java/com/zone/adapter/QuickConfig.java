package com.zone.adapter;
import android.util.Log;
import com.zone.adapter.loadmore.view.LoadMoreFrameLayout;

/**
 * Created by Administrator on 2016/3/24.
 */
public class QuickConfig {
    public  static Class iLoadMoreFrameLayoutClass = LoadMoreFrameLayout.class;
    //全局切换 loadMore footerView
    public static void setLoadMoreView(Class iLoadMoreFrameLayout){
        QuickConfig.iLoadMoreFrameLayoutClass =iLoadMoreFrameLayout;
    }
    //默认滑到底部 手指不离开也加载更多 因为这种感觉更好
    public static  boolean SCROLL_STATE_IDLE_OnloadMore_Mode = false;

    public static void setLoadMoreMode(boolean mSCROLL_STATE_IDLE_ONLOADMORE){
        QuickConfig.SCROLL_STATE_IDLE_OnloadMore_Mode =mSCROLL_STATE_IDLE_ONLOADMORE;
    }

    private static final String TAG="ZAdapter";
    public static  boolean  writeLog=true;
    public static boolean  loadMoreListenerLog=false;
    public static void d(String str){
        if (writeLog )
            Log.d(TAG,str);
    }
    public static void e(String str){
        if (writeLog )
            Log.e(TAG,str);
    }
    public static void v(String str){
        if (writeLog )
            Log.v(TAG,str);
    }
    public static void i(String str){
        if (writeLog )
            Log.i(TAG,str);
    }
    public static void dLoad(String str){
        if (writeLog&&loadMoreListenerLog)
            Log.d(TAG,str);
    }
    public static void eLoad(String str){
        if (writeLog&&loadMoreListenerLog)
            Log.e(TAG,str);
    }
    public static void vLoad(String str){
        if (writeLog&&loadMoreListenerLog)
            Log.v(TAG,str);
    }
    public static void iLoad(String str){
        if (writeLog&&loadMoreListenerLog)
            Log.i(TAG,str);
    }
}
