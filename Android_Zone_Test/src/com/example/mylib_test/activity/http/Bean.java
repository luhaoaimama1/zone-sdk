package com.example.mylib_test.activity.http;

/**
 * 注意不能用内部类 会泄漏 activity 因为持有外部引用！
 */
public class Bean {
    String name;

    public Bean(String name) {
        this.name = name;
    }
}