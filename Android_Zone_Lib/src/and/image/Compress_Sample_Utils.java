package and.image;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import and.Constant;
import and.log.Logger_Zone;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
/**
 * 图片采样工具 bitmap保存工具
 * @author Zone
 *
 */
public class Compress_Sample_Utils {
	private static Logger_Zone logger;

	static{
		logger= new  Logger_Zone(Compress_Sample_Utils.class,Constant.Logger_Config);
		logger.closeLog();
	}
	/**
	 * @param filePath  仅仅解析边界的路径
	 * @return 仅仅解析边界的Options
	 */
	public static Options justDecodeBounds(String filePath) {
		Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, opts);
		return opts;
	}

	// 
	/**
	 * @param opts   传入一个仅仅解析原图边界的Options 就是已经计算出本身宽高的optoions
	 * @param targetWidth	为null的时候 以 height为标准
	 * @param targetHeight 为null的时候 以 width为标准
	 * @return 计算图片的采样值
	 */
	public static int calculateInSampleSize(BitmapFactory.Options opts,
			Integer targetWidth, Integer targetHeight) {
		int simpleScale = 0;
		if (targetWidth == null) {
			simpleScale = opts.outHeight / targetHeight;
		}
		if (targetHeight == null) {
			simpleScale = opts.outWidth / targetWidth;
		} 
		if(targetHeight != null&&targetWidth != null){
			float h_scale = opts.outHeight / targetHeight;
			float w_scale = opts.outWidth / targetWidth;
			logger.log("横向缩放比：h_scale:" + h_scale);
			logger.log("纵向缩放比：w_scale" + w_scale);
			// 谁 缩放比例大用
			simpleScale = (int) ((h_scale > w_scale) ? h_scale : w_scale);
		}
		logger.log("用的缩放比：simpleScale" + simpleScale);
		if (simpleScale <= 1) {
			// 不缩放 即原图大小
			simpleScale = 1;
		} else {
			for (int i = 0; i < 4; i++) {
				if (simpleScale < Math.pow(2, i)) {
					simpleScale =(int) Math.pow(2, i-1);
				}
				if(simpleScale == Math.pow(2, i)){
					simpleScale=(int) Math.pow(2, i);
					break;
				}
			}
		}
		logger.log("最终缩放比：simpleScale" + simpleScale);
		return simpleScale;
	}
	/**
	 *一般是640*960   480* 800  720*1280
	 * @param filePath 文件路径
	 * @param targetWidth	为null的时候 以 height为标准
	 * @param targetHeight 为null的时候 以 width为标准
	 * @param config  图片解析的config
	 * @return  返回 原图到目标宽高 等比缩放后的 采样位图
	 */
	public static Bitmap getSampleBitmap(String filePath,Integer targetWidth, Integer targetHeight,Config config) {
		Options options = justDecodeBounds(filePath);
		options.inSampleSize = calculateInSampleSize(options, targetWidth, targetHeight);
		options.inJustDecodeBounds = false;
		//另外，为了节约内存我们还可以使用下面的几个字段：
	    options.inPreferredConfig =config;// 默认是Bitmap.Config.ARGB_8888
	    options.inPurgeable = true;//true 内存不足可收回 再次用的时候 重新解码   false则不可收回
	    options.inInputShareable = true;//设置是否深拷贝，与inPurgeable结合使用，inPurgeable为false时，该参数无意义。 
		return BitmapFactory.decodeFile(filePath, options);
	
	}
	/**
	 *一般是640*960   480* 800  720*1280
	 *默认配置Bitmap.Config.ARGB_8888
	 * @param filePath 文件路径
	 * @param targetWidth	为null的时候 以 height为标准
	 * @param targetHeight 为null的时候 以 width为标准
	 * @return  返回 原图到目标宽高 等比缩放后的 采样位图
	 */
	public static Bitmap getSampleBitmap(String filePath,Integer targetWidth, Integer targetHeight) {
		return getSampleBitmap(filePath, targetWidth, targetHeight, Bitmap.Config.ARGB_8888);
	}
	public static Bitmap getRawBitmap(String filePath){
		return BitmapFactory.decodeFile(filePath);
	}

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
		logger.log("压缩的时候_开始_大小：" + baos.toByteArray().length / 1024 * 4 + "kb");
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
		logger.log("压缩的时候_完成后_大小：" + baos.toByteArray().length / 1024 * 4 + "kb");
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
