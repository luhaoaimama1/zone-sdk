package com.zone.lib.utils.view.graphics

/**
 * Created by fuzhipeng on 2016/11/15.
 */
//阻尼工具类
object DampingUtils {


    /**
     * 参考：DecelerateInterpolator
     *
     * @param x 移动的值
     * @param maxX 最大的移动值 达到最大的话 return的值也达到最大了
     * @param maxY return值返回的最大值
     * @param dampingRadio 控制曲线的曲率。 2正好  1线性。>1 后来的阻力越大前期越快 <1超级不建议
     * @return
     */
    fun damping(x: Float, maxX: Float, maxY: Float, dampingRadio: Float): Float {
        //0-1 的值的曲线才是对的
        //todo  这里 超过了 没有被先定到最大值
        val powR = MathUtils.linear(x, 0.0, maxX * 1F, 0.0, 1.0);
        val pow = Math.pow((1 - powR), (dampingRadio).toDouble()).toFloat()
        return (1-pow )* maxY
    }

}
