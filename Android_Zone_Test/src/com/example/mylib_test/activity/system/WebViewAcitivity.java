package com.example.mylib_test.activity.system;

import and.base.activity.BaseActvity;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.mylib_test.R;

public class WebViewAcitivity extends BaseActvity{

	private WebView webview;

	@Override
	public void setContentView() {
		setContentView(R.layout.a_webview);
	}

	@Override
	public void findIDs() {
		webview=(WebView)findViewById(R.id.webview);
	}

	@Override
	public void initData() {
		//启用支持javascript
		webview.getSettings().setJavaScriptEnabled(true);  
//		触摸焦点起作用
		webview.requestFocus();//如果不设置，则在点击网页文本输入框时，不能弹出软键盘及不响应其他的一些事件。
		//取消滚动条
		webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		//支持缩放
		webview.getSettings().setSupportZoom(true);
		webview.setWebViewClient(new WebViewClient(){
			@Override
			public void onReceivedSslError(WebView view,
					SslErrorHandler handler, SslError error) {
				// TODO Auto-generated method stub
				super.onReceivedSslError(view, handler, error);
				handler.proceed();  // 接受所有网站的证书
			}
		});
		webview.loadUrl("https://www.baidu.com/");
	}

	@Override
	public void setListener() {
		
	}

}
