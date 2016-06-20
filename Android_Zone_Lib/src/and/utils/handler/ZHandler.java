package and.utils.handler;

import android.os.Handler;
import android.os.Message;
import java.lang.ref.WeakReference;

/**
 * Created by sxl on 2016/6/20.
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
}