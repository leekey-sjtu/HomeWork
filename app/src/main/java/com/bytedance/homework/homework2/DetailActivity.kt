package com.bytedance.homework.homework2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.bytedance.homework.R
import com.bytedance.homework.homework2.RecyclerViewActivity.Companion.detail

class DetailActivity : AppCompatActivity() {

    private val tvDetail: TextView by lazy { findViewById(R.id.tv_detail) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val position = intent.getIntExtra("position", 0)
        tvDetail.text = detail[position]
    }
}