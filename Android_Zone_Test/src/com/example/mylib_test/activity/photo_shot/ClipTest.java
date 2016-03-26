package com.example.mylib_test.activity.photo_shot;

//import com.edmodo.cropper.CropImageView;
//import com.edmodo.cropper.cropwindow.CropOverlayView;

import and.base.activity.BaseActvity;
import and.base.activity.decorater.BaseDecorater;
import and.base.activity.decorater.FeaturesDecorater;

		import android.view.View;
import android.view.View.OnClickListener;

public class ClipTest extends BaseActvity implements OnClickListener{

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initFeature() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void findIDs() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		
	}
//	private static final int CHOOSE_SMALL_PICTURE=1;
//	private File imgFile;
//	private CropImageView imageView;
//	private Bitmap bitmap;
//	private Bitmap chipSmallBt;
//	private Feature_SystemClip clip;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.a_clip);
//		imgFile = FileUtils_SD.FolderCreateOrGet("", "abc.jpg");
//		imageView = (CropImageView) findViewById(R.id.iv);
//		//显示线   0不显示线　　1点的时候显示线  2不点也显示线
//		imageView.setGuidelines(0);
//		MeasureUtils.measureView_addGlobal(imageView, GlobalState.MEASURE_REMOVE_LISNTER, new OnMeasureListener() {
//			
//			@Override
//			public void measureResult(View v, int view_width, int view_height) {
//				bitmap = Compress_Sample_Utils.getSampleBitmap(imgFile.getPath(),imageView.getWidth(), null);
//				imageView.setImageBitmap(bitmap);
//				imageView.rotateImage(90);
//			}
//		});
//		
//	}
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.bt1:
//			//小图
//			saveSmall();
//			break;
//		case R.id.bt2:
//			//大图
//			clip.cropImageUri(imgFile.getPath());
//			break;
//
//		default:
//			break;
//		}
//	}
//
//	private void saveSmall() {
//		Bitmap croppedImage = imageView.getCroppedImage();
////		chipSmallBt = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth()/2,  bitmap.getHeight());
//		imageView.setImageBitmap(croppedImage);
//		imageView.setCropOverlayViewGone();
////		imageView.setCropOverlayViewVisible();
//	}
//	
//	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		switch (requestCode) {
//		case CHOOSE_SMALL_PICTURE:
//		    if(data != null){
//		        Bitmap bitmap = data.getParcelableExtra("data");
//		        imageView.setImageBitmap(bitmap);
//		    }else{
//		        Log.e("a", "CHOOSE_SMALL_PICTURE: data = " + data);
//		    }
//		    break;
//		default:
//		    break;
//		}
//		
//	}
//	@Override
//	protected void initFeature() {
//		clip=new Feature_SystemClip(ClipTest.this,FileUtils_SD.FolderCreateOrGet("", "Clip").getPath()) {
//			
//			@Override
//			public void getReturnedClipPath(Uri savePath) {
//				int[] screen = ScreenUtils.getScreenPix(ClipTest.this);
//				Bitmap bitmap = Compress_Sample_Utils.getSampleBitmap(savePath.getPath(), screen[0], screen[1]);
//				imageView.setImageBitmap(bitmap);
//			}
//		};
//		addFeature(clip);
//	}
//	@Override
//	public void setContentView() {
//		
//	}
//	@Override
//	public void findIDs() {
//		
//	}
//	@Override
//	public void initData() {
//		
//	}
//	@Override
//	public void setListener() {
//		
//	}

	@Override
	public void backRefresh() {
		// TODO Auto-generated method stub
		
	}
	
}
