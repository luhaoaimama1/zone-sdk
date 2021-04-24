package com.example.mylib_test.activity.lifecircle

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mylib_test.R
import com.example.mylib_test.activity.lifecircle.helper.IFragmentLife

class FragmentStudyNullFragment : FragmentStudyTagFragment(), IFragmentLife {
    companion object {
        fun newInstance(tag: String, isViewpager: Boolean): Fragment {
            val fragmentStudyViewPagerFragment = FragmentStudyNullFragment()
            fragmentStudyViewPagerFragment.printTag = tag
            fragmentStudyViewPagerFragment.fragmentLifeHelper.switchModeIsViewPager = isViewpager
            return fragmentStudyViewPagerFragment
        }
    }

    val colorArry = intArrayOf(Color.WHITE, Color.GREEN, Color.YELLOW, Color.CYAN)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val inflateView = inflater.inflate(R.layout.a_lifecircle_fragment_study_fragment, null)
        inflateView.findViewById<TextView>(R.id.tv).text = printTag
        val d = (Math.random() * 100).toInt() % 4
        inflateView.setBackgroundColor(colorArry[d])
        return inflateView
    }

}