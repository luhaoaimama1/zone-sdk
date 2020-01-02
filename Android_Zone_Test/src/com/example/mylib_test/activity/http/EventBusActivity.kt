package com.example.mylib_test.activity.http

import com.example.mylib_test.R
import com.example.mylib_test.activity.http.event.FirstEvent
import com.example.mylib_test.activity.http.framgent.EventFragment

import org.greenrobot.eventbus.EventBus

import com.zone.lib.utils.activity_fragment_ui.FragmentSwitcher
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import kotlinx.android.synthetic.main.a_eventbus.*

/**
 * Created by Administrator on 2016/4/19.
 */
class EventBusActivity : BaseFeatureActivity() {

    private var fragmentSwitcher: FragmentSwitcher? = null

    override fun setContentView() {
        setContentView(R.layout.a_eventbus)
        fragmentSwitcher = FragmentSwitcher(this, R.id.fl)
        fragmentSwitcher!!.initFragment(EventFragment())
        EventBus.getDefault().post(FirstEvent("Hello eventBus Sticky,method(post)!"))
        //一般用 post发送， @Subscribe接收就可以了
        // 此处因为发送消息的时候，SecondActivity还没启动，所以必须用粘性事件postSticky发送
        EventBus.getDefault().postSticky(FirstEvent("Hello eventBus Sticky,method(postSticky)!"))
        //        EventBus.getDefault().clearCaches();
        fragmentSwitcher!!.switchPage(0)
        eventBus.setOnClickListener {
            EventBus.getDefault().post(FirstEvent("Hello eventBus!"))
        }
    }

    override fun initData() {
    }

    override fun setListener() {
    }

}
