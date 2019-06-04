package com.zdy.customview.webview

import okhttp3.Response

class HeaderParser {

    companion object {
        const val DEFAULT_CHARSET = "text/html"
        const val DEFAULT_MIME_TYPE = "utf-8"
    }

    fun getMimeType(contentType: String): String {
        val regexResult = Regex("^.*(?=;)").find(contentType)
        return regexResult?.value ?: DEFAULT_MIME_TYPE
    }

    fun getCharset(contentType: String): String {
        val regexResult = Regex(
                "charset=([a-zA-Z0-9-]+)",
                RegexOption.IGNORE_CASE
        ).find(contentType)
        val groupValues = regexResult?.groupValues ?: return DEFAULT_CHARSET
        return if (groupValues.size != 2) DEFAULT_CHARSET
        else regexResult.groupValues[1]
    }

    fun getContentTypeHeader(response: Response): String {
        val headers = response.headers()
        val contentType = headers.get("Content-Type")
                ?: headers.get("content-Type")
                ?: "text/html; charset=utf-8"
        contentType.trim()
        return contentType
    }
}