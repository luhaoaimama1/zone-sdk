package and;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

import and.utils.data.convert.GsonUtils;

/**
 * tag自动产生，格式:className.methodName(L:lineNumber)
 * <p>
 * <p>
 * <p>
 * Author: Zone
 * Date: 17-6-3
 * Time: 下午12:23
 */
public class ZLog {

    public enum LogStyle {
        D, V, I, E, W;
    }

    public enum LogLevel {
        Logic(3), Utils(2), Lib(1);
        public final int i;

        LogLevel(int i) {
            this.i = i;
        }
    }

    private static boolean isDebug = true;
    private static LogStyle logStyle = LogStyle.I;
    private static LogLevel logLevel = LogLevel.Lib;
    private static List<String> filterList;

    public static class Build {

        /**
         * release 模式 log全部关闭
         * debug  的话 分等级
         */
        public static Build isDebug(boolean isDebug) {
            ZLog.isDebug = isDebug;
            return new ZLog.Build();
        }
        /**
         * 主要是调整log类型
         */
        public Build logStyle(LogStyle logStyle) {
            ZLog.logStyle = logStyle;
            return Build.this;
        }
        /**
         * 1 :lib库  2 :封装类逻辑  3 : 业务逻辑
         * 举例子: 当设置等级2的时候
         * 打印:2 封装类逻辑 ,3 业务逻辑
         * 不打印:1 lib库
         */
        public Build logLevel(LogLevel logLevel) {
            ZLog.logLevel = logLevel;
            return Build.this;
        }
    }

    public static void addFilter(String... strs) {
        if (filterList == null)
            filterList = Arrays.asList(strs);
        else
            throw new IllegalStateException("addFilter only use one time!");
    }

    // ======================================= 
    // ============= 外部类 打印 调用库的 log ============== 
    // =======================================
    public static void log(Class tag, String content, LogLevel logic) {
        if (!isDebug)
            return;
        logWithStyle(tag.getName(), content, logic);
    }

    // ======================================= 
    // ============= 逻辑层 ============== 
    // =======================================
    public static void log(String content) {
        logInner(content, LogLevel.Logic);
    }

    public static void log(Object obj) {
        log(GsonUtils.toJson(obj));
    }

    public static void log(String format, Object... args) {
        log(String.format(format, args));
    }

    // ======================================= 
    // ============= 细节层 ============== 
    // =======================================
    public static void logUtils(String content) {
        logInner(content, LogLevel.Utils);
    }

    public static void logUtils(Object obj) {
        logUtils(GsonUtils.toJson(obj));
    }

    public static void logUtils(String format, Object... args) {
        logUtils(String.format(format, args));
    }

    // ======================================= 
    // ============= lib层 ============== 
    // =======================================
    public static void logLib(String content) {
        logInner(content, LogLevel.Lib);
    }

    public static void logLib(Object obj) {
        logLib(GsonUtils.toJson(obj));
    }


    public static void logLib(String format, Object... args) {
        logLib(String.format(format, args));
    }

    // ======================================= 
    // ============= 内部方法 ============== 
    // =======================================

    //主要过滤是否是debug状态
    private static void logInner(String content, LogLevel logic) {
        if (!isDebug)
            return;
        String tag = generateTag();
        logWithStyle(tag, content, logic);
    }

    //过滤 filterList 与 logLevel
    private static void logWithStyle(String tag, String content, LogLevel logLevel) {
        if (filterList != null) {
            for (String s : filterList) {
                if (tag.contains(s)) {
                    logWithStyleInner(tag, content);
                    break;
                }
            }
        } else if (logLevel.i >= ZLog.logLevel.i) {
            logWithStyleInner(tag, content);
        }
    }

    //过滤 logStyle
    private static void logWithStyleInner(String tag, String content) {
        switch (logStyle) {
            case D:
                Log.d(tag, content);
                break;
            case E:
                Log.e(tag, content);
                break;
            case I:
                Log.i(tag, content);
                break;
            case V:
                Log.v(tag, content);
                break;
            case W:
                Log.w(tag, content);
                break;
        }
    }

    //tag范例:LogUtil.main(L:32)
    //tag范例:a_新手.LogUtil.main(L:33)
    private static String generateTag() {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
//        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        return tag;
    }

    private ZLog() {
    }
}
