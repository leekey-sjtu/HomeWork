package com.bytedance.homework.homework3

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.homework.MainActivity
import com.bytedance.homework.R

class BilibiliActivity : AppCompatActivity(), View.OnClickListener, View.OnLongClickListener {

    private var buttonView:  Button? = null
    private var likeView:  ImageView? = null
    private var like2View:  ImageView? = null
    private var coinView:  ImageView? = null
    private var favoriteView:  ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bilibili)
        buttonView = findViewById(R.id.home_page_btn)
        buttonView!!.setOnClickListener(this)
        likeView = findViewById(R.id.img_like)
        likeView!!.setOnClickListener(this)
        likeView!!.setOnLongClickListener(this)
        like2View = findViewById(R.id.img_like_2)
        coinView = findViewById(R.id.coin_image)
        coinView!!.setOnClickListener(this)
        favoriteView = findViewById(R.id.favorite_image)
        favoriteView!!.setOnClickListener(this)

        //////
        findViewById<Button>(R.id.btn_bili).setOnClickListener {
            startActivity(Intent(this, BilibiliActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        //////
        findViewById<Button>(R.id.btn_viewpager2).setOnClickListener {
            startActivity(Intent(this, NewsListActivity::class.java))
        }
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.img_like -> {
                likeView!!.startAnimation(AnimationUtils.loadAnimation(this, R.anim.like_click))
            }
            R.id.coin_image -> {
                coinView!!.startAnimation(AnimationUtils.loadAnimation(this, R.anim.like_click))
            }
            R.id.favorite_image -> {
                favoriteView!!.startAnimation(AnimationUtils.loadAnimation(this, R.anim.like_click))
            }
            R.id.home_page_btn -> {
                startActivity(Intent(this, MainActivity().javaClass))
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }
    }

    override fun onLongClick(v: View): Boolean {
        likeView!!.startAnimation(AnimationUtils.loadAnimation(this, R.anim.like_long_click))
        likeView!!.visibility= View.INVISIBLE
        var handler: Handler = Handler(Looper.getMainLooper())
        var runnable: Runnable = Runnable() {
            run() {
                like2View!!.visibility = View.VISIBLE
                like2View!!.startAnimation(AnimationUtils.loadAnimation(this, R.anim.like_long_click_2))
                coinView!!.startAnimation(AnimationUtils.loadAnimation(this, R.anim.like_click))
                favoriteView!!.startAnimation(AnimationUtils.loadAnimation(this, R.anim.like_click))
            }
        }
        handler.postDelayed(runnable, 1000)
//        handler.postDelayed(runnable, 2000)
        return true
    }
}