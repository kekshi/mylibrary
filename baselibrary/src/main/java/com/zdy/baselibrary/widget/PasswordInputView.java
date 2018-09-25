package com.zdy.baselibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import com.zdy.baselibrary.R;

import java.util.ArrayList;

/**
 * 密码控件
 */

public class PasswordInputView extends View {
    private InputMethodManager input;//输入法管理
    private ArrayList<Integer> result;//输入结果保存
    private int passwordLength;//密码位数
    private int size;//默认每一格的大小
    private Paint mTextPaint;//掩盖点的画笔
    private InputCallBack inputCallBack;//输入完成的回调
    private Drawable editBoxRightBackground;//输入框背景
    private Drawable editBoxErrorBackground;
    private Drawable drawable;
    private int dividerWidth;
    private Rect[] mDestRects;
    private float baseLine;

    public interface InputCallBack {
        void onInput(ArrayList<Integer> result);

        void onInputFinish(String result);
    }

    public PasswordInputView(Context context) {
        this(context, null);
    }

    public PasswordInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PasswordInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /**
     * 初始化相关参数
     */
    void init(AttributeSet attrs) {
        final float dp = getResources().getDisplayMetrics().density;
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        input = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.PasswordInputView);
            editBoxRightBackground = ta.getDrawable(R.styleable.PasswordInputView_editBoxRightBackground);
            editBoxErrorBackground = ta.getDrawable(R.styleable.PasswordInputView_editBoxErrorBackground);
            passwordLength = ta.getInt(R.styleable.PasswordInputView_passwordCount, 4);
            ta.recycle();
        } else {
            editBoxRightBackground = new ColorDrawable(Color.RED);
            editBoxErrorBackground = new ColorDrawable(Color.RED);
            passwordLength = 4;//默认4位密码
        }
        drawable = editBoxRightBackground;
        result = new ArrayList<>(passwordLength);
        size = (int) (dp * 48);//默认48dp一格
        dividerWidth = (int) (dp * 8);
        //color
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(16 * dp);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setColor(Color.BLACK);
        mDestRects = new Rect[passwordLength];
        for (int i = 0, len = passwordLength; i < len; i++) {
            int left = getPaddingLeft() + i * (size + dividerWidth);
            mDestRects[i] = new Rect(left, getPaddingTop(), left + size, getPaddingTop() +
                    size);
        }
        baseLine = (mDestRects[0].top + mDestRects[0].bottom - mTextPaint.getFontMetrics().bottom - mTextPaint
                .getFontMetrics().top) / 2;
        this.setOnKeyListener(new MyKeyListener());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getPaddingLeft() + getPaddingRight() + passwordLength * size +
                (passwordLength - 1) * dividerWidth;
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        int height = getPaddingBottom() + getPaddingTop() + size;
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {//点击控件弹出输入键盘
            openInput();
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void openInput() {
        requestFocus();
        input.showSoftInput(this, InputMethodManager.SHOW_FORCED);
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasWindowFocus) {
//        super.onWindowFocusChanged(hasWindowFocus);
//        if (!hasWindowFocus){
//            input.hideSoftInputFromWindow(this.getWindowToken(), 0);
//        }
//    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0, len = mDestRects.length; i < len; i++) {
            Rect destRect = mDestRects[i];
            if (drawable != null) {
                drawable.setBounds(destRect);
                drawable.draw(canvas);
            }
            if (result.size() > i)
                canvas.drawText(String.valueOf(result.get(i)), (destRect.right + destRect
                        .left) / 2, baseLine, mTextPaint);
        }
    }

    @Override
    public boolean onCheckIsTextEditor() {
        return true;
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        outAttrs.inputType = InputType.TYPE_CLASS_NUMBER;//输入类型为数字
        outAttrs.imeOptions = EditorInfo.IME_ACTION_DONE;
        return new MyInputConnection(this, false);
    }

    public void setInputCallBack(InputCallBack inputCallBack) {
        this.inputCallBack = inputCallBack;
    }


    public void clearResult(boolean openInput) {
        result.clear();
        invalidate();
        if (openInput)
            openInput();
    }

    public void setError() {
        setError(true);
    }

    /**
     *
     * @param openInput true 打开键盘 false 关闭键盘
     */
    public void setError(final boolean openInput) {
        drawable = editBoxErrorBackground;
        invalidate();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                clearResult(openInput);
            }
        }, 1000);
    }


    private class MyInputConnection extends BaseInputConnection {
        MyInputConnection(View targetView, boolean fullEditor) {
            super(targetView, fullEditor);
        }

        @Override
        public boolean commitText(CharSequence text, int newCursorPosition) {
            //这里是接受输入法的文本的，我们只处理数字，所以什么操作都不做
            return super.commitText(text, newCursorPosition);
        }

        @Override
        public boolean deleteSurroundingText(int beforeLength, int afterLength) {
            //软键盘的删除键 DEL 无法直接监听，自己发送del事件
            if (beforeLength == 1 && afterLength == 0) {
                return super.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL))
                        && super.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL));
            }

            return super.deleteSurroundingText(beforeLength, afterLength);
        }
    }

    /**
     * 按键监听器
     */
    private class MyKeyListener implements OnKeyListener {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (event.isShiftPressed()) {//处理*#等键
                    return false;
                }
                if (keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9) {//只处理数字
                    if (result.size() < passwordLength) {
                        result.add(keyCode - 7);
                        inputCallBack.onInput(result);
                        invalidate();
                    }
                    ensureFinishInput();
                    return true;
                }
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (!result.isEmpty()) {//不为空，删除最后一个
                        result.remove(result.size() - 1);
                        inputCallBack.onInput(result);
                        if (drawable != editBoxRightBackground) {
                            drawable = editBoxRightBackground;
                        }
                        invalidate();
                    }
                    return true;
                }
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    ensureFinishInput();
                    return true;
                }
            }
            return false;
        }//onKey

        /**
         * 判断是否输入完成，输入完成后调用callback
         */
    }//in class

    void ensureFinishInput() {
        if (result.size() == passwordLength && inputCallBack != null) {//输入完成
            StringBuffer sb = new StringBuffer();
            for (int i : result) {
                sb.append(i);
            }
            input.hideSoftInputFromWindow(getWindowToken(), 0);
            inputCallBack.onInputFinish(sb.toString());
        }
    }

    public void reset() {
        drawable = editBoxRightBackground;
        invalidate();
    }
}
