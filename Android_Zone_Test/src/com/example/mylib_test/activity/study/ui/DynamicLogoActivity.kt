package com.example.mylib_test.activity.study.ui

import com.example.mylib_test.R
import com.zone.lib.base.activity.BaseActivity
import kotlinx.android.synthetic.main.a_dynamic_logo.*
import android.content.pm.PackageManager
import android.content.ComponentName
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import com.example.mylib_test.MainActivity2


/**
 *[2018/11/14] by Zone
 */
class DynamicLogoActivity : BaseActivity() {

    override fun setContentView() {
        setContentView(R.layout.a_dynamic_logo)

//        try {
//            val applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
//            applicationInfo.metaData.putString("Your Key", "Your Value")
//        } catch (e: PackageManager.NameNotFoundException) {
//            e.printStackTrace()
//        }


        dynamicLogoOrginal.setOnClickListener {
            setPrimitiveIcon()
        }

        dynamicLogoNew.setOnClickListener {
            setRoundIcon()
            shortcutAdd("Zone", 1)
//            Thread{
//                print(this@DynamicLogoActivity)
//            }.start()
        }

        openDirectory(MediaStore.Files.getContentUri("external"))
    }

    fun openDirectory(pickerInitialUri: Uri) {
        // Choose a directory using the system's file picker.
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
            // Provide read access to files and sub-directories in the user-selected
            // directory.
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

            // Optionally, specify a URI for the directory that should be opened in
            // the system file picker when it loads.
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
        }

        startActivityForResult(intent, 123)
    }


    private fun shortcutAdd(name: String, number: Int) {
        // Intent to be send, when shortcut is pressed by user ("launched")
        val shortcutIntent = Intent(applicationContext, MainActivity2::class.java)
//        shortcutIntent.action = Constants.ACTION_PLAY

        // Create bitmap with number in it -> very default. You probably want to give it a more stylish look
        val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        val paint = Paint()
        paint.setColor(-0x7f7f80) // gray
        paint.setTextAlign(Paint.Align.CENTER)
        paint.setTextSize(50F)
        Canvas(bitmap).drawText("" + number, 50F, 50F, paint)

        // Decorate the shortcut
        val addIntent = Intent()
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent)
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name)
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, bitmap)

        // Inform launcher to create shortcut
        addIntent.action = "com.android.launcher.action.INSTALL_SHORTCUT"
        applicationContext.sendBroadcast(addIntent)
    }

    override fun findIDs() {
    }

    override fun initData() {
    }

    override fun setListener() {
    }

    private fun setRoundIcon() {
        val packageManager = packageManager
        packageManager.setComponentEnabledSetting(
                ComponentName(this, "$packageName.MainActivity2"),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP)
        packageManager.setComponentEnabledSetting(
                ComponentName(this, "$packageName.RoundActivity"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP)
    }

    private fun setPrimitiveIcon() {
        val packageManager = packageManager
        packageManager.setComponentEnabledSetting(
                ComponentName(this, "$packageName.RoundActivity"),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP)
        packageManager.setComponentEnabledSetting(
                ComponentName(this, "$packageName.MainActivity2"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP)
    }


}