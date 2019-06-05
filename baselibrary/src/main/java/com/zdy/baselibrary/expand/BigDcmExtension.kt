package com.zdy.baselibrary.expand

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * 金额显示拓展
 */
fun String.toBigDcm(): BigDecimal = BigDecimal(this)

fun Int.toBigDcm(): BigDecimal = BigDecimal(this.toString())

fun Double.toBigDcm(): BigDecimal = BigDecimal(this.toString())

fun BigDecimal.toText(): String {
    var strValue = stripTrailingZeros().toString()
    if (strValue.contains("E")) {
        strValue = stripTrailingZeros().toDouble().toString()
    }
    return strValue
}

fun BigDecimal.toText(precision: Int): String { // 保留几位小数
    var format = "0.0"
    if (precision > 1) {
        for (i in 2..precision) {
            format = format + "0"
        }
    }
    return DecimalFormat(format).format(this)

}

fun BigDecimal.formatPrice(halfUp: Boolean, precision: Int): String {
    val formater = DecimalFormat()
    formater.maximumFractionDigits = precision
    formater.minimumFractionDigits = precision
    formater.groupingSize = 3
    formater.roundingMode = if (halfUp) RoundingMode.HALF_UP else RoundingMode.FLOOR
    return formater.format(this)
}


