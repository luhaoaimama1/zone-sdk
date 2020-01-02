package com.example.mylib_test.activity.study.ui

import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebSettings
import com.zone.lib.base.controller.activity.BaseFeatureActivity


public class GoogleOfficeActivity : BaseFeatureActivity() {

    lateinit var urlWebView: WebView
    override fun setContentView() {
        urlWebView = WebView(this)
        setContentView(urlWebView)
        open()
    }

    override fun initData() {
    }

    override fun setListener() {
    }

    fun open() {
        urlWebView.getSettings().setJavaScriptEnabled(true)
        urlWebView.getSettings().setDomStorageEnabled(true)
        val settings = urlWebView.getSettings()
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL)    //排版适应屏幕
        settings.setLoadWithOverviewMode(true)                             // setUseWideViewPort方法设置webview推荐使用的窗口。setL
        settings.setUseWideViewPort(true)
        settings.setPluginState(WebSettings.PluginState.ON)
        settings.setJavaScriptCanOpenWindowsAutomatically(true)
        settings.setAllowFileAccess(true)
        settings.setDefaultTextEncodingName("UTF-8")

        urlWebView.setVisibility(View.VISIBLE);
        urlWebView.setWebViewClient(AppWebViewClients());
        urlWebView.getSettings().setJavaScriptEnabled(true);
        urlWebView.getSettings().setUseWideViewPort(true);
        // https://view.officeapps.live.com/op/view.aspx?src
        urlWebView.loadUrl("https://docs.google.com/viewer?url=newteach.pbworks.com%2Ff%2Fele%2Bnewsletter.docx")


    }

    class AppWebViewClients : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            view?.loadUrl(url);
//            return super.shouldOverrideUrlLoading(view, url)
            return true
        }
    }
}