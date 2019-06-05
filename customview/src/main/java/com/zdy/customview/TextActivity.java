package com.zdy.customview;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        initAnimate();
    }

    private void initAnimate() {
        //Argb渐变色动画
//        view.animate().scaleX(1).scaleY(1).alpha(1).start();
    }
}
