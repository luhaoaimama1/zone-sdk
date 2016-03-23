package and.network.engine;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import and.network.core.BaseNetworkQuest;
import and.network.callback.NetworkListener;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;

public class XutilsEngine extends BaseNetworkQuest{
	private static long timeout=1000*10;
	//请求编码
	private static final String Request_Charset="utf-8";
	// 设置返回文本的编码， 默认编码UTF-8
	private static final  String Response_Charset="utf-8";
	
	private static final String Params_Charset="utf-8";
	private RequestParams params ;

	public XutilsEngine(Context context, Handler handler) {
		this(context, handler,false);
	}

	public XutilsEngine(Context context, Handler handler, boolean showDialog) {
		super(context, handler, showDialog);
	}

	public void getXutilsParams() {
		params = new RequestParams(Params_Charset);
	}
	@Override
	protected void ab_Send(String urlString, Map<String, String> map, final int tag,final NetworkListener listener) {
		String get="";
		for (Entry<String, String> entry : map.entrySet()) {
			get+=entry.getKey()+"="+entry.getValue()+"&";
		}
		String reallyUrl= urlString+"?"+get;
		HttpUtils http=InnerDispose.getXutils_HttpUtils();
		http.send(HttpRequest.HttpMethod.GET, reallyUrl,InnerDispose.getXutilsCallback(this, tag, listener));
	}

	@Override
	protected void ab_SendPost(String urlString, Map<String, String> map,
			int tag, NetworkListener listener) {
		HttpUtils http=InnerDispose.getXutils_HttpUtils();
		getXutilsParams();
		for (Entry<String, String> entry : map.entrySet()) {
			params.addBodyParameter(entry.getKey(), entry.getValue());
		}
		http.send(HttpRequest.HttpMethod.POST,urlString,params,InnerDispose.getXutilsCallback(this, tag, listener));
	}

	@Override
	protected void ab_SendFile(String urlString, Map<String, String> map,
			Map<String, File> fileMap, int tag, NetworkListener listener) {
		HttpUtils http=InnerDispose.getXutils_HttpUtils();
		getXutilsParams();
		//添加字段
		for (Entry<String, String> entry : map.entrySet()) {
			params.addBodyParameter(entry.getKey(), entry.getValue());
		}
		// 添加文件
		for (Entry<String, File> entry : fileMap.entrySet()) {
			params.addBodyParameter(entry.getKey(), entry.getValue());
		}
		http.send(HttpRequest.HttpMethod.POST,urlString,params,InnerDispose.getXutilsCallback(this, tag, listener));
	}
	@Override
	protected Dialog createDefaultDialog(Context context) {
		return null;
	}
	static  class InnerDispose{
		private static HttpUtils getXutils_HttpUtils(){
			HttpUtils http = new HttpUtils(Request_Charset);
			http.configResponseTextCharset(Response_Charset);
			http.configCurrentHttpCacheExpiry(timeout);
			return http;
		}
		private static RequestCallBack<String> getXutilsCallback(final BaseNetworkQuest baseNetworkQuest,final int tag,final NetworkListener listener){
			return new RequestCallBack<String>() {
				boolean sendMsg=true;
				@Override
				public void onStart() {
					super.onStart();
					if(listener!=null)
						listener.onStart();
				}

				@Override
				public void onLoading(long total, long current,
						boolean isUploading) {
					super.onLoading(total, current, isUploading);
					if(listener!=null)
						listener.onLoading(total, current, isUploading);
				}

				@Override
				public void onCancelled() {
					super.onCancelled();
					if(listener!=null)
						listener.onCancelled();
				}

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					if (sendMsg) {
						baseNetworkQuest.sendhandlerMsg(responseInfo.result,tag);
						sendMsg=false;
					}
					if(listener!=null)
						listener.onSuccess(responseInfo.result);
				}

				@Override
				public void onFailure(HttpException error, String msg) {
					if (sendMsg) {
						baseNetworkQuest.sendhandlerMsg(msg, tag);
						sendMsg=false;
					}
					if(listener!=null)
						listener.onFailure(msg);
				}
			};
		}
	}

}
