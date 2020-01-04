package com.zone.lib.base.controller.activity.controller

import android.app.Activity
import android.os.Bundle
import com.zone.lib.base.controller.activity.ActivityController
import com.zone.lib.base.controller.activity.FeatureActivity
import java.util.concurrent.CopyOnWriteArrayList

class CollectionActivityController(activity: FeatureActivity) : ActivityController<FeatureActivity>(activity)
        , CollectionContract.Controller {

    companion object {
        var activitys: MutableList<Activity> = CopyOnWriteArrayList()
    }

    override fun onCreateBefore(bundle: Bundle?) {
        super.onCreateBefore(bundle)
        getActivity()?.apply {
            activitys.add(this)
        }
    }

    override fun onDestroy() {
        /* 防止内存泄漏 */
        getActivity()?.apply {
            activitys.remove(this)
        }
        super.onDestroy()
    }

    /**
     * 结束所有 还存在的activitys  一般在异常出现的时候
     */
    override fun finishAll() {
        for (item in activitys) {
            item.finish()
        }
    }

}
