package com.example.mylib_test

/**
 *[2018/11/13] by Zone
 *
 * @param traceString 页面埋点用的字符串
 * @param pageFrom 页面from 上报的数字
 */
enum class Page(val traceString: String,val pageFrom: String) {
    HOME("home","10"),
    STICKY("sticky","11"),
    other("other","-1");
}