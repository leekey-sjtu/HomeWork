package com.bytedance.homework.homework3

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.homework.MainActivity
import com.bytedance.homework.R
import com.bytedance.homework.homework4.AutoClockView
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class BilibiliActivity : AppCompatActivity(), View.OnClickListener, View.OnLongClickListener {

    private var buttonView:  Button? = null
    private var likeView:  ImageView? = null
    private var like2View:  ImageView? = null
    private var coinView:  ImageView? = null
    private var favoriteView:  ImageView? = null
    private val layParent: FrameLayout by lazy { findViewById(R.id.lay_parent) }
    private val imgLike3: ImageView by lazy { findViewById(R.id.img_like_3) }
//    private val imgLike4: ImageView by lazy { findViewById(R.id.img_like_4) }
    private var handler: Handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable = Runnable {
        run {
            myAnimator()
            handler.postDelayed(runnable, 200)  //隔一秒重复执行任务
        }
    }
    private var x = 0f
    private var y = 0f

    @SuppressLint("ClickableViewAccessibility")
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

        findViewById<Button>(R.id.btn_fresh).setOnClickListener {
            startActivity(Intent(this, BilibiliActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        findViewById<Button>(R.id.btn_viewpager2).setOnClickListener {
            startActivity(Intent(this, NewsListActivity::class.java))
        }
        findViewById<Button>(R.id.home_page_btn).setOnClickListener {
            onBackPressed()
        }

        imgLike3.setOnTouchListener { view, event ->
            x = event.x + 450
            y = event.y + 400
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {

                    handler.post(runnable)
                    val animator1 = ObjectAnimator.ofFloat(imgLike3, "scaleX", 1f, 0.7f)
                    val animator2 = ObjectAnimator.ofFloat(imgLike3, "scaleY", 1f, 0.7f)
                    val animSet = AnimatorSet()
                    animSet.duration = 200
                    animSet.play(animator1).with(animator2)
                    animSet.start()

                }
                MotionEvent.ACTION_UP -> {
                    handler.removeCallbacks(runnable)
                    val animator1 = ObjectAnimator.ofFloat(imgLike3, "scaleX", 0.7f, 1.5f, 1f)
                    val animator2 = ObjectAnimator.ofFloat(imgLike3, "scaleY", 0.7f, 1.5f, 1f)
                    val animSet = AnimatorSet()
                    animSet.duration = 400
                    animSet.play(animator1).with(animator2)
                    animSet.start()
                }
            }

            true
        }
    }

    private fun myAnimator() {
        var r = 500
        for (deg in 0..360 step 30) {
            val imgLike = ImageView(this)
            layParent.addView(imgLike)
            imgLike.setImageResource(R.drawable.icon_like_2)
            imgLike.elevation = 3f
            imgLike.layoutParams.width = 100
            imgLike.layoutParams.height = 100
            imgLike.x = x
            imgLike.y = y
            val animator1 = ObjectAnimator.ofFloat(imgLike, "translationX", x, (x + r * cos(deg * PI / 180)).toFloat())
            val animator2 = ObjectAnimator.ofFloat(imgLike, "translationY", y, (y + r * sin(deg * PI / 180)).toFloat())
            val animator3 = ObjectAnimator.ofFloat(imgLike, "alpha", 1f, 0f)
            val animator4 = ObjectAnimator.ofFloat(imgLike, "rotation", 0f, 360f)
            val animSet = AnimatorSet()
            animSet.duration = 500
            animSet.play(animator1).with(animator2).with(animator3).with(animator4)
            animSet.start()
        }

        Log.d("wdw", "x = $x, y = $y")
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