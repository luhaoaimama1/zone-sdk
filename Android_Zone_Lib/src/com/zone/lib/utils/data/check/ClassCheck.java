package com.zone.lib.utils.data.check;

import java.util.Collection;
import java.util.Date;

/**
 * 类工具
 * 
 * @author mty
 * @date 2013-6-10下午8:00:46
 */
public class ClassCheck {
	/**
	 * 判断类是否是基础数据类型
	 * 目前支持11种
	 *
	 * @param clazz
	 * @return
	 */
	public static boolean isBaseDataType(Class<?> clazz) {
		return clazz.isPrimitive() || clazz.equals(String.class) || clazz.equals(Boolean.class)
				|| clazz.equals(Integer.class) || clazz.equals(Long.class) || clazz.equals(Float.class)
				|| clazz.equals(Double.class) || clazz.equals(Byte.class) || clazz.equals(Character.class)
				|| clazz.equals(Short.class) || clazz.equals(Date.class) || clazz.equals(byte[].class)
				|| clazz.equals(Byte[].class);
	}
    public static boolean isCollection(Class claxx) {
        return Collection.class.isAssignableFrom(claxx);
    }

    public static boolean isArray(Class claxx) {
        return claxx.isArray();
    }
	//int, double, float, long, short, boolean, byte, char＿ void.也是有这个的
	public static boolean isPrimitiveWrap(Class<?> clas){
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
