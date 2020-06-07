package com.example.mylib_test.activity.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mylib_test.R
import com.zone.lib.base.controller.common.picture.PicktureHelper
import com.zone.lib.base.controller.fragment.BaseFeatureFragment

class ControllerMainFragment : BaseFeatureFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.f_controller_main, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.shot -> openCamera()
            R.id.photo -> openPhotos()
            R.id.pick -> pickPicture()
            else -> {
            }
        }
    }


    override fun getReturnedPicPath(path: String?, type: PicktureHelper.Type) {
        (activity as? ControllerMainActivity)?.toImageActivity(path)
    }
}