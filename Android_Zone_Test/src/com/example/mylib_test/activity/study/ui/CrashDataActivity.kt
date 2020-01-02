package com.example.mylib_test.activity.study.ui

import android.os.Bundle
import com.example.mylib_test.LogApp
import com.example.mylib_test.R
import com.example.mylib_test.SP1
import com.example.mylib_test.app.UncaughtExceptionHandler
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import kotlinx.android.synthetic.main.a_creash_data.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.lang.IllegalStateException
import java.util.concurrent.TimeUnit

class CrashSave
/**
 * todo zone 有问题
 *   方式1：用eventbus，但是异步的,所以接收方 处理数据多久完毕。还有全部接收方 全部处理完 才能关闭 这个暂时没法弄.
 *   方式2：这个不是异步所以暂时么问题 activity 和 fragment 然后 共同实现一个接口 如果属于这个接口则收集到弱引用集合里  最后倒叙执行。最近的开始执行完毕 就结束
 */
class CrashDataActivity : BaseFeatureActivity() {

    override fun onCreate(arg0: Bundle?) {
        super.onCreate(arg0)
    }

    val s = "这是啥"
    override fun setContentView() {
        setContentView(R.layout.a_creash_data)

        LogApp.d("SP1 restore==>${SP1.get("CrashDataActivity", "")}")
        tv_content.text = s
        EventBus.getDefault().register(this)

        Observable.interval( 0, 1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    tv_content.text = "$s$it"
                }

        click_error.setOnClickListener {
            LogApp.d("存储内容==>${tv_content.text}")
            throw  IllegalStateException("测试异常！")
        }

        Thread.setDefaultUncaughtExceptionHandler(object : UncaughtExceptionHandler(this) {

            /**
             * 当UncaughtException发生时会转入该重写的方法来处理
             */
            override fun uncaughtException(thread: Thread, ex: Throwable) {
                EventBus.getDefault().post(CrashSave())
                try {
                    Thread.sleep(3000)// 如果处理了，让程序继续运行3秒再退出，保证文件保存并上传到服务器
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                // 退出程序
                android.os.Process.killProcess(android.os.Process.myPid())
                System.exit(1)
            }
        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun crashSave(save: CrashSave) {
        LogApp.d("crashSave")
        SP1.put("CrashDataActivity", tv_content.text.toString())
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.apply {
            putString("CrashDataActivity", tv_content.text.toString())
            LogApp.d("onSaveInstanceState===>${tv_content.text}")
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        LogApp.d("onRestore===>${savedInstanceState?.getString("CrashDataActivity") ?: ""}")
    }

    override fun initData() {
    }

    override fun setListener() {
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

}