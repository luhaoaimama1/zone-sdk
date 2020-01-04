package zone.com.zadapter3kt.adapterimpl

import android.view.View
import com.zone.adapter3kt.holder.BaseHolder
import com.zone.adapter3kt.holder.Holder

/**
 *[2019/3/5] by Zone
 */

open class HolderExDemoImpl : HolderExDemo<HolderExDemoImpl> {
    constructor(view: View) : super(view)
    constructor(baseHolder: BaseHolder<*>) : super(baseHolder)

    override fun getReturnValue(): HolderExDemoImpl = this

    fun ex2(): HolderExDemoImpl {
        return getReturnValue()
    }
}
