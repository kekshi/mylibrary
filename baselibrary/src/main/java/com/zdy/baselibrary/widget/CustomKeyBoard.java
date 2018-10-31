package com.zdy.baselibrary.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Icy on 2018/5/15.
 * 数字键盘
 */

public class CustomKeyBoard extends View {
    private Paint textPaint = new Paint();
    private Paint linePaint = new Paint();
    private Paint linePaint2 = new Paint();

    private int mWidth = 0;
    private int mHeight = 0;
    private int select = -2;

    // 松开数字
    private static final int SELECT_NUMBER = -2;
    // 松开状态
    private static final int SELECT_STATE = 1;
    public OnKeyBoardClickLitener onKeyBoardClickLitener;

    public CustomKeyBoard(Context context) {
        super(context);
    }

    public CustomKeyBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomKeyBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(mHeight / 7);
        textPaint.setAntiAlias(true);
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(1);
        linePaint.setStyle(Paint.Style.FILL);
        linePaint2.setStrokeWidth(4);
        linePaint2.setColor(Color.BLACK);
        linePaint2.setStyle(Paint.Style.FILL);
        linePaint2.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        // 判断是否进行了点击
        // 画灰色块
        linePaint.setColor(Color.parseColor("#d2d6db"));
        if (select <= 9 && select > 0) {
            canvas.drawRect(mWidth * (float) ((select - 1) % 3) / 3, //
                    mHeight * (float) ((select - 1) / 3) / 4 + 1, //
                    mWidth * (float) (((select - 1) % 3) + 1) / 3, //
                    mHeight * (float) (((select - 1) / 3) + 1) / 4, linePaint);
        } else if (select == 0) {
            canvas.drawRect((float) mWidth / 3, mHeight * (float) 3 / 4 + 1, mWidth * (float) 2 / 3, mHeight,
                    linePaint);
        }

        linePaint.setColor(Color.parseColor("#8c8c8c"));
        // 画竖线
        canvas.drawLine(mWidth - 1, 0, mWidth - 1, mHeight, linePaint);
        // 画横线
        canvas.drawLine(0, mHeight - 1, mWidth, mHeight - 1, linePaint);

