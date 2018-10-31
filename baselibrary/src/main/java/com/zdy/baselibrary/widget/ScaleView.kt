package com.imio.financialmng.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.widget.Scroller
import com.zdy.baselibrary.R
import com.zdy.baselibrary.utils.DensityUtils

/**
 * Created by linapo on 2018/8/8.
 * 刻度View，支持手动输入和滑动
 */
class ScaleView : View {
    private var mMinVal: Int = DEF_MIN_VAL // 最小刻度
    private var mMaxVal: Int = DEF_MAX_VAL // 最大刻度
    private var mValSpace: Int = DEF_VAL_SPACE // 刻度顯示間距，如2000、2500、3000，這裡間距就是500
    private var mPerValue: Int = DEF_ONE_SPACE // 一個刻度代表多少
    private var mSelectorValue: Int = DEF_INITIAL_VAL // 當前刻度
    private var mRealValue: Int = mSelectorValue // 真实刻度

    private var mLineMargin: Int // 刻度間距
    private var mLineWidth: Int
    private var mScaleHeightNormal: Int // 短刻度線高度
    private var mScaleHeightLong: Int // 長刻度線高度

    private var mHighLightColor: Int // 刻度線強調顏色
    private var mNormalColor: Int // 普通刻度線顏色
    private var mCenterColor: Int // 中間刻度線顏色
    private var mScaleTextSize: Float
    private var mScaleTextColor: Int
    private var mScaleTextHeight: Float
    private var mIndicatorTextSize: Float
    private var mIndicatorTextHeight: Float

    private var mScroller: Scroller
    private var mScrollerListener: OnScrollerListener? = null
    private var mScrollLastX: Int = 0
    private var mScrollerLastY: Int = 0
    private var mMove: Int = 0 // 上一次按下后移动的距离
    private var mOffset: Float = 0f
    private var mVelocityTracker: VelocityTracker? = null // 跟踪触摸屏速率
    private var mTotalLine: Int = 0 // 共有多少條刻度
    private var mMaxOffset: Int = 0 // 所有刻度總長

    private var mScalePaint: Paint
    private var mIndicatorPaint: Paint

    private var mIsMove: Boolean = false

    companion object {
        const val DEF_MIN_VAL = 0
        const val DEF_MAX_VAL = 4000
        const val DEF_VAL_SPACE = 5000
        const val DEF_ONE_SPACE = 1000
        const val DEF_INITIAL_VAL = 0
    }

