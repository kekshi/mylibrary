package com.zdy.customview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.zdy.customview.R;

public class CustomVolumControlBar extends View {

    /**
     * 第一圈的颜色
     */
    private int mFirstColor;

    /**
     * 第二圈的颜色
     */
    private int mSecondColor;
    /**
     * 圈的宽度
     */
    private int mCircleWidth;
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 当前进度
     */
    private int mCurrentCount = 3;

    /**
     * 中间的图片
     */
    private Bitmap mImage;
    /**
     * 每个块块间的间隙
     */
    private int mSplitSize;
    /**
     * 个数
     */
    private int mCount;

    private Rect mRect;

    public CustomVolumControlBar(Context context) {
        this(context, null);
    }

    public CustomVolumControlBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomVolumControlBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (attrs == null) {
            return;
        }

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomVolumControlBar);
        mFirstColor = typedArray.getColor(R.styleable.CustomVolumControlBar_barFirstColor, Color.GREEN);
        mSecondColor = typedArray.getColor(R.styleable.CustomVolumControlBar_barSecondColor, Color.CYAN);
        mCircleWidth = (int) typedArray.getDimension(R.styleable.CustomVolumControlBar_barCircleWidth, 20);
        mImage = BitmapFactory.decodeResource(getResources(), typedArray.getResourceId(R.styleable.CustomVolumControlBar_bg, 0));
        mCount = typedArray.getInt(R.styleable.CustomVolumControlBar_dotCount, 20);
        mSplitSize = typedArray.getInt(R.styleable.CustomVolumControlBar_splitSize, 20);
        typedArray.recycle();

        mPaint = new Paint();
        mRect = new Rect();
    }

    private int xDown, xUp;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = (int) event.getX();
                break;
            case MotionEvent.ACTION_UP:
                xUp = (int) event.getY();
                if (xUp > xDown) {
                    down();
                } else {
                    up();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                break;
        }
        return true;
    }

    /**
     * 当前数量+1
     */
    private void up() {
        mCurrentCount++;
        postInvalidate();
    }

    /**
     * 当前数量-1
     */
    private void down() {
        mCurrentCount--;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true);// 消除锯齿
        mPaint.setStrokeWidth(mCircleWidth);// 设置圆环的宽度
        mPaint.setStrokeCap(Paint.Cap.ROUND);// 定义线段断电形状为圆头
        mPaint.setStyle(Paint.Style.STROKE);//设置空心

        int centre = getWidth() / 2;// 获取圆心的x坐标
        float radius = centre - mCircleWidth / 2;// 半径

        drawOval(canvas, centre, radius);

        /**
         * 计算内切正方形的位置
         */

        // 获得内圆的半径
        float relRadius = radius - mCircleWidth / 2;
        /**
         * 内切正方形的距离顶部 = mCircleWidth + relRadius - √2 / 2
         */
        mRect.left = (int) (relRadius - Math.sqrt(2) * 1f / 2 * relRadius) + mCircleWidth;
        mRect.top = (int) (relRadius - Math.sqrt(2) * 1f / 2 * relRadius) + mCircleWidth;
        mRect.right = (int) (mRect.left + Math.sqrt(2) * relRadius);
        mRect.bottom = (int) (mRect.left + Math.sqrt(2) * relRadius);
        /**
         * 如果图片比较小，那么根据图片的尺寸放置到正中心
         */
        if (mImage.getWidth() < Math.sqrt(2) * relRadius) {
            mRect.left = (int) (mRect.left + Math.sqrt(2) * relRadius * 1.0f / 2 - mImage.getWidth() * 1.0f / 2);
            mRect.top = (int) (mRect.top + Math.sqrt(2) * relRadius * 1.0f / 2 - mImage.getHeight() * 1.0f / 2);
            mRect.right = (int) (mRect.left + mImage.getWidth());
            mRect.bottom = (int) (mRect.top + mImage.getHeight());
        }
        // 绘图
        canvas.drawBitmap(mImage, null, mRect, mPaint);
    }

    /**
     * 根据参数画出每个小块
     *
     * @param canvas
     * @param centre
     * @param radius
     */
    private void drawOval(Canvas canvas, int centre, float radius) {
        /**
         * 根据需要画的个数以及间隙计算每个块块所占的比例*360
         */
        // TODO 间隙应该是数量减 1
        float itemSize = (360f - mCount * mSplitSize) / mCount;

        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);

        mPaint.setColor(mFirstColor);//设置圆环的颜色
        for (int i = 0; i < mCount; i++) {
            canvas.drawArc(oval, i * (itemSize + mSplitSize), itemSize, false, mPaint);
        }
        mPaint.setColor(mSecondColor);
        for (int i = 0; i < mCurrentCount; i++) {
            // 根据进度画圆弧
            canvas.drawArc(oval, i * (itemSize + mSplitSize), itemSize, false, mPaint);
        }
    }
}
