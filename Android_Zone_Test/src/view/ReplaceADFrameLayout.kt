package view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout


class ReplaceADFrameLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    var delegateView: ViewGroup? = null
    val restoreViews = ArrayList<View>()

    fun setDelegateViewGroup(view: ViewGroup) {
        restoreViews()

        delegateView = view
        for (i in 0 until childCount) {
            val element = getChildAt(i)

            if (element != null) {
                restoreViews.add(element)
                view.addView(element)
                removeView(element)
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        restoreViews()
    }

    fun restoreViews() {
        if (delegateView != null) {
            removeView(delegateView)
            delegateView = null;
        }
        for (restoreView in restoreViews) {
            (restoreView.parent as? ViewGroup)?.removeView(restoreView)
            addView(restoreView)
        }
    }
}