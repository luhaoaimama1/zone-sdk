package and.utils.reflect;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
/**
 * Type 可以直接强转成class
 */
class ReflectGenericUtils {

    public static Class getSuperGenericClass(Class<?> subclass) {
        return getSuperGenericClass(subclass, 0);
    }

    public static Class getSuperGenericClass(Class<?> subclass, int index) {
        return (Class) getSuperGenericType(subclass, index);
    }

    public static Type getSuperGenericType(Class<?> subclass) {
        return getSuperGenericType(subclass, 0);
    }

    public static Type getSuperGenericType(Class<?> subclass, int index) {
        Type superclass = subclass.getGenericSuperclass();
        if (!(superclass instanceof ParameterizedType))
            return superclass;
        Type[] params = ((ParameterizedType) superclass).getActualTypeArguments();
        if (index >= params.length || index < 0)
            throw new RuntimeException("Index outof bounds");
        return params[index];
    }

    public static Class getFieldGenericClass(Field field) {
        return getFieldGenericClass(field, 0);
    }

    public static Class getFieldGenericClass(Field field, int index) {
        return (Class) getFieldGenericType(field, 0);
    }

    public static Type getFieldGenericType(Field field) {
        return getFieldGenericType(field, 0);
    }

    public static Type getFieldGenericType(Field field, int index) {
        Type fieldClass = field.getGenericType();
        if (!(fieldClass instanceof ParameterizedType))
            return fieldClass;
        Type[] params = ((ParameterizedType) fieldClass).getActualTypeArguments();
        if (index >= params.length || index < 0)
            throw new RuntimeException("Index outof bounds");
        return params[index];
    }


}
