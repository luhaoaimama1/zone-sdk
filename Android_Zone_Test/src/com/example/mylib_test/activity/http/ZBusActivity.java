package com.example.mylib_test.activity.http;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.http.entity.Img;
import com.example.mylib_test.app.Apps;
import com.squareup.leakcanary.RefWatcher;

import and.utils.zeventbus.ThreadModeX;
import and.utils.zeventbus.Mode;
import and.utils.zeventbus.ZEventBus;
import butterknife.ButterKnife;

public class ZBusActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_http_ebus);
        ButterKnife.bind(this);

    }
//    /**
//     * 注意不能用内部类 会泄漏 activity 因为持有外部引用！
//     */
//    public  class Bean{
//        String name;
//
//        public Bean(String name) {
//            this.name = name;
//        }
//    }
    @ThreadModeX(threadMode = Mode.BACKGROUND)
    public void heihei(Bean bean) {
        ZEventBus.e("method:heihei---->" + bean.name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zeventBus:
                new ZEventBus(this).post(new Bean("what?"));
                return;
            case R.id.zeventBus2:
                new ZEventBus(this).post(new Img());
                return;
            case R.id.zeventBus3:
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new ZEventBus(ZBusActivity.this).post(new Bean("what?"));
                    }
                }, 1000);
                finish();
                return;
        }
    }

    private static final Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
        RefWatcher refWatcher = Apps.getRefWatcher(this);
        refWatcher.watch(this);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        ZEventBus.e("finalize:---->ZBusActivity");
    }
}
