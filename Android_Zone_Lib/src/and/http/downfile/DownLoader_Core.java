package and.http.downfile;
import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import and.http.downfile.DownLoader.ProgressListener;
import and.http.downfile.entity.ProgressEntity;
import android.os.Handler;


public class DownLoader_Core implements Runnable{
	private int threadId;
	private int startIndex;
	private int endIndex;
	private String urlPath;
	private File saveOutFile;
	private ProgressEntity pe;
	private ProgressListener pl;
	private Handler handler;
	/**
	 * @param path
	 *            下载文件在服务器上的路径
	 * @param threadId
	 *            线程id
	 * @param startIndex
	 *            线程下载的开始位置
	 * @param endIndex
	 *            线程下载的结束位置
	 * @param pl 
	 * @param pe 
	 * @param handler 
	 * @param targetFile 
	 */
	public DownLoader_Core(int threadId, int startIndex,
			int endIndex,String urlPath, File saveOutFile, ProgressEntity pe, ProgressListener pl, Handler handler) {
		this.threadId = threadId;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.urlPath = urlPath;
		this.saveOutFile = saveOutFile;
		this.pe=pe;
		this.pl=pl;
		this.handler=handler;
	}

	@Override
	public void run() {

		try {
			URL	url = new URL(urlPath);
			HttpURLConnection conn = (HttpURLConnection) url
					.openConnection();
			conn.setRequestMethod("GET");
			// 重要：请求服务器下载部分的文件 指定文件的位置
			conn.setRequestProperty("Range", "bytes=" + startIndex + "-"
					+ endIndex);
			conn.setConnectTimeout(5000);
			// 从服务器请求全部资源的状态码200 ok 如果从服务器请求部分资源的状态码206 ok
			int code = conn.getResponseCode();
			System.out.println("---code---" + code);
			InputStream is = conn.getInputStream();// 已经设置了请求的位置，返回的是当前位置对应的文件的输入流
			RandomAccessFile raf = new RandomAccessFile(saveOutFile, "rwd");
			// 随机写文件的时候从哪个位置开始写
			raf.seek(startIndex);// 定位文件
			int len = 0;
			byte[] buffer = new byte[1024];
			int count=0;
			int overLength=0;
			while ((len = is.read(buffer)) != -1) {//!=-1这里也好错
				count++;
				if(pl!=null){
					overLength+=len;
					System.out.println("threadId："+threadId+"  overLength:"+overLength);
					pe.set_updateProgress(threadId, overLength, pl,handler);
				}
				raf.write(buffer, 0, len);
			}
			System.out.println("线程" + threadId + "   startIndex "+startIndex+"  endIndex   "+endIndex+"下载字数："+overLength);
			System.out.println("线程" + threadId + "玄幻次数："+count);
			is.close();
			raf.close();
			pe.set_complete(threadId);
			System.out.println("线程" + threadId + ":下载完毕了！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

}
