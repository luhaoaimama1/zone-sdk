package com.example.mylib_test.activity.http.framgent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.mylib_test.R;
import com.example.mylib_test.activity.http.event.FirstEvent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2016/4/19.
 */
public class EventFragment extends Fragment {
    @BindView(R.id.textView5)
    TextView textView5;
    private Unbinder bk;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_event, null);
        bk=ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bk.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void zoneEvent(FirstEvent event) {/* Do something */
        String msg = "onEvent收到了消息：" + event.getMsg();
//        Logger.d(msg);
        textView5.setText(event.getMsg());
    }
    @Subscribe(sticky = true)
    public void onEvent(FirstEvent event) {
        // UI updates must run on MainThread
        String msg = "sticky onEvent收到了消息：" + event.getMsg();
//        Logger.d(msg);
    }

}
