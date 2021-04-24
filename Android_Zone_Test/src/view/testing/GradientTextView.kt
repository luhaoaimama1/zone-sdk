package view.testing

import android.content.Context
import android.graphics.BitmapShader
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.example.mylib_test.R

class GradientTextView@JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {
    private var gradientColorDrawalbe: Drawable? = null;

    init {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.GradientTextView, 0, 0)
            try {
                val drawable = typedArray.getDrawable(R.styleable.GradientTextView_gradientSrc)
                setGradientDrawable(drawable)

            } finally {
                typedArray.recycle()
            }
        }
    }

    private fun setGradientDrawable(drawable: Drawable?) {
        gradientColorDrawalbe = drawable
        postInvalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w != 0 && h != 0) {
            gradientColorDrawalbe?.apply {
                setBounds(0, 0, w, h)
                paint.shader = BitmapShader(Drawables.drawableToBitmap(this), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
                postInvalidate()
            }
        }
    }
}