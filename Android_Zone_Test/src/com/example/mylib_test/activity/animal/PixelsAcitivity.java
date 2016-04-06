package com.example.mylib_test.activity.animal;


import com.example.mylib_test.R;
import com.example.mylib_test.app.Constant;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

public class PixelsAcitivity extends Activity {
	private ImageView iv;
	private Bitmap bt;
	private int[] pixels;
	private int[] newPixels;
	private Bitmap newBt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		iv=new ImageView(this);
		setContentView(iv);
		
		bt=BitmapFactory.decodeResource(getResources(), R.drawable.abcd);
		//Android系统不允许修改原图
		newBt=Bitmap.createBitmap(bt.getWidth(), bt.getHeight(), Config.ARGB_8888);
		
		pixels=new int[bt.getWidth()*bt.getHeight()];
		//得到所有的像素点
		bt.getPixels(pixels, 0, bt.getWidth(), 0, 0, bt.getWidth(), bt.getHeight());
		
		System.out.println(" pixels.length:"+ pixels.length);
		newPixels=new int[pixels.length];
		for (int i = 0; i < pixels.length; i++) {
			//得到每个像素点的像素值
			int color = pixels[i];
			//得到对应的ARGB值
			int r = Color.red(color);
			int g = Color.green(color);
			int b = Color.blue(color)*0;//去掉蓝色
			int a = Color.alpha(color);
			//通过argb合成 像素值
			newPixels[i]=Color.argb(a, r, g, b);
		}
		newBt.setPixels(newPixels, 0, bt.getWidth(), 0, 0, bt.getWidth(), bt.getHeight());
		iv.setImageBitmap(newBt);
	}
}
