package com.zone.lib.base.activity.kinds.features;

import java.io.IOException;

import com.zone.lib.base.activity.kinds.features.core.ExtraFeature;
import com.zone.lib.utils.activity_fragment_ui.ToastUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.Surface;
import android.view.SurfaceView;

/**
 * 需要的服务如下： <br>
 * {@code
 *  <uses-feature android:name="android.hardware.camera" />
    <uses-feature android:name="android.hardware.camera.autofocus" />

    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission android:name="android.permission.FLASHLIGHT" />
 * } <br>
 * 点击对焦 在onclick里 故super.onclick传递进来
 * 
 *	TODO  初始化　和销毁功能 封装的不完善！
 *	因为外部setonclick会顶掉这里的　故onclick就不封装了　仅仅封装方法！！！
 * 
 * @author Zone
 * 
 */
public abstract class FeatrueCustomCamera extends ExtraFeature{
	public FeatrueCustomCamera(Activity activity) {
		super(activity);
	}
	// Camera.getCameraInfo(cameraIndex, cameraInfo);// 得到每一个摄像头的信息
	// parameters = camera.getParameters(); // 获取各项参数
	private static final String TAG="CustomCameraActivity";
	private  Camera camera;
	private int cameraCount, cameraNow; 
	private  SurfaceView surfaceView;
	private boolean isBehind = true, focusIsOk = false, autoFocus_ing=false;

	/**
	 * 初始化信息 必须先调此方法 完成对 下面两个参数的初始化
	 * 
	 * @param surfaceView
	 * @param cam
	 */
	public void initSurfaceView(SurfaceView surfaceView, Camera cam) {
		this.camera = cam;
		cameraCount=Camera.getNumberOfCameras();
		this.surfaceView = surfaceView;
		// holder=surfaceView.getHolder();
//		surfaceView.getHolder().setFixedSize(176, 164);
		surfaceView.getHolder().addCallback(new Callback() {
			// TODO 暂时就这些 当有需求的时候 在弄监听 或者抽象方法
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				releaseCamera(camera);
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				if (isBehind) {
					swapCamera(0);
				} else {
					swapCamera(1);
				}
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
			}
		});
	}

	/**
	 * 反转摄像机
	 */
	public void reverseCamera() {
		if (cameraCount > 1) {
			isBehind = !isBehind;
		}
		if (isBehind) {
			swapCamera(0);
		} else {
			swapCamera(1);
		}
	}

	private void swapCamera(int cameraIndex) {
		if (camera != null) {
			releaseCamera(camera);
		}
		openCamera(cameraIndex);
		cameraFocus();
	}

	/**
	 * 对焦
	 */
	@SuppressLint("ShowToast")
	private void cameraFocus() {
		focusIsOk = false;
		camera.autoFocus(new AutoFocusCallback() {
			@Override
			public void onAutoFocus(boolean success, Camera camera) {
				autoFocus_ing=true;
				if (success) {
					ToastUtils.showLong(activity, "对焦成功");
					focusIsOk = true;
				}
				camera.cancelAutoFocus();
				if (!success) {
					cameraFocus();
				}
				autoFocus_ing=false;
			}
		});
	}

	private boolean openCamera(int cameraIndex) {
		camera = Camera.open(cameraIndex);
		cameraNow = cameraIndex;
		camera.setDisplayOrientation(getPreviewDegree(activity));
		try {
			camera.setPreviewDisplay(surfaceView.getHolder());
		} catch (IOException e) {
			e.printStackTrace();
		}
		camera.startPreview();
		return true;
	}

	/**
	 * 拍照
	 */
	public void shutter() {
		if (focusIsOk) {
			camera.takePicture(null, null, pictureCallback);
		}
	}

	private PictureCallback pictureCallback = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			Bitmap bMapRotate;
			bMapRotate = BitmapFactory.decodeByteArray(data, 0, data.length);

			if (cameraNow == 0) { // 后摄像头右转90°
				Matrix matrix = new Matrix();
				matrix.reset();
				matrix.postRotate(90);
				bMapRotate = Bitmap.createBitmap(bMapRotate, 0, 0,
						bMapRotate.getWidth(), bMapRotate.getHeight(), matrix,
						true);
			} else if (cameraNow == 1) {// 前置摄像头上下翻转
				Matrix matrix = new Matrix();
				matrix.reset();
				matrix.postScale(1, -1);// 上下翻转
				matrix.postRotate(-90);
				bMapRotate = Bitmap.createBitmap(bMapRotate, 0, 0,
						bMapRotate.getWidth(), bMapRotate.getHeight(), matrix,
						true);
			}
			getShutterBitMap(bMapRotate);

		}

	};

	/**
	 * @param bMapRotate
	 *            拍照后返回的位图
	 */
	protected abstract void getShutterBitMap(Bitmap bMapRotate);

	private static int getPreviewDegree(Activity activity) {
		// 获得手机的方向
		int rotation = activity.getWindowManager().getDefaultDisplay()
				.getRotation();
		int degree = 0;
		// 根据手机的方向计算相机预览画面应该选择的角度
		switch (rotation) {
		case Surface.ROTATION_0:
			degree = 90;
			break;
		case Surface.ROTATION_90:
			degree = 0;
			break;
		case Surface.ROTATION_180:
			degree = 270;
			break;
		case Surface.ROTATION_270:
			degree = 180;
			break;
		}
		return degree;
	}

	/**
	 * 释放相机
	 * 
	 * @param camera
	 */
	protected void releaseCamera(Camera camera) {
		if (camera != null) {
			camera.setPreviewCallback(null);
			camera.stopPreview();
			camera.release();
			camera = null;
		}
	}

	public void cameraFocus_Click(){
//		PrintUtils.print("对焦");
		if (!autoFocus_ing) {
			cameraFocus();
		}
	}

	@Override
	public void init() {
		
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		
	}

	@Override
	public void destory() {
		
	}

}
