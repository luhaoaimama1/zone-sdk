package and.http;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import and.http.urlconnection.Conn_Core;
import and.http.urlconnection.Conn_FilePostRunnable;
import and.http.urlconnection.Conn_GetRunnable;
import and.http.urlconnection.Conn_PostRunnable;

public class MyConn_Utils {
	private static MyConn_Utils instance;
	private static ExecutorService executorService ;
	static{
		executorService = Executors.newFixedThreadPool(1); // 保证每次只有一个网络请求
	}
	private MyConn_Utils() {
	}
	public static MyConn_Utils getInstance(){
		if(instance==null){
			instance=new MyConn_Utils();
		}
		return instance;
	}
	public  void executeHttpGet(String urlString,String encoding,final CallBack callBack){
		this.executeHttpGet(urlString, null, encoding, callBack);
	};
	public  void executeHttpGet(String urlString,Map<String,String> paramsMap,String encoding,final CallBack callBack){
		executorService.execute(new Conn_Core(encoding,new Conn_GetRunnable(urlString, paramsMap)) {
				
				@Override
				public void onSuccess(String msg) {
					callBack.onSuccess(msg);
				}
				
				@Override
				public void onFailure(String msg) {
					callBack.onFailure(msg);
				}
			});
	};
	public  void executeHttpPost(String urlString,Map<String,String> map,String encoding,final CallBack callBack){
		executorService.execute(new Conn_Core(encoding,new Conn_PostRunnable(urlString, map)) {
			
			@Override
			public void onSuccess(String msg) {
				callBack.onSuccess(msg);
			}
			
			@Override
			public void onFailure(String msg) {
				callBack.onFailure(msg);
			}
		});
		
	};
	public  void executeHttpFile(String urlString,Map<String,Object> map,String encoding,final FileUpLoad_CallBack fileUpLoad_CallBack){
		executorService.execute(new Conn_Core(encoding,new Conn_FilePostRunnable(urlString, map) {
			@Override
			public void onStart() {
				fileUpLoad_CallBack.onStart();
			}
			
			@Override
			public void onLoading(Long total, Long current) {
				fileUpLoad_CallBack.onLoading(total, current);
				
			}
		}) {
			
			@Override
			public void onSuccess(String msg) {
				fileUpLoad_CallBack.onSuccess(msg);
				
			}
			
			@Override
			public void onFailure(String msg) {
				fileUpLoad_CallBack.onFailure(msg);				
			}
		});
	};
	
	public interface FileUpLoad_CallBack{
		public void onStart();
		public void onLoading(Long total, Long current);
		public void onSuccess(String msg);
		public void onFailure(String msg);
	}
	public interface CallBack{
		public void onSuccess(String msg);
		public void onFailure(String msg);
	}
	
	public String getEncoding() {
		return Conn_Core.encoding;
	}

	public MyConn_Utils setEncoding(String encoding) {
		Conn_Core.encoding = encoding;
		return this;
	}

	public int getTimeoutMillis() {
		return Conn_Core.timeoutMillis;
	}

	public MyConn_Utils setTimeoutMillis(int timeoutMillis) {
		Conn_Core.timeoutMillis = timeoutMillis;
		return this;
	}

	public boolean isNotPrintHeaders() {
		return Conn_Core.isNotPrintHeaders;
	}

	public MyConn_Utils setNotPrintHeaders(boolean isNotPrintHeaders) {
		Conn_Core.isNotPrintHeaders = isNotPrintHeaders;
		return this;
	}

}