        for (int i = 0; i < 9; i++) {
            // 画竖线
            if (i < 3) {
                canvas.drawLine(mWidth * i / 3, 0, mWidth * i / 3, mHeight, linePaint);
            }
            // 画横线
            if (i < 4) {
                canvas.drawLine(0, mHeight * i / 4, mWidth, mHeight * i / 4, linePaint);
            }
            // 画数字1~9
            canvas.drawText(String.valueOf(i + 1), mWidth * (float) ((3 + 2 * ((i % 3) - 1))) / 6 - 15,
                    mHeight * (float) ((1 + 2 * (i / 3))) / 8 + mHeight / 24, textPaint);
        }
        // 画0
        canvas.drawText("0", mWidth / 2 - 15, mHeight * 7 / 8 + mHeight / 24, textPaint);
        // 画灰色块
        linePaint.setColor(Color.parseColor("#d2d6db"));
        canvas.drawRect(1, mHeight * 3 / 4 + 1, mWidth / 3, mHeight - 1, linePaint);
        canvas.drawRect(mWidth * 2 / 3 + 1, mHeight * 3 / 4 + 1, mWidth - 1, mHeight - 1, linePaint);
        // 画取消符号
        // 向上倾斜
        canvas.drawLine(mWidth * (float) 50 / 63, //
                mHeight * 7 / 8, //
                mWidth * ((float) 50 / 63 + (float) 1 / 42), //
                mHeight * ((float) 7 / 8 - (float) 5 / 144), linePaint2);
        // 上面那一横
        canvas.drawLine(mWidth * ((float) 50 / 63 + (float) 1 / 42), //
                mHeight * ((float) 7 / 8 - (float) 5 / 144), //
                mWidth * ((float) 50 / 63 + (float) 1 / 42 + (float) 1 / 21), //
                mHeight * ((float) 7 / 8 - (float) 5 / 144), linePaint2);
        // 向下倾斜
        canvas.drawLine(mWidth * (float) 50 / 63, //
                mHeight * 7 / 8, //
                mWidth * ((float) 50 / 63 + (float) 1 / 42), //
                mHeight * ((float) 7 / 8 + (float) 5 / 144), linePaint2);
        // 下面那一横
        canvas.drawLine(mWidth * ((float) 50 / 63 + (float) 1 / 42), //
                mHeight * ((float) 7 / 8 + (float) 5 / 144), //
                mWidth * ((float) 50 / 63 + (float) 1 / 42 + (float) 1 / 21), //
                mHeight * ((float) 7 / 8 + (float) 5 / 144), linePaint2);
        // 垂直线
        canvas.drawLine(mWidth * ((float) 50 / 63 + (float) 1 / 42 + (float) 1 / 21), //
                mHeight * ((float) 7 / 8 - (float) 5 / 144), //
                mWidth * ((float) 50 / 63 + (float) 1 / 42 + (float) 1 / 21), //
                mHeight * ((float) 7 / 8 + (float) 5 / 144), linePaint2);
        // 叉叉X
        canvas.drawLine(mWidth * ((float) 50 / 63 + (float) 1 / 42 + (float) 1 / 84), //
                mHeight * ((float) 7 / 8 - (float) 5 / 144) + mWidth * (float) 1 / 84, //
                mWidth * ((float) 50 / 63 + (float) 1 / 42 + (float) 1 / 21 - (float) 1 / 84), //
                mHeight * ((float) 7 / 8 + (float) 5 / 144) - mWidth * (float) 1 / 84, linePaint2);
        canvas.drawLine(mWidth * ((float) 50 / 63 + (float) 1 / 42 + (float) 1 / 84), //
                mHeight * ((float) 7 / 8 + (float) 5 / 144) - mWidth * (float) 1 / 84, //
                mWidth * ((float) 50 / 63 + (float) 1 / 42 + (float) 1 / 21 - (float) 1 / 84), //
                mHeight * ((float) 7 / 8 - (float) 5 / 144) + mWidth * (float) 1 / 84, linePaint2);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float pointX = event.getX();
                float pointY = event.getY();
                select = getNumber(pointX, pointY);
                // // 通知系统更新界面,相当于调用了onDraw函数
                postInvalidate();
                return true;
            case MotionEvent.ACTION_UP:
                Message msg = new Message();
                msg.what = SELECT_STATE;
                msg.arg1 = select;
                handler.sendMessage(msg);
                return true;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SELECT_STATE:
                    if (msg.arg1 != SELECT_NUMBER) {
                        onKeyBoardClickLitener.getNumber(msg.arg1);
                    }
                    select = SELECT_NUMBER;
                    // 通知系统更新界面,相当于调用了onDraw函数
                    postInvalidateDelayed(50);
                    break;

                default:
                    break;
            }
        }

    };

    /**
     * 得到点击的数字
     *
     * @param pointX
     * @param pointY
     * @return
     */
    private int getNumber(float pointX, float pointY) {
        if (pointX >= mWidth * ((float) 1 / 3) && pointX < mWidth * ((float) 2 / 3)
                && pointY >= mHeight * ((float) 3 / 4) && pointY < mHeight) {
            return 0;
        }

        if (pointX >= mWidth * ((float) 2 / 3) && pointX < mWidth && pointY >= mHeight * ((float) 3 / 4)
                && pointY < mHeight) {
            return -1;
        }
        for (int j = 1; j < 4; j++) {
            for (int i = 0; i < 3; i++) {
                if (pointX >= mWidth * (float) i / 3 && pointX < mWidth * (float) (i + 1) / 3
                        && pointY >= mHeight * (float) (j - 1) / 4 && pointY < mHeight * (float) j / 4) {
                    return j * 3 + i - 2;
                }
            }
        }
        return -2;
    }

    public void setOnKeyBoardClickLitener(OnKeyBoardClickLitener onKeyBoardClickLitener) {
        this.onKeyBoardClickLitener = onKeyBoardClickLitener;
    }

    public interface OnKeyBoardClickLitener {
        public void getNumber(int num);
    }
}
