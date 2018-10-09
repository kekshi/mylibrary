package com.zdy.customview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 一个完整的绘制过程会依次绘制以下几个内容：
 * <p>
 * 1.背景
 * 2.主体（onDraw()）
 * 3.子 View（dispatchDraw()）
 * 4.滑动边缘渐变和滑动条
 * 5.前景
 * 一般来说，一个 View（或 ViewGroup）的绘制不会这几项全都包含，但必然逃不出这几项，并且一定会严格遵守这个顺序。
 * 例如通常一个 LinearLayout 只有背景和子 View，那么它会先绘制背景再绘制子 View；
 */
public class DrawInfo extends LinearLayout {
    public DrawInfo(Context context) {
        super(context);
    }

    public DrawInfo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawInfo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * onDraw方法执行之后调用
     * 子View绘制之后调用
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    /**
     * 这个方法是 API 23 才引入的,版本必须高于23才有效果.
     * 在 onDrawForeground() 中，会依次绘制滑动边缘渐变、滑动条和前景。
     * dispatchDraw方法执行之后调用
     */
    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
    }

    /**
     * draw() 是绘制过程的总调度方法。
     * 一个 View 的整个绘制过程都发生在 draw() 方法里。前面讲到的背景、主体、子 View 、
     * 滑动相关以及前景的绘制，它们其实都是在 draw() 方法里的。
     * <p>
     * 由于 draw() 是总调度方法，所以如果把绘制代码写在 super.draw() 的下面，那么这段代码会在其他所有绘制完成之后再执行，
     * 也就是说，它的绘制内容会盖住其他的所有绘制内容。
     * <p>
     * 如果把绘制代码写在 super.draw() 的上面，那么这段代码会在其他所有绘制之前被执行，所以这部分绘制内容会被其他所有的内容盖住，包括背景。
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }
}
