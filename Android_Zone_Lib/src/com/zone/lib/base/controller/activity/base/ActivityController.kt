package com.zone.lib.base.controller.activity.base

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import com.zone.lib.base.controller.BaseFeatureView
import com.zone.lib.base.controller.ViewController
import java.lang.IllegalStateException

open class ActivityController<V : BaseFeatureView>(v: V) : ViewController<V>(v), Handler.Callback {

    fun getActivity(): FeatureActivity? {
        val view = getView()
        return view?.run {
            if (view is FeatureActivity) {
                view
            } else throw IllegalStateException("v must be FeatureActivity!")
        }
    }

    /**
     * @param bundle
     * @param activity 唯一有差别的地方
     */
    open fun onCreateBefore(bundle: Bundle?) {}

    open fun onCreateAfter(bundle: Bundle?) {}

    open fun onPostCreate(savedInstanceState: Bundle?) {}

    open fun onStart() {
    }

    open fun onResume() {
    }

    open fun onStop() {

    }

    open fun onPause() {
    }

    open fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
    }

    open fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

    }
    open fun onOptionsItemSelected(item: MenuItem?): Boolean =false

    open fun onDestroy() {
        removeAllMessage()
    }

}
