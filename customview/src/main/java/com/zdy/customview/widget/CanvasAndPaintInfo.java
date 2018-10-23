package com.zdy.customview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
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
        /**
         * Style样式
         *  STROKE 只绘制图形轮廓（描边）
         *  FILL 只绘制图形内容
         * FILL_AND_STROKE 既绘制轮廓也绘制内容
         *  */
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setStyle(Paint.Style.FILL);
        /**线条形状,4 个方法：
         * setStrokeWidth(float width),设置线条宽度。单位为像素，默认值是 0
         * setStrokeCap(Paint.Cap cap),设置线头的形状。线头形状有三种：BUTT 平头、ROUND 圆头、SQUARE 方头。默认为 BUTT。
         * setStrokeJoin(Paint.Join join),设置拐角的形状。有三个值可以选择：MITER 尖角、 BEVEL 平角和 ROUND 圆角。默认为 MITER
         * setStrokeMiter(float miter),这个方法是对于 setStrokeJoin() 的一个补充，它用于设置 MITER 型拐角的延长线的最大值
         * */
        mPaint.setStrokeWidth(10);
//        mPaint.setStrokeCap(Paint.Cap.BUTT);
//        mPaint.setStrokeJoin(Paint.Join.MITER);
//        mPaint.setStrokeMiter(4);
        /**
         * 色彩优化
         * setDither(dither) ，设置抖动来优化色彩深度降低时的绘制效果；
         * setFilterBitmap(filterBitmap) ，设置双线性过滤来优化 Bitmap 放大绘制的效果。
         * */
//        mPaint.setDither(true);//设置图像的抖动。只有当你向自建的 Bitmap 中绘制，并且选择 16 位色的 ARGB_4444 或者 RGB_565 的时候，开启它才会有比较明显的效果。
//        mPaint.setFilterBitmap(true);//设置是否使用双线性过滤来绘制 Bitmap 。图像在放大绘制的时候，默认使用的是最近邻插值过滤，这种算法简单，但会出现马赛克现象；而如果开启了双线性过滤，就可以让结果图像显得更加平滑。
        mPaint.setColor(Color.BLACK);
        /**
         * 使用 PathEffect 来给图形的轮廓设置效果。对 Canvas 所有的图形绘制有效，也就是 drawLine() drawCircle() drawPath() 这些方法。大概像这样：
         * 下面就具体说一下 Android 中的 6 种 PathEffect。PathEffect 分为两类，
         * 单一效果的 CornerPathEffect DiscretePathEffect DashPathEffect PathDashPathEffect ，
         * 组合效果的 SumPathEffect ComposePathEffect。
         * */
//        PathEffect pathEffect = new CornerPathEffect(20);//把所有拐角变成圆角（参数为圆角半径）
//        PathEffect pathEffect = new DiscretePathEffect(20,5);//把线条进行随机的偏离，segmentLength 是用来拼接的每个线段的长度， deviation 是偏离量。
        /*
         第一个参数 intervals 是一个数组，它指定了虚线的格式：数组中元素必须为偶数（最少是 2 个），按照「画线长度、空白长度、画线长度、空白长度」……的顺序排列，
         例如上面代码中的 20, 5, 10, 5 就表示虚线是按照「画 20 像素、空 5 像素、画 10 像素、空 5 像素」的模式来绘制；第二个参数 phase 是虚线的偏移量。
        */
//        PathEffect pathEffect = new DashPathEffect(new float[]{20, 10, 5, 10}, 10);//绘制虚线
        /*
        * shape 参数是用来绘制的 Path ； advance 是两个相邻的 shape 段之间的间隔，不过注意，这个间隔是两个 shape 段的起点的间隔，而不是前一个的终点和后一个的起点的距离；
         * phase 和 DashPathEffect 中一样，是虚线的偏移；最后一个参数 style，是用来指定拐弯改变的时候 shape 的转换方式。
         * style 的类型为 PathDashPathEffect.Style ，是一个 enum ，具体有三个值：
            TRANSLATE：位移
            ROTATE：旋转
            MORPH：变体
        * */
//        PathEffect pathEffect = new PathDashPathEffect(mPath, 40, 0, PathDashPathEffect.Style.TRANSLATE);

//        PathEffect dashEffect = new DashPathEffect(new float[]{20, 10}, 0);
//        PathEffect discreteEffect = new DiscretePathEffect(20, 5);
        //这是一个组合效果类的 PathEffect 。它的行为特别简单，就是分别按照两种 PathEffect 分别对目标进行绘制。
//        PathEffect pathEffect = new SumPathEffect(dashEffect, discreteEffect);

//        PathEffect dashEffect = new DashPathEffect(new float[]{20, 10}, 0);
//        PathEffect discreteEffect = new DiscretePathEffect(20, 5);
        /*
        这也是一个组合效果类的 PathEffect 。不过它是先对目标 Path 使用一个 PathEffect，然后再对这个改变后的 Path 使用另一个 PathEffect。
        * 两个 PathEffect 参数，  innerpe 是先应用的， outerpe 是后应用的。所以上面的代码就是「先偏离，再变虚线
        * */
//        PathEffect pathEffect = new ComposePathEffect(dashEffect, discreteEffect);
//        mPaint.setPathEffect(pathEffect);
        /**
         * 注意： PathEffect 在有些情况下不支持硬件加速，需要关闭硬件加速才能正常使用：
         * Canvas.drawLine() 和 Canvas.drawLines() 方法画直线时，setPathEffect() 是不支持硬件加速的；
         * PathDashPathEffect 对硬件加速的支持也有问题，所以当使用 PathDashPathEffect 的时候，最好也把硬件加速关了。
         * */

        /**
         * 在之后的绘制内容下面加一层阴影。
         *  radius 是阴影的模糊范围； dx dy 是阴影的偏移量； shadowColor 是阴影的颜色。
         *  注意：
         在硬件加速开启的情况下， setShadowLayer() 只支持文字的绘制，文字之外的绘制必须关闭硬件加速才能正常绘制阴影。
         如果 shadowColor 是半透明的，阴影的透明度就使用 shadowColor 自己的透明度；而如果 shadowColor 是不透明的，阴影的透明度就使用 paint 的透明度。
         * */
