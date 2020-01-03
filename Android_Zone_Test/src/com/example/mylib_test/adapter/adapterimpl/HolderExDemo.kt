package zone.com.zadapter3kt.adapterimpl

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.zone.adapter3kt.holder.BaseHolder
import com.zone.adapter3kt.holder.Holder

/**
 *[2019/3/5] by Zone
 */

abstract class HolderExDemo< T : androidx.recyclerview.widget.RecyclerView.ViewHolder>: BaseHolder<T> {
    constructor(view: View) : super(view)
    constructor(baseHolder: BaseHolder<*>) : super(baseHolder)
    fun ex1(): T {
        return getReturnValue()
    }
}
