package com.zdy.customview

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        init()
        initAnimate()
    }

    private fun initAnimate() {
        //X轴平移500像素
//        val animator = ObjectAnimator.ofFloat(image, "translationX", 500f)
//        animator.interpolator = AnticipateOvershootInterpolator()
//        animator.duration = 2000
//        animator.start()

//        //Argb渐变色动画
//        val animator = ObjectAnimator.ofArgb(view, "color", android.R.color.holo_red_dark, android.R.color.holo_green_dark)
//        animator.duration = 2000
//        animator.start()

    }


    override fun onNewIntent(intent: Intent) {
        Log.e("TAG", "onNewIntent")
        val input = intent.getStringExtra(com.zdy.qrcodelibrary.activity.ScanActivity.INTENT_EXTRA_RESULT)
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                val input = intent.getStringExtra(com.zdy.qrcodelibrary.activity.ScanActivity.INTENT_EXTRA_RESULT)
                Toast.makeText(this, input, Toast.LENGTH_SHORT).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, intent)
        }
    }
}
