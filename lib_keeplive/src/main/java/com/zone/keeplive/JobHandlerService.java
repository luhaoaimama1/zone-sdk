//package com.zone.keeplive;
//
//import android.app.job.JobInfo;
//import android.app.job.JobParameters;
//import android.app.job.JobScheduler;
//import android.app.job.JobService;
//import android.content.ComponentName;
//import android.content.Intent;
//import android.os.Build;
//
//import androidx.annotation.RequiresApi;
//
//import com.zone.keeplive.service.KeepLiveService;
//
//@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//public class JobHandlerService extends JobService {
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        startService(new Intent(this, KeepLiveService.class));
//        timerDoTask();
//        return START_STICKY;
//    }
//
//    @Override
//    public boolean onStartJob(JobParameters params) {
//        startService(new Intent(this, KeepLiveService.class));
//
//        timerDoTask();
//        return false;
//    }
//
//    @Override
//    public boolean onStopJob(JobParameters params) {
//        timerDoTask();
//        return false;
//    }
//
//    private JobScheduler mJobScheduler;
//    private int jobId = 100;
//
//    private void timerDoTask() {
//        if (Build.VERSION.SDK_INT >= 21) {
//            this.mJobScheduler = (JobScheduler) this.getSystemService("jobscheduler");
//            this.mJobScheduler.cancel(this.jobId);
//            JobInfo.Builder builder = new JobInfo.Builder(this.jobId, new ComponentName(this.getPackageName(), JobHandlerService.class.getName()));
////            long minLatencyMillis = 30000L;
//            long minLatencyMillis = 3000L;
//            if (Build.VERSION.SDK_INT >= 24) {
//                builder.setMinimumLatency(minLatencyMillis);
//                builder.setOverrideDeadline(minLatencyMillis);
//                builder.setMinimumLatency(minLatencyMillis);
//                builder.setBackoffCriteria(minLatencyMillis, 0);
//            } else {
//                builder.setPeriodic(minLatencyMillis);
//            }
//
//            builder.setRequiredNetworkType(1);
//            builder.setPersisted(true);
//            this.mJobScheduler.schedule(builder.build());
//        }
//    }
//}
