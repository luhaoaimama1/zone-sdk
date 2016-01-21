package com.example.mylib_test.activity.animal;

import com.example.mylib_test.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

public class ColorMarixTry extends Activity implements OnClickListener{

	private ImageView imageView1;
	private Bitmap bt;
	private EditText[] ed=new EditText[20];
	private Bitmap temp,recycled;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_animal_colormarix);
		bt=BitmapFactory.decodeResource(getResources(), R.drawable.abcd);
		imageView1=(ImageView) findViewById(R.id.imageView1);
		for (int i = 0; i < ed.length; i++) {
			try {
				int id = R.id.class.getField("ed_"+i).getInt(null);
				ed[i]=(EditText) findViewById(id);
				if(i==0||i==6||i==12||i==18)
					ed[i].setText("1");
				else
					ed[i].setText("0");
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
		
		setImage();
	}
	private void setImage() {
		recycled=temp;
		ColorMatrix colorMatrix=new ColorMatrix();
		float[] colorArr = colorMatrix.getArray();
		for (int i = 0; i < colorArr.length; i++) {
			colorArr[i]=Float.parseFloat(ed[i].getText().toString());
		}
		temp = Bitmap.createBitmap(bt.getWidth(), bt.getHeight(), Config.ARGB_8888);
		Canvas canvas=new Canvas(temp);
		Paint paint =new Paint();
		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
		canvas.drawBitmap(bt, 0,0, paint);
		
		imageView1.setImageBitmap(temp);
		if(recycled!=null&&recycled!=temp&&!recycled.isRecycled()){
			recycled.recycle();
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.reset:
			reset();
			break;
		case R.id.change:
			setImage();
			break;

		default:
			break;
		}
	}
	private void reset() {
		for (int i = 0; i < ed.length; i++) {
			if(i==0||i==6||i==12||i==18)
				ed[i].setText("1");
			else
				ed[i].setText("0");
		}
		setImage();
	}
}
