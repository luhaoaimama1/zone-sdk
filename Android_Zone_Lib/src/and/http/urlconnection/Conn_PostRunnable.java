package and.http.urlconnection;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.util.Map;
import java.util.Map.Entry;

import and.http.urlconnection.Conn_Core.HttpType;


public  class Conn_PostRunnable implements Conn_Interface{
	private String urlString;
	private String postParams;
	private String encoding;
	/**
	 * 
	 * @param urlString
	 * @param map
	 */
	public Conn_PostRunnable(String urlString,Map<String,String> map) {
		if(urlString==null) throw new IllegalArgumentException("urlString may be null");
		this.urlString=urlString;
		String get="";
		if(map!=null){
			for (Entry<String, String> entry : map.entrySet()) {
				System.out.println("key:"+entry.getKey()+"\t value:"+entry.getValue());
				get+=entry.getKey()+"="+entry.getValue()+"&";
			}
			postParams=get.substring(0, get.length()-1);
		}
		
	}

	@Override
	public HttpType getHttpType() {
		return HttpType.POST;
	}

	@Override
	public String getHttpUrl() {
		return urlString;
	}

	@Override
	public void connInit(HttpURLConnection conn) {
		try {
			//设置请求模式：
			conn.setRequestMethod("POST");
			//意思是正文是urlencoded编码过的form参数
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset="+encoding);
		} catch (ProtocolException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void writeConn(HttpURLConnection conn) {
		try {
			if (postParams!=null) {
				//得到post参数的byte
				byte[] data_bytes = postParams.getBytes(encoding);
				//获取输出流，此时才真正建立链接s
				conn.getOutputStream().write(data_bytes);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setEncoding(String encoding) {
		this.encoding=encoding;
	}


}
