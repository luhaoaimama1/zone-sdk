package com.example.mylib_test.activity.http.aidl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import android.view.View
import com.example.mylib_test.R
import com.zone.lib.base.controller.activity.BaseFeatureActivity

/**
 * MIT License
 * Copyright (c) [2018] [Zone]
 */

class AIDLActivity : BaseFeatureActivity() {
    lateinit var iBookManager: IBookManager

    internal var serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            iBookManager = IBookManager.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName) {

        }
    }

    override fun setContentView() {
        setContentView(R.layout.a_http_aidl)
        bindService(Intent(this, BookService::class.java), serviceConnection,
                Context.BIND_AUTO_CREATE)

        findViewById<View>(R.id.tv).setOnClickListener {
            try {
                iBookManager.addBook(Book(1, "IPC"))
                iBookManager.addBook(Book(2, "IPC2"))
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

        findViewById<View>(R.id.tv2).setOnClickListener {
            try {
                for (book in iBookManager.bookList) {
                    Log.e("书名", book.bookName)
                }
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

    }

    override fun initData() {
    }

    override fun setListener() {
    }
}
