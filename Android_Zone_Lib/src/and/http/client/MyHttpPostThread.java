package and.http.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * application/x-www-form-urlencoded
 * <br>普通的表单提交
 * @author Zone
 *
 */
public abstract class MyHttpPostThread extends Thread {
	private DefaultHttpClient httpclient;
	private HttpPost request;
	private List<BasicNameValuePair> params;
	private String encoding;

	
	private boolean writeLog=true;
	private void log(String str){
		if(writeLog){
			System.out.println(str);
		}
	}
	/**
	 * application/x-www-form-urlencoded
	 * <br>普通的表单提交
	 * @param urlString		url地址
	 * @param map			键值对
	 * @param encoding		键值对的编码方式
	 */
	public MyHttpPostThread(String urlString, Map<String, String> map, String encoding) {
		this.encoding = encoding;
		httpclient = new DefaultHttpClient();
		request = new HttpPost(urlString);
		params = new ArrayList<BasicNameValuePair>();
		for (Entry<String, String> entry : map.entrySet()) {
			params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}

	}

	@Override
	public void run() {
		try {
			HttpEntity httpentity = new UrlEncodedFormEntity(params, encoding);
			request.setEntity(httpentity);
			HttpResponse response = httpclient.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				log("---连接---url---------------success---------------------------------------");
				//得到响应返回的字符串
				String responseStr=EntityUtils.toString(response.getEntity(),encoding);
				// mHandler.obtainMessage(0, zifu).sendToTarget();
				//打印回来的值
				log("回来的值:" + responseStr);
				success(response,responseStr);
			} else {
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
	public abstract void success(HttpResponse response,String responseStr);
}
