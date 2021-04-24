package com.example.mylib_test.activity.lifecircle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mylib_test.R
import com.zone.lib.utils.activity_fragment_ui.FragmentSwitcher

class FragmentStudyTABFragment : FragmentStudyTagFragment() {
    companion object {
        fun newInstance(tag: String, callback: (position: Int) -> Fragment): Fragment {
            val fragmentStudyOnlyFragment = FragmentStudyTABFragment()
            fragmentStudyOnlyFragment.callback = callback
            fragmentStudyOnlyFragment.printTag = tag
            return fragmentStudyOnlyFragment
        }
    }

    lateinit var callback: (position: Int) -> Fragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val inflateView = inflater.inflate(R.layout.a_lifecircle_fragment_study_tab_fragment, null)
        inflateView.findViewById<TextView>(R.id.tv).text = printTag
        inflateView.findViewById<TextView>(R.id.bt1).setOnClickListener {
            val findFragmentByTag = childFragmentManager.findFragmentByTag("tab1")
            val fragment = findFragmentByTag ?: callback(0)
            val beginTransaction = childFragmentManager.beginTransaction()
            childFragmentManager.findFragmentByTag("tab2")?.let {
                beginTransaction.hide(it)
            }

            findFragmentByTag?.let {
                beginTransaction.show(it)
            } ?: beginTransaction.add(R.id.fl, fragment, "tab1")
            beginTransaction.commitNowAllowingStateLoss()
        }

        inflateView.findViewById<TextView>(R.id.bt2).setOnClickListener {
            val findFragmentByTag = childFragmentManager.findFragmentByTag("tab2")
            val fragment = findFragmentByTag ?: callback(1)
            val beginTransaction = childFragmentManager.beginTransaction()
            childFragmentManager.findFragmentByTag("tab1")?.let {
                beginTransaction.hide(it)
            }

            findFragmentByTag?.let {
                beginTransaction.show(it)
            } ?: beginTransaction.add(R.id.fl, fragment, "tab2")
            beginTransaction.commitNowAllowingStateLoss()
        }
        return inflateView
    }


    override fun onDestroyView() {
        super.onDestroyView()
        val beginTransaction = childFragmentManager.beginTransaction()
        for (fragment in childFragmentManager.fragments) {
            beginTransaction.remove(fragment)
        }
        beginTransaction.commitNowAllowingStateLoss()
    }

}