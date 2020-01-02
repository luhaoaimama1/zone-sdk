package com.zone.lib.base.controller

import androidx.annotation.CallSuper
import rx.Subscriber

abstract class AbstractSubscriber<T> : Subscriber<T>() {

    override fun onError(t: Throwable) {
        onDataDealOnPrepare()
        onDataFailed(t)
        onDataDealOnEnd()
    }

    override fun onCompleted() {}

    @CallSuper
    override fun onNext(t: T) {
        onDataDealOnPrepare()
        onDataSuccess(t)
        onDataDealOnEnd()
    }

    open fun onDataFailed(t: Throwable) {}

    abstract fun onDataSuccess(data: T)

    open fun onDataDealOnEnd() {}

    open fun onDataDealOnPrepare() {}
}
