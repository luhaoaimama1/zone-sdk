package com.example.mylib_test.app;

import java.lang.Thread.UncaughtExceptionHandler;

public class TestHandler2 implements UncaughtExceptionHandler {

    /**
     * 当UncaughtException发生时会转入该重写的方法来处理
     */
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            Thread.sleep(3000);// 如果处理了，让程序继续运行3秒再退出，保证文件保存并上传到服务器
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 退出程序
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}