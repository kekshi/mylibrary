package com.zdy.customview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class PracticeView extends View {
    Paint mPaint;

    public PracticeView(Context context) {
        this(context, null);
    }

    public PracticeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PracticeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        drawCircle(canvas);
        drawDrawArc(canvas);
    }

    private void drawDrawArc(Canvas canvas) {
        mPaint.setColor(Color.RED);
        canvas.drawArc(80, 80, 160, 160, -180, 120, true, mPaint);
        mPaint.setColor(Color.YELLOW);
        canvas.drawArc(84, 84, 164, 164, -60, 50, true, mPaint);
        mPaint.setColor(Color.DKGRAY);
        canvas.drawArc(84, 84, 164, 164, -10, 10, true, mPaint);
        mPaint.setColor(Color.LTGRAY);
        canvas.drawArc(84, 84, 164, 164, 0, 10, true, mPaint);
        mPaint.setColor(Color.CYAN);
        canvas.drawArc(84, 84, 164, 164, 10, 60, true, mPaint);
        mPaint.setColor(Color.CYAN);
        canvas.drawArc(84, 84, 164, 164, 70, 110, true, mPaint);
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(30, 30, 20, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        canvas.drawCircle(80, 30, 20, mPaint);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(30, 80, 20, mPaint);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(80, 80, 20, mPaint);
    }
}
