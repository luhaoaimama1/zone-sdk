package com.example.mylib_test.activity.three_place.workmanager;

import android.content.Context;

import androidx.work.WorkManager;

public class WorkManagers {
    public void queryWork(Context context){
        WorkManager workManager = WorkManager.getInstance(context);
        // by id
//        workManager.getWorkInfoById(syncWorker.id); // ListenableFuture<WorkInfo>
        // by name
        workManager.getWorkInfosForUniqueWork("sync"); // ListenableFuture<List<WorkInfo>>
        // by tag
        workManager.getWorkInfosByTag("syncTag"); // ListenableFuture<List<WorkInfo>>
    }

    public void cancel(Context context,String tag){
        WorkManager workManager = WorkManager.getInstance(context);
        // ) 会取消带有特定标记的所有工作请求，
        workManager.cancelAllWorkByTag(tag);
    }
}
