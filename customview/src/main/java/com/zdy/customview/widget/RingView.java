package com.zdy.customview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.zdy.customview.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//圆环交替循环 View
public class RingView extends View {
    //圆环第一种颜色
    private int mFirstColor;
    //圆环第二种颜色
    private int mSecondColor;
    //圆环宽度
    private int mCircleWidth;
    //圆环绘制速度
    private int mSpeed;

    private Paint mPaint;
    private ExecutorService mExecutor;
    private int mProgress;
    private int x;
    private int y;
    private int radius;
    private boolean isNext;


    public RingView(Context context) {
        this(context, null);
    }

    public RingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RingView);
        mFirstColor = typedArray.getColor(R.styleable.RingView_firstColor, Color.RED);
        mSecondColor = typedArray.getColor(R.styleable.RingView_secondColor, Color.BLUE);
        mSpeed = typedArray.getInt(R.styleable.RingView_speed, 20);
        mCircleWidth = typedArray.getInt(R.styleable.RingView_circleWidth, 20);
        typedArray.recycle();

        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mExecutor = Executors.newSingleThreadExecutor();

        onStart();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        x = getWidth() / 2;
        y = getHeight() / 2;
        radius = (int) (Math.min(x, y) * 0.85);

        if (!isNext) {
            mPaint.setColor(mFirstColor);
            canvas.drawCircle(x, y, radius, mPaint);
            mPaint.setColor(mSecondColor);
            canvas.drawArc(x - radius, y - radius, x + radius, y + radius, -90, mProgress, false, mPaint);
        } else {
            mPaint.setColor(mSecondColor);
            canvas.drawCircle(x, y, radius, mPaint);
            mPaint.setColor(mFirstColor);
            canvas.drawArc(x - radius, y - radius, x + radius, y + radius, -90, mProgress, false, mPaint);
        }
    }

    public void onStart() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    mProgress++;
                    if (mProgress == 360) {
                        isNext = !isNext;
                        mProgress = 0;
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(mSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
