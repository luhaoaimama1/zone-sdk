package com.example.mylib_test.activity.http;

import com.example.mylib_test.R;
import com.example.mylib_test.activity.http.event.FirstEvent;
import com.example.mylib_test.activity.http.framgent.EventFragment;

import org.greenrobot.eventbus.EventBus;

import and.base.activity.BaseFragmentActivity;
import and.utils.activity_fragment_ui.FragmentSwitcher;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/4/19.
 */
public class EventBusActivity extends BaseFragmentActivity {

    private FragmentSwitcher fragmentSwitcher;

    @Override
    public void setContentView() {
        setContentView(R.layout.a_eventbus);
        ButterKnife.bind(this);
        fragmentSwitcher=new FragmentSwitcher(this, R.id.fl);
        fragmentSwitcher.initFragment(new EventFragment());
        EventBus.getDefault().post(new FirstEvent("Hello eventBus Sticky,method(post)!"));
        EventBus.getDefault().postSticky(new FirstEvent("Hello eventBus Sticky,method(postSticky)!"));
//        EventBus.getDefault().clearCaches();
        fragmentSwitcher.switchPage(0);
    }

    @Override
    public void findIDs() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

    }

    @OnClick(R.id.eventBus)
    public void onClick() {
        EventBus.getDefault().post(new FirstEvent("Hello eventBus!"));
    }
}
