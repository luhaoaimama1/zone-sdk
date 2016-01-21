package and.http.urlconnection;

import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.util.Map;
import java.util.Map.Entry;

import and.http.urlconnection.Conn_Core.HttpType;


public  class Conn_GetRunnable implements Conn_Interface{
	private String urlString;
	public Conn_GetRunnable(String urlString,Map<String,String> paramsMap) {
		if(urlString==null) throw new IllegalArgumentException("urlString may be null");
		String get="";
		if(paramsMap!=null){
			for (Entry<String, String> entry : paramsMap.entrySet()) {
				System.out.println("key:"+entry.getKey()+"\t value:"+entry.getValue());
				get+=entry.getKey()+"="+entry.getValue()+"&";
			}
			urlString+="?"+get;
			urlString=urlString.substring(0, urlString.length()-1);
		}
		this.urlString=urlString;
	}
	@Override
	public void connInit(HttpURLConnection conn) {
		try {
			conn.setRequestMethod("GET");
		} catch (ProtocolException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void writeConn(HttpURLConnection conn) {
		
	}
	@Override
	public HttpType getHttpType() {
			return HttpType.GET;
	}
	@Override
	public String getHttpUrl() {
		return urlString;
	}
	@Override
	public void setEncoding(String encoding) {
	}
	


}
