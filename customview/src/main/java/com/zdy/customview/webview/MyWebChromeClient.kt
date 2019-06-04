package com.zdy.customview.webview

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.webkit.ConsoleMessage
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.orhanobut.logger.Logger
import com.zdy.customview.BuildConfig

class MyWebChromeClient : WebChromeClient(){

    var onOpenFilePickerListener: ((ValueCallback<Array<Uri>>?) -> Boolean)? = null
    var onProgressChangedListener: ((Int) -> Unit)? = null
    var onTitleReceivedListener: ((String) -> Unit)? = null
    var onIconReceivedListener: ((Bitmap) -> Unit)? = null

    // 为'<input type="file" />'显示文件选择器，返回false使用默认处理
    //打开相册或调用相机
    override fun onShowFileChooser(
            webView: WebView?,
            filePathCallback: ValueCallback<Array<Uri>>?,
            fileChooserParams: FileChooserParams?): Boolean {
        return onOpenFilePickerListener?.invoke(filePathCallback) ?: false
    }

    //获取加载进度
    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        onProgressChangedListener?.invoke(newProgress)
    }

    // 接收JavaScript控制台消息
    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT <= 18) {
            Logger.d("WebView Console: ${consoleMessage?.message()}")
        }
        return super.onConsoleMessage(consoleMessage)
    }

    override fun onReceivedTitle(view: WebView?, title: String?) {
        if (title != null) onTitleReceivedListener?.invoke(title)
    }

    override fun onReceivedIcon(view: WebView?, icon: Bitmap?) {
        if (icon != null) onIconReceivedListener?.invoke(icon)
    }

}