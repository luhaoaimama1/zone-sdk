package and.utils.view;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 返回 true 有效
 */
public class ValidEventUtils {

    //记录上次的点击时间即可。通过object;
    static int SPACE_TIME = 500;
    private static Map<WeakReference<Object>, Long> map = new WeakHashMap();


    public synchronized static boolean isValid() {
        return isValid(ValidEventUtils.class);
    }

    public synchronized static boolean isValid(int spaceTime) {
        return isValid(ValidEventUtils.class, spaceTime);
    }

    public synchronized static boolean isValid(Object obj) {
        return isValid(obj, SPACE_TIME);
    }

    public synchronized static boolean isValid(Object obj, int spaceTime) {
        WeakReference<Object> key = null;
        for (Map.Entry<WeakReference<Object>, Long> weakReferenceLongEntry : map.entrySet()) {
            if (weakReferenceLongEntry.getKey().get() == obj) {
                key = weakReferenceLongEntry.getKey();
                break;
            }
        }
        boolean isValid;
        long currentTime = System.currentTimeMillis();
        if (key != null) {
            //有值
            Long lastClick = map.get(key);
            if (currentTime - lastClick > spaceTime) {
                isValid = true;
                map.put(key, currentTime);
            } else
                isValid = false;
        } else {
            //没值
            key = new WeakReference<Object>(obj);
            map.put(key, currentTime);
            isValid = true;
        }
        return isValid;
    }


    public static int getSpaceTime() {
        return SPACE_TIME;
    }

    public static void setSpaceTime(int spaceTime) {
        SPACE_TIME = spaceTime;
    }
}