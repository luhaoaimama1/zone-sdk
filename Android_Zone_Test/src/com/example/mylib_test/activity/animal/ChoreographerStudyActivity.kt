package com.example.mylib_test.activity.animal

import android.view.Choreographer
import android.view.View
import com.example.mylib_test.LogApp
import com.example.mylib_test.R
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import kotlinx.android.synthetic.main.a_animal_choreographer.*

/**
 * 模仿ValueAnimator的学习
 */
class ChoreographerStudyActivity : BaseFeatureActivity() {
    val mChoreographer = Choreographer.getInstance()
    var isCircle = false
    var before = -1L
    val NANOS_PER_MS: Long = 1000000
    var count = 1

    //    anonymous
    val mFrameCallback = object : Choreographer.FrameCallback {
        override fun doFrame(frameTimeNanos: Long) {
            val now = frameTimeNanos / NANOS_PER_MS //纳秒转换成毫秒
            val s = if (before != -1L) (now - before).toString() else "第一次"
            LogApp.d("模拟 16ms now时间:$now \t 差值：$s")
            btUpdate.text = "update:${count++}"
            before = now
            if (isCircle) mChoreographer.postFrameCallback(this)

        }
    }

    override fun setContentView() {
        setContentView(R.layout.a_animal_choreographer)


        //测试 我添加一个callback 然后在回调中使用更新text 调用重绘 对有影响么？ 没有

        // postFrameCallback(callback)放入一个类型是CALLBACK_ANIMATION的
        // 然后从mChoreographer.mCallbackQueues[callbackType]不同类型的链表中通过extractDueCallbacksLocked取出CALLBACK_ANIMATION的回调
        // 执行回调的时候callback已经在mChoreographer中的extractDueCallbacksLocked清除掉了 你不添加就不会在执行

        // 然后在回调中使用更新text 调用重绘 ，然后走ViewRootImpl scheduleTraversals -> mChoreographer.postCallback(Choreographer.CALLBACK_TRAVERSAL, mTraversalRunnable, null);
        // 在mChoreographer.mCallbackQueues[callbackType]不同类型的链表中通过extractDueCallbacksLocked取出CALLBACK_TRAVERSAL的回调
        // 然后执行然后走ViewRootImpl#mTraversalRunnable 就是view的三大流程
        btUpdate.postDelayed({
            mChoreographer.postFrameCallback(mFrameCallback)
        }, 10000)
    }

    override fun initData() {
    }

    override fun setListener() {
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v?.id) {
            R.id.btStart -> {
                isCircle = true
                mChoreographer.postFrameCallback(mFrameCallback)
            }
            R.id.btEnd -> isCircle = false
        }
    }
}
