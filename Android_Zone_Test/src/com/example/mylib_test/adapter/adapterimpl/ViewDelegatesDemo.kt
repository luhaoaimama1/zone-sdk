package zone.com.zadapter3kt.adapterimpl

import android.view.View
import com.zone.adapter3kt.delegate.ViewDelegate

/**
 *[2019/3/5] by Zone
 */
abstract class ViewDelegatesDemo<T>: ViewDelegate<T,HolderExDemoImpl>() {
    override fun createHolder(view: View): HolderExDemoImpl {
        return HolderExDemoImpl(view)
    }
}