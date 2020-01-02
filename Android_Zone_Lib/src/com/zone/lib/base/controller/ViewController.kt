package com.zone.lib.base.controller

import android.os.Handler
import android.os.Looper
import android.os.Message
import rx.Observable
import rx.subscriptions.CompositeSubscription
import java.lang.ref.WeakReference

interface BaseFeatureView
open class ViewController<V : BaseFeatureView>(v: V) : Handler.Callback {
    protected var view: WeakReference<V> = WeakReference(v)

    private val subscriptions = CompositeSubscription()
    //为什么要单个弄个Handler 而不用HandlerU˙iUtil那种全局的呢?
    //为了使用removeAllMessage  把延时的都清除掉,而全局会的会吧别的请求移除掉
    private var handler: Handler

    init {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw IllegalStateException("请在主线程创建!")
        }
        handler = Handler(this)
    }

    fun getView(): V? = view.get()

    //通过subscriptions 收集  Subscriber,然后在生命周期结束的时候 全部解除订阅 并clear掉
    fun <T> ioIoTransformer(): Observable.Transformer<T, T> = RxComposes.ioTransformer(subscriptions)

    //通过subscriptions 收集  Subscriber,然后在生命周期结束的时候 全部解除订阅 并clear掉
    fun <T> ioIuTransformer(): Observable.Transformer<T, T> = RxComposes.asycTransformer(subscriptions)

    fun post(callback: () -> Unit) {
        if (view.get() != null) {
            handler.post {
                callback()
            }
        }
    }

    fun doSafe(callback: (v: V) -> Unit) {
        val get = view.get()
        if (get != null) {
            callback(get)
        }
    }

    fun postDelayed(callback: () -> Unit, delayMillis: Long) {
        if (view.get() != null) {
            handler.postDelayed({
                callback()
            }, delayMillis)
        }
    }

    //remove全部任务！
    fun removeAllMessage() {
        subscriptions.clear()
        handler.removeCallbacksAndMessages(null)
    }

    override fun handleMessage(msg: Message?): Boolean {
        return false
    }

}
