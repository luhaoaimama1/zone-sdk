package com.zone.lib.utils.reflect;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
/**
 * Type 可以直接强转成class
 * 使用范例: T result=((Class<T>) ReflectGenericUtils.getSuperGenericClass(this)).newInstance();
 */
public class ReflectGenericUtils {

    //        getGenericInterfaces()
    //        getGenericSuperclass

    public static class Self_ {
        //---------------------------1.在本类中使用--------------------------------------
        /**
         * 在本类中使用  可以这样
         * public class Parent<M, B> {
         * public String tag="Parent";
         * public Class<M> class_Unsafe;
         * public M entity;
         * public Parent(M entity,B bilibili) {
         * }
         * }
         */

        //---------------------------2.在外部类中使用--------------------------------------
        /**
         *new Child<Short,Float,Person>(){} 有这个{} 相当于匿名类继承 本类
         * 所有可以获得相当于本类的参数
         * new Child<Short,Float,Person>(){}.getClass().getGenericSuperclass()
         * {@link  Super_}
         */
    }



    public static class Super_ {

        public static TypeToken_ getType(Class<?> subclass) {
            return getType(subclass, 0, 0);
        }

        public static TypeToken_ getType(Class<?> subclass, int superIndex, int index) {
            for (int i = 0; i < superIndex; i++)
                subclass = subclass.getSuperclass();
            Type superclass = subclass.getGenericSuperclass();
            if (!(superclass instanceof ParameterizedType))
                return new TypeToken_(superclass);
            Type[] params = ((ParameterizedType) superclass).getActualTypeArguments();
            if (index >= params.length || index < 0)
                throw new RuntimeException("Index outof bounds");
            return new TypeToken_(params[index]);
        }
    }




    public static class Interface_ {

        public static TypeToken_ getType(Class<?> subclass) {
            return getType(subclass, 0, 0);
        }

        public static TypeToken_ getType(Class<?> subclass, int interfaceIndex, int typeIndex) {
            Type[] superclass = subclass.getGenericInterfaces();
            Type fieldClass = subclass.getGenericInterfaces()[interfaceIndex];
            if (!(fieldClass instanceof ParameterizedType))
                return new TypeToken_(fieldClass);
            Type[] params = ((ParameterizedType) fieldClass).getActualTypeArguments();
            if (typeIndex >= params.length || typeIndex < 0)
                throw new RuntimeException("Index outof bounds");
            return new TypeToken_(params[typeIndex]);
        }
    }

    public static class Field_ {
        public static TypeToken_ getType(Field field) {
            return getType(field, 0);
        }

        public static TypeToken_ getType(Field field, int index) {
            Type fieldClass = field.getGenericType();
            if (!(fieldClass instanceof ParameterizedType))
                return new TypeToken_(fieldClass);
            Type[] params = ((ParameterizedType) fieldClass).getActualTypeArguments();
            if (index >= params.length || index < 0)
                throw new RuntimeException("Index outof bounds");
            return new TypeToken_(params[index]);
        }
    }

    public static class TypeToken_ {
        private Type type;

        public TypeToken_(Type type) {
            this.type = type;
        }

        public Type type() {
            return type;
        }

        public Class class_Unsafe() {
            return (Class<?>) type;
        }

        public Class class_() {
            try {
                return (Class<?>) type;
            } catch (Exception e) {
                return Object.class;
            }
        }
    }
}