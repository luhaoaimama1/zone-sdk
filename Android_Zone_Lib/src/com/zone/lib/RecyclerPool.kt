package com.zone.lib

import androidx.collection.SparseArrayCompat

/**
 * Created by qibin on 16-9-25.
 * extend：Zone
 *
 *  横向列表(普通) 0-1-2-3
 *
 *  每个位置 对应两个竖向列表 a(release),b(holding)（不考虑效率话了(单列表)  这样简单。）
 *
 *  每个位置 存着一个 竖向列表 。所有情况
 *      1.没有  生成 - 取出
 *      2.有  没有被持有 -取出
 *      3.有   被持有 ，生成 -取出
 *
 * 使用范例:如果 在view的onMeasure中 每次走都会复用实例如果没有创建
 *
 * 单元测试： RecyclerPoolTest.kt
 */
class RecyclerPool<T>(private val mCreateFactory: CreateFactory<T>) {
    interface CreateFactory<T> {
        fun newInstance(): T
    }

    internal class Binder(var horizontalKey: Int = -1, var isRelease: Boolean = true)
    internal class DoubleList<T> {
        val holdingSet: HashSet<T> by lazy {
            HashSet<T>()
        }
        val releaseSet: HashSet<T> by lazy {
            HashSet<T>()
        }
    }

    //android  替换成SparseArrayCompat
    private val mPool: SparseArrayCompat<DoubleList<T>?> = SparseArrayCompat()
    private val instanceBindMap: HashMap<T, Binder> by lazy {
        HashMap<T, Binder>()
    }

    operator fun get(key: Int): T {
        //没有的话 放入一个,res 这回一定有了
        val res = getDoubleList(key)
        //看看 release中有没有 == > 有则返回这个  |  没有生成一个 并返回这个
        return res.releaseSet.firstOrNull() ?: mCreateFactory.newInstance().apply {
            res.releaseSet.add(this)
            //实例生成的时候  绑定对象 放入位置,与是否 释放属性
            instanceBindMap.put(this, Binder(key))
        }
    }

    private fun getDoubleList(key: Int): DoubleList<T> =
        mPool.get(key) ?: DoubleList<T>().apply {
            mPool.put(key, this)
        }

    fun isRelease(item: T): Boolean = safeCheck(item).isRelease

    fun release(item: T) {
        val bindObj = safeCheck(item)
        if (bindObj.horizontalKey < 0) return
        val res = getDoubleList(bindObj.horizontalKey)
        res.holdingSet.remove(item)
        res.releaseSet.add(item)
        bindObj.isRelease = true
    }

    private fun safeCheck(item: T): Binder {
        return instanceBindMap[item] ?: throw IllegalStateException("this obj not create by RecyclerPool's mCreateFactory")
    }

    fun holding(item: T) {
        val bindObj = safeCheck(item)
        if (bindObj.horizontalKey < 0) return
        val res = getDoubleList(bindObj.horizontalKey)
        res.releaseSet.remove(item)
        res.holdingSet.add(item)
        bindObj.isRelease = false
    }
}



