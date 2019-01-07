package com.example.mylib_test.activity.http.aidl;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * MIT License
 * Copyright (c) [2018] [Zone]
 */

public class BookService extends Service {

    public List<Book> bookList=new ArrayList<>();

    private final IBookManager.Stub binder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            bookList.add(book);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
//        return null;
    }

}
