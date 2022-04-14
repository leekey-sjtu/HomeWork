package com.bytedance.homework

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.homework.homework3.BilibiliActivity
import com.bytedance.homework.homework4.ClockActivity
import com.bytedance.homework.homework5.TranslateActivity
import com.bytedance.homework.homework6.TodoListActivity
import com.bytedance.homework.homework7.ImgVidActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        skipActivity(R.id.btn_2, ::class.java)
        skipActivity(R.id.btn_3, BilibiliActivity::class.java)
        skipActivity(R.id.btn_4, ClockActivity::class.java)
        skipActivity(R.id.btn_5, TranslateActivity::class.java)
        skipActivity(R.id.btn_6, TodoListActivity::class.java)
        skipActivity(R.id.btn_7, ImgVidActivity::class.java)
//        skipActivity(R.id.btn_8, ::class.java)
//        skipActivity(R.id.btn_9, ::class.java)
    }

    private fun skipActivity(btnId: Int, activityClass: Class<*>) {
        findViewById<View>(btnId).setOnClickListener {
            startActivity(Intent(this, activityClass))
        }
    }
}