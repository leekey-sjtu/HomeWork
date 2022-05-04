package com.bytedance.homework.homework7

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import com.bytedance.homework.R


class VideoActivity : AppCompatActivity() {

    private val videoView: VideoView by lazy { findViewById(R.id.videoView) }
    private val playController: LinearLayout by lazy { findViewById(R.id.lay_playController) }
    private val layPlayPause: FrameLayout by lazy { findViewById(R.id.lay_play_pause) }
    private val imgPlay: ImageView by lazy { findViewById(R.id.img_play) }
    private val imgPause: ImageView by lazy { findViewById(R.id.img_pause) }
    private val currentTime: TextView by lazy { findViewById(R.id.tv_current_time) }
    private val seekBar: SeekBar by lazy { findViewById(R.id.seekBar) }
    private val totalTime: TextView by lazy { findViewById(R.id.tv_total_time) }
    private val layFullScreen: FrameLayout by lazy { findViewById(R.id.lay_full_screen) }
    private val imgFullScreen: ImageView by lazy { findViewById(R.id.img_full_screen) }
    private val imgFullScreenExit: ImageView by lazy { findViewById(R.id.img_full_screen_exit) }
    private val rePlay: LinearLayout by lazy { findViewById(R.id.lay_replay) }
    private var seekBarIsTracking = false  //判断进度条是否处于拖拽状态
    private var mGestureDetector: GestureDetector? = null  //视频手势检测
    private var handler: Handler = Handler(Looper.getMainLooper())  //更新UI
    private var runnable: Runnable = Runnable {  //Runnable接口
        run {
            Log.d("wdw","runnable")
            if (videoView.isPlaying) {
                currentTime.text = getTimeString(videoView.currentPosition / 1000)
                seekBar.progress = (videoView.currentPosition * 100.0 / videoView.duration).toInt()
            }
            handler.postDelayed(runnable, 300)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "视频播放"
        setContentView(R.layout.activity_video)
//        videoView.setVideoURI(Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))
        videoView.setVideoPath(getVideoPath(R.raw.sample_video))  //设置视频路径
        videoView.keepScreenOn = true  //屏幕常亮
        videoView.setOnInfoListener { mp, what, extra -> true }  //监听播放信息, what: 信息或者警告的类型
        videoView.setOnErrorListener { p0, p1, p2 -> true }  //监听播放错误
        videoView.setOnTouchListener { v, event -> mGestureDetector!!.onTouchEvent(event) }  //监听触摸事件并交由GestureDetector代为处理
        videoView.setOnPreparedListener {  //监听视频装载完成
            videoView.seekTo(1)  //简单设置视频封面
            totalTime.text = getTimeString(videoView.duration / 1000)  //初始化视频totalTime
            handler.post(runnable)  //利用线程定时更新currentTime和seekBar
        }
        videoView.setOnCompletionListener {   //监听播放完成
            rePlay.visibility = View.VISIBLE
            window.decorView.systemUiVisibility = View.VISIBLE  //显示顶部状态栏，否则重播按钮不显示//TODO
            pauseAnimator()
        }

        rePlay.setOnClickListener {  //监听重播
            playAnimator()
            videoView.start()
            rePlay.visibility = View.INVISIBLE
        }

        layPlayPause.setOnClickListener {  //监听播放或暂停
            if (videoView.isPlaying) {
                videoView.pause()
                pauseAnimator()
            } else {
                videoView.start()
                rePlay.visibility = View.INVISIBLE
                playAnimator()
            }
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (seekBarIsTracking) {  //控制播放进度
                    videoView.seekTo((videoView.duration * progress * 1.0 / 100 ).toInt())
                }
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {  //进度条开始拖拽，停止播放
                seekBarIsTracking = true
                pauseAnimator()
                rePlay.visibility = View.INVISIBLE
                videoView.pause()
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {  //进度条停止拖拽，继续播放
                seekBarIsTracking = false
                playAnimator()
                videoView.start()
            }
        })

        layFullScreen.setOnClickListener {  //监听全屏
            if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                supportActionBar?.show()  //显示该页面的标题栏
                window.decorView.systemUiVisibility = View.VISIBLE  //显示顶部状态栏
                fullScreenExitAnimator()
            } else {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                supportActionBar?.hide()  //隐藏该页面的标题栏
                window.decorView.systemUiVisibility = View.INVISIBLE  //隐藏顶部状态栏
                fullScreenAnimator()
            }
        }

        mGestureDetector = GestureDetector(this, object : GestureDetector.OnGestureListener {  //监听视频手势
            override fun onDown(p0: MotionEvent?): Boolean {
                Log.d("wdw", "onDown")
                return true
            }

            override fun onShowPress(p0: MotionEvent?) {
                Log.d("wdw", "onShowPress")
            }

            override fun onSingleTapUp(p0: MotionEvent?): Boolean {
                Log.d("wdw", "onSingleTapUp")
                return true
            }

            //TODO("可以实现快进快退")
            override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
                Log.d("wdw", "onScroll")
                Log.d("wdw","vX = $velocityX, vY = $velocityY")
                return true
            }

            //TODO("可以实现倍速播放")
            override fun onLongPress(p0: MotionEvent?) {
                Log.d("wdw", "onLongPress")
            }

            override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
                Log.d("wdw", "onFling")
                return true
            }

        })

        mGestureDetector!!.setOnDoubleTapListener(object : GestureDetector.OnDoubleTapListener {
            override fun onSingleTapConfirmed(p0: MotionEvent?): Boolean {
                Log.d("wdw", "onSingleTapConfirmed")
                if(playController.alpha == 0f) {
                    showPlayController()
                    handler.postDelayed({  //5s后自动隐藏状态栏
                        hidePlayController()
                    }, 5000)
                } else {
                    hidePlayController()
                }
                return true
            }

            override fun onDoubleTap(p0: MotionEvent?): Boolean {
                Log.d("wdw", "onDoubleTap")
                if(videoView.isPlaying) {
                    pauseAnimator()
                    videoView.pause()
                } else {
                    playAnimator()
                    videoView.start()
                }
                return true
            }

            override fun onDoubleTapEvent(p0: MotionEvent?): Boolean {
                Log.d("wdw", "onDoubleTapEvent")
                return true
            }

        })

    }

    private fun getVideoPath(resId: Int): String {
        return "android.resource://" + this.packageName + "/" + resId
    }

    @SuppressLint("SetTextI18n")
    private fun getTimeString(totalSec: Int) : String {
        val s = totalSec % 60
        val sec = if (s < 10) "0$s" else "$s"
        val m = (totalSec % 3600 - s) / 60
        val min = if (m < 10) "0$m" else "$m"
        val h = totalSec / 3600
        val hr = if (h < 10)  "0$h" else "$h"
        return if (h == 0) "$min:$sec" else "$hr:$min:$sec"
    }

    override fun onBackPressed() {  //设置横屏返回变为竖屏
        if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            supportActionBar?.show()  //显示该页面的标题栏
            window.decorView.systemUiVisibility = View.VISIBLE
            fullScreenExitAnimator()
        } else {
            super.onBackPressed()
        }
    }

    private fun pauseAnimator() {
        val animator1 = ObjectAnimator.ofFloat(imgPlay, "rotation", -90f, 0f)
        val animator2 = ObjectAnimator.ofFloat(imgPlay, "alpha", 0f, 1f)
        val animator3 = ObjectAnimator.ofFloat(imgPause, "rotation", 0f, 90f)
        val animator4 = ObjectAnimator.ofFloat(imgPause, "alpha", 1f, 0f)
        val animSet = AnimatorSet()
        animSet.duration = 300
        animSet.play(animator1).with(animator2).with(animator3).with(animator4)
        animSet.start()
    }

    private fun playAnimator() {
        val animator1 = ObjectAnimator.ofFloat(imgPlay, "rotation", 0f, -90f)
        val animator2 = ObjectAnimator.ofFloat(imgPlay, "alpha", 1f, 0f)
        val animator3 = ObjectAnimator.ofFloat(imgPause, "rotation", 90f, 0f)
        val animator4 = ObjectAnimator.ofFloat(imgPause, "alpha", 0f, 1f)
        val animSet = AnimatorSet()
        animSet.duration = 300
        animSet.play(animator1).with(animator2).with(animator3).with(animator4)
        animSet.start()
    }

    private fun fullScreenAnimator() {
        val animator1 = ObjectAnimator.ofFloat(imgFullScreen, "scaleX", 1f, 0f)
        val animator2 = ObjectAnimator.ofFloat(imgFullScreen, "scaleY", 1f, 0f)
        val animator3 = ObjectAnimator.ofFloat(imgFullScreen, "alpha", 1f, 0f)
        val animator4 = ObjectAnimator.ofFloat(imgFullScreenExit, "scaleX", 0f, 1f)
        val animator5 = ObjectAnimator.ofFloat(imgFullScreenExit, "scaleY", 0f, 1f)
        val animator6 = ObjectAnimator.ofFloat(imgFullScreenExit, "alpha", 0f, 1f)
        val animSet = AnimatorSet()
        animSet.duration = 300
        animSet.play(animator1).with(animator2).with(animator3).with(animator4).with(animator5).with(animator6).after(600)
        animSet.start()
    }

    private fun fullScreenExitAnimator() {
        val animator1 = ObjectAnimator.ofFloat(imgFullScreen, "scaleX", 0f, 1f)
        val animator2 = ObjectAnimator.ofFloat(imgFullScreen, "scaleY", 0f, 1f)
        val animator3 = ObjectAnimator.ofFloat(imgFullScreen, "alpha", 0f, 1f)
        val animator4 = ObjectAnimator.ofFloat(imgFullScreenExit, "scaleX", 1f, 0f)
        val animator5 = ObjectAnimator.ofFloat(imgFullScreenExit, "scaleY", 1f, 0f)
        val animator6 = ObjectAnimator.ofFloat(imgFullScreenExit, "alpha", 1f, 0f)
        val animSet = AnimatorSet()
        animSet.duration = 300
        animSet.play(animator1).with(animator2).with(animator3).with(animator4).with(animator5).with(animator6).after(600)
        animSet.start()
    }

    private fun hidePlayController() {
        val animator1 = ObjectAnimator.ofFloat(playController, "translationY", 0f, 60f)
        val animator2 = ObjectAnimator.ofFloat(playController, "alpha", 1f, 0f)
        val animSet = AnimatorSet()
        animSet.duration = 300
        animSet.play(animator1).with(animator2)
        animSet.start()
    }

    private fun showPlayController() {
        val animator1 = ObjectAnimator.ofFloat(playController, "translationY", 60f, 0f)
        val animator2 = ObjectAnimator.ofFloat(playController, "alpha", 0f, 1f)
        val animSet = AnimatorSet()
        animSet.duration = 300
        animSet.play(animator1).with(animator2)
        animSet.start()
    }

    override fun onDestroy() {  //页面结束，清空消息队列
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}