package androidx.recyclerview.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener
import com.example.mylib_test.LogApp

class PagerSnapLinearLayoutManager : LinearLayoutManager {
    interface OnViewPagerListener {
        fun onInitComplete()
        fun onPageSelected(position: Int, isBottom: Boolean)
    }

    private var lastSelectPosition = RecyclerView.NO_POSITION
    var viewPagerListener: OnViewPagerListener? = null
    val pagerSnapHelper = FilterPagerSnapHelper()
    var findSnapView: IFindSnapView? = null
        set(value) {
            field = value
            pagerSnapHelper.findSnapView = value
        }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        LogApp.d("onScrollStateChanged$state")
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            notifyOnPageSelected()
        }
    }

    override fun scrollToPosition(position: Int) {
        LogApp.d("scrollToPosition")
        super.scrollToPosition(position)
    }

    private fun notifyOnPageSelected() {
        val viewIdle: View = pagerSnapHelper.findSnapView(this) ?: return
        val positionIdle = getPosition(viewIdle)
        notifySelectPosi(positionIdle)
    }

    private fun notifySelectPosi(positionIdle: Int) {
        if (positionIdle != lastSelectPosition) {
            lastSelectPosition = positionIdle
            viewPagerListener?.onPageSelected(positionIdle, positionIdle == itemCount - 1)
        }
    }

    //不用 因为 这个吸附用不到offset，如果用了offset也不会滚动，因为snap是依赖滚动监听进行吸附的
    override fun scrollToPositionWithOffset(position: Int, offset: Int) {
        LogApp.d("scrollToPositionWithOffset")
        super.scrollToPositionWithOffset(position, offset)
    }

    private var inited = false
    override fun onLayoutCompleted(state: RecyclerView.State?) {
        super.onLayoutCompleted(state)
        LogApp.d("onLayoutCompleted")
        //通知选中的页
        onScrollStateChanged(RecyclerView.SCROLL_STATE_IDLE)

        //如果需要吸附 就吸附一般用于 scrollToPositionWithOffset
        // 因为这个不会触发SnapHelper里面的滚动监听所以不会吸附
        pagerSnapHelper.snapNotFling()
    }

    override fun onAttachedToWindow(view: RecyclerView?) {
        super.onAttachedToWindow(view)
        pagerSnapHelper.attachToRecyclerView(view)
        view?.addOnChildAttachStateChangeListener(mChildAttachStateChangeListener)
    }

    override fun onDetachedFromWindow(view: RecyclerView?, recycler: RecyclerView.Recycler?) {
        super.onDetachedFromWindow(view, recycler)
        view?.removeOnChildAttachStateChangeListener(mChildAttachStateChangeListener)
    }

    //不能在onLayoutCompleted 弄onInitComplete,因为如果没有数据的话后续添加是不会从新 layout的。
    private val mChildAttachStateChangeListener: OnChildAttachStateChangeListener = object : OnChildAttachStateChangeListener {
        override fun onChildViewAttachedToWindow(view: View) {
            if (viewPagerListener != null && childCount >0 && !inited) {
                viewPagerListener!!.onInitComplete()
                inited = true
                mRecyclerView?.removeOnChildAttachStateChangeListener(this)
            }
        }

        override fun onChildViewDetachedFromWindow(view: View) {}
    }
}