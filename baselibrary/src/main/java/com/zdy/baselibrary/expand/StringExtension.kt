package com.zdy.baselibrary.expand

import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan

/**
 * 字符串替换为*
 */
fun String.replaceName(begin: Int = 0, end: Int): String {
    if (begin >= this.length || begin < 0) {
        return this
    }
    if (end >= this.length || end < 0) {
        return this
    }
    if (begin >= end) {
        return this
    }
    var starStr = ""
    for (i in begin until end) {
        starStr = "$starStr*"
    }
    return this.substring(0, begin) + starStr + this.substring(end, this.length)
}

/**
 * 设置金额字符串元和分的大小
 */
fun String.formatPriceTextSize(yuanSize: Int, centSize: Int): SpannableString {
    val pointIndex = indexOf(".")
    if (pointIndex == -1) {
        return SpannableString(this)
    }

    return SpannableString(this)
            .apply {
                setSpan(AbsoluteSizeSpan(yuanSize, true), 0, pointIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(AbsoluteSizeSpan(centSize, true), pointIndex + 1, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
}

/**
 * 设置数字字符串，自定义分隔符前后的大小
 */
fun String.formatNumberTextSize(yuanSize: Int, centSize: Int, sign: String): SpannableString {
    val pointIndex = indexOf(sign)
    if (pointIndex == -1) {
        return SpannableString(this)
    }

    return SpannableString(this)
            .apply {
                setSpan(AbsoluteSizeSpan(yuanSize, true), 0, pointIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(AbsoluteSizeSpan(centSize, true), pointIndex + 1, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
}