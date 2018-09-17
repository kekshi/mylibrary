package com.zdy.customview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/*
 *Canvas 类下的所有 draw- 打头的方法，例如 drawCircle() drawBitmap()。
 *Paint 类的几个最常用的方法。具体是：
 *
 *Paint.setStyle(Style style) 设置绘制模式(三种)
 * Paint.Style.STROKE 只绘制图形轮廓（描边）
 * Paint.Style.FILL 只绘制图形内容
 * Paint.Style.FILL_AND_STROKE 既绘制轮廓也绘制内容
 *
 *Paint.setColor(int color) 设置颜色
 *Paint.setStrokeWidth(float width) 设置线条宽度
 *Paint.setTextSize(float textSize) 设置文字大小
 *Paint.setAntiAlias(boolean aa) 设置抗锯齿开关
 * Paint.setStrokeCap(Paint.Cap.ROUND);设置点的形状，但这个方法并不是专门用来设置点的形状的，而是一个设置线条端点形状的方法。
 *  端点有圆头 (ROUND)、平头 (BUTT) 和方头 (SQUARE) 三种
 * */
public class CanvasAndPaintInfo extends View {
    Paint mPaint;
    Path mPath;

    public CanvasAndPaintInfo(Context context) {
        this(context, null);
    }

    public CanvasAndPaintInfo(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanvasAndPaintInfo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(10);

        mPath = new Path();
    }

