package com.example.mylib_test.activity.three_place.workmanager;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.mylib_test.LogApp;

import java.util.concurrent.TimeUnit;

//workManager入门文档：https://developer.android.com/topic/libraries/architecture/workmanager/how-to/define-work?hl=zh-cn
public class OneTimeExampleWorker extends Worker {

    public static void dao(@NonNull Context context) {
        Log.d("OneTimeExampleWorker","add Work");
//        LogApp.INSTANCE.d("add Work");
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)//wifi
                .setRequiresBatteryNotLow(true)//电量不足 不运行
                .build();

        OneTimeWorkRequest uploadWorkRequest = new OneTimeWorkRequest
                .Builder(OneTimeExampleWorker.class)
                .setInitialDelay(20, TimeUnit.SECONDS)
                .setConstraints(constraints)
                .setBackoffCriteria(
                        BackoffPolicy.LINEAR,
                        OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                        TimeUnit.MILLISECONDS
                )
                .addTag("keeplive")
                //类似intent传递数据与当前界面解耦
                .setInputData(new Data.Builder()
                        .putString("IMAGE_URI", "wakaka")
                        .build())
                .build();

        //唯一工作.只会执行一次 而不会因为APP多次启动而多次执行
        WorkManager.getInstance(context).enqueueUniqueWork(
                "keeplive",
                ExistingWorkPolicy.KEEP,
                uploadWorkRequest);
    }

    public OneTimeExampleWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {
        String imageUriInput = getInputData().getString("IMAGE_URI");
        LogApp.INSTANCE.d("doWork params:"+imageUriInput);
        return Result.success();
    }
}
