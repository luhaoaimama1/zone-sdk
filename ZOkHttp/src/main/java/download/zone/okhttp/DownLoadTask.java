package download.zone.okhttp;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import download.zone.okhttp.entity.ThreadInfo;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Zone on 2016/2/14.
 * 暂停 异常   完成保存数据库   删除也是  不过不是这个类实现的
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


    //内存中已经又downloaderinfo信息了  出错就保存就好了  但是必须是所有线程都出错才能保存   还有就是保存的时候暂停
    @Override
    public void run() {
        long startIndex=threadInfo.getStartIndex()+threadInfo.getDownloadLength();
        Request request =new Request.Builder().url(urlString).tag(urlString).header("RANGE", "bytes=" +startIndex + "-"
                + threadInfo.getEndIndex()).build();
        Response response = null;
        try {
            response = ourInstance.mOkHttpClient.newCall(request).execute();
        } catch (IOException e) {
            ExceptionHelper.quiet(e);
        }
        if(response == null || !response.isSuccessful()){
            DownLoader.writeLog("threadId:" + threadInfo.getThreadId() + "下载文件失败了");
            uiHelper.onError(response);
        }else{
            try {
                InputStream inputStream = response.body().byteStream();
                RandomAccessFile raf = new RandomAccessFile(saveOutFile, "rwd");
                // 随机写文件的时候从哪个位置开始写
                raf.seek(startIndex);// 定位文件
                int len = 0;
                byte[] buffer = new byte[1024];
                int count=0;
                while ((len = inputStream.read(buffer)) != -1&&threadInfo.getDownloadInfo().isThreadWork()) {
                    //!=-1这里也好错
                    count++;
                    dowloadLength +=len;
                    threadInfo.setDownloadLength(dowloadLength);
                    uiHelper.onProgress();
                    DownLoader.writeLog("threadId：" + threadInfo.getThreadId() + "  dowloadLength:" + dowloadLength);
                    raf.write(buffer, 0, len);
                }
                DownLoader.writeLog("线程" + threadInfo.getThreadId() + ":下载完毕了！   startIndex " + threadInfo.getStartIndex() +
                        "   开始的地方：" + startIndex + "  endIndex   " + threadInfo.getEndIndex() + "此线程下载字数：" + dowloadLength + " \t 循环次数：" + count);
                threadInfo.setComplete(true);
                uiHelper.onSuccess(dbhelper, saveOutFile);
                inputStream.close();
                raf.close();
            } catch (FileNotFoundException e) {
                //异常的时候不保存信息  因为要是保存了 就和显示的进度不一样了
                ExceptionHelper.quiet(e);
            } catch (IOException e) {
                ExceptionHelper.quiet(e);
            }
        }
        threadInfo.setStoping(true);
        //异常的时候保存数据库
        dbhelper.exceptionSaveTaskSync(threadInfo.getDownloadInfo());
    }
}
