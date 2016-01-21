package and.http.client;


import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
/**
 * get提交
 * @author Zone
 *
 */
public abstract class MyHttpGetThread extends Thread {
	private DefaultHttpClient httpClinet;
	private HttpGet httpGet ;
	private String encoding;
	
	private boolean writeLog=true;
	private void log(String str){
		if(writeLog){
			System.out.println(str);
		}
	}	
	/**
	 * 
	 * @param urlString 已经拼接好的
	 */
	public MyHttpGetThread(String urlString,String encoding) {
		// TODO Auto-generated constructor stub
		this.encoding=encoding;
		httpClinet = new DefaultHttpClient();
		httpGet = new HttpGet(urlString);
		
	}
	/**
	 * 
	 * @param urlString	 未拼接的地址
	 * @param map		map参数
	 */
	public MyHttpGetThread(String urlString,Map<String,String> map,String encoding) {
		this.encoding=encoding;
		httpClinet = new DefaultHttpClient();
		String get="";
		for (Entry<String, String> entry : map.entrySet()) {
//			System.out.println("key:"+entry.getKey()+"\t value:"+entry.getValue());
			get+=entry.getKey()+"="+entry.getValue()+"&";
		}
		urlString+="?"+get;
//		System.out.println("url="+urlString.substring(0, urlString.length()-1));
		httpGet = new HttpGet(urlString.substring(0, urlString.length()-1));
	}

	@Override
	public void run() {
		try {
			HttpResponse response = httpClinet.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				log("---连接---url---------------success---------------------------------------");
				//得到响应返回的字符串
				String responseStr = EntityUtils.toString(response.getEntity(),encoding);
//				mHandler.obtainMessage(0, zifu).sendToTarget();
				//打印回来的值
//				System.out.println(sysoOp+"回来的值:" + responseStr);
				onSuccess(response,responseStr);
			}else {
				log("---连接---url---------------failed---------------------------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		super.run();
	}
	/**
	 * 访问成功后的代码在这里写
	 * @param response  就是response
	 * @param responseStr  就是response返回来的String
	 */
	public abstract void onSuccess(HttpResponse response,String responseStr);
}
