package com.example.mylib_test.activity.http.aidl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import com.example.mylib_test.R;

/**
 * MIT License
 * Copyright (c) [2018] [Zone]
 */

public class AIDLActivity extends Activity {
    IBookManager iBookManager;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iBookManager = IBookManager.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_http_aidl);
        bindService(new Intent(this, BookService.class), serviceConnection, BIND_AUTO_CREATE);

        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    iBookManager.addBook(new Book(1, "IPC"));
                    iBookManager.addBook(new Book(2, "IPC2"));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.tv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    for (Book book : iBookManager.getBookList()) {
                        Log.e("书名", book.bookName);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
