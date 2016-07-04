package and.utils.file2io2data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

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




    /**
     * Decodes the specified URL as per RFC 3986, i.e. transforms
     * percent-encoded octets to characters by decoding load the UTF-8 character
     * set. This function is primarily intended for usage load
     * {@link java.net.URL} which unfortunately does not enforce proper URLs. As
     * such, this method will leniently accept invalid characters or malformed
     * percent-encoded octets and simply pass them literally through to the
     * result string. Except for rare edge cases, this will make unencoded URLs
     * pass through unaltered.
     *
     * @param url The URL to decode, may be {@code null}.
     * @return The decoded URL or {@code null} if the input was
     * {@code null}.
     */
    static String decodeUrl(String url) {
        String decoded = url;
        if (url != null && url.indexOf('%') >= 0) {
            int n = url.length();
            StringBuffer buffer = new StringBuffer();
            ByteBuffer bytes = ByteBuffer.allocate(n);
            for (int i = 0; i < n; ) {
                if (url.charAt(i) == '%') {
                    try {
                        do {
                            byte octet = (byte) Integer.parseInt(url.substring(i + 1, i + 3), 16);
                            bytes.put(octet);
                            i += 3;
                        } while (i < n && url.charAt(i) == '%');
                        continue;
                    } catch (RuntimeException e) {
                        // malformed percent-encoded octet, fall through and
                        // append characters literally
                    } finally {
                        if (bytes.position() > 0) {
                            bytes.flip();
                            buffer.append(Charset.forName("UTF-8").decode(bytes).toString());
                            bytes.clear();
                        }
                    }
                }
                buffer.append(url.charAt(i++));
            }
            decoded = buffer.toString();
        }
        return decoded;
    }


    /**
     * Deletes a file. If file is a directory, delete it and all sub-directories.
     * <p>
     * The difference between File.delete() and this method are:
     * <ul>
     * <li>A directory to be deleted does not have to be empty.</li>
     * <li>You get exceptions when a file or directory cannot be deleted.
     * (java.io.File methods returns a boolean)</li>
     * </ul>
     *
     * @param file file or directory to delete, must not be {@code null}
     * @throws NullPointerException          if the directory is {@code null}
     * @throws java.io.FileNotFoundException if the file was not found
     * @throws java.io.IOException           in case deletion is unsuccessful
     */
    public static void forceDelete(File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectory(file);
        } else {
            boolean filePresent = file.exists();
            if (!file.delete()) {
                if (!filePresent) {
                    throw new FileNotFoundException("File does not exist: " + file);
                }
                String message =
                        "Unable to delete file: " + file;
                throw new IOException(message);
            }
        }
    }

    /**
     * Cleans a directory without deleting it.
     *
     * @param directory directory to clean
     * @throws java.io.IOException in case cleaning is unsuccessful
     */
    public static void cleanDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }

        if (!directory.isDirectory()) {
            String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }

        File[] files = directory.listFiles();
        if (files == null) {  // null if security restricted
            throw new IOException("Failed to list contents of " + directory);
        }

        IOException exception = null;
        for (File file : files) {
            try {
                forceDelete(file);
            } catch (IOException ioe) {
                exception = ioe;
            }
        }

        if (null != exception) {
            throw exception;
        }
    }

    /**
     * Deletes a directory recursively.
     *
     * @param directory directory to delete
     * @throws java.io.IOException in case deletion is unsuccessful
     */
    public static void deleteDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            return;
        }
        cleanDirectory(directory);

        if (!directory.delete()) {
            String message =
                    "Unable to delete directory " + directory + ".";
            throw new IOException(message);
        }
    }

    /**
     * Tests if the specified <code>File</code> is newer than the specified
     * time reference.
     *
     * @param file       the <code>File</code> of which the modification date must
     *                   be compared, must not be {@code null}
     * @param timeMillis the time reference measured in milliseconds since the
     *                   epoch (00:00:00 GMT, January 1, 1970)
     * @return true if the <code>File</code> exists and has been modified after
     * the given time reference.
     * @throws IllegalArgumentException if the file is {@code null}
     */
    public static boolean isFileNewer(File file, long timeMillis) {
        if (file == null) {
            throw new IllegalArgumentException("No specified file");
        }
        if (!file.exists()) {
            return false;
        }
        return file.lastModified() > timeMillis;
    }

    /***
     * 获取文件扩展名
     *
     * @param filename
     * @return 返回文件扩展名
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }
    /**
     * 获取文件类型 D:\psb.jpg->jpg 没有点
     *
     * @param file 文件
     * @return 文件后缀名
     */
    public static String getFileSuffix(File file) {
        String fileName = file.getName();
        int typeIndex = fileName.lastIndexOf(".");
        if (typeIndex != -1) {
            return fileName.substring(typeIndex + 1).toLowerCase();
        } else {
            return "";
        }
    }

    /**
     * 根据后缀名判断是否是图片文件
     *
     * @param file 文件
     * @return 是否是图片结果true or false
     */
    public static boolean isImage(File file) {
        String fileName = file.getName();
        int typeIndex = fileName.lastIndexOf(".");
        if (typeIndex != -1) {
            String fileType = fileName.substring(typeIndex + 1).toLowerCase();
            if (fileType != null
                    && (fileType.equals("jpg") || fileType.equals("gif")
                    || fileType.equals("png")
                    || fileType.equals("jpeg")
                    || fileType.equals("bmp")
                    || fileType.equals("wbmp")
                    || fileType.equals("ico") || fileType.equals("jpe"))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件的上传类型
     * @param file
     * @return 图片格式为image/png,image/jpg等。非图片为application/octet-stream
     */
    public static String getContentType(File file) {
        if (isImage(file)) {
            return "image/" + getFileSuffix(file).toLowerCase();// 将FormatName返回的值转换成小写，默认为大写r
        }
        return "application/octet-stream";
    }
}
