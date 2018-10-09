package com.zdy.customview.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class PropertyAnimation extends View {
    private float progress = 0;
    private Paint paint;
    private ObjectAnimator animator;

    public PropertyAnimation(Context context) {
        super(context);
    }

    public PropertyAnimation(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PropertyAnimation(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
        animator = ObjectAnimator.ofFloat(this, "progress", 0, 65);// 执行动画
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(200f, 20, 400f, 220f, 135, progress * 2.7f, false, paint);
    }

    public void start() {
//        animator.setDuration(1500);
//        animator.start();
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(2000);
        animator.start();
        /**
         *AccelerateDecelerateInterpolator  先加速再减速。这是默认的 Interpolator
         *
         * LinearInterpolator  匀速。
         *
         * AccelerateInterpolator  持续加速。在整个动画过程中，一直在加速，直到动画结束的一瞬间，直接停止。
         *
         * DecelerateInterpolator  持续减速直到 0
         *
         * AnticipateInterpolator  先回拉一下再进行正常动画轨迹。效果看起来有点像投掷物体或跳跃等动作前的蓄力。
         *
         * OvershootInterpolator  动画会超过目标值一些，然后再弹回来。效果看起来有点像你一屁股坐在沙发上后又被弹起来一点的感觉。
         *
         * AnticipateOvershootInterpolator  上面这两个的结合版：开始前回拉，最后超过一些然后回弹。
         *
         * BounceInterpolator  在目标值处弹跳。有点像玻璃球掉在地板上的效果。
         *
         * CycleInterpolator  这个也是一个正弦 / 余弦曲线，不过它和 AccelerateDecelerateInterpolator 的区别是，它可以自定义曲线的周期，所以动画可以不到终点就结束，也可以到达终点后回弹，回弹的次数由曲线的周期决定，曲线的周期由 CycleInterpolator() 构造方法的参数决定。
         *
         * PathInterpolator  用这个 Interpolator 你可以定制出任何你想要的速度模型。定制的方式是使用一个 Path 对象来绘制出你要的动画完成度 / 时间完成度曲线。
         *
         *
         *
         * Android5.0新加入的3个插值器
         * FastOutLinearInInterpolator  加速运动。这个 Interpolator 的作用你不能看它的名字，一会儿 fast 一会儿 linear 的，完全看不懂。其实它和  AccelerateInterpolator 一样，都是一个持续加速的运动路线。只不过 FastOutLinearInInterpolator 的曲线公式是用的贝塞尔曲线，而 AccelerateInterpolator 用的是指数曲线。具体来说，它俩最主要的区别是  FastOutLinearInInterpolator 的初始阶段加速度比 AccelerateInterpolator 要快一些。
         *
         * FastOutSlowInInterpolator  先加速再减速。用的是贝塞尔曲线
         *
         * LinearOutSlowInInterpolator  持续减速。
         *
         * */
        Path interpolatorPath = new Path();

        // 先以「动画完成度 : 时间完成度 = 1 : 1」的速度匀速运行 25%
        interpolatorPath.lineTo(0.25f, 0.25f);
        // 然后瞬间跳跃到 150% 的动画完成度
        interpolatorPath.moveTo(0.25f, 1.5f);
        // 再匀速倒车，返回到目标点
        interpolatorPath.lineTo(1, 1);

        /**
         * 监听器
         *
         * 设置监听器的方法， ViewPropertyAnimator 和 ObjectAnimator 略微不一样：
         * ViewPropertyAnimator 用的是  setListener() 和 setUpdateListener() 方法，可以设置一个监听器，要移除监听器时通过  set[Update]Listener(null) 填 null 值来移除；
         * 而 ObjectAnimator 则是用 addListener() 和  addUpdateListener() 来添加一个或多个监听器，移除监听器则是通过 remove[Update]Listener() 来指定移除对象。
         * */

        /*
         * 3.1 ViewPropertyAnimator.setListener() / ObjectAnimator.addListener()
         *   这两个方法的名称不一样，可以设置的监听器数量也不一样，但它们的参数类型都是 AnimatorListener，所以本质上其实都是一样的。 AnimatorListener 共有 4 个回调方法：
         *   onAnimationStart(Animator animation)  当动画开始执行时，这个方法被调用。

         *   onAnimationEnd(Animator animation)  当动画结束时，这个方法被调用。

         *   onAnimationCancel(Animator animation)  当动画被通过 cancel() 方法取消时，这个方法被调用。需要说明一下的是，就算动画被取消，onAnimationEnd() 也会被调用。onAnimationCancel() 会先于 onAnimationEnd() 被调用。

         *   onAnimationRepeat(Animator animation)  当动画通过 setRepeatMode() / setRepeatCount() 或 repeat() 方法重复执行时，这个方法被调用。由于 ViewPropertyAnimator 不支持重复，所以这个方法对 ViewPropertyAnimator 相当于无效。
         * */

        /*
         * 3.2 ViewPropertyAnimator.setUpdateListener() / ObjectAnimator.addUpdateListener()
         * 和上面 3.1 的两个方法一样，这两个方法虽然名称和可设置的监听器数量不一样，但本质其实都一样的，它们的参数都是 AnimatorUpdateListener。它只有一个回调方法：onAnimationUpdate(ValueAnimator animation)。
         *
         * onAnimationUpdate(ValueAnimator animation)
         *当动画的属性更新时（不严谨的说，即每过 10 毫秒，动画的完成度更新时），这个方法被调用。

         * 方法的参数是一个 ValueAnimator，ValueAnimator 是 ObjectAnimator 的父类，也是 ViewPropertyAnimator 的内部实现，所以这个参数其实就是 ViewPropertyAnimator 内部的那个 ValueAnimator，或者对于  ObjectAnimator 来说就是它自己本身。
         *  ValueAnimator 有很多方法可以用，它可以查看当前的动画完成度、当前的属性值等等。
         * */

        /*
         *3.3 ViewPropertyAnimator.withStartAction/EndAction()
         * 这两个方法是 ViewPropertyAnimator 的独有方法。它们和 set/addListener() 中回调的 onAnimationStart() /  onAnimationEnd() 相比起来的不同主要有两点：
         *
         * withStartAction() / withEndAction() 是一次性的，在动画执行结束后就自动弃掉了，就算之后再重用  ViewPropertyAnimator 来做别的动画，用它们设置的回调也不会再被调用。而 set/addListener() 所设置的 AnimatorListener 是持续有效的，当动画重复执行时，回调总会被调用。
         *
         * withEndAction() 设置的回调只有在动画正常结束时才会被调用，而在动画被取消时不会被执行。这点和 AnimatorListener.onAnimationEnd() 的行为是不一致的。
         * */
    }
}
