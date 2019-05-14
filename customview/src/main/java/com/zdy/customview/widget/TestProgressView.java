package com.zdy.customview.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class TestProgressView extends View {
    private Paint paintBg;
    private Paint paintQg;
    // 根据是否完成的标记 确定绘制的图片
    private Bitmap audit_drawBitmap;
    // 绘制文字
    private String text;
    // 画布宽高
    private int width, height;

    public TestProgressView(Context context) {
        this(context, null);
    }

    public TestProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
//        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AuditProgressView, defStyleAttr, 0);
//        array.recycle();

        paintBg = new Paint();
        paintBg.setColor(Color.parseColor("#757575"));
        paintBg.setAntiAlias(true);
        paintQg = new Paint();
        paintQg.setColor(Color.parseColor("#03A9F5"));
        paintQg.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(widthMeasureSpec);

        // 在宽高不是精确模式时,定义最小宽高

        if (widthMode != MeasureSpec.EXACTLY) {
            width = getDisplayMetrics(getContext()).widthPixels - dp2px(getContext(), 20);
        }

        if (heightMode != MeasureSpec.EXACTLY) {
            height = dp2px(getContext(), 90);
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    /**
     * 获取屏幕Metrics参数
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }
}
