package com.imio.mine.view

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import com.zdy.baselibrary.utils.DensityUtil

/**
 * Created by linapo on 2018/5/28.
 */
class SlidingDelView : HorizontalScrollView {

    private var mScale: DisplayMetrics
    private var mDelBtnWidth: Int

    constructor(ctx: Context) : this(ctx, null)

    constructor(ctx: Context, attrs: AttributeSet?) : this(ctx, attrs, 0)

    constructor(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(ctx, attrs, defStyleAttr) {
        mScale = context.resources.displayMetrics
        mDelBtnWidth = DensityUtil.dip2px(72f)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        val layout: LinearLayout = getChildAt(0) as LinearLayout
        val layoutLeft = layout.getChildAt(0)
        val layoutRight = layout.getChildAt(1)

        layout.layout(layout.left, layout.top, layout.left + measuredWidth + mDelBtnWidth, layout.bottom)
        layoutLeft.layout(layoutLeft.left, layoutLeft.top, layoutLeft.left + measuredWidth, layout.bottom)
        layoutRight.layout(layoutLeft.left + measuredWidth, layoutRight.top, layoutLeft.left + measuredWidth + mDelBtnWidth, layoutRight.bottom)
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (ev!!.action == MotionEvent.ACTION_DOWN) {
            return true
        }
        if (ev.action == MotionEvent.ACTION_CANCEL || ev.action == MotionEvent.ACTION_UP) {
            val range = 70
            if (scrollX < mDelBtnWidth - range) {
                smoothScrollTo(0, 0)
            } else {
                smoothScrollTo(mDelBtnWidth, 0)
            }
            return true
        }
        return super.onTouchEvent(ev)
    }

    fun reset() {
        smoothScrollTo(0, 0)
    }

}