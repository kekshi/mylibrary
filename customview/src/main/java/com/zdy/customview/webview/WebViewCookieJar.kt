package com.zdy.customview.webview

import android.webkit.CookieManager
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class WebViewCookieJar : CookieJar {
    private val webViewCookieManager by lazy { CookieManager.getInstance() }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        val urlString = url.toString()
        cookies.forEach { webViewCookieManager.setCookie(urlString, it.toString()) }
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val urlString = url.toString()
        val cookiesString = webViewCookieManager.getCookie(urlString)
        if (cookiesString != null && cookiesString.isNotEmpty()) {
            val cookieHeaders = cookiesString.split(";".toRegex())
            val cookies = mutableListOf<Cookie>()
            cookieHeaders.map { Cookie.parse(url, it) }
                    .forEach { it?.let { cookies.add(it) } }
            return cookies
        }
        return emptyList()
    }
}