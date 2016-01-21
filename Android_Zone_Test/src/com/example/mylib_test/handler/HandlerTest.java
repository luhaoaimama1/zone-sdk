package com.example.mylib_test.handler;

import android.os.Handler;
import android.widget.Button;

public class HandlerTest {
	private Handler handler=new Handler();
	public HandlerTest() {
	}
	public void updateView(final Button  button){
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				button.setText("更改成功了吗");				
			}
		});
	}

}
