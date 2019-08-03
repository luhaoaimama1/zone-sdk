package com.zone.lib

import org.junit.Test

/**
 *[2019-08-02] by Zone
 */

private class Rect

class RecyclerPoolTest {

    @Test
    fun demoFinal() {
        val pool = RecyclerPool(object : RecyclerPool.CreateFactory<Rect> {
            override fun newInstance(): Rect = Rect()
        })
        val a = pool.get(0)//没有创建 有的话 复用
        val b = pool.get(0)

        assert(a == b)
        val c = pool.get(1)
        pool.holding(c)
        val d = pool.get(1)

        assert(c != d)
        pool.holding(d)
        pool.release(c)
        val e = pool.get(1)
        assert(c == e)

        //断定异常
        try {
            pool.isRelease(Rect())
        } catch (ex: IllegalStateException) {
            assert(true)
        }
    }
}

