package and.network.core;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Map;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;

import and.network.callback.NetworkListener;

public class NetworkGlobalEngine extends BaseNetworkQuest{
	private  static Class<? extends BaseNetworkQuest> engineClass;
	private BaseNetworkQuest engine;
	public NetworkGlobalEngine(Context context, Handler handler) {
		this(context, handler,false);
	
	}
	public NetworkGlobalEngine(Context context, Handler handler, boolean showDialog) {
		super(context, handler,showDialog);
		try {
			Constructor<? extends BaseNetworkQuest> con = engineClass.getDeclaredConstructor(Context.class, Handler.class,boolean.class);
			engine = (BaseNetworkQuest) con.newInstance(context, handler,showDialog);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void ab_Send(String urlString, Map<String, String> map, int tag,
			NetworkListener listener) {
		engine.ab_Send(urlString, map, tag, listener);
	}

	@Override
	protected void ab_SendPost(String urlString, Map<String, String> map,
			int tag, NetworkListener listener) {
		engine.ab_SendPost(urlString, map, tag, listener);
	}

	@Override
	protected void ab_SendFile(String urlString, Map<String, String> map,
			Map<String, File> fileMap, int tag, NetworkListener listener) {
		engine.ab_SendPost(urlString, map, tag, listener);
	}

	@Override
	protected Dialog createDefaultDialog(Context context) {
		return engine.createDefaultDialog(context);
	}
	
	public static void setGlobalEngine(Class<? extends BaseNetworkQuest>  engineClass){
		NetworkGlobalEngine.engineClass=engineClass;
	}
}
