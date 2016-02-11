package and.utils.java;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;

public class ReflectUtils {
    public static <T> T clone(T data) {
        return clone(data, false);
    }

    /**
     * 暂时支持  基础类型 基础类型的封装类 list Map
     * static 的不赋值
     *
     * TODO final 暂时不会管   异常再说
     * 不支持枚丿 不过不会报错~ 剩下不支持的会报锿
     * @param data
     * @return
     */
    public static <T> T clone(T data,boolean log) {
        //是基本类型与其封装类
        if(data.getClass().isPrimitive()||isPrimitiveWrap(data.getClass()))
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
                        if (fieldClass.isPrimitive()|| isPrimitiveWrap(fieldClass)) {
                            //判断是什么类垿  如果是基本类垿 就直接赋值
                            field.set(dataClone, field.get(data));
                        } else if (List.class.isAssignableFrom(fieldClass)) {
                            List<Object> listClone = new ArrayList<Object>();
                            List<Object> list = (List<Object>) field
                                    .get(data);
                            for (int i = 0; i < list.size(); i++) {
                                listClone.add(clone(list.get(i)));
                            }
                            field.set(dataClone, listClone);
                        } else if (Map.class.isAssignableFrom(fieldClass)) {
                            ParameterizedType fieldClass2Params = ((ParameterizedType) fieldClass2);
                            Type[] types = fieldClass2Params
                                    .getActualTypeArguments();
                            Map<Object, Object> map = (Map<Object, Object>) field
                                    .get(data);
                            Map<Object, Object> mapClone = new HashMap<Object, Object>();
                            for (Map.Entry<Object, Object> item : map
                                    .entrySet()) {
                                mapClone.put(clone(item.getKey()),
                                        clone(item.getValue()));
                            }
                            field.set(dataClone, mapClone);
                        } else {
                            //不是  的话 就继续clone
                            field.set(dataClone, clone(field.get(data)));
                        }
                    } else {
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
    public static <T> void toStringGson(T data) {
        System.out.println(new Gson().toJson(data));
    }


    //int, double, float, long, short, boolean, byte, char＿ void.也是有这个的
    public static boolean isPrimitiveWrap(Class clas){
        if(Integer.class.isAssignableFrom(clas))
            return true;
        if(Double.class.isAssignableFrom(clas))
            return true;
        if(Float.class.isAssignableFrom(clas))
            return true;
        if(Long.class.isAssignableFrom(clas))
            return true;
        if(Short.class.isAssignableFrom(clas))
            return true;
        if(Boolean.class.isAssignableFrom(clas))
            return true;
        if(Byte.class.isAssignableFrom(clas))
            return true;
        if(Character.class.isAssignableFrom(clas))
            return true;
        if(Void.class.isAssignableFrom(clas))
            return true;
        if(String.class.isAssignableFrom(clas))
            return true;
        return false;
    }

}
