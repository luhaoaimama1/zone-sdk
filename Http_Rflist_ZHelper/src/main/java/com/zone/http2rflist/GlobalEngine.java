package com.zone.http2rflist;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import com.zone.http2rflist.base.BaseNetworkEngine;
import com.zone.http2rflist.base.BasePullView;
import com.zone.http2rflist.callback.IBaseNetworkEngine;
import com.zone.http2rflist.utils.Pop_Zone;
import java.lang.reflect.Constructor;

//代理类
public class GlobalEngine implements IBaseNetworkEngine {
    private static Class<? extends BaseNetworkEngine> engineClass;
    private BaseNetworkEngine engine;

    public GlobalEngine(Context context, Handler handler) {
        this(context, handler, false);

    }
    public GlobalEngine(Context context, Handler handler, boolean showDialog) {
        try {
            Constructor<? extends BaseNetworkEngine> con = engineClass.getDeclaredConstructor(Context.class, Handler.class, boolean.class);
            engine = con.newInstance(context, handler, showDialog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //设置全局 网络请求类
    public static void setGlobalEngine(Class<? extends BaseNetworkEngine> engineClass) {
        GlobalEngine.engineClass = engineClass;
    }

    public static String getLimitColumn() {
        return BaseNetworkEngine.getLimitColumn();
    }

    public static String getOffsetColumn() {
        return BaseNetworkEngine.getOffsetColumn();
    }

    @Override
    public void setStartPage(int startPage) {
        engine.setStartPage(startPage);
    }

    @Override
    public void firstPage() {
        engine.firstPage();
    }

    @Override
    public void nextPage() {
        engine.nextPage();
    }

    @Override
    public void turnPage(int number) {
        engine.turnPage(number);
    }

    @Override
    public void start() {
        engine.start();
    }

    @Override
    public void prepare(RequestParams request) {
        engine.prepare(request);
    }

    @Override
    public void prepare(RequestParams.Builder request) {
        engine.prepare(request);
    }
    @Override
    public void sendhandlerMsg(String msg, int handlerTag) {
        engine.sendhandlerMsg(msg, handlerTag);
    }

    @Override
    public void relatePullView(BasePullView listView) {
        engine.relatePullView(listView);
    }

    @Override
    public void setDialog(Dialog dialog) {
        engine.setDialog(dialog);
    }

    @Override
    public void setPopWindow(Pop_Zone popWindow) {
        engine.setPopWindow(popWindow);
    }

    @Override
    public boolean isShowDialog() {
        return engine.isShowDialog();
    }

    @Override
    public void setShowDialog(boolean showDialog) {
        engine.setShowDialog(showDialog);
    }

    @Override
    public <A> A gsonParseNoRelateList(String msg, Class<A> clazz) {
        return engine.gsonParseNoRelateList(msg, clazz);
    }

    @Override
    public void setLimit(int limit) {
        engine.setLimit(limit);
    }

    @Override
    public int getLimit() {
        return engine.getLimit();
    }

    @Override
    public void cancelByContext() {
        engine.cancelByContext();
    }

    @Override
    public void cancel() {
        engine.cancel();
    }
}
