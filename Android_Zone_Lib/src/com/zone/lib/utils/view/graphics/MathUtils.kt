package com.zone.lib.utils.view.graphics

/**
 *[2018/8/24] by Zone
 */
object MathUtils {

    enum class Linear {
        OverMax, OverOver;
    }

    //    t, tMin, tMax, value1, value2
    fun <T : Number, R : Number> linear(srcNow: T, src1: T, src2: T, dst1: R, dst2: R, type: Linear? = Linear.OverMax): R {

        val srcNow2 = when (type) {
            Linear.OverMax -> getMax(srcNow, src1, src2)
            Linear.OverOver -> srcNow
            else -> getMax(srcNow, src1, src2)
        }

        val radio: Float = when (srcNow2) {
            is Float -> (srcNow2.toFloat() - src1.toFloat()) / (src2.toFloat() - src1.toFloat())
            is Double -> ((srcNow2.toDouble() - src1.toDouble()) / (src2.toDouble() - src1.toDouble())).toFloat()
            is Int -> ((srcNow2.toInt() - src1.toInt()) / (src2.toInt() - src1.toInt())).toFloat()
            is Long -> ((srcNow2.toLong() - src1.toLong()) / (src2.toLong() - src1.toLong())).toFloat()
            else -> throw IllegalStateException("状态异常")
        }
        val result: Float = when (dst1) {
            is Double -> (radio * (dst2.toDouble() - dst1.toDouble()) + dst1).toFloat()
            is Float -> radio * (dst2.toFloat() - dst1.toFloat()) + dst1
            is Int -> radio * (dst2.toInt() - dst1.toInt()).toFloat() + dst1.toFloat()
            is Long -> radio * (dst2.toLong() - dst1.toLong()) + dst1
            else -> throw IllegalStateException("状态异常")
        }
        return when (dst1) {
            is Double -> result.toDouble()
            is Float -> result
            is Int -> result.toInt()
            is Long -> result.toLong()
            else -> throw IllegalStateException("状态异常")
        } as R
    }

    private fun <T : Number> getMax(srcNow: T, src1: T, src2: T): T {
        return when (srcNow) {
            is Float -> {
                when {
                    srcNow.toFloat() < src1.toFloat() -> src1
                    srcNow.toFloat() > src2.toFloat() -> src2
                    else -> srcNow
                }
            }
            is Double -> {
                when {
                    srcNow.toFloat() < src1.toDouble() -> src1
                    srcNow.toFloat() > src2.toDouble() -> src2
                    else -> srcNow
                }
            }
            is Int -> {
                when {
                    srcNow.toInt() < src1.toInt() -> src1
                    srcNow.toInt() > src2.toInt() -> src2
                    else -> srcNow
                }
            }
            is Long -> {
                when {
                    srcNow.toLong() < src1.toLong() -> src1
                    srcNow.toLong() > src2.toLong() -> src2
                    else -> srcNow
                }
            }
            else -> throw IllegalStateException("状态异常")
        }
    }


    @JvmStatic
    fun main(args: Array<String>) {

    }
}