package com.zone.http2rflist;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;

import com.zone.http2rflist.callback.NetworkListener;

import java.lang.reflect.Constructor;

public class NetworkGlobalEngine extends BaseNetworkQuest{
	private  static Class<? extends BaseNetworkQuest> engineClass;
	private BaseNetworkQuest engine;
	public NetworkGlobalEngine(Context context, Handler handler) {
		this(context, handler, false);
	
	}
	public NetworkGlobalEngine(Context context, Handler handler, boolean showDialog) {
		super(context, handler, showDialog);
		try {
			Constructor<? extends BaseNetworkQuest> con = engineClass.getDeclaredConstructor(Context.class, Handler.class,boolean.class);
			engine = con.newInstance(context, handler,showDialog);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void ab_Send(String urlString, RequestParamsNet params, int tag, NetworkListener listener) {
		engine.ab_Send(urlString, params, tag, listener);
	}

	@Override
	protected void cancelAllRequest() {
		engine.cancelAllRequest();
	}


	@Override
	protected Dialog createDefaultDialog(Context context) {
		return engine.createDefaultDialog(context);
	}
	
	public static void setGlobalEngine(Class<? extends BaseNetworkQuest>  engineClass){
		NetworkGlobalEngine.engineClass=engineClass;
	}
}
