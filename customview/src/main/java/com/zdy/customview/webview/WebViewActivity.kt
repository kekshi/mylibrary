package com.zdy.customview.webview

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import com.zdy.baselibrary.base.BaseCompatActivity
import com.zdy.customview.R
import kotlinx.android.synthetic.main.activity_webview.*

class WebViewActivity : BaseCompatActivity() {

    override fun onErrorViewClick(v: View?) {
    }

    override fun initView(savedInstanceState: Bundle?) {
        initWebSettings()
        mWebView.webViewClient = MyWebClient(this,mWebView.settings.userAgentString)
        mWebView.webChromeClient = MyWebChromeClient()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebSettings() {
        val webSettings = mWebView.settings
        // 是否支持Javascript，默认值false
        webSettings.javaScriptEnabled = true
        // 根据cache-control决定是否从网络上取数据
        webSettings.cacheMode = WebSettings.LOAD_DEFAULT
        // 是否使用内置缩放机制
        webSettings.builtInZoomControls = true
        // 是否显示内置缩放控件
        webSettings.displayZoomControls = false
        // 是否支持viewport属性，默认值 false
        // 页面通过`<meta name="viewport" ... />`自适应手机屏幕
        webSettings.useWideViewPort = true
        // 是否使用overview mode加载页面，默认值 false
        // 当页面宽度大于WebView宽度时，缩小使页面宽度等于WebView宽度
        webSettings.loadWithOverviewMode = true
        // 存储(storage)
        // 启用HTML5 DOM storage API，默认值 false
//        webSettings.domStorageEnabled = true

//        webSettings.setGeolocationEnabled(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 开启网页内容(js,css,html...)调试模式，添加于API19
            WebView.setWebContentsDebuggingEnabled(true)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_webview
    }
}