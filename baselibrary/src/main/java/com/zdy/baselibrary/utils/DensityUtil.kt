package com.zdy.baselibrary.utils

import android.content.res.Resources


/**
 * Created by Icy on 2017/11/16.
 */

object DensityUtil {
    fun dip2px(dpValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun getScreenWidth(): Int {
        val dm = Resources.getSystem().displayMetrics
        return dm.widthPixels
    }

    fun sp2px(spValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.scaledDensity
        return (spValue * scale + 0.5f).toInt()
    }
}
