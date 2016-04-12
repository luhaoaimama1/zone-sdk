package com.zone.http2rflist.callback;

import android.app.Dialog;

import com.zone.http2rflist.base.BasePullView;
import com.zone.http2rflist.BaseRequestParams;
import com.zone.http2rflist.utils.Pop_Zone;

/**
 * Created by Administrator on 2016/4/7.
 */
public interface IBaseNetworkEngine {

    void firstPage();

    void nextPage();

    void turnPage(int number);

    void start();

    void newCall(BaseRequestParams request);

    void sendhandlerMsg(String msg, int handlerTag);

    void relateList(BasePullView listView);


    void setDialog(Dialog dialog);

    void setPopWindow(Pop_Zone popWindow);

    boolean isShowDialog();

    void setShowDialog(boolean showDialog);

    <A> A gsonParseNoRelateList(String msg, Class<A> clazz);

    void setLimit(int limit);

    int getLimit();
}
