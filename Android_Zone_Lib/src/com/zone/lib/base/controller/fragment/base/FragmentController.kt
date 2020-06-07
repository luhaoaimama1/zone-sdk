package com.zone.lib.base.controller.fragment.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zone.lib.base.controller.ViewController
import com.zone.lib.base.controller.BaseFeatureView
import java.lang.IllegalStateException

open class FragmentController<V : BaseFeatureView>(v: V) : ViewController<V>(v), Handler.Callback {

    fun getFragment(): FeatureFragment? {
        val view = getView()
        return view?.run {
            if (view is FeatureFragment) {
                view
            } else throw IllegalStateException("v must be FeatureFragment!")
        }
    }

    fun onAttach(context: Context?) {
    }

    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {}

    fun onViewCreated(view: View, savedInstanceState: Bundle?) {}

    fun onDetach() {
        removeAllMessage()
    }

    fun onStart() {
    }

    fun onResume() {
    }

    fun onPause() {
    }

    fun onStop() {
    }

    fun onDestroyView() {
    }

    fun onDestroy() {
    }

    open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {}

}
