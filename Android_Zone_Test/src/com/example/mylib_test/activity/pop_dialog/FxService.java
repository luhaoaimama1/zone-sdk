package com.example.mylib_test.activity.pop_dialog;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class FxService extends Service {
    private FloatWindowPop floatWindowPop;

    @Override
    public void onCreate() {
        super.onCreate();
        floatWindowPop = new FloatWindowPop(this);
        floatWindowPop.show();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("FxService——onDestroy");
        floatWindowPop.remove();
    }

}
