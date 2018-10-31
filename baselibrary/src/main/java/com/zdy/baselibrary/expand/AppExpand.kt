package com.imio.publicchainwallet.expand

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast

/**
 * 复制内容到剪切板
 */
fun copyAddress(context: Context, str: String) {
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    //创建ClipData对象
    val clipData = ClipData.newPlainText("copy", str)
    //添加ClipData对象到剪切板中
    clipboardManager.primaryClip = clipData
    Toast.makeText(context, "地址已複製", Toast.LENGTH_SHORT).show()
}