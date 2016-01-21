package and.http.urlconnection;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import and.utlis.IOUtils;


public abstract class Conn_Core implements Runnable{
	private Conn_Interface myConnection=null;
	private HttpURLConnection conn=null;
	/** =======================set get================================ */
	public static String encoding;
	public static  int timeoutMillis=6*1000;
	public static  boolean isNotPrintHeaders=false;
	
	public enum HttpType{
		GET,POST,POST_FILE
	}
	@SuppressWarnings("static-access")
	public Conn_Core(String encoding,Conn_Interface myConnection){
		this.encoding=encoding;
		this.myConnection=myConnection;
		myConnection.setEncoding(encoding);
		
	}
	@Override
	public void run() {
		conn=initConnection(myConnection.getHttpUrl(), conn, myConnection.getHttpType());
		myConnection.connInit(conn);
		myConnection.writeConn(conn);
		readConn();
	}
	
	/**
     * 访问成功后的代码在这里写
     * @param msg  就是response返回来的String
     */
    public abstract void onSuccess(String msg);
    /**
     * 反问失败的代码在这里写
     * @param msg  就是response返回来的String
     */
    public abstract void onFailure(String msg); 
   
	
	@SuppressWarnings("finally")
	private  HttpURLConnection initConnection(String urlString,HttpURLConnection conn,HttpType httpType){
			try {
				//创建一个URL对象
				URL url = new URL(urlString);
				//利用HttpURLConnection对象从网络中获取网页数据
				conn = (HttpURLConnection) url.openConnection();
				//设置连接超时
				conn.setConnectTimeout(timeoutMillis);
				//允许输入流，即允许下载
				conn.setDoInput(true);
				//允许输出流，即允许上传   
				conn.setDoOutput(true);
				//不使用缓冲
				conn.setUseCaches(false);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				return conn;
			}
	}
	private void readConn() {
		try {
			//			判断是否连接成功
			if (conn.getResponseCode() == 200){
				if (isNotPrintHeaders) {
					printHeaders(conn);
				}
				/**  =====================读取 数据==========================*/
				InputStream input = conn.getInputStream();
				String s=IOUtils.read(input, encoding);
				onSuccess(s);
			}else{
				onFailure(" 200 is ok,but response code is"+conn.getResponseCode()+"!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(conn!=null){
				conn.disconnect();
			}
		}
	}
	private  void printHeaders(HttpURLConnection conn){
		Map<String, List<String>>  map=conn.getHeaderFields();
		StringBuilder sb=new StringBuilder();
		sb.append("==================请求头信息 华丽丽的开始=======================\n");
		for (Entry<String, List<String>> iterable_element : map.entrySet()) {
			List<String> lsit = iterable_element.getValue();
			sb.append(iterable_element.getKey()+":{");
			for (String string : lsit) {
				sb.append(string);
			}
			sb.append("} \n");
		}
		sb.append("==================请求头信息 华丽丽的结束=======================\n");
		System.out.println(sb.toString());
	}
	
}
