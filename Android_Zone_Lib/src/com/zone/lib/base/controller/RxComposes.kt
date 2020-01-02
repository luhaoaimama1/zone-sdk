package com.zone.lib.base.controller

import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

object RxComposes {

    @JvmStatic
    fun <T> asycTransformer(): Observable.Transformer<T, T> = asycTransformer(null)

    @JvmStatic
    //代理模式 真正的处理和订阅的是 subscriberResult
    fun <T> asycTransformer(compositeSubscription: CompositeSubscription?): Observable.Transformer<T, T> {
        //不能在lift前面加判断 用upstream .lift操作各种不好事.一套连下去好了
        return Observable.Transformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .lift(object : Observable.Operator<T, T> {
                        override fun call(subscriber: Subscriber<in T>?): Subscriber<in T> {
                            val subscriberResult = object : Subscriber<T>() {
                                override fun onNext(t: T?) {
                                    subscriber?.onNext(t)
                                }

                                override fun onCompleted() {
                                    subscriber?.onCompleted()
                                }

                                override fun onError(e: Throwable?) {
                                    subscriber?.onError(e)
                                }

                            }
                            compositeSubscription?.add(subscriberResult)
                            return subscriberResult
                        }

                    })
        }
    }

    @JvmStatic
    fun <T> ioTransformer(): Observable.Transformer<T, T> = ioTransformer(null)

    @JvmStatic
    fun <T> ioTransformer(compositeSubscription: CompositeSubscription?): Observable.Transformer<T, T> {
        return Observable.Transformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .lift(object : Observable.Operator<T, T> {
                        override fun call(subscriber: Subscriber<in T>?): Subscriber<in T> {
                            val subscriberResult = object : Subscriber<T>() {
                                override fun onNext(t: T?) {
                                    subscriber?.onNext(t)
                                }

                                override fun onCompleted() {
                                    subscriber?.onCompleted()
                                }

                                override fun onError(e: Throwable?) {
                                    subscriber?.onError(e)
                                }

                            }
                            compositeSubscription?.add(subscriberResult)
                            return subscriberResult
                        }

                    })
        }
    }

}