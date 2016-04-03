package com.zone.zrflist;
import android.content.Context;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
/**
 * Created by Administrator on 2016/3/31.
 * 全局都调用这个  所以更改配置在这里
 */
public class UltraControl {
    public static  void init(Context context, final PtrFrameLayout ptrMain, final OnRefreshListener onRefresh){
        init(context,ptrMain,onRefresh,false);
    }
    public static  void init(Context context, final PtrFrameLayout ptrMain, final OnRefreshListener onRefresh,boolean autoRefresh){
        UltraHeaderUtils.setMyCustomHeader(context,ptrMain);
        ptrMain.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                onRefresh.onRefreshBegin(frame);
            }
        });
        //todo 剩下的配置在这里写  反正全局都调用这个

        /* 自动刷新*/
        if (autoRefresh)
            ptrMain.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ptrMain.autoRefresh();
                }
            }, 100);

    }
    public interface  OnRefreshListener{
        void onRefreshBegin(PtrFrameLayout frame);
    }
}
