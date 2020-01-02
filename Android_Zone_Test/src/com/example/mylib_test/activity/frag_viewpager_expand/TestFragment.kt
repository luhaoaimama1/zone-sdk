package com.example.mylib_test.activity.frag_viewpager_expand

import android.os.Bundle
import androidx.fragment.app.Fragment

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView

class TestFragment : Fragment() {

    private var mContent: String? = "???"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_CONTENT)) {
            mContent = savedInstanceState.getString(KEY_CONTENT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val text = TextView(activity)
        text.gravity = Gravity.CENTER
        text.text = mContent
        text.textSize = 20 * resources.displayMetrics.density
        text.setPadding(20, 20, 20, 20)

        val layout = LinearLayout(activity)
        layout.layoutParams = LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT)
        layout.gravity = Gravity.CENTER
        layout.addView(text)

        return layout
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_CONTENT, mContent)
    }

    companion object {
        private val KEY_CONTENT = "TestFragment:Content"

        fun newInstance(content: String): TestFragment {
            val fragment = TestFragment()

            val builder = StringBuilder()
            for (i in 0..19) {
                builder.append(content).append(" ")
            }
            builder.deleteCharAt(builder.length - 1)
            fragment.mContent = builder.toString()

            return fragment
        }
    }
}