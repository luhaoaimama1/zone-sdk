package and.utils.executor;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ExecutorUtils {

    public static final Executor SERIAL_EXECUTOR = AsyncTask.SERIAL_EXECUTOR;
    public static final Executor THREAD_POOL_EXECUTOR = AsyncTask.THREAD_POOL_EXECUTOR;
    //线程池申请那么大 如果没有线程执行也不会有那么大资源；所以不怕；
    public static ScheduledExecutorService scheduled =
            Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() + 1);

    /**
     * 并行
     *
     * @param command
     */
    public static void execute(Runnable command) {
        THREAD_POOL_EXECUTOR.execute(command);
    }

    /**
     * 串行
     *
     * @param command
     */
    public static void executeSerial(Runnable command) {
        SERIAL_EXECUTOR.execute(command);
    }

    public void schedule(Runnable command, long delay, TimeUnit unit) {
        scheduled.schedule(command, delay, unit);
    }

    public void scheduleAtFixedRate(Runnable command,
                                    long initialDelay,
                                    long period,
                                    TimeUnit unit) {
        scheduled.scheduleAtFixedRate(command, initialDelay, period, unit);
    }

    public void scheduleWithFixedDelay(Runnable command,
                                       long initialDelay,
                                       long delay,
                                       TimeUnit unit) {
        scheduled.scheduleWithFixedDelay(command, initialDelay, delay, unit);
    }

    /**
     * 解释
     *
     * @param args
     */
    public static void main(String[] args) {
        //FixedThreadPool  (1) 一样
        ExecutorService single = Executors.newSingleThreadExecutor();
        //固定的  然后 按顺序循环
        ExecutorService fixed = Executors.newFixedThreadPool(5);
        //通常会创建与所需数量相同的线程  当回收旧线程时停止创建新的线程   即可重复利用
        ExecutorService cached = Executors.newCachedThreadPool();
        //此线程对时间排班比较有利
        ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(10);
        //上面那几个都是应用的原理都是下面的　　此粒子为cached源码
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        //allowCoreThreadTimeOut属性为true 那么闲置的核心线程也会有超市时时间　超过时间也会被终止　
        //非核心则不管设不设置都会有超市时间
        threadPoolExecutor.allowCoreThreadTimeOut(true);
    }
}