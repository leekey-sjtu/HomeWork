package com.bytedance.homework.homework3

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bytedance.homework.R
import com.bytedance.homework.homework3.NewsListAdapter.OnItemClickListener
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class NewsListActivity : AppCompatActivity() {

    private var isTwoPane: Boolean = false  //判断横竖屏
    private var viewPager2: ViewPager2? = null
//    private val tabLayout: TabLayout by lazy { findViewById(R.id.tabLayout) }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)
//        viewPager2 = findViewById(R.id.vp_news_content)
//        val fragments = mutableListOf<Fragment>()
//        fragments.add(NewsContentFragment("title 1", "content 1", Color.parseColor("#00f312")))
//        fragments.add(NewsContentFragment("title 2", "content 2", Color.parseColor("#ffff12")))
//        fragments.add(NewsContentFragment("title 3", "content 3", Color.parseColor("#f8937f")))
//        viewPager2?.adapter = ViewPager2Adapter(supportFragmentManager, lifecycle, fragments)

        val data = mutableListOf<String>("新闻1", "新闻2", "新闻3")
        val adapter = NewsListAdapter(data)
        val newslist = findViewById<RecyclerView>(R.id.rv_news_list)
        newslist.layoutManager = LinearLayoutManager(this)
        newslist.adapter = adapter

        viewPager2 = findViewById(R.id.vp_news_content)

        if (viewPager2 != null) {
            isTwoPane = true
            val fragments = mutableListOf<Fragment>()
            fragments.add(NewsContentFragment("title 1", "content 1", Color.parseColor("#00f312")))
            fragments.add(NewsContentFragment("title 2", "content 2", Color.parseColor("#ffff12")))
            fragments.add(NewsContentFragment("title 3", "content 3", Color.parseColor("#f8937f")))
            viewPager2?.adapter = ViewPager2Adapter(this, fragments)
        }

        adapter.setOnItemClickListener(object: OnItemClickListener {  //adapter设置点击事件
            override fun onItemClick(position: Int) {
                if(isTwoPane) {
                    Log.d("12345", "横屏")
                    viewPager2?.currentItem = position
                } else {
                    Log.d("12345", "竖屏")
                    val myIntent = Intent(this@NewsListActivity, NewsContentActivity::class.java)
                    myIntent.putExtra("position", position)
                    startActivity(myIntent)
                }
            }
        })

        //将TabLayout中的TabItem与ViewPager2中的Fragment绑定
//        if(isTwoPane) {
//            TabLayoutMediator(tabLayout, viewPager2!!) { tab: TabLayout.Tab, position: Int ->
//                when (position) {
//                    0 -> {
//                        tab.text = "页面1"
//                        tab.icon = getDrawable(R.drawable.icon_like_2)
//                    }
//                    1 -> {
//                        tab.text = "页面2"
//                        tab.icon = getDrawable(R.drawable.coin2)
//                    }
//                    2 -> {
//                        tab.text = "页面3"
//                        tab.icon = getDrawable(R.drawable.favorite2)
//                    }
//                }
//            }.attach()
//        }
    }

    override fun onBackPressed() {  //返回到左一页面，而不直接退出
        Log.d("12345", "currentItem = ${viewPager2?.currentItem}")
        if (viewPager2?.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager2?.currentItem = viewPager2?.currentItem!! - 1
        }
    }
}