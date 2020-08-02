package com.zone.lib.utils.view.graphics

/**
 *[2018/8/24] by Zone
 */
object MathUtils {

    enum class Linear {
        OverMax, OverOver;
    }

    //    t, tMin, tMax, value1, value2
    @Suppress("UNCHECKED_CAST")
    @JvmStatic
    fun <T : Number, R : Number> linear(srcNow: T, src1: T, src2: T, dst1: R, dst2: R, type: Linear? = Linear.OverMax): R {
        //需要精度
        val radio: Number = when (srcNow) {
            is Float, is Int -> (srcNow.toFloat() - src1.toFloat()) / (src2.toFloat() - src1.toFloat())
            is Double, is Long -> (srcNow.toDouble() - src1.toDouble()) / (src2.toDouble() - src1.toDouble())
            else -> throw IllegalStateException("状态异常")
        }
        //按照精度计算 后返回等结果as R
        val result: R = when (radio) {
            is Double, is Long -> radio.toDouble() * (dst2.toDouble() - dst1.toDouble()) + dst1.toDouble()
            is Float, is Int -> radio.toFloat() * (dst2.toFloat() - dst1.toFloat()) + dst1.toFloat()
            else -> throw IllegalStateException("状态异常")
        } as R

        return when (type) {
            Linear.OverMax -> {
                if (dst2.toDouble() - dst1.toDouble() > 0) clamp(result, dst1, dst2)
                else clamp(result, dst2, dst1)
            }
            Linear.OverOver -> result
            else -> throw IllegalStateException("状态异常")
        }
    }

    @Suppress("UNCHECKED_CAST")
    @JvmStatic
    fun <T : Number> clamp(value: T, min: T, max: T): T {
        return when (value) {
            is Float -> when {
                value.toFloat() < min.toFloat() -> min.toFloat()
                value.toFloat() > max.toFloat() -> max.toFloat()
                else -> value.toFloat()
            }
            is Double -> when {
                value.toDouble() < min.toDouble() -> min.toDouble()
                value.toDouble() > max.toDouble() -> max.toDouble()
                else -> value.toDouble()
            }
            is Int -> when {
                value.toInt() < min.toInt() -> min.toInt()
                value.toInt() > max.toInt() -> max.toInt()
                else -> value.toInt()
            }
            is Long -> when {
                value.toLong() < min.toLong() -> min.toLong()
                value.toLong() > max.toLong() -> max.toLong()
                else -> value.toLong()
            }
            else -> throw IllegalStateException("状态异常")
        } as T
    }

//    @JvmStatic
//    fun main(args: Array<String>) {
//        println("============正逆逻辑验证============")
//        println("\t逆向")
//        println("\t \tint:${linear(30, 0, 100, 1000, 0)}")
//        println("\t \tdouble:${linear(30.0, 0.0, 100.0, 1000.0, 0.0)}")
//        println("\t \tlong:${linear(30, 0L, 100L, 1000L, 0L)}")
//        println("\t \tfloat:${linear(30, 0F, 100F, 1000F, 0F)}")
//
//        println("\t正向")
//        println("\t\tint:${linear(30, 0, 100, 0, 1000)}")
//        println("\t\tdouble:${linear(30.0, 0.0, 100.0, 0.0, 1000.0)}")
//        println("\t\tlong:${linear(30, 0L, 100L, 0L, 1000L)}")
//        println("\t\tfloat:${linear(30, 0F, 100F, 0F, 1000F)}")
//
//
//        println("============超出逻辑验证============")
//
//        println("\t超过 OverMax")
//        println("\t\tint:${linear(-30, 0, 100, 0, 1000)}")
//        println("\t\tdouble:${linear(-30.0, 0.0, 100.0, 0.0, 1000.0)}")
//        println("\t\tlong:${linear(-30, 0L, 100L, 0L, 1000L)}")
//        println("\t\tfloat:${linear(-30, 0F, 100F, 0F, 1000F)}")
//
//        println("\t超过 OverOver")
//        println("\t\tint:${linear(-30, 0, 100, 0, 1000, Linear.OverOver)}")
//        println("\t\tdouble:${linear(-30.0, 0.0, 100.0, 0.0, 1000.0, Linear.OverOver)}")
//        println("\t\tlong:${linear(-30, 0L, 100L, 0L, 1000L, Linear.OverOver)}")
//        println("\t\tfloat:${linear(-30, 0F, 100F, 0F, 1000F, Linear.OverOver)}")
//    }
}