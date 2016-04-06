package com.example.mylib_test.activity.utils;

import and.utils.ToastUtils;
import and.utils.SpannableUtils;
import and.utils.SpannableUtils.onClickSpannableListener;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class GetPhoneTest extends Activity {
	private static String Content = "wo185106400111we18510640011ishenmald185-1064-0011klajldj"
			+ "185-1064-0011fadfjladjkfl4002342222akjdfajdflkajdfl400-234-2222ajdflka22223334jld"
			+ "fjl2222-3334ajd 2222-33344aa133-7015-6232";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		tv.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		tv.setGravity(Gravity.CENTER);
		SpannableUtils.contentToPhone(tv, this, Content,Color.RED, new onClickSpannableListener() {
			
			@Override
			public void onClick(View widget, String phone) {
				ToastUtils.showLong(GetPhoneTest.this, phone);
			}
		});
		setContentView(tv);
	}
}
