package com.zdy.customview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 甜甜圈控件
 */
public class AnimatedPieView extends View {
    final String TAG = getClass().getSimpleName();

    private Paint paint1;
    private Paint paint2;
    private Paint paint3;

    RectF mDrawRectf;

    public AnimatedPieView(Context context) {
        this(context, null);
    }

    public AnimatedPieView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimatedPieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint1.setStyle(Paint.Style.STROKE);//设置样式为只绘制图形轮廓（描边）
        paint1.setStrokeWidth(80);//设置线条宽度
        paint1.setColor(Color.RED);

        paint2 = new Paint(paint1);
        paint2.setColor(Color.GREEN);

        paint3 = new Paint(paint1);
        paint3.setColor(Color.BLUE);

        mDrawRectf = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        int height = getHeight() - getPaddingTop() - getPaddingBottom();

        canvas.translate(width / 2, height / 2);//将坐标移动到这个X,Y坐标

        float radius = (float) (Math.min(width, height) / 2 * 0.85);
        mDrawRectf.set(-radius, -radius, radius, radius);

        canvas.drawArc(mDrawRectf, -90, 120, false, paint1);
        canvas.drawArc(mDrawRectf, 30, 120, false, paint2);
        canvas.drawArc(mDrawRectf, 150, 120, false, paint3);
    }
}
