package com.example.mylib_test.activity.three_place

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Intent
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.airbnb.lottie.LottieComposition
import com.bumptech.glide.Glide
import com.example.mylib_test.LogApp
import com.example.mylib_test.R
import com.example.mylib_test.activity.http.event.FirstEvent
import com.zone.lib.base.controller.RxComposes
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import com.zone.lib.base.controller.activity.controller.CollectionActivityController
import kotlinx.android.synthetic.main.a_asynctask_test.*
import kotlinx.android.synthetic.main.a_thirdparty.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import rx.Observable
import rx.observers.Observers
import rx.subscriptions.CompositeSubscription
import java.util.concurrent.TimeUnit

/**
 *[2018/7/10] by Zone
 */
class ThirdParty_MainActivity : BaseFeatureActivity(), View.OnClickListener {

    private val subscriptions = CompositeSubscription()
    override fun setContentView() {
        setContentView(R.layout.a_thirdparty)
        //rxjava
        Observable.just(1, 2, 3)
            .compose(RxComposes.asycTransformer(subscriptions))
            .subscribe({

            })
        EventBus.getDefault().register(this)
    }

    val liveData = MutableLiveData<Int>()
    val everObserver=object :Observer<Int>{
        override fun onChanged(t: Int) {
            LogApp.d("Forever onchanged:"+t.toString())
        }
    }
    override fun initData() {
        liveData.observeForever(everObserver)
        liveData.observe(this, object :Observer<Int>{
            override fun onChanged(t: Int) {
                LogApp.d("not  Forever onchanged:"+t.toString())
            }
        })
        lav.imageAssetsFolder="checkNotificationLong/images/"
        lav.setAnimation("checkNotificationLong/data_short.json")
        lav.repeatCount=-1
        lav.addAnimatorListener(object :AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                println("onAnimationEnd")
                lav.imageAssetsFolder="air/images/"
                lav.setAnimation("air/data.json")
                lav.playAnimation()
            }

            override fun onAnimationCancel(animation: Animator?) {
                super.onAnimationCancel(animation)
                println("onAnimationCancel")
            }
        })
        lav.playAnimation()
//        lav.cancelAnimation()
//        LottieComposition.Factory.fromAssetFileName(this,"checkNotificationLong/data_short.json",{
//            lav.setComposition(it!!)
//            lav.cancelAnimation();
//            val ofFloat = ValueAnimator.ofFloat(0f, 1f)
//            ofFloat.addUpdateListener {
//                val animatedValue = it.animatedValue
//                lav.progress= animatedValue as Float
//            }
//            ofFloat.start()
//        })

    }


    override fun onDestroy() {
        super.onDestroy()
        liveData.removeObserver(everObserver)
        EventBus.getDefault().unregister(this)
    }

    data class EventInt(val a: Int)
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun zoneEvent(event: EventInt) { /* Do something */
        liveData.value = event.a
    }

    override fun onStart() {
        super.onStart()
        LogApp.d("  Forever onStart")
    }

    override fun onPause() {
        super.onPause()
        LogApp.d("  Forever onPause")
    }

    override fun onStop() {
        super.onStop()
        LogApp.d("  Forever onStop")
    }

    override fun setListener() {
    }

    override fun onClick(v: View?) {
        v?.visibility
        when (v?.getId()) {
            R.id.google_pull -> startActivity(Intent(this, GooglePullActvity::class.java))
            R.id.bt_glide -> startActivity(Intent(this, GildeActivity::class.java))
            R.id.bt_fresco -> startActivity(Intent(this, FrescoActivity::class.java))
//            R.id.bt_swtichButton -> startActivity(Intent(this, RippleViewActivity::class.java))
            R.id.bt_blur -> startActivity(Intent(this, BlurActivity::class.java))
            R.id.lifeCycle -> startActivity(Intent(this, LifecycleActivity::class.java))
            R.id.live_data -> startActivity(Intent(this, LiveDataSendActivity::class.java))
            else -> {
            }
        }
    }

}