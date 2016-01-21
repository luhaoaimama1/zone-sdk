package and.http.downfile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import and.http.downfile.entity.ProgressEntity;
import and.http.downfile.entity.ProgressEntity.RangeStaue;
import android.os.Handler;

/**
 * 	String urlPath="http://down.360safe.com/360/inst.exe";
 *  String urlPath="http://img4.freemerce.com/ci49h5p.jpg";
 *	真正的网址是这个
 * @author Administrator
 *
 */
//TODO 断点下载       超时等参数的setget
public enum DownLoader  {
	INSTANCE;
	private static final int CONTAIN_THREAD_COUNT=5;
	private ExecutorService executorService=Executors.newFixedThreadPool(CONTAIN_THREAD_COUNT);
	
	public interface ProgressListener{
		 public void onProgressUpdate( int current,int total,float progress);
	}
	private  Handler handler=new Handler();
	
	
	public void  downLoader(final String urlPath,File targetFolder,int threadCount){
		downLoader(urlPath, targetFolder, threadCount, null);
	}
	public void  downLoader(final String urlPath,final File targetFolder,final int tc,final ProgressListener pl){
			for (ProgressEntity item : ProgressEntity.peList) {
				if((urlPath).equals(item.url)){
					System.out.println("此地址已经有了  ");
					return ;
				}
			}
			executorService.execute(new Runnable() {
				
				@Override
				public void run() {
					ProgressEntity pe =  new ProgressEntity(urlPath);
					try {
						// 连接服务器，获取一个文件，获取文件的长度，在本地创建一个大小跟服务器文件大小一样的临时文件
						URL url = new URL(urlPath);
						HttpURLConnection conn = (HttpURLConnection) url
								.openConnection();
						// 设置网络请求超时时间
						conn.setConnectTimeout(5000);
						// 设置请求方式
						conn.setRequestMethod("GET");//get不能小写 必须大写
						int code = conn.getResponseCode();
						int length = 0;
						String fileName = "";
						String rangeStr = null;
						if (code == 200) {
							// 服务器返回的数据的长度，实际就是文件的长度
							length = conn.getContentLength();
							rangeStr = conn.getHeaderField("Accept-Ranges");
							if (rangeStr == null) {
								rangeStr = "0";
							} else {
								rangeStr = "1";
							}

							System.out.println("rangeStr   " + rangeStr);
							//						for (Entry<String, List<String>> item : conn.getHeaderFields().entrySet()) {
							//							System.out.print("key: "+item.getKey());
							//							System.out.print("  value:");
							//							for (String str : item.getValue()) {
							//								System.out.print(str+"  ");
							//							}
							//							System.out.println();
							//						}
							System.out.println("服务器：" + conn.toString());
							System.out.println("----文件总长度----" + length);
							String name = url.getFile();
							String[] lin = name.split("[/]");
							for (int i = lin.length - 1; i >= 0; i--) {
								if (lin[i].contains(".")) {
									fileName = lin[i];
									break;
								}

							}
						}
						int supportStatue = Integer.parseInt(rangeStr);
						File saveOutFile = new File(targetFolder, fileName);
						try {
							RandomAccessFile raf = new RandomAccessFile(
									saveOutFile, "rwd");
							// 指定创建的这个文件的长度
							raf.setLength(length);
							System.out.println("byte文件长度：" + length);
							// 关闭raf
							raf.close();
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					
						int threadCount=tc;
						switch (supportStatue) {
						case 0:
							pe.init(length,threadCount, RangeStaue.UNSUPPORTED);
							threadCount = 1;
							break;
						case 1:
							pe.init(length,threadCount, RangeStaue.SUPPORTED);
							break;

						default:
							break;
						}
						int blockSize = (int) Math.ceil(length / threadCount);
						for (int threadId = 1; threadId <= threadCount; threadId++) {
							// 计算每个线程下载的开始位置和结束位置
							int startIndex = (threadId - 1) * blockSize;
							int endIndex = threadId * blockSize - 1;
							if (threadId == threadCount) {
								endIndex = length;
							}
							System.out.println("----threadId---" + threadId
									+ "--startIndex--" + startIndex
									+ "--endIndex--" + endIndex);
							executorService.execute(new DownLoader_Core(
									threadId, startIndex, endIndex, urlPath,
									saveOutFile, pe, pl,handler));
						}
						//防止新任务被提交
//						executorService.shutdown();
					
					} catch (Exception e) {
						e.printStackTrace();
					}
				
					
				}
			});
			
			}
}
