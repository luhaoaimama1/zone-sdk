package com.example.mylib_test.app;

import java.io.PrintWriter;
import java.io.StringWriter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class UncaughtExceptionHandler implements
		java.lang.Thread.UncaughtExceptionHandler {
	private final Context context;

	public UncaughtExceptionHandler(Context context) {
		this.context = context;
	}

	public void uncaughtException(Thread thread, Throwable exception) {
		final StringWriter stackTrace = new StringWriter();
		exception.printStackTrace(new PrintWriter(stackTrace));
		Log.i("exceptionHandler", stackTrace.toString());
		saveErrorLog(stackTrace.toString());

		// 杀死自己
		System.exit(0);
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	private void saveErrorLog(String stackInfo) {
		StringBuilder reportText = new StringBuilder();
		reportText.append("Model:").append(Build.MODEL).append("\n");
		reportText.append("Device:").append(Build.DEVICE).append("\n");
		reportText.append("Product:").append(Build.PRODUCT).append("\n");
		reportText.append("Manufacturer:").append(Build.MANUFACTURER)
				.append("\n");
		// 系统版本
		reportText.append("Version:").append(Build.VERSION.RELEASE)
				.append("\n");
		// 趣读版本
//		String versionName = new ErrorUtil(context).getVersionName();
//		reportText.append("QureadVersion:").append(versionName).append("\n");
		// 详细堆栈错误信息
		reportText.append(stackInfo);
		// 存储bug日志
//		IOUtils.saveFile(reportText.toString(), FileCache.logFile, "bugs.txt");
		Log.e("Exception", stackInfo);
	}
}
