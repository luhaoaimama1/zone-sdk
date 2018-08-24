package com.zone.lib.utils.activity_fragment_ui;

import android.app.Activity;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/18.
 *  里面也是这样，不过功能多很多。比如获取最顶层的Activity
 *  移除最顶层的。添加，删除，或者统一关闭某一序列的activity，某一类型的
 */
public class ActivityCollectionUtils {
    public static List<WeakReference<Activity>> activitys=new ArrayList<>();
    public static void add(Activity acitivity){
        activitys.add(new WeakReference(acitivity));
    }
    public static void finishAll(){
        for (WeakReference<Activity> item : activitys) {
            if(item.get()!=null){
                item.get().finish();
            }
        }
    }
}
