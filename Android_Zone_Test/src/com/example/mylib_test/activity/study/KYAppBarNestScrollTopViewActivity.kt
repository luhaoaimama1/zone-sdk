package com.example.mylib_test.activity.study

import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.ViewCompat
import com.example.mylib_test.R
import com.zone.lib.base.controller.activity.BaseFeatureActivity
import kotlinx.android.synthetic.main.a_ky_app_nest_scroll_top.*


class KYAppBarNestScrollTopViewActivity : BaseFeatureActivity() {

    override fun setContentView() {
        setContentView(R.layout.a_ky_app_nest_scroll_top)
    }

    override fun initData() {
//        rv.layoutManager = LinearLayoutManager(this)
//        rv.adapter = QuickAdapter<String>(this).apply {
//            registerDelegate(TextDelegates())
//            add(NestedScrollBActivity.data)
//        }
        wv.loadUrl("http:www.baidu.com")
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(object : WebViewClient() {
            //点击网页中按钮时，在原页面打开
            override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
                view.loadUrl(url)
                return true
            }

            //页面加载完成后执行
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
//                if (xiaoQu.equals("LX")) {
//                    mWebView.loadUrl("javascript:LN('5772','9404','良乡校区');")
//                }
            }
        })
    }

    override fun setListener() {

    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v?.id) {
            R.id.tvClick -> {
//                ll.scro
//                cl.onne
//                cl.onStartNestedScroll(ll, cl, ViewCompat.SCROLL_AXIS_VERTICAL)
//                cl.onNestedPreFling(ll, 0f, -1000f)
//                nsv.fullScroll(View.FOCUS_UP)
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }
}
