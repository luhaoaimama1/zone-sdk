// IBookManager.aidl
package com.example.mylib_test.activity.http.aidl;
import  com.example.mylib_test.activity.http.aidl.Book;
interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}
