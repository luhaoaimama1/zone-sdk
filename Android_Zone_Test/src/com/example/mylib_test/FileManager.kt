package com.example.mylib_test

import android.app.Activity
import com.zone.lib.utils.data.file2io2data.FileUtils
import com.zone.lib.utils.data.file2io2data.SDCardUtils
import com.zone.lib.utils.image.compress2sample.CompressUtils
import com.zone.lib.utils.image.compress2sample.SampleUtils
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

object FileManager {
    @JvmField
    val MyFolder = FileUtils.getFile(SDCardUtils.getSDCardDir(), "Zone")
    @JvmField
    val PicFolder = FileUtils.getFile(MyFolder, "picSave")
    @JvmField
    val jpgA = FileUtils.getFile(MyFolder, "picSave", "a.jpg")

    fun firstInit(activity: Activity) {


        Observable.fromCallable {
            if (!jpgA.exists()) {
                val bt = SampleUtils.load(activity, R.drawable.aaaaaaaaaaaab).bitmap()
                CompressUtils.saveBitmap(jpgA.path, bt)
            }
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({})
    }
}