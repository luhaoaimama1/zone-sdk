package com.zone.lib.base.controller.activity.base

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.zone.lib.utils.activity_fragment_ui.handler.WeakRefHandler
import com.zone.lib.base.controller.BaseFeatureView
import java.util.*

/**
 * //todo zone
 *      putSetting{
 *          状态栏
 *          title
 *          }
 */
abstract class FeatureActivity : AppCompatActivity(), WeakRefHandler.Callback, BaseFeatureView
        , View.OnClickListener {

    lateinit var mainHandler: WeakRefHandler
    protected var kindsManagerMap: HashMap<Class<*>, ActivityController<*>> = HashMap()

    fun registerPrestener(controller: ActivityController<*>) {
        kindsManagerMap.put(controller::class.java, controller)
    }

    fun <T : ActivityController<*>> unRegisterPrestener(key: Class<T>) {
        kindsManagerMap.remove(key)
    }

    fun <T : ActivityController<*>> getController(key: Class<T>): T? {
        return kindsManagerMap.get(key) as? T
    }

    fun iterateDo(method: ActivityController<*>.() -> Unit) {
        for (item in kindsManagerMap.entries) {
            method.invoke(item.value)
        }
    }

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        mainHandler = WeakRefHandler(this)
        initBaseControllers()
        onCreateBefore(bundle)
        setContentView()
        onCreateAfter(bundle)
        findIDs()
        initData()
        setListener()
    }

    open fun onCreateBefore(bundle: Bundle?) {
        iterateDo { onCreateBefore(bundle) }
    }

    open fun onCreateAfter(bundle: Bundle?) {
        iterateDo { onCreateAfter(bundle) }
    }

    fun initPrivateControllers(vararg controllers: ActivityController<*>) {
        controllers.forEach {
            registerPrestener(it)
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        iterateDo { onPostCreate(savedInstanceState) }
    }

    override fun onResume() {
        super.onResume()
        iterateDo { onResume() }
    }

    override fun onPause() {
        super.onPause()
        iterateDo { onPause() }
    }

    override fun onStart() {
        super.onStart()
        iterateDo { onStart() }
    }

    override fun onStop() {
        super.onStop()
        iterateDo { onStop() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        iterateDo { onActivityResult(requestCode, resultCode, data) }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        iterateDo { onRequestPermissionsResult(requestCode, permissions, grantResults) }
    }

    override fun onDestroy() {
        super.onDestroy()
        iterateDo { onDestroy() }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var result = false
        iterateDo {
            val onOptionsItemSelected = onOptionsItemSelected(item)
            if (onOptionsItemSelected == true) {
                result = onOptionsItemSelected
            }
        }
        return if (result) result
        else super.onOptionsItemSelected(item)
    }

    // ---------------本类抽象的方法-------------------
    open fun initBaseControllers() {
    }

    /**
     * 设置子类布局对象
     */
    abstract fun setContentView()

    /**
     * 子类查找当前界面所有id
     */
    fun findIDs() {

    }

    /**
     * 子类初始化数据
     */
    abstract fun initData()

    /**
     * 子类设置事件监听
     */
    abstract fun setListener()

    override fun handleMessageSafe(msg: Message) {

    }

    override fun onClick(v: View?) {
    }
}