package com.zdy.customview.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Locale;

public class PaintTextInfo extends View {
    Paint mPaint;
    Path mPath;
    private String text = "我是测试内容";

    public PaintTextInfo(Context context) {
        this(context, null);
    }

    public PaintTextInfo(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaintTextInfo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
         * https://hencoder.com/ui-1-3/
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
//        是否开启次像素级的抗锯齿（ sub-pixel anti-aliasing ）。由于现在手机屏幕像素密度已经很高，所以默认抗锯齿效果就已经足够好了，一般没必要开启次像素级抗锯齿，所以这个方法基本上没有必要使用。
        mPaint.setSubpixelText(true);
//        不知道干啥用的
        mPaint.setLinearText(true);

        /**
         *测量文字尺寸类
         * */
        /*
        * 获取推荐的行距。
        即推荐的两行文字的 baseline 的距离。这个值是系统根据文字的字体和字号自动计算的。
        它的作用是当你要手动绘制多行文字（而不是使用 StaticLayout）的时候，可以在换行的时候给 y 坐标加上这个值来下移文字。
        * */
        mPaint.getFontSpacing();
        /*
         *获取 Paint 的 FontMetrics。
         *FontMetrics 是个相对专业的工具类，它提供了几个文字排印方面的数值：ascent, descent, top, bottom,  leading。
         *
         *baseline: 上图中黑色的线。前面已经讲过了，它的作用是作为文字显示的基准线。

        ascent / descent: 上图中绿色和橙色的线，它们的作用是限制普通字符的顶部和底部范围。
        普通的字符，上不会高过 ascent ，下不会低过 descent ，例如上图中大部分的字形都显示在 ascent 和 descent 两条线的范围内。具体到 Android 的绘制中， ascent 的值是图中绿线和 baseline 的相对位移，它的值为负（因为它在 baseline 的上方）； descent 的值是图中橙线和 baseline 相对位移，值为正（因为它在 baseline 的下方）。

        top / bottom: 上图中蓝色和红色的线，它们的作用是限制所有字形（ glyph ）的顶部和底部范围。
        除了普通字符，有些字形的显示范围是会超过 ascent 和 descent 的，而 top 和 bottom 则限制的是所有字形的显示范围，包括这些特殊字形。例如上图的第二行文字里，就有两个泰文的字形分别超过了  ascent 和 descent 的限制，但它们都在 top 和 bottom 两条线的范围内。具体到 Android 的绘制中，  top 的值是图中蓝线和 baseline 的相对位移，它的值为负（因为它在 baseline 的上方）； bottom 的值是图中红线和 baseline 相对位移，值为正（因为它在 baseline 的下方）。

        leading: 这个词在上图中没有标记出来，因为它并不是指的某条线和 baseline 的相对位移。 leading 指的是行的额外间距，即对于上下相邻的两行，上行的 bottom 线和下行的 top 线的距离，也就是上图中第一行的红线和第二行的蓝线的距离（对，就是那个小细缝）。

        leading 这个词的本意其实并不是行的额外间距，而是行距，即两个相邻行的 baseline 之间的距离。不过对于很多非专业领域，leading 的意思被改变了，被大家当做行的额外间距来用；而 Android 里的  leading ，同样也是行的额外间距的意思。
        另外，ascent 和 descent 这两个值还可以通过 Paint.ascent() 和 Paint.descent() 来快捷获取。
         * */
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        /*
         * 如果你要对文字手动换行绘制，多数时候应该选取 getFontSpacing() 来得到行距，不但使用更简单，显示效果也会更好。
         * getFontMetrics() 的返回值是 FontMetrics 类型。它还有一个重载方法  getFontMetrics(FontMetrics fontMetrics) ，
         * 计算结果会直接填进传入的 FontMetrics 对象，而不是重新创建一个对象。这种用法在需要频繁获取 FontMetrics 的时候性能会好些。
         * */
        float fontSpacing = mPaint.getFontSpacing();
        /*
         * getTextBounds(String text, int start, int end, Rect bounds)
         *  获取文字的显示范围。
         *  参数里，text 是要测量的文字，start 和 end 分别是文字的起始和结束位置，bounds 是存储文字显示范围的对象，方法在测算完成之后会把结果写进 bounds。
         *
         *它测量的是文字的显示范围（关键词：显示）。
         * 形象点来说，你这段文字外放置一个可变的矩形，然后把矩形尽可能地缩小，一直小到这个矩形恰好紧紧包裹住文字，那么这个矩形的范围，就是这段文字的 bounds。
         * */
//        mPaint.getTextBounds(text,0,text.length(),bounds);

        /*测量文字的宽度并返回。
         *measureText(): 它测量的是文字绘制时所占用的宽度（关键词：占用）。
         * 前面已经讲过，一个文字在界面中，往往需要占用比他的实际显示宽度更多一点的宽度，以此来让文字和文字之间保留一些间距，不会显得过于拥挤。
         * */
        float textWidth = mPaint.measureText(this.text);
        /*
         *获取字符串中每个字符的宽度，并把结果填入参数 widths。
         *这相当于 measureText() 的一个快捷方法，它的计算等价于对字符串中的每个字符分别调用 measureText() ，并把它们的计算结果分别填入 widths 的不同元素。
         *
         * */
        int textWidths = mPaint.getTextWidths(text, new float[text.length()]);
        /*
        * breakText() 的返回值是截取的文字个数（如果宽度没有超限，则是文字的总个数）。
        * 参数中， text 是要测量的文字；measureForwards 表示文字的测量方向，true 表示由左往右测量；maxWidth 是给出的宽度上限；
        * measuredWidth 是用于接受数据，而不是用于提供数据的：方法测量完成后会把截取的文字宽度（如果宽度没有超限，则为文字总宽度）赋值给 measuredWidth[0]。

        这个方法可以用于多行文字的折行计算。
        *
        * */
        int measuredCount;
        float[] measuredWidth = {0};
        measuredCount = mPaint.breakText(text, 0, text.length(), true, 300, measuredWidth);
        canvas.drawText(text, 0, measuredCount, 150, 150, mPaint);

        /**光标相关
         * getRunAdvance(CharSequence text, int start, int end, int contextStart, int contextEnd, boolean isRtl, int offset)
         对于一段文字，计算出某个字符处光标的 x 坐标。 start end 是文字的起始和结束坐标；contextStart contextEnd 是上下文的起始和结束坐标；
         isRtl 是文字的方向；offset 是字数的偏移，即计算第几个字符处的光标。
         * */
        int length = text.length();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            float advance = mPaint.getRunAdvance(text, 0, length, 0, length, false, length);
            canvas.drawText(text, 100, 100, mPaint);
            canvas.drawLine(100f + advance, 100f, 100f, 100f + advance, mPaint);

            /**
             *  getOffsetForAdvance(CharSequence text, int start, int end, int contextStart, int contextEnd, boolean isRtl, float advance)
             * 给出一个位置的像素值，计算出文字中最接近这个位置的字符偏移量（即第几个字符最接近这个坐标）。

             方法的参数很简单： text 是要测量的文字；start end 是文字的起始和结束坐标；contextStart contextEnd 是上下文的起始和结束坐标；
             isRtl 是文字方向；advance 是给出的位置的像素值。填入参数，对应的字符偏移量将作为返回值返回。
             * */
//            mPaint.getOffsetForAdvance()
        }


    }
}
