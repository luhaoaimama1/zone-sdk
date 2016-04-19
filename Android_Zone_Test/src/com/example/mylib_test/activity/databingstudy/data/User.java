package com.example.mylib_test.activity.databingstudy.data;

import java.util.List;

/**
 * Created by Administrator on 2016/4/16.
 */
public class User {
    public final String firstName="firstName";
    public  String lastName;
    public List<String> list;

    public User(String firstName, String lastName) {
//        this.firstName = firstName;
        this.lastName = lastName;
    }
}