package com.zdy.customview.webview

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.orhanobut.logger.Logger
import okhttp3.*
import java.io.ByteArrayInputStream
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.security.cert.CertificateException
import javax.net.ssl.SSLPeerUnverifiedException

//将资源请求转为 okhttp   和 webview注入流程
class MyWebClient(
        private val context: Context,
        private val userAgentString: String
) : WebViewClient() {

    companion object {
        const val HEADER_USER_AGENT = "User-Agent"
    }

    private var temporaryResponse: WebResourceResponse? = null
    var onMainFrameProgressChangedListener: ((Int) -> Unit)? = null
    var onPageLoadingStartedListener: (() -> Unit)? = null

    private val httpClient by lazy { initOkHttpClient() }


    private fun initOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .cookieJar(WebViewCookieJar())
                .followRedirects(true)//是否自动跟随 http 重定向
                .followSslRedirects(true)//是否自动跟随 https 重定向
                .addNetworkInterceptor { interceptOkHttpRequest(it) }
                .build()
    }

    private fun interceptOkHttpRequest(chain: Interceptor.Chain): Response? {
        val response = chain.proceed(chain.request())
        val responseBody = response.body()
        return if (responseBody == null) {
            null
        } else {
            buildProgressResponseInterceptor(response, responseBody)
        }
    }

    private fun buildProgressResponseInterceptor(response: Response, responseBody: ResponseBody): Response? {
        return response.newBuilder()
                .body(ProgressResponseBody(responseBody, {
                    onMainFrameProgressChangedListener?.invoke(it)
                }))
                .build()
    }

    // 页面(url)开始加载
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        onPageLoadingStartedListener?.invoke()
    }

    /**
     * 在每一次请求资源时，都会通过这个函数来回调,调用于非UI线程
     */
    override fun shouldInterceptRequest(view: WebView?, webRequest: WebResourceRequest?): WebResourceResponse? {
        return when {
            webRequest?.method != "GET" || !webRequest.isForMainFrame -> null
            else -> interceptRequest(webRequest)
        }
    }

    private fun interceptRequest(webRequest: WebResourceRequest): WebResourceResponse? {
        val request = buildRequest(webRequest)
        val address = webRequest.url.toString()
        if (address.startsWith("data:")) return null
        if (request == null) return null
        return try {
            if (temporaryResponse != null) {
                val response = temporaryResponse
                temporaryResponse = null
                response
            } else {
                val response = httpClient.newCall(request).execute()
                val body = response.body()?.string() ?: ""
//                val injectedBody = injectBody(body)
//                buildWebResponse(response, injectedBody)
                buildWebResponse(response, body)
            }
        } catch (e: SSLPeerUnverifiedException) {
            null
        } catch (e: UnknownHostException) {
            null
        } catch (e: ConnectException) {
            null
        } catch (e: SocketTimeoutException) {
            null
        } catch (e: CertificateException) {
            null
        } catch (e: Exception) {
            // The activity should not crash if something is wrong with the response.
            Logger.e("Error while injecting web3", e)
            null
        }
    }

    private fun buildWebResponse(response: Response, body: String): WebResourceResponse? {
        val byteStream = ByteArrayInputStream(body.toByteArray())
        val headerParser = HeaderParser()
        val contentType = headerParser.getContentTypeHeader(response)
        val charset = headerParser.getCharset(contentType)
        val mimeType = headerParser.getMimeType(contentType)
        return WebResourceResponse(mimeType, charset, byteStream)
    }

    //webview 注入 js
    private fun injectBody(body: String): String {
        val script = loadInjections()
        //注入脚本
        return injectScripts(body, script)
    }

    //版本大于24 注入sofa.js（web3） ，否则注入 webviewsupport.js + sofa.js
    private fun loadInjections(): String {
        return if (Build.VERSION.SDK_INT >= 24) {
            loadSofaScript()
        } else {
            loadWebViewSupportInjection() + loadSofaScript()
        }
    }

    private fun injectScripts(body: String?, script: String): String {
        val safeBody = body ?: ""
        val position = getInjectionPosition(safeBody)
        if (position == -1) return safeBody
        val beforeTag = safeBody.substring(0, position)
        val afterTab = safeBody.substring(position)
        return beforeTag + script + afterTab
    }

    private fun loadSofaScript(): String {
        val sb = StringBuilder()
        //以太坊网络
//        val networkId = Networks.getInstance().currentNetwork.id
//        val rcpUrl = "window.SOFA = {" +
//                "config: {netVersion: \"$networkId\", " +
//                "accounts: [\"${getWallet().paymentAddress}\"]," +
//                "rcpUrl: \"${context.getString(R.string.rcp_url)}\"}" +
//                "};"
//        sb.append(rcpUrl)
//        val stream = context.resources.openRawResource(R.raw.sofa)
//        val reader = BufferedReader(InputStreamReader(stream))
//        val text: List<String> = reader.readLines()
//        for (line in text) sb.append(line).append("\n")
        return "<script type=\"text/javascript\">$sb</script>"
    }

    private fun loadWebViewSupportInjection(): String {
        val sb = StringBuilder()
//        val stream = context.resources.openRawResource(R.raw.webviewsupport)
//        val reader = BufferedReader(InputStreamReader(stream))
//        val text: List<String> = reader.readLines()
//        for (line in text) sb.append(line).append("\n")
        return "<script type=\"text/javascript\">$sb</script>"
    }

    private fun getInjectionPosition(body: String): Int {
        val ieDetectTagIndex = body.indexOf("<!--[if", 0, true)
        val scriptTagIndex = body.indexOf("<script", 0, true)
        return if (ieDetectTagIndex < 0) scriptTagIndex else minOf(scriptTagIndex, ieDetectTagIndex)
    }

    private fun buildRequest(webRequest: WebResourceRequest): Request? {
        return try {
            val requestBuilder = Request.Builder()
                    .header(HEADER_USER_AGENT, userAgentString)
                    .get()
                    .url(webRequest.url.toString())
            webRequest.requestHeaders.forEach {
                requestBuilder.addHeader(it.key, it.value)
            }
            requestBuilder.build()
        } catch (e: IllegalArgumentException) {
            null
        }
    }
}