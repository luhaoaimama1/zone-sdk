package com.zone.http2rflist;

import com.zone.http2rflist.base.BaseNetworkEngine;
import com.zone.http2rflist.base.MsgErrorCheck;

/**
 * Created by Administrator on 2016/5/9.
 */
public class Config {


    public  Config setLimitColumn(String limitColumn) {
        BaseNetworkEngine.setLimitColumn(limitColumn);
        return this;
    }
    public  Config setOffsetColumn(String offsetColumn) {
        BaseNetworkEngine.setOffsetColumn(offsetColumn);
        return this;
    }

    public Config  setMsgCheckCallBack(MsgErrorCheck.MsgCheckCallBack msgCheckCallBack){
        MsgErrorCheck.setMsgCheckCallBack(msgCheckCallBack);
        return this;
    };
    public Config setGlobalEngine(Class<? extends BaseNetworkEngine> engineClass){
        GlobalEngine.setGlobalEngine(engineClass);
        return this;
    }

}
