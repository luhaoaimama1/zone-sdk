package and.sd;

import java.io.File;

import android.content.Context;
import android.os.Environment;

/**
 * 仅仅是创建文件夹的事情
 *
 * @author Zone
 * @version 2015.7.15
 */
public class FileUtils {
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

    public static File getDiskCacheDir(Context context, String fileDirName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + fileDirName);
    }
    /**
     * 关于SD卡下  多层文件的建立
     * 可以得到文件夹 ,文件 ,根目录
     *
     * @param arg 参数文件夹路径
     *            <br><strong> 范例：getFile("test001","test002","test003"); 文件夹目录
     *            <br> 参数可以为空getFile("") 表示SD卡根目录
     *            <br> 参数可以为空getFile("a.txt")   文件
     *            </strong>
     * @return
     */
    public static File getFile(String... arg) {
        return getFile(true, arg);
    }

    /**
     * 关于SD卡下  多层文件的建立
     *
     * @param isNotCreate 当文件不存在的时候是否创建
     *                    <br>如果创建的是文件 而不是文件夹  即使 isNotCreate false了也会自动修改成true
     * @param arg         参数文件夹路径
     * @return
     */
    private static File getFile(boolean isNotCreate, String... arg) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            throw new NullPointerException("sd卡存在否：false!");
        }
        String pathJoin = "";
        String fileEnd = null;
        for (String str : arg) {
            if (str.contains(".")) {
                fileEnd = str;
            } else {
                pathJoin += "/" + str;
            }

        }
        String f = Environment.getExternalStorageDirectory().getPath();
        File file = new File(f + pathJoin);
        if (fileEnd != null)
            isNotCreate = true;
        if (isNotCreate && !file.exists()) {
            boolean isOk = file.mkdirs();
            if (!isOk) {
            }
        }
        if (fileEnd != null) {
            file = new File(file, fileEnd);
        }
        return file;
    }

}
