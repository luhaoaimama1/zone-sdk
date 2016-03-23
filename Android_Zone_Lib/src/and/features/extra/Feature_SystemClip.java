package and.features.extra;
import java.io.File;
import java.util.Calendar;
import java.util.Locale;

import and.Constant;
import and.features.core.ExtraFeature;
import and.features.RequestCodeConfig;
import and.log.Logger_Zone;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.format.DateFormat;

public abstract class Feature_SystemClip extends ExtraFeature{
	private  String saveFolder="";
	private Uri savePath=null;
	private Logger_Zone logger;
	public Feature_SystemClip(Activity activity,String saveFolder) {
		super(activity);
		this.saveFolder=saveFolder;
		logger= new  Logger_Zone(Feature_SystemClip.class,Constant.Logger_Config);
		logger.closeLog();
	}
	
	public void cropImageUri(String path){
		Uri uri = Uri.parse("file://" + "/"+path);
		//小米特殊的intent action
//		不难知道，我们从相册选取图片的Action为Intent.ACTION_GET_CONTENT。
//		 Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		 Intent intent = new Intent("com.android.camera.action.CROP");
		//可以选择图片类型，如果是*表明所有类型的图片
		 intent.setDataAndType(uri, "image/*");
	     // 下面这个crop = true是设置在开启的Intent中设置显示的VIEW可裁剪
		 intent.putExtra("crop", "true");
		//裁剪时是否保留图片的比例，这里的比例是1:1
		 intent.putExtra("scale", true);
		 
		// 这两项为裁剪框的比例.   固定比率　　就同时缩放了。。。
//		 intent.putExtra("aspectX", 2);
//		 intent.putExtra("aspectY", 1);
		  // outputX outputY 是裁剪图片宽高　　就是固定裁剪图的大小了　不要固定
//		 intent.putExtra("outputX", outputX);
//		 intent.putExtra("outputY", outputY);
		 
		 //是否将数据保留在Bitmap中返回
		 intent.putExtra("return-data", true);
		 //设置输出的格式
		 intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		 intent.putExtra("noFaceDetection", true); // no face detection
		 
		 String picName = DateFormat.format("yyyyMMdd_hhmmss",Calendar.getInstance(Locale.CHINA))+ ".jpg";
		 File saveFile = new File(saveFolder, picName);
		 savePath=Uri.fromFile(saveFile);
		 logger.log("savePath Uri:"+savePath);
		 intent.putExtra(MediaStore.EXTRA_OUTPUT,savePath );//输出地址  
		 activity.startActivityForResult(intent, RequestCodeConfig.getRequestCode(RequestCodeConfig.Feature_SystemClip__REQUESTCODE_Clip));
		}
	
	@Override
	public void init() {
		
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
			switch (RequestCodeConfig.getSwitchRequestCode(requestCode)) {
			case RequestCodeConfig.Feature_SystemClip__REQUESTCODE_Clip:
				if (intent != null) {
					logger.log("onActivityResult  savePath:"+savePath);
					getReturnedClipPath(savePath);
				}
				break;

			default:
				break;
			}
	}

	public abstract void getReturnedClipPath(Uri savePath);

	@Override
	public void destory() {
		
	}

}
