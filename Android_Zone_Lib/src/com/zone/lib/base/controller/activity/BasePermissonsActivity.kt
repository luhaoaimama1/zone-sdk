package com.zone.lib.base.controller.activity

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import com.zone.lib.LogZSDK
import com.zone.lib.base.controller.activity.base.FeatureActivity
import permissions.dispatcher.*

/**
 * 特殊权限 没有程序内 允许 。需要引导用户去
 * 悬浮权限使用：FloatWindowPermissionUtils
 * 通知权限使用：NotificationPermissionUtils
 */
@RuntimePermissions
abstract class BasePermissonsActivity : FeatureActivity() {
    private val APP_SETTINGS_RC = 7534
    private val permissCheckList by lazy {
        HashSet<String>()
    }
    private val permissRationaleList by lazy {
        HashSet<String>()
    }
    private val permissMustPermitList by lazy {
        HashSet<String>()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated method
        onRequestPermissionsResult(requestCode, grantResults)
    }

    open fun permissionCheckCamera() {
        onCameraPermitWithPermissionCheck()
    }

    open fun permissionCheckStorage() {
        onStoragePermitWithPermissionCheck()
    }

    open fun permissionCheckRecordAudio() {
        onRecordAudioPermitWithPermissionCheck()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == APP_SETTINGS_RC) {
            permissCheckList.forEach {
                when (it) {
                    Manifest.permission.CAMERA -> {
                        if (PermissionUtils.hasSelfPermissions(this, Manifest.permission.CAMERA)) {
                            onCameraPermit()
                        } else {
                            onCameraDenied()
                        }
                    }
                    Manifest.permission.WRITE_EXTERNAL_STORAGE -> {
                        if (PermissionUtils.hasSelfPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            onStoragePermit()
                        } else {
                            onStorageDenied()
                        }
                    }
                    Manifest.permission.RECORD_AUDIO -> {
                        if (PermissionUtils.hasSelfPermissions(this, Manifest.permission.RECORD_AUDIO)) {
                            onRecordAudioPermit()
                        } else {
                            onRecordAudioDenied()
                        }
                    }
                    else -> {
                    }
                }
            }
        }
    }

    //永远拒绝后  引导弹窗
    open fun neverDialog(element: String,nega:()->Unit) {
        val posiListener = DialogInterface.OnClickListener { _, _ ->
            permissCheckList.clear()
            permissCheckList.add(element)
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    .apply {
                        setData(Uri.fromParts("package", packageName, null))
                        addFlags(0)
                    }
            startActivityForResult(intent, APP_SETTINGS_RC)

        }
        val negaListener = DialogInterface.OnClickListener { _, _ ->
            nega()
        }
        showDialog(this, null, title = "Permissions Required",
                message = "是真的需要权限啊", posi = "允许", nega = "拒绝",
                posiListener = posiListener, negaListener = negaListener)
    }

    //拒绝后 提示的弹窗
    open fun showRationaleDialog(message: String, request: PermissionRequest) {
        showDialog(this, request, message = message, posi = "允许", nega = "拒绝")
    }

    /**
     * 覆盖这个可以更改弹窗样式
     */
    open fun showDialog(context: Context, request: PermissionRequest? = null,
                        title: String? = null, message: String? = null,
                        posi: String? = null, posiListener: DialogInterface.OnClickListener? = null,
                        nega: String? = null, negaListener: DialogInterface.OnClickListener? = null) {
        AlertDialog.Builder(this).apply {
            posi?.let {
                setPositiveButton(it) { v, w ->
                    request?.proceed()
                    posiListener?.onClick(v, w)
                }
            }
            nega?.let {
                setNegativeButton(it) { v, w ->
                    request?.cancel()
                    negaListener?.onClick(v, w)
                }
            }
            setCancelable(false)
            message?.let { setMessage(it) }
            title?.let { setTitle(it) }
        }.show()
    }

    open fun onXXXDenied(permission: String,
                         permissionCheckXXX_: () -> Unit,
                         onXXXDeniedMustPermit_: () -> Unit,
                         onXXXDenied_: () -> Unit) {
        if (permissMustPermitList.contains(permission)) {
            if (!permissRationaleList.contains(permission) && PermissionUtils.shouldShowRequestPermissionRationale(this, permission)) {
                permissionCheckXXX_()
            } else {
                onXXXDeniedMustPermit_()
            }
        } else {
            onXXXDenied_()
        }
    }

    open fun showRationaleForXXX(request: PermissionRequest, permission: String,
                                 onXXXDeniedMustPermit_: () -> Unit,
                                 showRationaleForXXX_: () -> Unit) {
        if (permissMustPermitList.contains(permission)) {
            permissRationaleList.add(permission)
            val negaListener = DialogInterface.OnClickListener { _, _ ->
                onXXXDeniedMustPermit_()
            }
            showDialog(this, request, title = "权限",
                    message = "不给就退出了啊", posi = "允许", nega = "拒绝",
                    negaListener = negaListener)
        } else {
            showRationaleForXXX_()
        }
    }

    //----------------------------------- CAMERA --------------------------------------------------
    @NeedsPermission(Manifest.permission.CAMERA)
    open fun onCameraPermit() {
        LogZSDK.d("call Camera")
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    open fun onCameraDenied() {
        // NOTE: Deal with a denied permission, e.g. by showing specific UI
        // or disabling certain functionality
        LogZSDK.d("camera denied")
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    open fun showRationaleForCamera(request: PermissionRequest) {
        // NOTE: Show a rationale to explain why the permission is needed, e.g. with a dialog.
        // Call proceed() or cancel() on the provided PermissionRequest to continue or abort
        showRationaleDialog("Camera permission is needed to xxx", request)
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    open fun onCameraNeverAskAgain() {
        neverDialog(Manifest.permission.CAMERA,{onCameraDenied()})
    }

    //----------------------------------- STORAGE --------------------------------------------------
    open fun permissionCheckStorageMustPermit() {
//        permissMustPermitList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//        onStoragePermitWithPermissionCheck()
    }

    open fun onStorageDeniedMustPermit() {

    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    open fun onStoragePermit() {
        LogZSDK.d("call Storage")
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    open fun onStorageDenied() {
        // NOTE: Deal with a denied permission, e.g. by showing specific UI
        // or disabling certain functionality
        val element = Manifest.permission.WRITE_EXTERNAL_STORAGE
        onXXXDenied(element, {
            permissionCheckStorage()
        }, {
            onStorageDeniedMustPermit()
        }, {
            LogZSDK.d("Storage denied")
        })

    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    open fun showRationaleForStorage(request: PermissionRequest) {
        // NOTE: Show a rationale to explain why the permission is needed, e.g. with a dialog.
        // Call proceed() or cancel() on the provided PermissionRequest to continue or abort
        val element = Manifest.permission.WRITE_EXTERNAL_STORAGE
        showRationaleForXXX(request, element, {
            onStorageDeniedMustPermit()
        }, {
            showRationaleDialog("STORAGE permission is needed to xxx", request)
        })
    }


    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    fun onStorageNeverAskAgain() {
        neverDialog(Manifest.permission.WRITE_EXTERNAL_STORAGE,{
            onStorageDenied()
        })
    }


    //----------------------------------- RECORD_AUDIO --------------------------------------------------
    @NeedsPermission(Manifest.permission.RECORD_AUDIO)
    open fun onRecordAudioPermit() {
        LogZSDK.d("call RecordAudio")
    }

    @OnPermissionDenied(Manifest.permission.RECORD_AUDIO)
    open fun onRecordAudioDenied() {
        // NOTE: Deal with a denied permission, e.g. by showing specific UI
        // or disabling certain functionality
        LogZSDK.d("RecordAudio denied")
    }

    @OnShowRationale(Manifest.permission.RECORD_AUDIO)
    open fun showRationaleForRecordAudio(request: PermissionRequest) {
        // NOTE: Show a rationale to explain why the permission is needed, e.g. with a dialog.
        // Call proceed() or cancel() on the provided PermissionRequest to continue or abort
        showRationaleDialog("RecordAudio permission is need to xxx", request)
    }

    @OnNeverAskAgain(Manifest.permission.RECORD_AUDIO)
    open fun onRecordAudioNeverAskAgain() {
        neverDialog(Manifest.permission.RECORD_AUDIO,{
            onRecordAudioDenied()
        })
    }
}