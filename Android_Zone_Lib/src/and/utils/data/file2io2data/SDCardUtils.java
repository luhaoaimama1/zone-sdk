package and.utils.data.file2io2data;

import java.io.File;

import and.LogUtil;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;

public class SDCardUtils {
    /**
     * 判断SDCard是否可用
     * @return
     */
    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);

    }

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static File getSDCardDir() {
        return Environment.getExternalStorageDirectory();
    }

    /**
     * Get {@link StatFs}.
     */
    public static StatFs getStatFs(String path) {
        return new StatFs(path);
    }

    /**
     * 获取SD卡的剩余容量 单位byte
     *
     * @return
     */
    public static long getSDCardAllSize() {
        if (isSDCardEnable()) {
            StatFs stat = new StatFs(getSDCardPath());
            // 获取空闲的数据块的数量
            long availableBlocks = (long) stat.getAvailableBlocks() - 4;
            // 获取单个数据块的大小（byte）
            long freeBlocks = stat.getAvailableBlocks();
            return freeBlocks * availableBlocks;
        }
        return 0;
    }

    /**
     * 获取指定路径所在空间的剩余可用容量字节数，单位byte
     *
     * @param filePath
     * @return 容量字节 SDCard可用空间，内部存储可用空间
     */
    public static long getFreeBytes(String filePath) {
        // 如果是sd卡的下的路径，则获取sd卡可用容量
        if (filePath.startsWith(getSDCardPath())) {
            filePath = getSDCardPath();
        } else {// 如果是内部存储的路径，则获取内存存储的可用容量
            filePath = Environment.getDataDirectory().getAbsolutePath();
        }
        StatFs stat = new StatFs(filePath);
        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        return stat.getBlockSize() * availableBlocks;
    }

    /**
     * 获取SD卡路径 带分隔符的
     *
     * @return
     */
    private static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator;
    }

    /**
     * SdSituation.IsSDspaceEnough(MainActivity.this, null);会打印 剩余内存 <br>
     * SdSituation.IsSDspaceEnough(MainActivity.this, "1000mb"); 会打印
     * 剩余内存、用完此空间之后的剩余内存
     *
     * @param context 上下文
     * @param need    可以为null（打印 剩余内存） <br>
     *                不为null的时候 不区分大小写 kb KB都行 但必须是KB,MB,GB之一（ 会打印 剩余内存、用完此空间之后的剩余内存
     *                ）
     * @return 是否够用
     */
    public static boolean IsSDspaceEnough(Context context, String need) {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {// 有sd卡
            File sd = Environment.getExternalStorageDirectory();// 获取跟目录
            StatFs size = new StatFs(sd.getPath());// 得到关于SD卡的信息
            Long kuai = (long) size.getBlockSize();// 从信息中得到每一块 的大小
            long sum = (long) size.getBlockCount();// 一共多少块
            long left = (long) size.getAvailableBlocks();// 还剩下多少块
            // Formatter.formatFileSize(activity, left*kuai) 把byte 转化成 GB,KB什么的
            String leftStr = Formatter.formatFileSize(context, left * kuai);
            LogUtil.d("剩余空间：" + leftStr);
            if (need == null) {
                return true;
            }

            // float b=(left*kuai)/(float)(1024*1024*1024);
            // Myshare.MyLog(SdSituation.class,"剩余空间："+String.valueOf(b));
            // 可表示为1KB 0 ,1MB 1,或1GB 2
            int sign = -1;
            String[] danwei = new String[]{"KB", "MB", "GB"};
            String str = need.substring(need.length() - 2, need.length());
            for (int i = 0; i < danwei.length; i++) {
                if (danwei[i].equals(str.toUpperCase())) {
                    sign = i;
                }
            }
            long needlong = Long.parseLong(need.substring(0, need.length() - 2)
                    .trim());
            long needBig = 0;
            switch (sign) {
                case 0:
                    needBig = needlong * 1024;
                    break;
                case 1:
                    needBig = needlong * 1024 * 1024;
                    break;
                case 2:
                    needBig = needlong * 1024 * 1024 * 1024;
                    break;
                default:
                    LogUtil.d("不是 KB,MB,GB 之一");
                    return false;
            }
            if (needBig < (left * kuai)) {
                long lin = left * kuai - needBig;
                LogUtil.d("用完你所需要的内存还剩：" + Formatter.formatFileSize(context, lin));
                return true;
            } else {
                LogUtil.d("内存不足");
                return false;
            }

        } else {
            LogUtil.d("SD卡不存在等状况！！");
            return false;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static SDCardInfo getSDCardInfo() {
        SDCardInfo sd = new SDCardInfo();
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            sd.isExist = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                File sdcardDir = Environment.getExternalStorageDirectory();
                StatFs sf = new StatFs(sdcardDir.getPath());

                sd.totalBlocks = sf.getBlockCountLong();
                sd.blockByteSize = sf.getBlockSizeLong();

                sd.availableBlocks = sf.getAvailableBlocksLong();
                sd.availableBytes = sf.getAvailableBytes();

                sd.freeBlocks = sf.getFreeBlocksLong();
                sd.freeBytes = sf.getFreeBytes();

                sd.totalBytes = sf.getTotalBytes();
            }
        }
        LogUtil.i(sd.toString());
        return sd;
    }

    /**
     * see more {@link StatFs}
     */
    public static class SDCardInfo {
        public boolean isExist;
        public long totalBlocks;
        public long freeBlocks;
        public long availableBlocks;

        public long blockByteSize;

        public long totalBytes;
        public long freeBytes;
        public long availableBytes;

        @Override
        public String toString() {
            return "SDCardInfo{" +
                    "isExist=" + isExist +
                    ", totalBlocks=" + totalBlocks +
                    ", freeBlocks=" + freeBlocks +
                    ", availableBlocks=" + availableBlocks +
                    ", blockByteSize=" + blockByteSize +
                    ", totalBytes=" + totalBytes +
                    ", freeBytes=" + freeBytes +
                    ", availableBytes=" + availableBytes +
                    '}';
        }
    }
}
