package com.example.mylib_test.activity.animal;
import view.FlowLayout_Zone;

import com.example.mylib_test.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import and.image.BitmapUtils;
import and.image.imageloader.ImageLoaderURIUtils;
import and.image.imageloader.ImageLoaderURIUtils.Type;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class Animal_MainActivity extends Activity implements OnClickListener{
	private FlowLayout_Zone flowLayoutZone1;
	private ImageView iv_iv;
	private Bitmap bt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_animaltest);
		flowLayoutZone1=(FlowLayout_Zone) findViewById(R.id.flowLayoutZone1);
		iv_iv=(ImageView) findViewById(R.id.iv_iv);
		 bt =BitmapFactory.decodeResource(getResources(), R.drawable.abcd);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.animal:
			startActivity(new Intent(this,AniPro.class));
			break;
		case R.id.color:
			startActivity(new Intent(this,ColorMarixTry.class));
			break;
		case R.id.bt_canvas:
			startActivity(new Intent(this,CanvasTest.class).putExtra("type", "layer"));
			break;
		case R.id.bt_PorterDuff:
			startActivity(new Intent(this,CanvasTest.class).putExtra("type", "porterDuff"));
			break;
		case R.id.bt_shader:
			startActivity(new Intent(this,CanvasTest.class).putExtra("type", "shader"));
			break;
		case R.id.bt_surfaceView:
			startActivity(new Intent(this,CanvasTest.class).putExtra("type", "surfaceView"));
			break;
		case R.id.bt_surface:
			startActivity(new Intent(this,CanvasTest.class).putExtra("type", "bt_surface"));
			break;
		case R.id.bt_MatrixPre:
			startActivity(new Intent(this,CanvasTest.class).putExtra("type", "bt_MatrixPre"));
			break;
		case R.id.bt_bitmap:
			startActivity(new Intent(this,CanvasTest.class).putExtra("type", "bt_bitmap"));
			break;
		case R.id.bt_bitmaptoRound:
			startActivity(new Intent(this,CanvasTest.class).putExtra("type", "bt_bitmaptoRound"));
			break;
		case R.id.bt_bitmaptoScale:
			startActivity(new Intent(this,CanvasTest.class).putExtra("type", "bt_bitmaptoScale"));
			break;
		case R.id.bt_bitmaptoRorate:
			startActivity(new Intent(this,CanvasTest.class).putExtra("type", "bt_bitmaptoRorate"));
			break;
		case R.id.bt_Pixels:
			startActivity(new Intent(this,PixelsAcitivity.class));
			break;
		case R.id.bt_customAni:
			startActivity(new Intent(this,CustomAniActivity.class));
			break;
		case R.id.bt_bitmapRecyle:
			bitmapRecyleTest();
			break;

		default:
			break;
		}
	}

	private void bitmapRecyleTest() {
	if (bt!=null) {
		//		ImageLoader.getInstance().loadImageSync(ImageLoaderURIUtils.transformURI(R.drawable.abcd+"", Type.Drawable));
		Log.i("hei", "bitmapRecyleTest" +(bt.isRecycled()==true ? "掩隙彶賸" : "羶衄掩隙彶"));
		iv_iv.setImageBitmap(bt);
		rec(bt);
		System.gc();
		Log.i("hei", "bitmapRecyleTest" + (bt.isRecycled()==true ? "掩隙彶賸" : "羶衄掩隙彶"));
		}
	
	}

	private void rec(Bitmap bt1) {
		bt1.recycle();
		Log.i("hei", "rec"+(bt1.isRecycled()==true ? "掩隙彶賸" : "羶衄掩隙彶"));
	}
	

}
