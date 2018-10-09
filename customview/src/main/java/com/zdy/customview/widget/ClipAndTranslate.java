package com.zdy.customview.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.zdy.customview.R;

/**
 * 范围裁切和几何变换api
 */
public class ClipAndTranslate extends View {
    private Bitmap mBitmap;
    private Paint mPaint;
    private float x = 300;
    private float y = 300;
    private Matrix mMatrix;

    public ClipAndTranslate(Context context) {
        super(context);
    }

    public ClipAndTranslate(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ClipAndTranslate(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        //做初始化操作
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.ic_launcher);
        mMatrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 裁切
         * */
//        canvas.save();//保存状态
//        canvas.clipRect(200, 200, 400, 400);//裁切范围
//        canvas.drawBitmap(mBitmap, x, y, mPaint);
//        canvas.restore();//恢复状态
        /**
         * 平移
         * 参数里的 dx 和 dy 表示横向和纵向的位移。
         * */
//        canvas.save();
//        canvas.translate(300, 0);//
//        canvas.drawBitmap(mBitmap, x, y, mPaint);
//        canvas.restore();//恢复状态
        /**
         * 旋转
         * 参数里的 degrees 是旋转角度，单位是度（也就是一周有 360° 的那个单位），方向是顺时针为正向； px 和 py 是轴心的位置。
         * */
//        canvas.save();
//        canvas.rotate(45, 300, 300);//
//        canvas.drawBitmap(mBitmap, x, y, mPaint);
//        canvas.restore();//恢复状态
        /**
         * 缩放
         * 参数里的 sx sy 是横向和纵向的放缩倍数； px py 是放缩的轴心。
         * */
//        canvas.save();
//        canvas.scale(3, 1, 300, 300);//
//        canvas.drawBitmap(mBitmap, x, y, mPaint);
//        canvas.restore();//恢复状态
        /**
         * 倾斜
         * 参数里的 sx 和 sy 是 x 方向和 y 方向的错切系数。
         * */
//        canvas.save();
//        canvas.skew(0, 0.5f);//不知道为什么会位移一段距离
//        canvas.drawBitmap(mBitmap, x, y, mPaint);
//        canvas.restore();//恢复状态
        mMatrix.reset();
        mMatrix.postTranslate(500, 1f);
        mMatrix.postRotate(45f);

        canvas.save();
        canvas.concat(mMatrix);
        canvas.drawBitmap(mBitmap, x, y, mPaint);
        canvas.restore();//恢复状态

    }
}
