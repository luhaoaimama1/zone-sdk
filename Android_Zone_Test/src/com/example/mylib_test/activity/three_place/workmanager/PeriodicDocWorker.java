package com.example.mylib_test.activity.three_place.workmanager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.mylib_test.LogApp;

import java.util.concurrent.TimeUnit;

//workManager入门文档：https://developer.android.com/topic/libraries/architecture/workmanager/how-to/define-work?hl=zh-cn
public class PeriodicDocWorker extends Worker {

    public static void dao(@NonNull Context context) {
        //该工作请求仅在用户设备正在充电且连接到 Wi-Fi 网络时才会运行：
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)//wifi
                .setRequiresCharging(true)//充电
//                .setRequiresBatteryNotLow(true)//电量不足 不运行
//                .setRequiresDeviceIdle(true) //设备空闲的时候 运行 api 23
                .build();

        PeriodicWorkRequest uploadWorkRequest = new PeriodicWorkRequest
                //工作的运行时间间隔定为一小时。 tips:可以定义的最短重复间隔是 15 分钟（与 JobScheduler API 相同）
//                .Builder(PerUploadWorker.class, 1, TimeUnit.HOURS)
                //可在每小时的最后 15 分钟内运行的定期工作的示例
                .Builder(PeriodicDocWorker.class, 1, TimeUnit.HOURS,
                15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .setInitialDelay(10, TimeUnit.MINUTES)//在这种情况下，定期工作只有首次运行时会延迟

                //退避政策 最小10秒 重试 。
                // linear:在 20 秒、30 秒、40 秒
                // EXPONENTIAL:重试时长序列将接近 20、40、80 秒
                .setBackoffCriteria(
                        BackoffPolicy.LINEAR,
                        OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                        TimeUnit.MILLISECONDS)

                /**
                 * 特定的唯一标识，可以向单个工作请求添加多个标记。这些标记在内部以一组字符串的形式进行存储。对于工作请求
                 * 您可以通过 WorkRequest.getTags() 检索其标记集。
                 *
                 * WorkManager.cancelAllWorkByTag(String) 会取消带有特定标记的所有工作请求，
                 * WorkManager.getWorkInfosByTag(String) 会返回一个 WorkInfo 对象列表，该列表可用于确定当前工作状态。
                 */
                .addTag("cleanup")
                //类似intent传递数据与当前界面解耦
                .setInputData(new Data.Builder()
                        .putString("IMAGE_URI", "http://...")
                        .build())
                .build();

        WorkManager.getInstance(context).enqueue(uploadWorkRequest);

        //唯一工作.只会执行一次 而不会因为APP多次启动而多次执行
        //WorkManager.enqueueUniqueWork()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "sendLogs",
                //此 enum 可告知 WorkManager：如果已有使用该名称且尚未完成的唯一工作链，应执行什么操作。如需了解详情，
                ExistingPeriodicWorkPolicy.KEEP,
                uploadWorkRequest);
    }


    public PeriodicDocWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    /**
     * Result.success()：工作成功完成。
     * Result.failure()：工作失败。
     * Result.retry()：工作失败，应根据其重试政策在其他时间尝试
     *
     * @return
     */
    @Override
    public Result doWork() {

        //
        String imageUriInput = getInputData().getString("IMAGE_URI");

        // Do the work here--in this case, upload the images.
//        uploadImages();
        LogApp.INSTANCE.d("doWork!");
        // Indicate whether the work finished successfully with the Result

        //return  Result.retry()//重试 会根据setBackoffCriteria的策略重试

        return Result.success();
    }
}
