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
    private  Context context;
    public ExecutorService serialExecutor = Executors.newSingleThreadExecutor();
    private  LiteOrm liteOrm;

    public Dbhelper() {
    }
    public void initDb(Context context){
        this.context=context;
        liteOrm = LiteOrm.newCascadeInstance(context, "okhttp.db");
        liteOrm.setDebugged(true);
    }


    //开始任务的时候 查信息  有下载中的 就从中开始
    public DownloadInfo queryTask(final String url){
        Future<DownloadInfo> temp = serialExecutor.submit(new Callable<DownloadInfo>() {
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
    //暂停 存信息
    public void pauseSaveTaskSync(final DownloadInfo downloadInfo){
        serialExecutor.execute(new Runnable() {
            @Override
            public void run() {
                downloadInfo.setSaving(true);
                liteOrm.save(downloadInfo);
                downloadInfo.setSaving(false);
            }
        });
    }
    //暂停 存信息   突然断网就是异常 存信息
    public void exceptionSaveTaskSync(final DownloadInfo downloadInfo){
        serialExecutor.execute(new Runnable() {
            @Override
            public void run() {
                //不是正常的暂停的 保存任务
                if (downloadInfo.isThreadWork() &&downloadInfo.getTaskState()==DownloadInfo.PAUSE){
                    downloadInfo.setSaving(true);
                    liteOrm.save(downloadInfo);
                    downloadInfo.setSaving(false);
                }
                //所有子线程都走完 就清除内存  不管是否完成
                if(downloadInfo.getTaskState()==DownloadInfo.PAUSE||downloadInfo.getTaskState()==DownloadInfo.COMPLETE){
                    downloadInfo.getUihelper().onFinish();
                    CleanMemoryUtils.clear(downloadInfo.getUrl());
                }
            }
        });
    }
    //完成的时候  删除信息
    public void completeDeleteTaskSync(final DownloadInfo downloadInfo){
        serialExecutor.execute(new Runnable() {
            @Override
            public void run() {
                liteOrm.delete(DownloadInfo.class, new WhereBuilder().where("url = ?", new String[]{downloadInfo.getUrl()}));
                downloadInfo.setDeleteing(false);
            }
        });
    }
    //点删除的时候  删除信息
    public void deleteTaskOnly(final DownloadInfo downloadInfo){
          liteOrm.delete(DownloadInfo.class, new WhereBuilder().where("url = ?", new String[]{downloadInfo.getUrl()}));
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    //todo  这种应该是  点击暂停 删除的时候 弹pop 防止数据库未保存完成继续点
    public interface DbCallBack{
        void saveTask(boolean isFinish);
        void deleteTask(boolean isFinish);
    }
}
