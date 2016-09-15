package and.utils.activity_fragment_ui.handler;

import android.os.Handler;
import android.os.Message;
import java.lang.ref.WeakReference;

/**
 * 主要是防止空缓存
 */
public  class ZHandler extends Handler {
    private final WeakReference<ZCallback> mCallback;

    public ZHandler(ZCallback callback) {
        mCallback = new WeakReference<ZCallback>(callback);
    }

    @Override
    public void handleMessage(Message msg) {
        if (mCallback.get() == null) {
            return;
        }
        mCallback.get().handleMessage(msg);
    }
    public interface ZCallback{
         void handleMessage(Message msg);
    }

    public void removeAllMessage(){
        removeCallbacksAndMessages(null);//remove全部任务！
    }
}