//        mPaint.setShadowLayer(10, 0, 0, Color.RED);
//        mPaint.clearShadowLayer();//如果要清除阴影层使用该方法
        /**
         * 为之后的绘制设置 MaskFilter。上一个方法 setShadowLayer() 是设置的在绘制层下方的附加效果；而这个  MaskFilter 和它相反，设置的是在绘制层上方的附加效果。
         * MaskFilter 有两种： BlurMaskFilter 和 EmbossMaskFilter。
         * */
        /*
         * 模糊效果的 MaskFilter。
         * 它的构造方法 BlurMaskFilter(float radius, BlurMaskFilter.Blur style) 中， radius 参数是模糊的范围，  style 是模糊的类型。一共有四种：
         *    NORMAL: 内外都模糊绘制
         *    SOLID: 内部正常绘制，外部模糊
         *    INNER: 内部模糊，外部不绘制
         *    OUTER: 内部不绘制，外部模糊（什么鬼？）
         * */
//        mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.NORMAL));
        /*
         * 浮雕效果的 MaskFilter。
         * 它的构造方法 EmbossMaskFilter(float[] direction, float ambient, float specular, float blurRadius) 的参数里，
         * direction 是一个 3 个元素的数组，指定了光源的方向； ambient 是环境光的强度，数值范围是 0 到 1； specular 是炫光的系数； blurRadius 是应用光线的范围。
         * */
//        mPaint.setMaskFilter(new EmbossMaskFilter(new float[]{0,1,1},0.2f,8,10));
        /**
         *获取绘制的 Path
         * 根据 paint 的设置，计算出绘制 Path 或文字时的实际 Path。
         * 默认情况下（线条宽度为 0、没有 PathEffect），原 Path 和实际 Path 是一样的；而在线条宽度不为 0 （并且模式为 STROKE 模式或 FLL_AND_STROKE ），
         * 或者设置了 PathEffect 的时候，实际 Path 就和原 Path 不一样了：
         *
         * 通过 getFillPath(src, dst) 方法就能获取这个实际 Path。方法的参数里，src 是原 Path ，而 dst 就是实际 Path 的保存位置。 getFillPath(src, dst) 会计算出实际 Path，然后把结果保存在 dst 里。
         *
         *  getTextPath(String text, int start, int end, float x, float y, Path path) / getTextPath(char[] text, int index, int count, float x, float y, Path path)
         这里就回答第二个问题：「文字的 Path」。文字的绘制，虽然是使用 Canvas.drawText() 方法，但其实在下层，文字信息全是被转化成图形，对图形进行绘制的。
         getTextPath() 方法，获取的就是目标文字所对应的 Path 。这个就是所谓「文字的 Path」。
         * */

        /**
         *4 初始化类
         这一类方法很简单，它们是用来初始化 Paint 对象，或者是批量设置 Paint 的多个属性的方法。

         4.1 reset()
         重置 Paint 的所有属性为默认值。相当于重新 new 一个，不过性能当然高一些啦。

         4.2 set(Paint src)
         把 src 的所有属性全部复制过来。相当于调用 src 所有的 get 方法，然后调用这个 Paint 的对应的 set 方法来设置它们。

         4.3 setFlags(int flags)
         批量设置 flags。相当于依次调用它们的 set 方法。
         *
         * */

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
        // 顺时针为正角度，逆时针为负角度），sweepAngle 是弧形划过的角度（距离）；useCenter 表示是否连接到圆心，如果不连接到圆心，就是弧形，如果连接到圆心，就是扇形。
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
//        Canvas.drawXXX() 方法，都是以左上角作为基准点的，而 drawText() 却是文字左下方,因此Y=0时，将看不到文字内容
//        canvas.drawText("afsadfasdfaasdfasdf", 200, 100, mPaint);
//        沿着一条 Path 来绘制文字. 记住一条原则： drawTextOnPath() 使用的 Path ，拐弯处全用圆角，别用尖角。
//        参数里，需要解释的只有两个： hOffset 和 vOffset。它们是文字相对于 Path 的水平偏移量和竖直偏移量，利用它们可以调整文字的位置。
//        例如你设置 hOffset 为 5， vOffset 为 10，文字就会右移 5 像素和下移 10 像素。
        canvas.drawTextOnPath("Hello HeCoder", mPath, 0, 0, mPaint);

        /**
         * 如果需要绘制多行的文字，你必须自行把文字切断后分多次使用 drawText() 来绘制，或者——使用  StaticLayout 。
         *  StaticLayout 支持换行，它既可以为文字设置宽度上限来让文字自动换行，也会在 \n 处主动换行。
         * */
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Color.BLACK);
        String text1 = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.";
        StaticLayout staticLayout1 = new StaticLayout(text1, textPaint, 600,
                Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
        String text2 = "a\nbc\ndefghi\njklm\nnopqrst\nuvwx\nyz";
        StaticLayout staticLayout2 = new StaticLayout(text2, textPaint, 600,
                Layout.Alignment.ALIGN_NORMAL, 1, 0, true);

        canvas.save();
        canvas.translate(50, 100);
        staticLayout1.draw(canvas);
        canvas.translate(0, 200);
        staticLayout2.draw(canvas);
        canvas.restore();
    }
}
