package com.zdy.customview

import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.AnticipateOvershootInterpolator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        initAnimate()
    }

    private fun initAnimate() {
        val animator = ObjectAnimator.ofFloat(image, "translationX", 500f)
        animator.interpolator = AnticipateOvershootInterpolator()
        animator.duration = 2000
        animator.start()
    }

    private fun init() {
        property.progress = 65f
        property.start()
    }

}
