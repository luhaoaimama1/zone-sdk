package and.utils.file2io2data;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by fuzhipeng on 16/7/11.
 */
public class EnvironmentUtils {
    /**
     * 获取系统存储路径
     *
     * @return
     */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }

    public static File getCacheDir(Context context) {
        return context.getCacheDir();
    }

    public static File getExternalCacheDir(Context context) {
        return context.getExternalCacheDir();
    }

    public static File getFilesDir(Context context) {
        return context.getFilesDir();
    }

    public static File getExternalFilesDir(Context context) {
        return context.getExternalFilesDir(null);
    }

    /**
     * 这里的文件夹已经被创建过了
     *
     * @param context
     * @param ignoreRemovable 可移除的sd是否无视
     * @return
     */
    public static File getSmartCacheDir(Context context, boolean ignoreRemovable) {
        File fileResult=context.getCacheDir();
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                &&(ignoreRemovable || Environment.isExternalStorageRemovable())){
            File fileTemp= context.getExternalCacheDir();
            if(fileTemp!=null)
                fileResult=fileTemp;
        }
        return fileResult;
    }

    public static File getSmartCacheDir(Context context) {
        return getSmartCacheDir(context, true);
    }

    /**
     * 这里的文件夹已经被创建过了
     *
     * @param context
     * @param ignoreRemovable 可移除的sd是否无视
     * @return
     */
    public static File getSmartFilesDir(Context context, boolean ignoreRemovable) {
        //SD卡 存在 并且不是 拔插的
        File fileResult=context.getFilesDir();
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                &&(ignoreRemovable || Environment.isExternalStorageRemovable())){
                File fileTemp= context.getExternalFilesDir(null);
                if(fileTemp!=null)
                     fileResult=fileTemp;
            }
        return fileResult;
    }

    public static File getSmartFilesDir(Context context) {
        return getSmartFilesDir(context, true);
    }
}
