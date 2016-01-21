package and.http.urlconnection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import and.http.urlconnection.Conn_Core.HttpType;
import and.http.urlconnection.utils.Conn_FileUtils;

public abstract class Conn_FilePostRunnable implements Conn_Interface{
	private static final String PREFIX = "--", LINEND = "\r\n";
	private final static String BOUNDARY = "---------------------------123821742118716"; //boundary就是request头和上传文件内容的分隔符    
	private String urlString;
	private String encoding;
	private Map<String, String> map_str=null;
	private Map<String, File> map_file=null;
	
	public Conn_FilePostRunnable(String urlString,Map<String,Object> map) {
		if(urlString==null) throw new IllegalArgumentException("urlString may be null");
		this.urlString=urlString;
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
	private void writeFile(OutputStream out) {
		 try {
			if (map_file != null) {  
				long total =0;
				for (Entry<String, File> item : map_file.entrySet()) {
					 total += item.getValue().length();//这么多字节
				}
				 long current=0;
			 	for (Entry<String, File> item : map_file.entrySet()) {
			 		StringBuilder sb = new StringBuilder();
					sb.append(PREFIX);
					sb.append(BOUNDARY);
					sb.append(LINEND);
					sb.append("Content-Disposition: form-data; name=\""+item.getKey()+"\"; filename=\""
							+item.getValue().getName()+ "\"" + LINEND);
					sb.append("Content-Type: "+Conn_FileUtils.getContentType(item.getValue())+"; charset="
							+ encoding + LINEND);
					sb.append(LINEND);
				    out.write(sb.toString().getBytes(encoding));  
			        DataInputStream in = new DataInputStream(new FileInputStream(item.getValue()));  
			         int bytes = 0;  
			         byte[] bufferOut = new byte[1024];  
			         onStart();
			         onLoading(total, current);
			         while ((bytes = in.read(bufferOut)) != -1) {  
			        	 current+=bytes;
			        	 onLoading(total, current);
			             out.write(bufferOut, 0, bytes);  
			         }  
			         in.close();  
			         out.write(LINEND.getBytes(encoding));
					}
			 }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}

	private void writeString(OutputStream out) {
		try {
			if (map_str != null) {
				StringBuffer sb = new StringBuffer();
				for (Entry<String, String> item : map_str.entrySet()) {
					sb.append(PREFIX);
					sb.append(BOUNDARY);
					sb.append(LINEND);
					sb.append("Content-Disposition: form-data; name=\""
							+ item.getKey() + "\"" + LINEND);
					sb.append("Content-Type: text/plain; charset=" + encoding
							+ LINEND);
					sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
					sb.append(LINEND);
					sb.append(URLEncoder.encode(item.getValue(), encoding));
					sb.append(LINEND);
				}
				out.write(sb.toString().getBytes(encoding));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}


	@Override
	public HttpType getHttpType() {
		return HttpType.POST_FILE;
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
			conn.setRequestProperty("Connection", "Keep-Alive");  
			//TODO 这个貌似也可以不写 因为这个貌似是浏览器的属性
//    conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");  
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
		} catch (ProtocolException e) {
			e.printStackTrace();
		}		
	}


	@Override
	public void writeConn(HttpURLConnection conn) {
		OutputStream out=null ;
        try {
			/**  =====================写 数据==========================*/
			 out = new DataOutputStream(conn.getOutputStream());  
			// text    
			writeString(out);
			// file    
			writeFile(out);
			out.write((PREFIX + BOUNDARY + PREFIX + LINEND).getBytes());//// 请求结束标志   
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
				try {
					out.flush();  
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	@Override
	public void setEncoding(String encoding) {
		this.encoding=encoding;
	}

	 /**
     * 上传的代码在这里写
     */
    public abstract void onLoading(Long total,Long current);  
    /**
     * 要开始上传了
     */
    public abstract void onStart(); 
  
}
