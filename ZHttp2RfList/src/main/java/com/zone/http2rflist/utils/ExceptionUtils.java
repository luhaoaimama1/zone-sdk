package com.zone.http2rflist.utils;

import java.io.IOException;

/**
 * Created by Administrator on 2016/3/24.
 */
public class ExceptionUtils {
    public static void quiet(Exception e){
        LogUtils.e(callMethodAndLine()+"\t Message:"+e.getMessage()+" \t cause:"+e.getCause());
    }

    /**
     * log这个方法就可以显示超链
     */
    private static String callMethodAndLine() {
        String result = " at ";
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2];
        result += thisMethodStack.getClassName()+ ".";
        result += thisMethodStack.getMethodName();
        result += "(" + thisMethodStack.getFileName();
        result += ":" + thisMethodStack.getLineNumber() + ")  ";
        return result;
    }
}
