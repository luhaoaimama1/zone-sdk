package com.zone.lib.utils.data.convert;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuzhipeng on 16/9/23.
 */

public class GsonUtils {
    static Gson gson=new Gson();

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T fromJson(String str, Class<T> t) {
        try {
            return gson.fromJson(str, t);
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * 可以使用
     * List<Person> ps = Arrays.asList(gson.fromJson(str, Person[].class));
     * 但是还是提供了下此方法
     * @param str
     * @param t
     * @return
     */
    public static <T> T[] fromJsonToArray(String str, Class<T> t) {
        try {
            Class<? extends Object> cls = Array.newInstance(t, 0).getClass();
            return gson.fromJson(str, (Type) cls);
        } catch (Exception e) {
            return null;
        }

    }
    /**
     * 或者用这两种种方式
     * 1.List<Person> ps = gson.fromJson(str, new TypeToken<List<Person>>() {}.getType());
     * 2.List<Person> ps = Arrays.asList
     * (gson.fromJson(str, Person[].class));//不支持添加等操作!!!
     * @param str
     * @param t
     * @return
     */
    public static <T> List<T> fromJsonToList(String str, Class<T> t) {
        try {
            T[] temp = fromJsonToArray(str, t);
            List<T> result=new ArrayList<>();
            for (T t1 : temp)
                result.add(t1);
            return result;
        } catch (Exception e) {
            return null;
        }

    }
}
