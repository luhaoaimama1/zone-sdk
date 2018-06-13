package com.zone.lib.utils.data.check;

/**
 * [2018] by Zone
 *
 */
public class SafeCheck {
    public static void main(String[] args) {

//        item.description != null && item.description.profile != null
//          && item.description.profile.card != null
//         && item.description.profile.card.verify != null
//        相当于
//        checkSafe(()->quickArr(item.description.profile.card.verify))
        //** 在方法内 执行的时候 检测,因为执行的时候你写还没有运行。 但是return不能用 Obj... 所以只能这样写
    }
    public static boolean checkSafe(Callback callback) {
        try {
            callback.checkNull();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public interface Callback{
       void checkNull();
    }

    public static Object[] quickArr(Object... var){
        return var;
    }
}
