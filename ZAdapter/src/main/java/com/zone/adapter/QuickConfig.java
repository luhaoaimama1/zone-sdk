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
    private static final String TAG="ZHttp2RfList";
    public static  boolean  writeLog=true;
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
}
