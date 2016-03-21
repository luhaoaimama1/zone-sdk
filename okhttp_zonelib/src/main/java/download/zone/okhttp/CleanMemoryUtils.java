package download.zone.okhttp;


/**
 * Created by Zone on 2016/3/18.
 */
public class CleanMemoryUtils {
    //子线程都完成删除信息(完成 pause 线程内异常)  手动删除
    public static void clear(String url){
        DownLoader.downLoadInfoMap.remove(url);
    }

}
