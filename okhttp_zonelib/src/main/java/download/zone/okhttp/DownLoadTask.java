package download.zone.okhttp;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import download.zone.okhttp.UIhelper.UIhelper;
import download.zone.okhttp.entity.ThreadInfo;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Zone on 2016/2/14.
 */
public class DownLoadTask implements Runnable {
    private UIhelper uiHelper;
    private ThreadInfo threadInfo;
    private long startIndex;
    private long endIndex;
    private String urlString;
    private File saveOutFile;
    private int threadId;
    private int overLength;

    public DownLoadTask(long startIndex, long endIndex, String urlString, File saveOutFile, int threadId, ThreadInfo threadInfo, UIhelper uiHelper) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.urlString = urlString;
        this.saveOutFile = saveOutFile;
        this.threadId = threadId;
        this.threadInfo=threadInfo;
        this.uiHelper=uiHelper;
    }

    @Override
    public void run() {
        Request request =new Request.Builder().url(urlString).header("RANGE", "bytes=" + startIndex + "-"
                + endIndex).build();
        Response response = null;
        try {
            response = DownLoader.INSTANCE.mOkHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response == null || !response.isSuccessful()){
            if (response != null )
                System.err.println("下载文件失败了~ code=" + response.code() + "url=" + urlString);
        }else{
            try {
                InputStream inputStream = response.body().byteStream();
                RandomAccessFile raf = new RandomAccessFile(saveOutFile, "rwd");
                // 随机写文件的时候从哪个位置开始写
                raf.seek(startIndex);// 定位文件
                int len = 0;
                byte[] buffer = new byte[1024];
                int count=0;
                while ((len = inputStream.read(buffer)) != -1) {//!=-1这里也好错
                    count++;
                    overLength+=len;
                    threadInfo.setDownloadLength(overLength);
                    uiHelper.onProgress(threadInfo);
                    System.out.println("threadId："+threadId+"  overLength:"+overLength);
                    raf.write(buffer, 0, len);
                }
                System.out.println("线程" + threadId + "   startIndex "+startIndex+"  endIndex   "+endIndex+"下载字数："+overLength);
                System.out.println("线程" + threadId + "循环次数："+count);
                inputStream.close();
                raf.close();
//                pe.set_complete(threadId);
                System.out.println("线程" + threadId + ":下载完毕了！");
                threadInfo.setComplete(true);
                uiHelper.onFinish(threadInfo);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {

            }
        }
    }
}