    /*
     * Canvas 画布
     * 例如 drawColor(Color.BLACK) 会把整个区域染成纯黑色，覆盖掉原有内容；
     * drawColor(Color.parse("#88880000") 会在原有的绘制效果上加一层半透明的红色遮罩。
     * */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        drawColor(canvas);
//        drawGraph(canvas);
//        drawPath(canvas);
        drawBitmapAndText(canvas);
    }


    /**
     * 绘制背景
     */
    private void drawColor(Canvas canvas) {
//        canvas.drawColor(Color.BLACK);  // 纯黑
//        canvas.drawColor(Color.parseColor("#88880000")); // 半透明红色
//        canvas.drawRGB(100, 200, 100);
//        canvas.drawARGB(10, 100, 200, 100);
    }

    /**
     * 绘制图形
     * Android坐标系的原点是 View 左上角的那个点；水平方向是 x 轴，右正左负；竖直方向是 y 轴，下正上负（注意，是下正上负，不是上正下负，和上学时候学的坐标系方向不一样）
     */
    private void drawGraph(Canvas canvas) {
        //绘圆。
//        canvas.drawCircle(300, 300, 200, mPaint);//前两个参数 centerX centerY 是圆心的坐标，第三个参数 radius 是圆的半径，单位都是像素
//        它还有两个重载方法 drawRect(RectF rect, Paint paint) 和 drawRect(Rect rect, Paint paint)
        //绘矩形。
//        canvas.drawRect(100, 100, 500, 500, mPaint);
//        mPaint.setStrokeWidth(50);
//        mPaint.setStrokeCap(Paint.Cap.ROUND);//设置点的形状为圆形.圆头 (ROUND)、平头 (BUTT) 和方头 (SQUARE)
        //绘点
//        canvas.drawPoint(50, 50, mPaint);
//        float[] points = {0, 0, 50, 50, 50, 100, 100, 50, 100, 100, 150, 50, 150, 100};
//        canvas.drawPoints(points, mPaint);
        // 绘制四个点：(50, 50) (50, 100) (100, 50) (100, 100)
//        canvas.drawPoints(points, 2/* 跳过两个数，即前两个 0 */, 8/* 一共绘制 8 个数（4 个点）*/, mPaint);
        //绘椭圆.  重载方法 drawOval(RectF rect, Paint paint)，让你可以直接填写 RectF 来绘制椭圆
//        canvas.drawOval(50, 50, 350, 200, mPaint);//left, top, right, bottom 是这个椭圆的左、??上、右、下四个边界点的坐标。
        //画直线
//        canvas.drawLine(200, 200, 800, 500, mPaint);//startX, startY, stopX, stopY 分别是线的起点和终点坐标。
        //批量画直线
//        float[] points = {20, 20, 120, 20, 70, 20, 70, 120, 20, 120, 120, 120, 150, 20, 250, 20, 150, 20, 150, 120, 250, 20, 250, 120, 150, 120, 250, 120};
//        canvas.drawLines(points,mPaint);
        //绘圆角矩形
        //left, top, right, bottom 是四条边的坐标，rx 和 ry 是圆角的横向半径和纵向半径。
        //重载方法 drawRoundRect(RectF rect, float rx, float ry, Paint paint)，让你可以直接填写 RectF 来绘制圆角矩形。
//        canvas.drawRoundRect(100, 100, 500, 300, 50, 50, mPaint);
        //画圆弧。
        //left, top, right, bottom 描述的是这个弧形所在的椭圆；startAngle 是弧形的起始角度（x 轴的正向，即正右的方向，是 0 度的位置；
        // 顺时针为正角度，逆时针为负角度），sweepAngle 是弧形划过的角度；useCenter 表示是否连接到圆心，如果不连接到圆心，就是弧形，如果连接到圆心，就是扇形。
//        canvas.drawArc(200, 100, 800, 500, -110, 100, true, mPaint);//绘制扇形
//        canvas.drawArc(200, 100, 800, 500, 20, 140, false, mPaint);//绘制圆弧
//        mPaint.setStyle(Paint.Style.STROKE);//描边模式
//        canvas.drawArc(200, 100, 800, 500, 180, 60, false, mPaint); // 绘制不封口的弧形
    }

    /**
     * 绘制自定义图形
     * Path 有两类方法，一类是直接描述路径的，另一类是辅助的设置或计算。
     */
    private void drawPath(Canvas canvas) {
        /**
         *Path 方法第一类：直接描述路径。
         *这一类方法还可以细分为两组：添加子图形和画线（直线或曲线）
         *
         *第一组： addXxx() ——添加子图形
         *addCircle(float x, float y, float radius, Direction dir) 添加圆
         * x, y, radius 这三个参数是圆的基本信息，最后一个参数 dir 是画圆的路径的方向。
         * 路径方向有两种：顺时针 (CW clockwise) 和逆时针 (CCW counter-clockwise) 。
         * 对于普通情况，这个参数填 CW 还是填 CCW 没有影响。它只是在需要填充图形 (Paint.Style 为 FILL 或 FILL_AND_STROKE) ，并且图形出现自相交时，用于判断填充范围的。
         * */
//        mPath.addCircle(300, 300, 200, Path.Direction.CW);//其他方法使用差不多
//        canvas.drawPath(mPath, mPaint);


        /**
         * 第二组：xxxTo() ——画线（直线或曲线）
         这一组和第一组 addXxx() 方法的区别在于，第一组是添加的完整封闭图形（除了 addPath() ），而这一组添加的只是一条线。

         lineTo(float x, float y) / rLineTo(float x, float y) 画直线
         从当前位置向目标位置画一条直线， x 和 y 是目标位置的坐标。这两个方法的区别是，lineTo(x, y) 的参数是绝对坐标，
         而 rLineTo(x, y) 的参数是相对当前位置的相对坐标 （前缀 r 指的就是 relatively 「相对地」)。

         当前位置：所谓当前位置，即最后一次调用画 Path 的方法的终点位置。初始值为原点 (0, 0)。
         * */
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPath.lineTo(100, 100);// 由当前位置 (0, 0) 向 (100, 100) 画一条直线
//        mPath.rLineTo(100, 0);// 由当前位置 (100, 100) 向正右方 100 像素的位置画一条直线
//        canvas.drawPath(mPath, mPaint);

        /**
         * 贝塞尔曲线
         *quadTo(float x1, float y1, float x2, float y2) / rQuadTo(float dx1, float dy1, float dx2, float dy2) 画二次贝塞尔曲线
         这条二次贝塞尔曲线的起点就是当前位置，而参数中的 x1, y1 和 x2, y2 则分别是控制点和终点的坐标。
         和 rLineTo(x, y) 同理，rQuadTo(dx1, dy1, dx2, dy2) 的参数也是相对坐标

         cubicTo(float x1, float y1, float x2, float y2, float x3, float y3) / rCubicTo(float x1, float y1, float x2, float y2, float x3, float y3) 画三次贝塞尔曲线
         和上面这个 quadTo() rQuadTo() 的二次贝塞尔曲线同理，cubicTo() 和 rCubicTo() 是三次贝塞尔曲线，不再解释。
         * */


        /**
         *moveTo(float x, float y) / rMoveTo(float x, float y) 移动到目标位置
         不论是直线还是贝塞尔曲线，都是以当前位置作为起点，而不能指定起点。但你可以通过 moveTo(x, y) 或  rMoveTo() 来改变当前位置，从而间接地设置这些方法的起点。
         * */
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPath.lineTo(100, 100);// 画斜线，直线终点坐标
//        mPath.moveTo(200,100);// 此坐标为下次绘制的起点
//        mPath.lineTo(200, 0);// 画竖线
//        canvas.drawPath(mPath, mPaint);

        /**
         *另外，第二组还有两个特殊的方法： arcTo() 和 addArc()。它们也是用来画线的，但并不使用当前位置作为弧线的起点。

         arcTo(RectF oval, float startAngle, float sweepAngle, boolean forceMoveTo) /
         arcTo(float left, float top, float right, float bottom, float startAngle, float sweepAngle, boolean forceMoveTo) /
         arcTo(RectF oval, float startAngle, float sweepAngle) 画弧形
         这个方法和 Canvas.drawArc() 比起来，少了一个参数 useCenter，而多了一个参数 forceMoveTo 。
         少了 useCenter ，是因为 arcTo() 只用来画弧形而不画扇形，所以不再需要 useCenter 参数；
         而多出来的这个 forceMoveTo 参数的意思是，绘制是要「抬一下笔移动过去」，还是「直接拖着笔过去」，区别在于是否留下移动的痕迹。

         addArc(float left, float top, float right, float bottom, float startAngle, float sweepAngle) / addArc(RectF oval, float startAngle, float sweepAngle)
         又是一个弧形的方法。一个叫 arcTo ，一个叫 addArc()，都是弧形，区别在哪里？其实很简单： addArc() 只是一个直接使用了 forceMoveTo = true 的简化版 arcTo() 。
         * */
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPath.lineTo(100, 100);// 直线终点坐标
//        mPath.arcTo(100, 100, 300, 300, -90, 90, true);//以结束点为X轴原点 移动到弧形起点(200,100)（无痕迹）
//        canvas.drawPath(mPath, mPaint);

        /**
         *close() 封闭当前子图形
         它的作用是把当前的子图形封闭，即由当前位置向当前子图形的起点绘制一条直线。
         * */
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPath.moveTo(100,100);// 此坐标为下次绘制的起点
//        mPath.lineTo(200, 100);// 画斜线，直线终点坐标
//        mPath.lineTo(150, 150);// 画竖线
//        // 子图形未封闭
//        mPath.close();// 使用 close() 封闭子图形。等价于 path.lineTo(100, 100)
//        canvas.drawPath(mPath, mPaint);

        /**
         *Path 方法第二类：辅助的设置或计算
         这类方法的使用场景比较少，我在这里就不多讲了，只讲其中一个方法：
         Path.setFillType(Path.FillType ft) 设置填充方式,用来设置图形自相交时的填充算法的
         FillType 的取值有四个：EVEN_ODD，WINDING （默认值），INVERSE_EVEN_ODD，INVERSE_WINDING
         其中后面的两个带有 INVERSE_ 前缀的，只是前两个的反色版本，所以只要把前两个，即 EVEN_ODD 和  WINDING，搞明白就可以了。
         WINDING 是「全填充」，而 EVEN_ODD 是「交叉填充」：
         * */
        /**
         *EVEN_ODD 和 WINDING 的原理
         EVEN_ODD
         即 even-odd rule （奇偶原则）：对于平面中的任意一点，向任意方向射出一条射线，这条射线和图形相交的次数（相交才算，相切不算哦）如果是奇数，
         则这个点被认为在图形内部，是要被涂色的区域；如果是偶数，则这个点被认为在图形外部，是不被涂色的区域。
         WINDING
         即 non-zero winding rule （非零环绕数原则）：首先，它需要你图形中的所有线条都是有绘制方向的：
         然后，同样是从平面中的点向任意方向射出一条射线，但计算规则不一样：以 0 为初始值，对于射线和图形的所有交点，
         遇到每个顺时针的交点（图形从射线的左边向右穿过）把结果加 1，遇到每个逆时针的交点（图形从射线的右边向左穿过）把结果减 1，
         最终把所有的交点都算上，得到的结果如果不是 0，则认为这个点在图形内部，是要被涂色的区域；如果是 0，则认为这个点在图形外部，是不被涂色的区域。
         * */
    }

    /**
     * 绘制图片和文字
     */
    private void drawBitmapAndText(Canvas canvas) {
        /**绘制 Bitmap 对象，也就是把这个 Bitmap 中的像素内容贴过来。其中 left 和 top 是要把 bitmap 绘制到的位置坐标。它的使用非常简单。
         *重载方法
         * drawBitmap(Bitmap bitmap, Rect src, RectF dst, Paint paint)
         * drawBitmap(Bitmap bitmap, Rect src, Rect dst, Paint paint)
         * drawBitmap(Bitmap bitmap, Matrix matrix, Paint paint)
         * */
        //  canvas.drawBitmap(bitmap, 200, 100, paint);
        /**
         *drawText(String text, float x, float y, Paint paint) 绘制文字
         界面里所有的显示内容，都是绘制出来的，包括文字。 drawText() 这个方法就是用来绘制文字的。
         参数  text 是用来绘制的字符串，x 和 y 是绘制的起点坐标
         * */
        mPaint.setTextSize(60);
        canvas.drawText("afsadfasdfaasdfasdf", 200, 100, mPaint);
    }
}
