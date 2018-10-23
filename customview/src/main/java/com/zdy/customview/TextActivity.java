package com.zdy.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TextActivity extends AppCompatActivity {
    private View view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        view = findViewById(R.id.view);
        initAnimate();
    }

    private void initAnimate() {
        //Argb渐变色动画
        view.animate().scaleX(1).scaleY(1).alpha(1).start();
    }
}
