package com.zone.lib;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fuzhipeng on 2016/10/24.
 */

public class TimeDiffUtils {


    static Map<String, Long> timeMaps = new HashMap<>();

    public static void start(String tag) {
        timeMaps.put(tag, System.currentTimeMillis());
    }

    public static long end(String tag) {
        Long startTime = timeMaps.get(tag);
        if (startTime == 0)
        throw  new IllegalStateException("please first use Method:start()");
            long diff = System.currentTimeMillis() -startTime;
        System.out.println("TimeDiffUtils--->"+tag+":"+diff);
        return diff;
    }

}
