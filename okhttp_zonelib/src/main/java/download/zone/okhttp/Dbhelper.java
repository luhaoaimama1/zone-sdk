package download.zone.okhttp;
import android.content.Context;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.WhereBuilder;

import download.zone.okhttp.entity.DownloadInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * todo 有时间在优化吧  暂时没时间了
 * Created by Zone on 2016/2/14.
 */
public class Dbhelper {
    private  DownLoader ourInstance;
    private  Context context;
    public ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Map<String, Boolean> urlDbState_IsOpenMap;
    private  LiteOrm liteOrm;

    public Dbhelper( DownLoader ourInstance) {
        this.ourInstance=ourInstance;
        urlDbState_IsOpenMap = new ConcurrentHashMap<>();

    }
    public void initDb(Context context){
        this.context=context;
        liteOrm = LiteOrm.newCascadeInstance(context, "okhttp.db");
        liteOrm.setDebugged(true);
    }


    //开始任务的时候 查信息  有下载中的 就从中开始
    public DownloadInfo queryTask(final String url){
        Future<DownloadInfo> temp = executorService.submit(new Callable<DownloadInfo>() {
            @Override
            public DownloadInfo call() throws Exception {
                QueryBuilder qb = new QueryBuilder(DownloadInfo.class).whereEquals("url", url);
                List<DownloadInfo> taskList = liteOrm.query(qb);
                ArrayList<DownloadInfo> temp = liteOrm.query(DownloadInfo.class);
                if (taskList == null || taskList.size() == 0)
                    return null;
                else
                    return taskList.get(taskList.size()-1);
            }
        });
        try {
            return temp.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }
    //暂停 存信息   突然断网就是异常 存信息
    public void saveTask(final DownloadInfo downloadInfo){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                if(urlDbState_IsOpenMap.get(downloadInfo.getUrl())==null){
                    if(liteOrm.save(downloadInfo)!=-1){
                        urlDbState_IsOpenMap.put(downloadInfo.getUrl(),true);
                    }
                }else{
                    if(!urlDbState_IsOpenMap.get(downloadInfo.getUrl())&&liteOrm.save(downloadInfo)!=-1){
                        urlDbState_IsOpenMap.put(downloadInfo.getUrl(),true);
                    }
                }
            }
        });

    }
    //完成的时候删除信息
    public void deleteTask(final DownloadInfo downloadInfo){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                ourInstance.clearUrlMemory(downloadInfo.getUrl());
                liteOrm.delete(DownloadInfo.class, new WhereBuilder().where("url = ?", new String[]{downloadInfo.getUrl()}));
            }
        });
    }
    //完成的时候删除信息
    public void deleteTaskOnly(final DownloadInfo downloadInfo){
          liteOrm.delete(DownloadInfo.class, new WhereBuilder().where("url = ?", new String[]{downloadInfo.getUrl()}));
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Map<String, Boolean> getUrlDbState_IsOpenMap() {
        return urlDbState_IsOpenMap;
    }

    public void setUrlDbState_IsOpenMap(Map<String, Boolean> urlDbState_IsOpenMap) {
        this.urlDbState_IsOpenMap = urlDbState_IsOpenMap;
    }
    public interface DbCallBack{
        void saveTask(boolean isFinish);
        void deleteTask(boolean isFinish);
    }
}
