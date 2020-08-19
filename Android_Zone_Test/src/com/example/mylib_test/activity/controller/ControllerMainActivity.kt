package com.example.mylib_test.activity.controller

import android.content.Intent
import android.net.Uri
import android.view.View
import com.example.mylib_test.FileManager
import com.example.mylib_test.R
import com.example.mylib_test.activity.photo_shot.ShowPicActivity
import com.zone.lib.base.controller.activity.BasePictureFeatureActivity
import com.example.mylib_test.base.controller.SystemClipActivityController
import com.zone.lib.base.controller.common.picture.PictureActivityController
import com.zone.lib.base.controller.activity.controller.ShowState
import com.zone.lib.base.controller.common.picture.PicktureHelper
import com.zone.lib.utils.activity_fragment_ui.FragmentSwitcher
import com.zone.lib.utils.activity_fragment_ui.ToastUtils
import com.zone.lib.utils.permissions.FloatWindowPermissionUtils
import com.zone.lib.utils.permissions.NotificationPermissionUtils

class ControllerMainActivity : BasePictureFeatureActivity() {

    lateinit var systemClipActivityController: SystemClipActivityController
    private var fragmentSwitcher: FragmentSwitcher? = null

    override fun setContentView() {
        setContentView(R.layout.a_controller_main)

        fragmentSwitcher = FragmentSwitcher(this, R.id.fl_controller)
        fragmentSwitcher!!.setPriDefaultAnimal(android.R.anim.fade_in, android.R.anim.fade_out)
        fragmentSwitcher!!.initFragment(ControllerMainFragment())
        fragmentSwitcher!!.switchPage(0)
    }

    override fun initBaseControllers() {
//        super.initBaseControllers()
//        systemClipActivityController = object : SystemClipActivityController(this@ControllerMainActivity, FileManager.PicFolder.path) {
//            override fun getReturnedClipPath(savePath: String?) {
//                toImageActivity(savePath)
//            }
//        }
//        initPrivateControllers(systemClipActivityController)
    }

    fun toImageActivity(path: String?) {
        val intent = Intent(this@ControllerMainActivity, ShowPicActivity::class.java)
        val uri = Uri.parse(path)
        intent.data = uri
        startActivity(intent)
    }

    override fun getReturnedPicPath(path: String?, type: PicktureHelper.Type) {
        toImageActivity(path)
    }

    override fun initData() {
    }

    override fun setListener() {
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        val intent = Intent(this, ActionBarControllerActivity::class.java)
        when (v?.id) {
            R.id.bt_ActionBarController_FullScreen_NoTitle -> startActivity(intent.apply {
                putStringArrayListExtra("key", arrayListOf(ShowState.FullScreen.name, ShowState.HideTitle.name))
            })
            R.id.bt_ActionBarController_FullScreen_HasTitle -> startActivity(intent.apply {
                putStringArrayListExtra("key", arrayListOf(ShowState.FullScreen.name, ShowState.ShowTitle.name))
            })
            R.id.bt_ActionBarController_HasTitle -> startActivity(intent.apply {
                putStringArrayListExtra("key", arrayListOf(ShowState.ShowTitle.name))

            })
            R.id.bt_ActionBarController_NoTitle -> startActivity(intent.apply {
                putStringArrayListExtra("key", arrayListOf(ShowState.HideTitle.name))

            })
            R.id.bt_CollectionActivityController -> startActivity(Intent(this, CollectionActivityControllerActivity::class.java))
            R.id.bt_SwipeBackController -> startActivity(Intent(this, SwipeBackControllerActivity::class.java))
            R.id.shot -> openCamera()
            R.id.photo -> openPhotos()
            R.id.pick -> pickPicture()
            R.id.clip -> systemClipActivityController.cropImageUri(FileManager.jpgA.path)
            R.id.tv_permission_file -> permissionCheckStorage()
            R.id.tv_permission_camera -> permissionCheckCamera()
            R.id.tv_permission_record_audio -> permissionCheckRecordAudio()
            R.id.tv_permission_activity -> PermissnActivity.start(this,PermissnActivity.permissions);
            R.id.tv_permission_floatwindow -> {
                if (!FloatWindowPermissionUtils.isEnable(this)) {
                    FloatWindowPermissionUtils.guideToPermissionWindow(this)
                } else {
                    ToastUtils.showShort(this, "悬浮框可用！")
                }
            }
            R.id.tv_permission_notification -> {
                if (!NotificationPermissionUtils.isEnable(this)) {
                    NotificationPermissionUtils.guideToPermissionWindow(this)
                } else {
                    ToastUtils.showShort(this, "通知权限可以用！")
                }
            }
            else -> {
            }
        }
    }
}