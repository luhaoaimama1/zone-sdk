package com.zone.lib.utils.view.graphics

/**
 *[2018/8/24] by Zone
 */
object MathUtils {

    //    t, tMin, tMax, value1, value2
    @Deprecated(" 请用下面的 method linear")
    fun <T : Number, R : Number> linearMap(srcNow: T, src1: T, src2: T, dst1: R, dst2: R): R {

        val radio: Float = when (srcNow) {
            is Float -> (srcNow.toFloat() - src1.toFloat()) / (src2.toFloat() - src1.toFloat())
            is Double -> ((srcNow.toDouble() - src1.toDouble()) / (src2.toDouble() - src1.toDouble())).toFloat()
            is Int -> ((srcNow.toInt() - src1.toInt()) / (src2.toInt() - src1.toInt())).toFloat()
            is Long -> ((srcNow.toLong() - src1.toLong()) / (src2.toLong() - src1.toLong())).toFloat()
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

    enum class Linear {
        OverMax, OverOver;
    }

    //    t, tMin, tMax, value1, value2

    fun <T : Number, R : Number> linear(srcNow: T, src1: T, src2: T, dst1: R, dst2: R, type: Linear? = Linear.OverMax): R {

        val radio: Float = when (srcNow) {
            is Float -> (srcNow.toFloat() - src1.toFloat()) / (src2.toFloat() - src1.toFloat())
            is Double -> ((srcNow.toDouble() - src1.toDouble()) / (src2.toDouble() - src1.toDouble())).toFloat()
            is Int -> ((srcNow.toInt() - src1.toInt()) / (src2.toInt() - src1.toInt())).toFloat()
            is Long -> ((srcNow.toLong() - src1.toLong()) / (src2.toLong() - src1.toLong())).toFloat()
            else -> throw IllegalStateException("状态异常")
        }
        val result: Float = when (dst1) {
            is Double -> (radio * (dst2.toDouble() - dst1.toDouble()) + dst1).toFloat()
            is Float -> radio * (dst2.toFloat() - dst1.toFloat()) + dst1
            is Int -> radio * (dst2.toInt() - dst1.toInt()).toFloat() + dst1.toFloat()
            is Long -> radio * (dst2.toLong() - dst1.toLong()) + dst1
            else -> throw IllegalStateException("状态异常")
        }
        val realResult = if (type == Linear.OverMax) {
            if (dst2.toFloat() > dst1.toFloat()) getOverMax(result, dst1, dst2)
            else getOverMax(result, dst2, dst1)
        } else result

        return realResult as R

    }

    /**
     * dst2>dst1
     */
    private fun <R : Number> getOverMax(result: Float, dst1: R, dst2: R): Float {
        var realResult1 = result
        if (result > dst2.toFloat()) realResult1 = dst2.toFloat()
        if (result < dst1.toFloat()) realResult1 = dst1.toFloat()
        return realResult1
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println( linear(0F, 1F, 0F, 0.5F, 0F))
    }
}