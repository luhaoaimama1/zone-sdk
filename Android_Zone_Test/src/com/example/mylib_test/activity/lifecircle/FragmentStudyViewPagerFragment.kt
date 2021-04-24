package com.example.mylib_test.activity.lifecircle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.mylib_test.R

class FragmentStudyViewPagerFragment : FragmentStudyTagFragment() {
    companion object {
        fun newInstance(tag: String, callback: (position: Int) -> Fragment): Fragment {
            val fragmentStudyViewPagerFragment = FragmentStudyViewPagerFragment()
            fragmentStudyViewPagerFragment.callback = callback
            fragmentStudyViewPagerFragment.printTag = tag
            return fragmentStudyViewPagerFragment
        }
    }
    lateinit var callback: (position: Int) -> Fragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val inflateView = inflater.inflate(R.layout.a_lifecircle_fragment_study_viewpager, null)
        inflateView.findViewById<TextView>(R.id.tv).text = printTag
        inflateView.findViewById<ViewPager>(R.id.vp).adapter = MyPagerAdapter(childFragmentManager)
        return inflateView
    }

    override fun onResume() {
        super.onResume()
    }

    inner class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment? {
            return callback.invoke(position)
        }

        override fun getCount(): Int {
            return 4
        }
    }

}