package com.zdy.baselibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zdy.baselibrary.R;
import com.zdy.baselibrary.utils.DensityUtils;

import java.util.List;

/**
 * 数据滑动控件
 */
public class WheelView extends View {
    /**
     * 数据集合
     */
    private List<String> mDataList;
    /**
     * Item的Text的颜色
     */
    @ColorInt
    private int mTextColor;
    private int mTextSize;
    private Paint mTextPaint;
    /**
     * 字体渐变，开启后越靠近边缘，字体越模糊
     */
    private boolean mIsTextGradual;
    /**
     * 选中的Item的Text颜色
     */
    @ColorInt
    private int mSelectedItemTextColor;
    /**
     * 选中的Item的Text大小
     */
    private int mSelectedItemTextSize;
    private Paint mSelectedItemPaint;
    private Paint mPaint;
    /**
     * 最大的一个Item的文本的宽高
     */
    private int mTextMaxWidth, mTextMaxHeight;
    /**
     * 输入的一段文字，可以用来测量 mTextMaxWidth
     */
    private String mItemMaximumWidthText;
    /**
     * 显示的Item一半的数量（中心Item上下两边分别的数量）
     * 总显示的数量为 mHalfVisibleItemCount * 2 + 1
     */
    private int mHalfVisibleItemCount;
    /**
     * 两个Item之间的高度间隔
     */
    private int mItemHeightSpace;
    private int mItemHeight;
    /**
     * 当前的Item的位置
     */
    private int mCurrentPosition;
    /**
     * 是否将中间的Item放大
     */
    private boolean mIsZoomInSelectedItem;
    /**
     * 整个控件的可绘制面积
     */
    private Rect mDrawnRect;
    /**
     * 中心被选中的Item的坐标矩形
     */
    private Rect mSelectedItemRect;
    /**
     * 第一个Item的绘制Text的坐标
     */
    private int mFirstItemDrawX, mFirstItemDrawY;
    /**
     * 中心的Item绘制text的Y轴坐标
     */
    private int mCenterItemDrawnY;
    private Scroller mScroller;
    private int mTouchSlop;
    /**
     * 该标记的作用是，令mTouchSlop仅在一个滑动过程中生效一次。
     */
    private boolean mTouchSlopFlag;
    private VelocityTracker mTracker;
    private int mTouchDownY;
    /**
     * Y轴Scroll滚动的位移
     */
    private int mScrollOffsetY;
    /**
     * 最后手指Down事件的Y轴坐标，用于计算拖动距离
     */
    private int mLastDownY;
    /**
     * 最大可以Fling的距离
     */
    private int mMaxFlingY, mMinFlingY;
    /**
     * 滚轮滑动时的最小/最大速度
     */
    private int mMinimumVelocity = 50, mMaximumVelocity = 12000;
    /**
     * 是否是手动停止滚动
     */
    private boolean mIsAbortScroller;
    private LinearGradient mLinearGradient;
    private Handler mHandler = new Handler();
    private OnWheelChangeListener mOnWheelChangeListener;

    public WheelView(Context context) {
        this(context, null);
    }

