package com.zdy.customview.webview

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*

class ProgressResponseBody(
        private val responseBody: ResponseBody,
        private val progressListener: (Int) -> Unit
) : ResponseBody() {

    private var bufferedSource: BufferedSource? = null

    override fun contentLength(): Long = responseBody.contentLength()

    override fun contentType(): MediaType? = responseBody.contentType()

    override fun source(): BufferedSource? {
        if (bufferedSource == null) bufferedSource = Okio.buffer(source(responseBody.source()))
        return bufferedSource
    }

    private fun source(source: Source): Source {
        return object : ForwardingSource(source) {
            private var totalBytesRead = 0L

            override fun read(sink: Buffer?, byteCount: Long): Long {
                if (sink == null) return totalBytesRead
                val bytesRead = super.read(sink, byteCount)
                totalBytesRead += if (bytesRead != -1L) bytesRead else 0L
                val contentLength = responseBody.contentLength() or Math.max(bytesRead, 5000L)
                val progress = if (bytesRead == -1L) 0L else (100 * bytesRead) / contentLength
                progressListener.invoke(progress.toInt())
                return bytesRead
            }
        }
    }
}