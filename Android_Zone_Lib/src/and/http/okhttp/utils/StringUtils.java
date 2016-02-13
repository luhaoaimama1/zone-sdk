package and.http.okhttp.utils;

/**
 * Created by Administrator on 2016/2/13.
 */
public class StringUtils {
    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0) && (str.trim().length()==0);
    }
}
