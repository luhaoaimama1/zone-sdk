package download.zone.okhttp.db;

import android.content.Context;

import com.litesuits.orm.LiteOrm;
import download.zone.okhttp.entity.DownloadInfo;

import java.util.List;

/**
 * Created by Zone on 2016/2/14.
 */
public class Dbhelper {
    private  LiteOrm liteOrm;

    public Dbhelper(Context context) {
        liteOrm = LiteOrm.newSingleInstance(context, "okhttp.db");
        liteOrm.setDebugged(true);
    }
    public List<DownloadInfo> queryTask(){
        return liteOrm.query(DownloadInfo.class);
    }
    public void saveTask(DownloadInfo downloadInfo){
        liteOrm.save(downloadInfo);
    }
}
