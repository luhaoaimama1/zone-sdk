package download.zone.okhttp;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import download.zone.okhttp.entity.DownloadInfo;
import download.zone.okhttp.entity.ThreadInfo;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Zone on 2016/2/14.
 */
public class DownLoadTask implements Runnable {
    private UIhelper uiHelper;
    private ThreadInfo threadInfo;
    private String urlString;
    private File saveOutFile;
    private DownLoader ourInstance;
    private Dbhelper dbhelper;
    private long dowloadLength;

    public DownLoadTask(File saveOutFile, ThreadInfo threadInfo, UIhelper uiHelper, DownLoader ourInstance, Dbhelper dbhelper) {
        this.saveOutFile = saveOutFile;
        this.threadInfo=threadInfo;
        this.uiHelper=uiHelper;
        this.ourInstance=ourInstance;
        this.dbhelper=dbhelper;
        this.urlString=threadInfo.getDownloadInfo().getUrl();
        this.dowloadLength =threadInfo.getDownloadLength();
    }


    @Override
    public void run() {
        long startIndex=threadInfo.getStartIndex()+threadInfo.getDownloadLength();
        Request request =new Request.Builder().url(urlString).tag(urlString).header("RANGE", "bytes=" +startIndex + "-"
                + threadInfo.getEndIndex()).build();
        Response response = null;
        try {
            response = ourInstance.mOkHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response == null || !response.isSuccessful()){
            uiHelper.onError(response);
            DownLoader.writeLog("threadId:" + threadInfo.getThreadId() + "下载文件失败了~" + "url=" + urlString);
            //todo 出现异常的时候 db有问题还没保存  所以继续不了
            if(ourInstance.getTaskStatuMap().get(threadInfo.getDownloadInfo().getUrl())!=null
                    &&ourInstance.getTaskStatuMap().get(threadInfo.getDownloadInfo().getUrl())== DownloadInfo.PAUSE)
                uiHelper.onPause(threadInfo, dbhelper);
            ourInstance.tryRemoveTask(urlString);
        }else{
            try {
                InputStream inputStream = response.body().byteStream();
                RandomAccessFile raf = new RandomAccessFile(saveOutFile, "rwd");
                // 随机写文件的时候从哪个位置开始写
                raf.seek(startIndex);// 定位文件
                int len = 0;
                byte[] buffer = new byte[1024];
                int count=0;
                while ((len = inputStream.read(buffer)) != -1&&
                        ourInstance.getTaskStatuMap().get(threadInfo.getDownloadInfo().getUrl())== DownloadInfo.DOWNLOADING) {
                    ourInstance.makesureRunningTask(urlString);
                    //!=-1这里也好错
                    count++;
                    dowloadLength +=len;
                    threadInfo.setDownloadLength(dowloadLength);
                    uiHelper.onProgress(threadInfo);
                    DownLoader.writeLog("threadId：" + threadInfo.getThreadId() + "  dowloadLength:" + dowloadLength);
                    raf.write(buffer, 0, len);
                }
                if(ourInstance.getTaskStatuMap().get(threadInfo.getDownloadInfo().getUrl())== DownloadInfo.PAUSE)
                    uiHelper.onPause(threadInfo, dbhelper);
                if (ourInstance.getTaskStatuMap().get(threadInfo.getDownloadInfo().getUrl())== DownloadInfo.DOWNLOADING) {
                    DownLoader.writeLog("线程" + threadInfo.getThreadId() + "   startIndex " + threadInfo.getStartIndex()+
                            "   开始的地方："+ startIndex+ "  endIndex   " + threadInfo.getEndIndex() + "此线程下载字数：" + dowloadLength);
                    DownLoader.writeLog("线程" + threadInfo.getThreadId() + "循环次数：" + count);
                    inputStream.close();
                    raf.close();
                    DownLoader.writeLog("线程" + threadInfo.getThreadId() + ":下载完毕了！");
                    threadInfo.setComplete(true);
                }
                uiHelper.onFinish(threadInfo,dbhelper,saveOutFile);
            } catch (FileNotFoundException e) {
                //异常的时候不保存信息  因为要是保存了 就和显示的进度不一样了
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                ourInstance.tryRemoveTask(urlString);
            }
        }
    }
}
