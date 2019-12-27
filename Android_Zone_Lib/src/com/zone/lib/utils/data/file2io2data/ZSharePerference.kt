package com.zone.lib.utils.data.file2io2data

import android.content.Context
import android.content.SharedPreferences

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import com.zone.lib.Configure
import com.zone.lib.LogZSDK
import com.zone.lib.utils.data.check.StringCheck
import com.zone.lib.utils.data.convert.GsonUtils
import java.util.*

/**
 * 引用项目:https://github.com/openproject/LessCode
 * 不习惯$符号的方法名,所以改了下；
 */
open class ZSharePerference protected constructor(val shareName: String) {

    private val weakHashMap: WeakHashMap<Any, Any> by lazy {
        WeakHashMap<Any, Any>()
    }

    private val sharedPreferences: SharedPreferences
        get() = Configure.getApplicationContext()
                .getSharedPreferences(shareName, Context.MODE_PRIVATE)

    val all: Map<String, *>
        get() = sharedPreferences.all


    fun <T> put(key: String, value: T?) {
        val sp = sharedPreferences
        val editor = sp.edit()

        weakHashMap.put(key, value)

        if (value == null) {
            // if value is null, just handler it as a String
            editor.putString(key, null)
        } else {
            if (value is Boolean) {
                editor.putBoolean(key, (value as Boolean?)!!)
            } else if (value is Float) {
                editor.putFloat(key, (value as Float?)!!)
            } else if (value is Int) {
                editor.putInt(key, (value as Int?)!!)
            } else if (value is Long) {
                editor.putLong(key, (value as Long?)!!)
            } else if (value is String) {
                editor.putString(key, value as String?)
            } else {
                editor.putString(key, GsonUtils.toJson(value))
                //throw new RuntimeException("the put value type can't support.");
            }
        }
        SharedPreferencesCompat.apply(editor)
    }

    operator fun <T> get(key: String, t: Class<T>): T? {
        val value = getValueFromCache<T>(key)
        return value ?: getValueByClassInner(key, t)
    }

    private fun <T> getValueFromCache(key: String): T? {
        val value = (weakHashMap.get(key) as? T).apply {
            LogZSDK.d("from cache: key:$key \t value:$this")
        }
        return value
    }

    private fun <T> getValueByClassInner(key: String, t: Class<T>): T? {
        val content = sharedPreferences.getString(key, "")
        return if (StringCheck.isEmptyTrim(content)) null
        else GsonUtils.fromJson(content, t)
    }

    operator fun <T> get(key: String, defaultValue: T): T {
        val value = getValueFromCache<T>(key)
        return value ?: getValueInner(defaultValue, key)
    }

    private fun <T> getValueInner(defaultValue: T, key: String): T {
        var result: T = defaultValue
        when (defaultValue) {
            is String -> {
                result = sharedPreferences.getString(key, defaultValue) as T
            }
            is Boolean -> {
                result = sharedPreferences.getBoolean(key, defaultValue) as T
            }
            is Float -> {
                result = sharedPreferences.getFloat(key, defaultValue) as T
            }
            is Int -> {
                result = sharedPreferences.getInt(key, defaultValue) as T
            }
            is Long -> {
                result = sharedPreferences.getLong(key, defaultValue) as T
            }
            else -> {
                throw  RuntimeException("the put value type can't support.");
            }
        }
        return result
    }

    fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    operator fun contains(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    fun remove(key: String) {
        val editor = sharedPreferences.edit()
        editor.remove(key)
        SharedPreferencesCompat.apply(editor)
    }

    fun clear() {
        val editor = sharedPreferences.edit()
        editor.clear()
        SharedPreferencesCompat.apply(editor)
    }


    /**
     * *********************************************************************************************
     * Unlike commit(), which writes its preferences out to persistent storage synchronously,
     * apply() commits its changes to the in-memory SharedPreferencesimmediately
     * but starts an asynchronous commit to disk and you won't be notified of any failures.
     * If another editor on this SharedPreferences does a regularcommit() while a apply() is still outstanding,
     * the commit() will block until all async commits are completed as well as the commit itself.
     * *********************************************************************************************
     */
    private object SharedPreferencesCompat {

        private val sApplyMethod = findApplyMethod()

        /**
         * check apply mthod by reflect
         *
         * @return
         */
        private fun findApplyMethod(): Method? {
            try {
                val clz = SharedPreferences.Editor::class.java
                return clz.getMethod("apply")
            } catch (e: NoSuchMethodException) {
            }

            return null
        }

        /**
         * if it has apply(), use apply() first;
         * else just use the commit().
         *
         * @param editor
         */
        fun apply(editor: SharedPreferences.Editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor)
                    return
                }
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            }

            editor.commit()
        }
    }
}
