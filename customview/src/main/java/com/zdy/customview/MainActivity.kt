package com.zdy.customview

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.Toast
import com.zdy.qrcodelibrary.scan.ScanActivity
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

        image.setOnClickListener {
            startActivityForResult(Intent(this@MainActivity, ScanActivity::class.java), 0)
        }
    }

    private fun init() {
        property.progress = 65f
        property.start()
    }

    override fun onNewIntent(intent: Intent) {
        Log.e("TAG", "onNewIntent")
        val input = intent.getStringExtra(ScanActivity.INTENT_EXTRA_RESULT)
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                val input = intent.getStringExtra(ScanActivity.INTENT_EXTRA_RESULT)
                Toast.makeText(this, input, Toast.LENGTH_SHORT).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, intent)
        }
    }
}
