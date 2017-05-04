package and.utils.zeventbus;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/**
 * [2017] by Zone
 */

public class ZEventBus {
    private static final Handler mHandler = new Handler(Looper.getMainLooper());

    WeakReference<Object> mCallBackWR = null;

    public ZEventBus(Object callBackObj) {
        if (callBackObj == null)
            e("ZEventBus构造参数为null");
        mCallBackWR = new WeakReference<Object>(callBackObj);
    }

    //百度:http://blog.csdn.net/clevergump/article/details/50995612
    private boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void post(Object eventObj) {
//        System.gc();// 经过测试activity还是没有好使但是 其他对象是可以被回收的。说明activity被系统缓存了
        Object callBackObj = mCallBackWR.get();
        if (callBackObj == null) {
            e("对象已被回收！");
            return;
        }
        Method method = getTargetMethod(eventObj, callBackObj.getClass().getMethods());
        if (method == null) {
            e(callBackObj.getClass().getCanonicalName()+"类无此方法");
            return;
        }
        method.setAccessible(true);
        ThreadModeX threadModeX = method.getAnnotation(ThreadModeX.class);
        Task task = new Task(eventObj, this, method);
        if (threadModeX == null){
            e("ThreadMode:null 走默认");
            task.run();
        }
        else
            switch (threadModeX.threadMode()) {
                case MAIN:
                    e("ThreadMode:MAIN");
                    mHandler.post(task);
                    break;
                case BACKGROUND:
                    e("ThreadMode:BACKGROUND");
                    if (!isMainThread()){
                        e("本身不是主线程 继续");
                        task.run();
                    }else{
                        e("本身是主线程 切换!");
                        AsyncTask.THREAD_POOL_EXECUTOR.execute(task);
                    }

                    break;
                case ASYNC:
                    e("ThreadMode:ASYNC");
                    AsyncTask.THREAD_POOL_EXECUTOR.execute(task);
                    break;
                case POSTING:
                    e("ThreadMode:POSTING");
                    task.run();
                    break;
            }
    }

    private Method getTargetMethod(Object obj, Method[] methods) {
        for (Method method : methods) {
            Class<?>[] parTypes = method.getParameterTypes();
            if (parTypes != null && parTypes.length == 1
                    && parTypes[0].getCanonicalName().equals(obj.getClass().getCanonicalName()))
                return method;
        }
        return null;
    }

    private static class Task implements Runnable {
        private final Method method;
        private final ZEventBus zEventBus;
        private final Object eventObj;

        public Task(Object eventObj, ZEventBus zEventBus, Method method) {
            this.eventObj = eventObj;
            this.method = method;
            this.zEventBus = zEventBus;
        }

        @Override
        public void run() {
            try {
                Object callBackObj = zEventBus.mCallBackWR.get();
                if (callBackObj == null) {
                    e("对象已被回收！");
                    return;
                }
                e(String.format("执行方法:%s(%s)",method.getName(),eventObj.getClass().getSimpleName()));
                method.invoke(callBackObj, eventObj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }


    private static boolean writeLog = true;
    private static String TAG = "ZEventBus";

    public static boolean isWriteLog() {
        return writeLog;
    }

    public static void setWriteLog(boolean writeLog) {
        ZEventBus.writeLog = writeLog;
    }

    public static void d(String str) {
        if (writeLog)
            Log.d(TAG, str);
    }

    public static void e(String str) {
        if (writeLog)
            Log.e(TAG, str);
    }

    public static void v(String str) {
        if (writeLog)
            Log.v(TAG, str);
    }

    public static void i(String str) {
        if (writeLog)
            Log.i(TAG, str);
    }
}
