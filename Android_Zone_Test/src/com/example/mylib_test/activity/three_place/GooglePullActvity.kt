package com.example.mylib_test.activity.three_place

import android.graphics.Rect
import android.os.AsyncTask
import android.os.Handler
import android.os.Message
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mylib_test.R
import com.example.mylib_test.activity.db.entity.MenuEntity
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import com.example.mylib_test.adapter.delegates.TextDelegates
import com.zone.adapter3kt.QuickAdapter
import com.zone.adapter3kt.ViewStyleDefault
import com.zone.adapter3kt.ViewStyleOBJ
import com.zone.adapter3kt.divder.StandardDivder
import com.zone.adapter3kt.loadmore.OnScrollRcvListener
import com.zone.lib.utils.system_hardware_software_receiver_shell.software.wifi.NetManager
import kotlinx.android.synthetic.main.a_menu.*
import kotlinx.android.synthetic.main.a_threeplace_google.*
import java.util.*

/**
 *[2018/7/10] by Zone
 */
class GooglePullActvity : BaseFeatureActivity(), SwipeRefreshLayout.OnRefreshListener, Handler.Callback {
    companion object {
        val data = LinkedList<String>()

        init {
            for (i in 0..29)
                data.add("一直很桑心")
        }
    }

    override fun setContentView() {
        setContentView(R.layout.a_threeplace_google)
    }

    var count = 0
    private val quickAdapter = QuickAdapter<String>(this).apply {
        registerDelegate(TextDelegates())
        add(data)
        loadOnScrollListener=object : OnScrollRcvListener(){

            override fun onLoading() {
                super.onLoading()
                //相当于告诉他加载完成了
                handler.postDelayed({
                    val s = "上啦加载的数据~${++count}"
                    add(s)
                    scrollTo(s)
                    //todo zone 做成快捷方法
                    handler.postDelayed({
                        loadMoreComplete()
                    },50)

                }, 1500)
            }
        }
    }

    override fun initData() {
        swipe_container.setOnRefreshListener(this)
//		swipe_container.setColorScheme(android.R.color.holo_blue_bright,  android.R.color.holo_green_light,
//	    android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipe_container.setColorScheme(android.R.color.holo_red_light)
        lv.layoutManager = LinearLayoutManager(this)
        lv.adapter = quickAdapter
        lv.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.bottom = 10
            }

        })
    }

    override fun setListener() {
    }


    override fun handleMessage(msg: Message?): Boolean {
        quickAdapter.add(0,"当前没有网络呢")
        lv.scrollToPosition(0)
        swipe_container.isRefreshing = false
        return false
    }

    override fun onRefresh() {
        if (!NetManager.haveNetWork(this)) {
            handler.sendEmptyMessage(1)
            return
        }
        Handler().postDelayed({
            // Do work to refresh the list here.
            GetDataTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        }, 3000)
    }

    inner class GetDataTask : AsyncTask<Void, Void, String>() {
        // 后台处理部分
        override fun doInBackground(vararg params: Void?): String {
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
            }

            return "Added after refresh...I add"
        }

        //这里是对刷新的响应，可以利用addFirst（）和addLast()函数将新加的内容加到LISTView中
        //根据AsyncTask的原理，onPostExecute里的result的值就是doInBackground()的返回值
        override fun onPostExecute(result: String?) {
            //在头部增加新添内容
            quickAdapter.add(0,result!!)
            lv.scrollToPosition(0)

            //通知程序数据集已经改变，如果不做通知，那么将不会刷新mListItems的集合
            // Call onRefreshComplete when the list has been refreshed.
            swipe_container.isRefreshing = false
            super.onPostExecute(result)//这句是必有的，AsyncTask规定的格式
        }

    }
}