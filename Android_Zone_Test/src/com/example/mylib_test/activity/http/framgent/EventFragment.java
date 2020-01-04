package com.example.mylib_test.activity.http.framgent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.http.event.FirstEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by Administrator on 2016/4/19.
 */
public class EventFragment extends Fragment {
    TextView textView5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_event, null);
        textView5 = view.findViewById(R.id.textView5);
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
        textView5.setText(event.getMsg());
    }

}
