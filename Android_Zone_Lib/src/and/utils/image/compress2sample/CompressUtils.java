package and.utils.image.compress2sample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import and.LogUtil;

/**
 * Created by Administrator on 2016/4/7.
 */
public class CompressUtils {
    /**
     *
     * @param bt	 想要压缩位图
     * @param targetSize  目标Kb
     * @param qualityMin  最低质量
     * @return  压缩后的位图
     */
    public static Bitmap compressBitmap(Bitmap bt,int targetSize,int qualityMin) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bt.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        LogUtil.d("压缩的时候_开始_大小：" + baos.toByteArray().length / 1024 * 4 + "kb");
        int options = 100;
        int step = 10;
        while (baos.toByteArray().length / 1024 * 4 > targetSize) {
            // 循环判断如果压缩后图片是否大于300kb 左右,大于继续压缩 经过我的实践貌似的*4
            baos.reset();// 重置baos即清空baos
            bt.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            if (options < qualityMin) {
                break;
            }
            if (options > step) {
                options -= step;// 每次都减少10
            }
            else {
                break;
            }
        }
        LogUtil.d("压缩的时候_完成后_大小：" + baos.toByteArray().length / 1024 * 4 + "kb");
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 保存位图到输出的路径
     * @param outPath  想要输出的路径
     * @param bitmap 想要保存的bitmap
     * @return  保存成功与否
     */
    public static boolean saveBitmap(String outPath, Bitmap bitmap) {
        try {
            FileOutputStream fos = new FileOutputStream(outPath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
