package and.utils.reflect;
import android.util.Log;
import com.google.gson.Gson;


/**
 * Created by Administrator on 2016/4/8.
 */
public class Print {
    /**
     * 可以去网站
     * http://www.bejson.com/ 看实体类是什么
     * @param tag
     * @param data
     * @param <T>
     */
    public static <T> void d(String tag,T data) {
        Log.d(tag,new Gson().toJson(data));
    }
}
