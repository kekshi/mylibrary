package com.zdy.customview.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 练习View
 */
public class PracticeView extends View {
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);

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
        paint1.setTextSize(60);
        paint2.setTextSize(120);
        paint3.setTextSize(60);
        paint2.setColor(Color.parseColor("#E91E63"));
        //测量文字宽度必须要在设置文字大小或其他改变大小的API之后
        measuredText1 = paint1.measureText(text1);
        measuredText2 = paint2.measureText(text2);
        measuredText3 = paint3.measureText(text3);
        Log.e("measuredText1", measuredText1 + "");
        Log.e("measuredText2", measuredText2 + "");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        drawCircle(canvas);
//        drawDrawArc(canvas);
        drawText(canvas);
    }

    String text1 = "三个月内你胖了";
    String text2 = "4.5";
    String text3 = "公斤";
    float measuredText1;
    float measuredText2;
    float measuredText3;
//    private Rect contentBounds = new Rect();
//    private Rect numBounds = new Rect();

    private void drawText(Canvas canvas) {
//        float contentWidth = mPaint.measureText(content);
//        float numWidth = mPaint.measureText(num);
//        Log.e("contentWidth", contentWidth + "");
//        Log.e("numWidth", numWidth + "");
//        mPaint.setColor(Color.BLACK);
//        mPaint.setTextSize(50);
//        mPaint.getTextBounds(content, 0, content.length(), contentBounds);
//        canvas.drawText(content, 50, 200, mPaint);
//        Log.e("TextBounds", contentBounds.width() + "");

//        mPaint.setColor(Color.RED);
//        mPaint.setTextSize(90);
//        mPaint.getTextBounds(num, 0, num.length(), numBounds);
//        canvas.drawText(num, 50 + contentBounds.width(), 200, mPaint);

//        mPaint.setColor(Color.BLACK);
//        mPaint.setTextSize(50);
//        canvas.drawText("公斤", 50 + contentBounds.width() + numBounds.width(), 200, mPaint);

        canvas.drawText(text1, 50, 200, paint1);
        canvas.drawText(text2, 50 + measuredText1 , 200, paint2);
        canvas.drawText(text3, 50 + measuredText1  + measuredText2 , 200, paint3);
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