    public WheelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WheelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initPaint();
        mLinearGradient = new LinearGradient(mTextColor, mSelectedItemTextColor);
        mDrawnRect = new Rect();
        mSelectedItemRect = new Rect();
        mScroller = new Scroller(context);

        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
    }

    private void initAttrs(Context context, @Nullable AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WheelView);
        mTextSize = a.getDimensionPixelSize(R.styleable.WheelView_itemTextSize,
                DensityUtils.sp2px(14f));
        mTextColor = a.getColor(R.styleable.WheelView_itemTextColor,
                Color.WHITE);
        mIsTextGradual = a.getBoolean(R.styleable.WheelView_textGradual, true);
        mHalfVisibleItemCount = a.getInteger(R.styleable.WheelView_halfVisibleItemCount, 2);
        mItemMaximumWidthText = a.getString(R.styleable.WheelView_itemMaximumWidthText);
        mSelectedItemTextColor = a.getColor(R.styleable.WheelView_selectedTextColor, Color.WHITE);
        mSelectedItemTextSize = a.getDimensionPixelSize(R.styleable.WheelView_selectedTextSize,
                DensityUtils.sp2px(16f));
        mCurrentPosition = a.getInteger(R.styleable.WheelView_currentItemPosition, 0);
        mItemHeightSpace = a.getDimensionPixelSize(R.styleable.WheelView_itemHeightSpace,
                DensityUtils.dp2px(16f));
        mIsZoomInSelectedItem = a.getBoolean(R.styleable.WheelView_zoomInSelectedItem, true);
        a.recycle();
    }

    private Runnable mScrollerRunnable = new Runnable() {
        @Override
        public void run() {

            if (mScroller.computeScrollOffset()) {

                mScrollOffsetY = mScroller.getCurrY();
                postInvalidate();
                mHandler.postDelayed(this, 16);
            }
            if (mScroller.isFinished() || (mScroller.getFinalY() == mScroller.getCurrY()
                    && mScroller.getFinalX() == mScroller.getCurrX())) {

                if (mItemHeight == 0) {
                    return;
                }
                int position = -mScrollOffsetY / mItemHeight;
                position = fixItemPosition(position);
                if (mCurrentPosition != position) {
                    mCurrentPosition = position;
                    if (mOnWheelChangeListener == null) {
                        return;
                    }
                    mOnWheelChangeListener.onWheelSelected(mDataList.get(position),
                            position);
                }
            }
        }
    };

    public String getCurValue() {
        return mDataList.get(mCurrentPosition);
    }

    public void computeTextSize() {
        mTextMaxWidth = mTextMaxHeight = 0;
        if (mDataList.size() == 0) {
            return;
        }

        //这里使用最大的,防止文字大小超过布局大小。
        mPaint.setTextSize(mSelectedItemTextSize > mTextSize ? mSelectedItemTextSize : mTextSize);

        if (!TextUtils.isEmpty(mItemMaximumWidthText)) {
            mTextMaxWidth = (int) mPaint.measureText(mItemMaximumWidthText);
        } else {
            mTextMaxWidth = (int) mPaint.measureText(mDataList.get(mDataList.size() - 1).toString());
        }
        Paint.FontMetrics metrics = mPaint.getFontMetrics();
        mTextMaxHeight = (int) (metrics.bottom - metrics.top);
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        mSelectedItemPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        mSelectedItemPaint.setStyle(Paint.Style.FILL);
        mSelectedItemPaint.setTextAlign(Paint.Align.CENTER);
        mSelectedItemPaint.setColor(mSelectedItemTextColor);
        mSelectedItemPaint.setTextSize(mSelectedItemTextSize);
    }

    /**
     * 计算实际的大小
     *
     * @param specMode 测量模式
     * @param specSize 测量的大小
     * @param size     需要的大小
     * @return 返回的数值
     */
    private int measureSize(int specMode, int specSize, int size) {
        if (specMode == MeasureSpec.EXACTLY) {
            return specSize;
        } else {
            return Math.min(specSize, size);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        int specHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = mTextMaxWidth;
        int height = (mTextMaxHeight + mItemHeightSpace) * getVisibleItemCount();

        width += getPaddingLeft() + getPaddingRight();
        height += getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(measureSize(specWidthMode, specWidthSize, width),
                measureSize(specHeightMode, specHeightSize, height));
    }

    /**
     * 计算Fling极限
     * 如果为Cyclic模式则为Integer的极限值，如果正常模式，则为一整个数据集的上下限。
     */
    private void computeFlingLimitY() {
        mMinFlingY = Integer.MIN_VALUE;
        mMaxFlingY = Integer.MAX_VALUE;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mDrawnRect.set(getPaddingLeft(), getPaddingTop(),
                getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
        mItemHeight = mDrawnRect.height() / getVisibleItemCount();
        mFirstItemDrawX = mDrawnRect.centerX();
        mFirstItemDrawY = (int) ((mItemHeight - (mSelectedItemPaint.ascent() + mSelectedItemPaint.descent())) / 2);
        //中间的Item边框
        mSelectedItemRect.set(getPaddingLeft(), mItemHeight * mHalfVisibleItemCount,
                getWidth() - getPaddingRight(), mItemHeight + mItemHeight * mHalfVisibleItemCount);
        computeFlingLimitY();
        mCenterItemDrawnY = mFirstItemDrawY + mItemHeight * mHalfVisibleItemCount;

        mScrollOffsetY = -mItemHeight * mCurrentPosition;
    }

    /**
     * 修正坐标值，让其回到dateList的范围内
     *
     * @param position 修正前的值
     * @return 修正后的值
     */
    private int fixItemPosition(int position) {
        if (position < 0) {
            //将数据集限定在0 ~ mDataList.size()-1之间
            position = mDataList.size() + (position % mDataList.size());

        }
        if (position >= mDataList.size()) {
            //将数据集限定在0 ~ mDataList.size()-1之间
            position = position % mDataList.size();
        }
        return position;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#80ffffff"));
        canvas.drawLine(getPaddingLeft(), mItemHeight * mHalfVisibleItemCount,
                getWidth() - getPaddingRight(), mItemHeight * mHalfVisibleItemCount, mPaint);
        canvas.drawLine(getPaddingLeft(), mItemHeight + mItemHeight * mHalfVisibleItemCount,
                getWidth() - getPaddingRight(), mItemHeight + mItemHeight * mHalfVisibleItemCount, mPaint);

        int drawnSelectedPos = -mScrollOffsetY / mItemHeight;
        mPaint.setStyle(Paint.Style.FILL);
        //首尾各多绘制一个用于缓冲
        for (int drawDataPos = drawnSelectedPos - mHalfVisibleItemCount - 1;
             drawDataPos <= drawnSelectedPos + mHalfVisibleItemCount + 1; drawDataPos++) {
            int position = drawDataPos;
            position = fixItemPosition(position);

            String data = mDataList.get(position);
            int itemDrawY = mFirstItemDrawY + (drawDataPos + mHalfVisibleItemCount) * mItemHeight + mScrollOffsetY;
            //距离中心的Y轴距离
            int distanceY = Math.abs(mCenterItemDrawnY - itemDrawY);

            if (mIsTextGradual) {
                //文字颜色渐变要在设置透明度上边，否则会被覆盖
                //计算文字颜色渐变
                if (distanceY < mItemHeight) {  //距离中心的高度小于一个ItemHeight才会开启渐变
                    float colorRatio = 1 - (distanceY / (float) mItemHeight);
                    mSelectedItemPaint.setColor(mLinearGradient.getColor(colorRatio));
                    mTextPaint.setColor(mLinearGradient.getColor(colorRatio));
                } else {
                    mSelectedItemPaint.setColor(mSelectedItemTextColor);
                    mTextPaint.setColor(mTextColor);
                }
                //计算透明度渐变
                float alphaRatio;
                if (itemDrawY > mCenterItemDrawnY) {
                    alphaRatio = (mDrawnRect.height() - itemDrawY) /
                            (float) (mDrawnRect.height() - (mCenterItemDrawnY));
                } else {
                    alphaRatio = itemDrawY / (float) mCenterItemDrawnY;
                }

                alphaRatio = alphaRatio < 0 ? 0 : alphaRatio;
                mSelectedItemPaint.setAlpha((int) (alphaRatio * 255));
                mTextPaint.setAlpha((int) (alphaRatio * 255));
            }

            //开启此选项,会将越靠近中心的Item字体放大
            if (mIsZoomInSelectedItem) {
                if (distanceY < mItemHeight) {
                    float addedSize = (mItemHeight - distanceY) / (float) mItemHeight * (mSelectedItemTextSize - mTextSize);
                    mSelectedItemPaint.setTextSize(mTextSize + addedSize);
                    mTextPaint.setTextSize(mTextSize + addedSize);
                } else {
                    mSelectedItemPaint.setTextSize(mTextSize);
                    mTextPaint.setTextSize(mTextSize);
                }
            } else {
                mSelectedItemPaint.setTextSize(mTextSize);
                mTextPaint.setTextSize(mTextSize);
            }
            String drawText = data.toString();
            //在中间位置的Item作为被选中的。
            if (distanceY < mItemHeight / 2) {
                canvas.drawText(drawText, mFirstItemDrawX, itemDrawY, mSelectedItemPaint);
            } else {
                canvas.drawText(drawText, mFirstItemDrawX, itemDrawY, mTextPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mTracker == null) {
            mTracker = VelocityTracker.obtain();
        }
        mTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                    mIsAbortScroller = true;
                } else {
                    mIsAbortScroller = false;
                }
                mTracker.clear();
                mTouchDownY = mLastDownY = (int) event.getY();
                mTouchSlopFlag = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mTouchSlopFlag && Math.abs(mTouchDownY - event.getY()) < mTouchSlop) {
                    break;
                }
                mTouchSlopFlag = false;
                float move = event.getY() - mLastDownY;
                mScrollOffsetY += move;
                mLastDownY = (int) event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (!mIsAbortScroller && mTouchDownY == mLastDownY) {
                    performClick();
                    if (event.getY() > mSelectedItemRect.bottom) {
                        int scrollItem = (int) (event.getY() - mSelectedItemRect.bottom) / mItemHeight + 1;
                        mScroller.startScroll(0, mScrollOffsetY, 0,
                                -scrollItem * mItemHeight);

                    } else if (event.getY() < mSelectedItemRect.top) {
                        int scrollItem = (int) (mSelectedItemRect.top - event.getY()) / mItemHeight + 1;
                        mScroller.startScroll(0, mScrollOffsetY, 0,
                                scrollItem * mItemHeight);
                    }
                } else {
                    mTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                    int velocity = (int) mTracker.getYVelocity();
                    if (Math.abs(velocity) > mMinimumVelocity) {
                        mScroller.fling(0, mScrollOffsetY, 0, velocity,
                                0, 0, mMinFlingY, mMaxFlingY);
                        mScroller.setFinalY(mScroller.getFinalY() +
                                computeDistanceToEndPoint(mScroller.getFinalY() % mItemHeight));
                    } else {
                        mScroller.startScroll(0, mScrollOffsetY, 0,
                                computeDistanceToEndPoint(mScrollOffsetY % mItemHeight));
                    }
                }
                mHandler.post(mScrollerRunnable);
                mTracker.recycle();
                mTracker = null;
                break;
        }
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private int computeDistanceToEndPoint(int remainder) {
        if (Math.abs(remainder) > mItemHeight / 2) {
            if (mScrollOffsetY < 0) {
                return -mItemHeight - remainder;
            } else {
                return mItemHeight - remainder;
            }
        } else {
            return -remainder;
        }
    }

    public void setOnWheelChangeListener(OnWheelChangeListener onWheelChangeListener) {
        mOnWheelChangeListener = onWheelChangeListener;
    }

    public List<String> getDataList() {
        return mDataList;
    }

    public void setDataList(@NonNull List<String> dataList) {
        mDataList = dataList;
        if (dataList.size() == 0) {
            return;
        }
        computeTextSize();
        computeFlingLimitY();
        requestLayout();
        postInvalidate();
    }

    /**
     * 一般列表的文本颜色
     *
     * @param textColor 文本颜色
     */
    public void setTextColor(@ColorInt int textColor) {
        if (mTextColor == textColor) {
            return;
        }
        mTextPaint.setColor(textColor);
        mTextColor = textColor;
        mLinearGradient.setStartColor(textColor);
        postInvalidate();
    }

    /**
     * 一般列表的文本大小
     *
     * @param textSize 文字大小
     */
    public void setTextSize(int textSize) {
        if (mTextSize == textSize) {
            return;
        }
        mTextSize = textSize;
        mTextPaint.setTextSize(textSize);
        computeTextSize();
        postInvalidate();
    }

    /**
     * 设置被选中时候的文本颜色
     *
     * @param selectedItemTextColor 文本颜色
     */
    public void setSelectedItemTextColor(@ColorInt int selectedItemTextColor) {
        if (mSelectedItemTextColor == selectedItemTextColor) {
            return;
        }
        mSelectedItemPaint.setColor(selectedItemTextColor);
        mSelectedItemTextColor = selectedItemTextColor;
        mLinearGradient.setEndColor(selectedItemTextColor);
        postInvalidate();
    }

    /**
     * 设置被选中时候的文本大小
     *
     * @param selectedItemTextSize 文字大小
     */
    public void setSelectedItemTextSize(int selectedItemTextSize) {
        if (mSelectedItemTextSize == selectedItemTextSize) {
            return;
        }
        mSelectedItemPaint.setTextSize(selectedItemTextSize);
        mSelectedItemTextSize = selectedItemTextSize;
        computeTextSize();
        postInvalidate();
    }

    /**
     * 设置输入的一段文字，用来测量 mTextMaxWidth
     *
     * @param itemMaximumWidthText 文本内容
     */
    public void setItemMaximumWidthText(String itemMaximumWidthText) {
        mItemMaximumWidthText = itemMaximumWidthText;
        requestLayout();
        postInvalidate();
    }

    public int getHalfVisibleItemCount() {
        return mHalfVisibleItemCount;
    }

    /**
     * 显示的个数等于上下两边Item的个数+ 中间的Item
     *
     * @return 总显示的数量
     */
    public int getVisibleItemCount() {
        return mHalfVisibleItemCount * 2 + 1;
    }

    /**
     * 设置显示数据量的个数的一半。
     * 为保证总显示个数为奇数,这里将总数拆分，总数为 mHalfVisibleItemCount * 2 + 1
     *
     * @param halfVisibleItemCount 总数量的一半
     */
    public void setHalfVisibleItemCount(int halfVisibleItemCount) {
        if (mHalfVisibleItemCount == halfVisibleItemCount) {
            return;
        }
        mHalfVisibleItemCount = halfVisibleItemCount;
        requestLayout();
    }

    public int getItemHeightSpace() {
        return mItemHeightSpace;
    }

    /**
     * 设置两个Item之间的间隔
     *
     * @param itemHeightSpace 间隔值
     */
    public void setItemHeightSpace(int itemHeightSpace) {
        if (mItemHeightSpace == itemHeightSpace) {
            return;
        }
        mItemHeightSpace = itemHeightSpace;
        requestLayout();
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    /**
     * 设置当前选中的列表项,将滚动到所选位置
     *
     * @param currentPosition 设置的当前位置
     */
    public void setCurrentPosition(int currentPosition) {
        setCurrentPosition(currentPosition, true);
    }

    /**
     * 设置当前选中的列表位置
     *
     * @param currentPosition 设置的当前位置
     * @param smoothScroll    是否平滑滚动
     */
    public synchronized void setCurrentPosition(int currentPosition, boolean smoothScroll) {
        if (currentPosition > mDataList.size() - 1) {
            currentPosition = mDataList.size() - 1;
        }
        if (currentPosition < 0) {
            currentPosition = 0;
        }
        if (mCurrentPosition == currentPosition) {
            return;
        }
        if (!mScroller.isFinished()) {
            mScroller.abortAnimation();
        }

        //如果mItemHeight=0代表还没有绘制完成，这时平滑滚动没有意义
        if (smoothScroll && mItemHeight > 0) {
            mScroller.startScroll(0, mScrollOffsetY, 0, (mCurrentPosition - currentPosition) * mItemHeight);
            int finalY = -currentPosition * mItemHeight;
            mScroller.setFinalY(finalY);
            mHandler.post(mScrollerRunnable);
        } else {
            mCurrentPosition = currentPosition;
            mScrollOffsetY = -mItemHeight * mCurrentPosition;
            postInvalidate();
            if (mOnWheelChangeListener != null) {
                mOnWheelChangeListener.onWheelSelected(mDataList.get(currentPosition), currentPosition);
            }
        }
    }

    public boolean isZoomInSelectedItem() {
        return mIsZoomInSelectedItem;
    }

    public void setZoomInSelectedItem(boolean zoomInSelectedItem) {
        if (mIsZoomInSelectedItem == zoomInSelectedItem) {
            return;
        }
        mIsZoomInSelectedItem = zoomInSelectedItem;
        postInvalidate();
    }

    public boolean isTextGradual() {
        return mIsTextGradual;
    }

    /**
     * 设置文字渐变，离中心越远越淡。
     *
     * @param textGradual 是否渐变
     */
    public void setTextGradual(boolean textGradual) {
        if (mIsTextGradual == textGradual) {
            return;
        }
        mIsTextGradual = textGradual;
        postInvalidate();
    }

    public interface OnWheelChangeListener {
        void onWheelSelected(String item, int position);
    }

    private class LinearGradient {
        private int mStartColor;
        private int mEndColor;
        private int mRedStart;
        private int mBlueStart;
        private int mGreenStart;
        private int mRedEnd;
        private int mBlueEnd;
        private int mGreenEnd;

        LinearGradient(@ColorInt int startColor, @ColorInt int endColor) {
            mStartColor = startColor;
            mEndColor = endColor;
            updateColor();
        }

        void setStartColor(@ColorInt int startColor) {
            mStartColor = startColor;
            updateColor();
        }

        void setEndColor(@ColorInt int endColor) {
            mEndColor = endColor;
            updateColor();
        }

        private void updateColor() {
            mRedStart = Color.red(mStartColor);
            mBlueStart = Color.blue(mStartColor);
            mGreenStart = Color.green(mStartColor);
            mRedEnd = Color.red(mEndColor);
            mBlueEnd = Color.blue(mEndColor);
            mGreenEnd = Color.green(mEndColor);
        }

        int getColor(float ratio) {
            int red = (int) (mRedStart + ((mRedEnd - mRedStart) * ratio + 0.5));
            int greed = (int) (mGreenStart + ((mGreenEnd - mGreenStart) * ratio + 0.5));
            int blue = (int) (mBlueStart + ((mBlueEnd - mBlueStart) * ratio + 0.5));
            return Color.rgb(red, greed, blue);
        }
    }
}
