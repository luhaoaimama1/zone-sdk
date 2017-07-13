package com.zone.lib.utils.data.convert;

import com.zone.lib.utils.data.check.EmptyCheck;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fuzhipeng on 2016/10/21.
 */
public class ArraysUtil {

    public static <T> List<T> asList(T... a) {
        List<T> result=new ArrayList<>();
        for (T t1 : a)
            result.add(t1);
        return result;
    }

    public static <T> T[] asArray(List<T> a) {
        if(EmptyCheck.isEmpty(a))
            return  null;
        //a.getClass().getTypeParameters()  这样的话泛型会被擦除掉
        //解决办法: 获取第一个值 得到他的类~  a.get(0).getClass()
        T[] ts= (T[]) Array.newInstance(a.get(0).getClass(), a.size());
        for (int i = 0; i < a.size(); i++)
            ts[i]=a.get(i);
        return  ts;
    }
}