    constructor(ctx: Context) : this(ctx, null)
    constructor(ctx: Context, attrs: AttributeSet?) : this(ctx, attrs, 0)
    constructor(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(ctx, attrs, defStyleAttr) {
        attrs?.let {
            val typedArray = ctx.obtainStyledAttributes(it, R.styleable.ScaleView)
            mMinVal = typedArray.getInt(R.styleable.ScaleView_minValue, DEF_MIN_VAL)
            mMaxVal = typedArray.getInt(R.styleable.ScaleView_maxValue, DEF_MAX_VAL)
            mValSpace = typedArray.getInt(R.styleable.ScaleView_valSpace, DEF_VAL_SPACE)
            mSelectorValue = typedArray.getInt(R.styleable.ScaleView_initialVal, DEF_INITIAL_VAL)
            mRealValue = mSelectorValue
            typedArray.recycle()
        }
        mLineMargin = DensityUtils.dp2px(10f)
        mLineWidth = DensityUtils.dp2px(2f)
        mScaleHeightNormal = DensityUtils.dp2px(12f)
        mScaleHeightLong = DensityUtils.dp2px(18f)
        mHighLightColor = ContextCompat.getColor(ctx, R.color.white)
        mNormalColor = Color.parseColor("#50ffffff")
        mCenterColor = ContextCompat.getColor(ctx, R.color.red)
        mScaleTextColor = ContextCompat.getColor(ctx, R.color.font_8e)
        mTotalLine = ((mMaxVal - mMinVal) / mPerValue) + 1
        mMaxOffset = -(mTotalLine - 1) * mLineMargin
        mScaleTextSize = DensityUtils.dp2px(12f).toFloat()
        mIndicatorTextSize = DensityUtils.dp2px(14f).toFloat()

        mScroller = Scroller(ctx)

        mScalePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mScalePaint.style = Paint.Style.STROKE
        mScalePaint.textSize = mScaleTextSize
        mScaleTextHeight = getFontHeight(mScalePaint)

        mIndicatorPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mIndicatorPaint.textSize = mIndicatorTextSize
        mIndicatorPaint.color = mCenterColor
        mIndicatorTextHeight = getFontHeight(mIndicatorPaint)
    }

    fun goValue(value: Int) {
        if (value < 0) {
            mSelectorValue = 0
            mRealValue = 0
        } else if (value > mMaxVal) {
            mSelectorValue = mMaxVal
            mRealValue = mMaxVal
        } else {
            mSelectorValue = Math.round(value * 1f / mPerValue) * mPerValue
            mRealValue = value
        }

        mOffset = ((mMinVal - mSelectorValue) / mPerValue * mLineMargin).toFloat()
        invalidate()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        goValue(mSelectorValue)
    }

    fun getSelectedVal(): Int {
        return mSelectorValue
    }

    fun getRealVal(): Int {
        return mRealValue
    }

    private fun getFontHeight(paint: Paint): Float {
        val fm = paint.getFontMetrics()
        return fm.descent - fm.ascent
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {
            for (i in 0 until mTotalLine) {
                val startX = measuredWidth / 2 + mOffset + i * mLineMargin
                if (startX < 0 || startX > measuredWidth) continue

                val value = mMinVal + i * mPerValue
                drawScale(it, mScalePaint, startX, value % mValSpace == 0,
                        value.toString())
            }
            drawIndicator(canvas, mIndicatorPaint)
        }
    }

    private fun drawScale(canvas: Canvas, paint: Paint, x: Float, isLong: Boolean, value: String) {
        val height = if (isLong) mScaleHeightLong else mScaleHeightNormal
        val startY = ((measuredHeight - height) / 2).toFloat()
        val endY = startY + height

        paint.strokeWidth = mLineWidth.toFloat()
        paint.color = if (isLong) mHighLightColor else mNormalColor
        canvas.drawLine(x, startY, x, endY, paint)

        if (isLong) {
            paint.strokeWidth = 0f
            paint.color = mScaleTextColor
            canvas.drawText(value, x - paint.measureText(value) / 2,
                    endY + DensityUtils.dp2px(9f) + mScaleTextHeight, paint)
        }
    }

    private fun drawIndicator(canvas: Canvas, paint: Paint) {
        paint.style = Paint.Style.STROKE
        val x = (measuredWidth / 2).toFloat()
        val lineHeight = DensityUtils.dp2px(28f)
        val lineStartY = ((measuredHeight - lineHeight) / 2).toFloat()
        val lineEndY = lineStartY + lineHeight

        paint.strokeWidth = mLineWidth.toFloat()
        canvas.drawLine(x, lineStartY, x, lineEndY, paint)

        paint.strokeWidth = 0f
        val value = mRealValue.toString()
        val radius = DensityUtils.dp2px(3f).toFloat()
        canvas.drawText(value, x - paint.measureText(value) / 2,
                lineStartY - radius * 2 - mIndicatorTextHeight / 2, paint)

        paint.style = Paint.Style.FILL
        canvas.drawCircle(x, lineStartY, radius, paint)
    }

    override fun computeScroll() {
        super.computeScroll()
        if (mScroller.computeScrollOffset()) {
            if (mScroller.currX == mScroller.finalX)
                countMoveEnd()
            else {
                mMove = mScrollLastX - mScroller.currX
                changeMoveAndValue()
                mScrollLastX = mScroller.currX
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event!!.x
        val y = event.y

        if (mVelocityTracker == null) mVelocityTracker = VelocityTracker.obtain()
        mVelocityTracker!!.addMovement(event)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mScroller.forceFinished(true)
                mMove = 0

                mScrollLastX = x.toInt()
                mScrollerLastY = y.toInt()
                return super.onTouchEvent(event)
            }
            MotionEvent.ACTION_MOVE -> {
                if (Math.abs(mScrollLastX - x) > Math.abs(mScrollerLastY - y))
                    parent.requestDisallowInterceptTouchEvent(true)
                mMove = (mScrollLastX - x).toInt()
                changeMoveAndValue()
                mIsMove = true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (mIsMove) {
                    mIsMove = false
                    countMoveEnd()
                    countVelocityTracker()
                    return false
                } else {
                    return super.onTouchEvent(event)
                }
            }
        }
        mScrollLastX = x.toInt()
        mScrollerLastY = y.toInt()
        return true
    }

    /**
     * 矯正使指針在刻度上
     */
    private fun countMoveEnd() {
        mOffset -= mMove
        if (mOffset <= mMaxOffset) mOffset = mMaxOffset.toFloat()
        else if (mOffset >= 0) mOffset = 0f

        mScrollLastX = 0
        mMove = 0

        mSelectorValue = mMinVal + Math.round(Math.abs(mOffset) / mLineMargin) * mPerValue
        mRealValue = mSelectorValue
        mOffset = ((mMinVal - mSelectorValue) / mPerValue * mLineMargin).toFloat()

        notifyValueChange()
        postInvalidate()
    }

    private fun changeMoveAndValue() {
        mOffset -= mMove

        if (mOffset <= mMaxOffset) {
            mOffset = mMaxOffset.toFloat()
            mMove = 0
            mScroller.forceFinished(true)
        } else if (mOffset >= 0) {
            mOffset = 0f
            mMove = 0
            mScroller.forceFinished(true)
        }

        mSelectorValue = mMinVal + Math.round(Math.abs(mOffset) / mLineMargin) * mPerValue
        mRealValue = mSelectorValue

        notifyValueChange()
        postInvalidate()
    }

    private fun notifyValueChange() {
        mScrollerListener?.onValueChanged(mRealValue)
    }

    private fun countVelocityTracker() {
        mVelocityTracker?.let {
            it.computeCurrentVelocity(700)
            val xVelocity = it.xVelocity
            if (Math.abs(xVelocity) > ViewConfiguration.get(context).scaledMinimumFlingVelocity)
                mScroller.fling(0, 0, xVelocity.toInt(), 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0)
        }
    }

    interface OnScrollerListener {
        fun onValueChanged(value: Int)
    }

    fun setOnScrollerListener(listener: OnScrollerListener) {
        mScrollerListener = listener
    }
}