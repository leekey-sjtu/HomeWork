package com.bytedance.homework.homework3

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bytedance.homework.R

class NewsContentActivity : AppCompatActivity() {

//    companion object {
//        fun actionStart(context: Context, title: String, content: String) {
//            val intent = Intent(context, NewsContentActivity::class.java).apply {
//                putExtra("news_title", title)
//                putExtra("news_content", content)
//            }
//            context.startActivity(intent)
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_content)
        var position = intent.getIntExtra("position", -1)  //获得当前tasklist编号，以查询对应数据
//        var fragment = findViewById<NewsContentFragment>(R.id.newsContentFragment)
        val fragments = mutableListOf<Fragment>()
        fragments.add(NewsContentFragment("title 1", "content 1", Color.parseColor("#00f312")))
        fragments.add(NewsContentFragment("title 2", "content 2", Color.parseColor("#ffff12")))
        fragments.add(NewsContentFragment("title 3", "content 3", Color.parseColor("#f8937f")))
        val supportFragmentManager = supportFragmentManager
        supportFragmentManager
            .beginTransaction()
            .add(R.id.newsContentFragmentContainer, fragments[position])
            .commit()
    }
}