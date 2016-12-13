package and.utils.image;

import android.content.Context;

/**
 * Created by fuzhipeng on 2016/12/13.
 * 主要是记住有用的
 */

public class ResourceUtils {

    /**
     * 例如获取资源id 通过名称；
     * get(context,"love","drawable")
     *
     * @param context
     * @param name       资源名称
     * @param defType    "drawable" 等类型
     * @param defPackage 包名
     * @return
     */
    public static int getIdByStrName(Context context, String name, String defType, String defPackage) {
        return context.getResources().getIdentifier(name, defType, defPackage);

    }

    public static int getIdByStrName(Context context, String name, String defType) {
        return getIdByStrName(context, name, defType, context.getPackageName());

    }
}
