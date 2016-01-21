package and.http.client;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.util.Log;

	/**
	 *  multipart/form-data; <br>
	 * 即文件的表单提交方式
	 * <br>传输的两种:File与String   
	 * 
	 * @author Zone
	 *
	 */
public abstract class MyHttpFilePostThread extends Thread {
		private String url;
		Map<String, String> map_str=null;
		Map<String, File> map_file=null;
		private String encoding;
		
		private boolean writeLog=true;
		private void log(String str){
			if(writeLog){
				System.out.println(str);
			}
		}
		/**
		 *  multipart/form-data; <br>
		 * 即文件的表单提交方式
		 * <br>传输的两种:File与String  
		 * <br>自带文件验证 是否存在！ 不存在不会添加但是会log打出err信息 
		 * @param url 服务器地址
		 * @param map 这个 key 分两种 ：1.String_真正的名字 <br>2.File_真正的名字
		 * <br>3.String 与 File 可以不分大小写
		 * <br>格式为：Map<String, Object> 是 Object!
		 */
		public MyHttpFilePostThread(String url,Map<String, Object> map,String encoding) {
			super();
			this.url = url;
			this.encoding = encoding;
			//把这两个map初始化
			map_str=new HashMap<String, String>();
			map_file=new HashMap<String, File>();
			if(map!=null)
			{//如果map不为空
				//从map 把数据分别装填到此类的两个map中
				for ( Entry<String, Object> item : map.entrySet()) {
					//把key值取出来  看是file还是String
					String[] splits=item.getKey().split("[_]");
					if("string".equals(splits[0].toLowerCase())){
						map_str.put(splits[1], (String)item.getValue());
					}
					if("file".equals(splits[0].toLowerCase())){
						map_file.put(splits[1], (File)item.getValue());
					}
					
				}
			}
			
		}

		@Override
		public void run() {
			/* 上传文件至Server，uploadUrl：接收文件的处理页面 */
			try {
				HttpParams httpParams = new BasicHttpParams();
				// 设置超时
				HttpConnectionParams.setConnectionTimeout(httpParams, 10 * 1000);
				HttpClient httpclient = new DefaultHttpClient(httpParams);
				HttpPost httppost = new HttpPost(url);
				//防止乱码  文件传输  就是服务器端 判断的是不是复合表单 
				MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE,  null, Charset.forName(encoding));
				if(map_str!=null&&map_file!=null)
				{//当这两map都不为null 
					
					//先填装String的map
					for ( Entry<String, String> item : map_str.entrySet()) {
						entity.addPart(item.getKey(), new StringBody(item.getValue()));
						log("String 装填的："+"key:"+item.getKey()+"\t value:"+item.getValue());	
					}
					//再填装File的map
					for ( Entry<String, File> item : map_file.entrySet()) {
						if(item.getValue().exists()){
							//验证有没有这个文件 。。。。
							entity.addPart(item.getKey(), new FileBody(item.getValue()));
							log("File 装填的："+"key:"+item.getKey()+"\t  File存在:OK! \tvalue的路径:"+item.getValue().getPath());
						}else{
							throw new IllegalStateException(item.getValue().getPath()+"路径下的文件  不存在啊！！！ 此文件未 装填！！！   ");
						}
					}
				}
				
				Log.e("params",httppost.getURI().toString()+ entity.getContentLength());
				//把entity 装填到 HttpPost
				httppost.setEntity(entity);
				Log.e("params222", httppost.getEntity().getContentLength() + "");
				//提交表单
				HttpResponse response = httpclient.execute(httppost);

				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					log("---连接---url---------------success---------------------------------------");
					//response.getEntity() 为null也不怕
					 String responseStr=EntityUtils.toString(response.getEntity(),encoding);
					// mHandler.obtainMessage(0, zifu).sendToTarget();
					//打印回来的值
//					System.out.println(sysoOp+"回来的值:" + responseStr);
					success(response,responseStr);
				} else {
					log("---连接---url---------------failed---------------------------------------");
				}
				  // 释放资源  
				httpclient.getConnectionManager().shutdown();
			} catch (Exception e) {
				e.printStackTrace();
			} 

		}
		/**
		 * 访问成功后的代码在这里写
		 * @param response  就是response
		 * @param responseStr  就是response返回来的String
		 */
		public abstract void success(HttpResponse response,String responseStr);
	}
