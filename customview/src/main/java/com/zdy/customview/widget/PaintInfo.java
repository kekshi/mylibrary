package com.zdy.customview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Locale;

public class PaintInfo extends View {
    Paint mPaint;
    Path mPath;

    public PaintInfo(Context context) {
        this(context, null);
    }

    public PaintInfo(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaintInfo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         *drawText(String text, float x, float y, Paint paint) 绘制文字
         界面里所有的显示内容，都是绘制出来的，包括文字。 drawText() 这个方法就是用来绘制文字的。
         参数  text 是用来绘制的字符串，x 和 y 是绘制的起点坐标
         * */
        mPaint.setTextSize(60);
//        Canvas.drawXXX() 方法，都是以左上角作为基准点的，而 drawText() 却是文字左下方,因此Y=0时，将看不到文字内容
//        canvas.drawText("afsadfasdfaasdfasdf", 200, 100, mPaint);

//        沿着一条 Path 来绘制文字. 记住一条原则： drawTextOnPath() 使用的 Path ，拐弯处全用圆角，别用尖角。
//        参数里，需要解释的只有两个： hOffset 和 vOffset。它们是文字相对于 Path 的水平偏移量和竖直偏移量，利用它们可以调整文字的位置。
//        例如你设置 hOffset 为 5， vOffset 为 10，文字就会右移 5 像素和下移 10 像素。
//        canvas.drawTextOnPath("Hello HeCoder", mPath, 0, 0, mPaint);

//        设置文字大小
        mPaint.setTextSize(20);
        /**
         * 设置字体类型
         * */
//        mPaint.setTypeface(Typeface.DEFAULT);
        //自己导入字体库
//        mPaint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Satisfy-Regular.ttf"));

//        是否使用伪粗体。因为它并不是通过选用更高 weight 的字体让文字变粗，而是通过程序在运行时把文字给「描粗」了。
        mPaint.setFakeBoldText(true);
//        是否加删除线。
        mPaint.setStrikeThruText(true);
//        是否加下划线。
        mPaint.setUnderlineText(true);
//        设置文字横向错切角度。其实就是文字倾斜度的啦。-右
        mPaint.setTextSkewX(-0.5f);
//        设置文字横向放缩。也就是文字变胖变瘦。
        mPaint.setTextScaleX(0.8f);
//        设置字符间距。默认值是 0。
        mPaint.setLetterSpacing(0.2f);
//        设置文字的对齐方式。一共有三个值：LEFT CETNER 和 RIGHT。默认值为 LEFT。
        mPaint.setTextAlign(Paint.Align.LEFT);
//        设置绘制所使用的 Locale。手机语言
        mPaint.setTextLocale(Locale.CHINA);//简体中文
//        设置是否启用字体的 hinting （字体微调）。当文字的尺寸过小（比如高度小于 16 像素），有些文字会由于失去过多细节而变得不太好看。
// hinting 技术就是为了解决这种问题的：通过向字体中加入 hinting 信息，让矢量字体在尺寸过小的时候得到针对性的修正，从而提高显示效果。
        mPaint.setHinting(Paint.HINTING_ON);
    }
}
