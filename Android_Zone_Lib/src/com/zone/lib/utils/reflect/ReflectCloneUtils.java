package com.zone.lib.utils.reflect;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zone.lib.utils.data.check.ClassCheck;

public class ReflectCloneUtils {


    public static <T> T clone(T data) {
        return clone(data, false);
    }

    /**
     * 支持 必须有默认构造器的类，
     * field:实体类 基础类型 基础类型的封装类 list Map 枚举(可以吧枚举考虑成类,必须有默认构造器  当然复杂的方法可能会是坑)
     * static,final 的不赋值
     * @param data
     * @return
     */
    public static <T> T clone(T data,boolean log) {
        //是基本类型与其封装类
        if(data.getClass().isPrimitive()||ClassCheck.isPrimitiveWrap(data.getClass()))
            return data;
        //是集合类型 list Map
        if (List.class.isAssignableFrom(data.getClass())) {
            List<Object> list = (List<Object>) data;
            List<Object> dataClone = new ArrayList<Object>();
            for (int i = 0; i < list.size(); i++) {
                dataClone.add(clone(list.get(i)));
            }
            return (T) dataClone;
        }
        if (Map.class.isAssignableFrom(data.getClass())){
            Map<Object,Object> dataMap=(Map<Object,Object>)data;
            Map<Object,Object> dataClone=new HashMap<Object,Object>();
            for (Map.Entry<Object, Object> item : dataMap.entrySet()) {
                dataClone.put(clone(item.getKey()),clone(item.getValue()));
            }
            return (T) dataClone;
        }
        //是类
        return cloneClass(data, log);
    }
    private static <T> T cloneClass(T data,boolean log){
        T dataClone = null;
        try {
            dataClone = (T) data.getClass().newInstance();
            Field[] fields = data.getClass().getDeclaredFields();
            for (Field field : fields) {
                Class<?> fieldClass = field.getType();
                Type fieldClass2 = field.getGenericType();
                if (log) {
                    //得到类型
                    System.out.println("getGenericType:" + fieldClass2);
                    System.out.println("getType:" + fieldClass);
                    System.out.println("field:" + field.getName());
                    System.out.println("isStatic:"
                            + Modifier.isStatic(field.getModifiers()));
                    System.out.println("isEnum:" + fieldClass.isEnum());
                }
                //不是静态  就赋值
                if (!Modifier.isStatic(field.getModifiers())) {
                    field.setAccessible(true);
                    if (!fieldClass.isEnum()) {
                        if (fieldClass.isPrimitive()|| ClassCheck.isPrimitiveWrap(fieldClass)) {
                            if(field.get(data)!=null)
                                //判断是什么类垿  如果是基本类垿 就直接赋值
                                field.set(dataClone, field.get(data));
                        } else if (List.class.isAssignableFrom(fieldClass)) {
                            List<Object> listClone = new ArrayList<Object>();
                            List<Object> list = (List<Object>) field
                                    .get(data);
                            if(list!=null&&list.size()>0){
                                for (int i = 0; i < list.size(); i++) {
                                    listClone.add(clone(list.get(i)));
                                }
                                field.set(dataClone, listClone);
                            }
                        } else if (Map.class.isAssignableFrom(fieldClass)) {
                            ParameterizedType fieldClass2Params = ((ParameterizedType) fieldClass2);
                            Type[] types = fieldClass2Params
                                    .getActualTypeArguments();
                            Map<Object, Object> map = (Map<Object, Object>) field
                                    .get(data);
                            if(map!=null&&map.size()>0){
                                Map<Object, Object> mapClone = new HashMap<Object, Object>();
                                for (Map.Entry<Object, Object> item : map
                                        .entrySet()) {
                                    mapClone.put(clone(item.getKey()),
                                            clone(item.getValue()));
                                }
                                field.set(dataClone, mapClone);
                            }
                        } else {
                            if(field.get(data)!=null)
                                //不是  的话 就继续clone
                                field.set(dataClone, clone(field.get(data)));
                        }
                    } else {
                        if(field.get(data)!=null)
                          field.set(dataClone, field.get(data));
                    }
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return dataClone;
    }
}
