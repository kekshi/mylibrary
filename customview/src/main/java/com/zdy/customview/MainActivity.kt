package com.zdy.customview

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Toast
import com.zdy.baselibrary.utils.PermissionUtil
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        init()
        initAnimate()
//        AndroidAndJs()
        JsAndAndroid()
//        startActivityForResult(Intent(this@MainActivity, ScanActivity::class.java),0)

        mBtnJs.setOnClickListener {
            //方法一
//            mWebView.post {
            // 注意调用的JS方法名要对应上
            // 调用javascript的callJS()方法
//                mWebView.loadUrl("javascript:callJS()")
            //方法二 有回调 适用于4.4+
//            mWebView.evaluateJavascript("javascript:callJS()") {
//                Log.e("TAG", "JS返回结果：$it")
//            }
        }
    }

    private fun JsAndAndroid() {
        val settings = mWebView.settings
        // 设置与Js交互的权限
        settings.javaScriptEnabled = true
        // 通过addJavascriptInterface()将Java对象映射到JS对象
        //参数1：Javascript对象名
        //参数2：Java对象名 ,和js中的对象名要一致
        mWebView.addJavascriptInterface(AndroidToJs(), "test")
        // 加载JS代码
        // 格式规定为:file:///android_asset/文件名.html
        mWebView.loadUrl("file:///android_asset/JavascriptToAndroid.html")
    }

    private fun AndroidAndJs() {
        val settings = mWebView.settings
        // 设置与Js交互的权限
        settings.javaScriptEnabled = true
        // 设置允许JS弹窗
        settings.javaScriptCanOpenWindowsAutomatically = true
        // 先载入JS代码
        // 格式规定为:file:///android_asset/文件名.html
        mWebView.loadUrl("file:///android_asset/javascript.html")


        // 由于设置了弹窗检验调用结果,所以需要支持js对话框
        // webview只是载体，内容的渲染需要使用webviewChromClient类去实现
        // 通过设置WebChromeClient对象处理JavaScript的对话框
        //设置响应js 的Alert()函数
        mWebView.webChromeClient = object : WebChromeClient() {
            override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                val b = AlertDialog.Builder(this@MainActivity)
                b.setTitle("Alert")
                b.setMessage(message)
                b.setPositiveButton(android.R.string.ok, { dialog, which -> result!!.confirm() })
                b.setCancelable(false)
                b.create().show()
                return true
            }
        }

    }

    private fun initAnimate() {
        //X轴平移500像素
//        val animator = ObjectAnimator.ofFloat(image, "translationX", 500f)
//        animator.interpolator = AnticipateOvershootInterpolator()
//        animator.duration = 2000
//        animator.start()

//        //Argb渐变色动画
//        val animator = ObjectAnimator.ofArgb(view, "color", android.R.color.holo_red_dark, android.R.color.holo_green_dark)
//        animator.duration = 2000
//        animator.start()

    }


    override fun onNewIntent(intent: Intent) {
        Log.e("TAG", "onNewIntent")
        val input = intent.getStringExtra(com.zdy.qrcodelibrary.activity.ScanActivity.INTENT_EXTRA_RESULT)
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                val input = intent.getStringExtra(com.zdy.qrcodelibrary.activity.ScanActivity.INTENT_EXTRA_RESULT)
                Toast.makeText(this, input, Toast.LENGTH_SHORT).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, intent)
        }
    }


    class AndroidToJs {
        @JavascriptInterface
        public fun hello(msg: String) {
            Log.e("TAG", "JS调用了Android的hello方法")
            (this as MainActivity).xiangce()
        }
    }

    public fun xiangce() {
        PermissionUtil.hasPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                PermissionUtil.READ_EXTERNAL_STORAGE_PERMISSION,
                { startGalleryActivity() }
        )
    }

    private fun startGalleryActivity() {
        val pickPictureIntent = Intent()
                .setType("image/*")
                .setAction(Intent.ACTION_GET_CONTENT)
        if (pickPictureIntent.resolveActivity(packageManager) == null) return
        val chooserIntent = Intent.createChooser(
                pickPictureIntent,
                "选择图片"
        )
        startActivityForResult(chooserIntent, 1)
    }
}
