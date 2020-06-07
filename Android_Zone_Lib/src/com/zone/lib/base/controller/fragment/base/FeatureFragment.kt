package com.zone.lib.base.controller.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import java.util.*

open class FeatureFragment : Fragment() {

    protected var kindsManagerMap: HashMap<Class<*>, FragmentController<*>> = HashMap()
    private var hasInitKindsControl = false

    fun registerPrestener(controller: FragmentController<*>) {
        kindsManagerMap.put(controller::class.java, controller)
    }

    fun <T : FragmentController<*>> getController(key: Class<T>): T {
        return kindsManagerMap.get(key) as T
    }

    fun iterateDo(method: FragmentController<*>.() -> Unit) {
        for (item in kindsManagerMap.entries) {
            method.invoke(item.value)
        }
    }

    open fun initKindsControl() {
    }

    override fun onAttach(context: Context?) {
        if (!hasInitKindsControl) {
            initKindsControl()
            hasInitKindsControl = true
        }
        super.onAttach(context)
        iterateDo { onAttach(context) }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        iterateDo { onCreateView(inflater, container, savedInstanceState) }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iterateDo { onViewCreated(view, savedInstanceState) }
    }

    override fun onStart() {
        super.onStart()
        iterateDo { onStart() }
    }


    override fun onResume() {
        super.onResume()
        iterateDo { onResume() }
    }

    override fun onPause() {
        super.onPause()
        iterateDo { onPause() }
    }

    override fun onStop() {
        super.onStop()
        iterateDo { onStop() }
    }

    override fun onDestroy() {
        super.onDestroy()
        iterateDo { onDestroy() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        iterateDo { onDestroyView() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        iterateDo { onActivityResult(requestCode, resultCode, data) }
    }
}