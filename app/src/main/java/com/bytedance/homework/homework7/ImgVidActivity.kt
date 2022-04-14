package com.bytedance.homework.homework7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bytedance.homework.R

class ImgVidActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_img_vid)
        skipActivity(R.id.btn_img, ImageActivity::class.java)
        skipActivity(R.id.btn_vid, VideoActivity::class.java)
    }

    private fun skipActivity(btnId: Int, activityClass: Class<*>) {
        findViewById<View>(btnId).setOnClickListener {
            startActivity(Intent(this, activityClass))
        }
    }
}