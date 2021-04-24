package com.example.mylib_test.activity.lifecircle

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mylib_test.LogApp
import com.example.mylib_test.activity.lifecircle.helper.FragmentLifeController
import com.example.mylib_test.activity.lifecircle.helper.IFragmentLife
import com.example.mylib_test.activity.lifecircle.helper.Mode

open class FragmentStudyTagFragment : Fragment(), IFragmentLife {
    lateinit var printTag: String
    val fragmentLifeHelper = FragmentLifeController(this, this)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        fragmentLifeHelper.debugTag = printTag
        LogApp.d("tag:${printTag} =>onAttach  _ isHidden:${isHidden} ")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogApp.d("tag:${printTag} =>onCreate  _ isHidden:${isHidden} ")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        LogApp.d("tag:${printTag} =>onCreateView  _ isHidden:${isHidden} ")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogApp.d("tag:${printTag}  =>onViewCreated  _ isHidden:${isHidden} ")
    }

    override fun onStart() {
        super.onStart()
        LogApp.d("tag:${printTag} =>onStart  _ isHidden:${isHidden} ")
    }

    override fun onResume() {
        super.onResume()
        LogApp.d("tag:${printTag} =>onResume  _ isHidden:${isHidden} ")
    }

    override fun onPause() {
        super.onPause()
        LogApp.d("tag:${printTag} =>onPause  _ isHidden:${isHidden} ")
    }

    override fun onStop() {
        super.onStop()
        LogApp.d("tag:${printTag} =>onStop _ isHidden:${isHidden} ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LogApp.d("tag:${printTag} =>onDestroyView  _ isHidden:${isHidden} ")
    }

    override fun onDetach() {
        super.onDetach()
        LogApp.d("tag:${printTag} =>onDetach  _ isHidden:${isHidden} ")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        LogApp.d("tag:${printTag} =>onHiddenChanged:${hidden} _ isHidden:${isHidden} ")
        fragmentLifeHelper.onHiddenChanged(hidden)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        LogApp.d("tag:${printTag} =>setUserVisibleHint:${isVisibleToUser}  _ isHidden:${isHidden} ")
        fragmentLifeHelper.setUserVisibleHint(isVisibleToUser)
    }

    override fun onFirstVisibility() {
        LogApp.d("tag:${printTag} =>onFirstVisibility")
    }

    override fun onFragmentVisibilityChanged(isVisibleToUser: Boolean, mode: Mode) {
        LogApp.d("tag:${printTag} =>onFragmentVisibilityChanged \t${isVisibleToUser} \t mode:${mode.name}")
    }

    override fun onDestroy() {
        LogApp.d("tag:${printTag} =>onDestroy")
        super.onDestroy()
    }
}