package view

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import androidx.customview.widget.ViewDragHelper
import com.example.mylib_test.LogApp
import com.zone.lib.utils.view.graphics.MathUtils

class DragLimitRelativeLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    private val mCallback: ViewDragHelper.Callback = object : ViewDragHelper.Callback() {

        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return true
        }

        override fun getViewHorizontalDragRange(child: View): Int = 1
        override fun getViewVerticalDragRange(child: View): Int = 1

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {

            val widthLimit = width - child.width
            val clampH: Int = MathUtils.clamp(left, 0, widthLimit)
            LogApp.d("clampViewPositionHorizontal:$clampH \tleft:$left")
            return clampH
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            val bottomLimit = height - child.height
            val clampV: Int = MathUtils.clamp(top, 0, bottomLimit)
            LogApp.d("clampViewPositionVertical:$top \ttop:$top")
            return clampV
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            super.onViewReleased(releasedChild, xvel, yvel)
//            mViewDragHelper.smoothSlideViewTo(mMoveView, 0, mIvLayoutInitTop);
        }
    }
    private val mViewDragHelper: ViewDragHelper = ViewDragHelper.create(this, mCallback);


    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        mViewDragHelper.processTouchEvent(event)
//        when (event.action) {
//            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> if (isViewDragProcessTouch) {
//                isViewDragProcessTouch = false
//            }
//        }
        val dispatchTouchEvent = super.dispatchTouchEvent(event)

        return super.dispatchTouchEvent(event)
    }

    val rectLimit = Rect()

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (changed) {
            val view = parent as? View
            rectLimit.set(view?.left ?: 0, view?.top ?: 0, view?.right ?: 0, view?.bottom ?: 0)
            LogApp.d("限定：rectlimit ${rectLimit.toShortString()}")
        }
    }

}