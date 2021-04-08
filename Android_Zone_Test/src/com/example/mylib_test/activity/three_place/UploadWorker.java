//package com.example.mylib_test.activity.three_place;
//
//import android.content.Context;
//
//import androidx.annotation.NonNull;
//import androidx.work.OneTimeWorkRequest;
//import androidx.work.WorkManager;
//import androidx.work.WorkRequest;
//import androidx.work.Worker;
//import androidx.work.WorkerParameters;
//
////workManager入门文档：https://developer.android.com/topic/libraries/architecture/workmanager/basics?hl=zh-cn
//public class UploadWorker extends Worker {
//
//    public static void dao(@NonNull Context context) {
//        WorkRequest uploadWorkRequest = new OneTimeWorkRequest.Builder(UploadWorker.class).build();
//        WorkManager.getInstance(context).enqueue(uploadWorkRequest);
//    }
//
//    public UploadWorker(@NonNull Context context, @NonNull WorkerParameters params) {
//        super(context, params);
//    }
//
//    /**
//     * Result.success()：工作成功完成。
//     * Result.failure()：工作失败。
//     * Result.retry()：工作失败，应根据其重试政策在其他时间尝试
//     *
//     * @return
//     */
//    @Override
//    public Result doWork() {
//
//        // Do the work here--in this case, upload the images.
////        uploadImages();
//
//        System.out.println("doWork");
//
//        // Indicate whether the work finished successfully with the Result
//        return Result.success();
//    }
//}
