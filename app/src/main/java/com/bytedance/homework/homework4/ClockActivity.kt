package com.bytedance.homework.homework4

import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_BACK
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.homework.R

class ClockActivity : AppCompatActivity(), View.OnClickListener {

    var soundPool = SoundPool(10, AudioManager.STREAM_SYSTEM, 5)
    var handler: Handler = Handler(Looper.getMainLooper())
    var runnable: Runnable = Runnable {
        run {
            soundPool.play(1, 1.0F, 1.0F, 1, 0, 1.2F)  //播放时钟声音
            findViewById<AutoClockView>(R.id.auto_clock).setDeg()  //每隔一秒修改度数
            handler.postDelayed(runnable, 1000)  //隔一秒重复执行任务
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "时钟"
        setContentView(R.layout.activity_clock)
        findViewById<Button>(R.id.man_button).setOnClickListener(this)
        findViewById<Button>(R.id.auto_button).setOnClickListener(this)
        soundPool.load(this, R.raw.audio_clock, 1)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.auto_button -> {  //自动时间
                Toast.makeText(this, "切换自动时间", Toast.LENGTH_SHORT).show()
                findViewById<ClockView>(R.id.clock).visibility = View.INVISIBLE
                findViewById<AutoClockView>(R.id.auto_clock).visibility = View.VISIBLE  //切换AutoClockView
                handler.post(runnable)  //启动runnable任务
            }
            R.id.man_button -> {  //手动时间
                Toast.makeText(this, "切换手动时间", Toast.LENGTH_SHORT).show()
                findViewById<AutoClockView>(R.id.auto_clock).visibility = View.INVISIBLE
                findViewById<ClockView>(R.id.clock).visibility = View.VISIBLE  //切换ClockView
                handler.removeCallbacks(runnable)  //停止runnable任务
                soundPool.autoPause()  //停止播放声音
            }
        }
    }

    //监测返回事件
    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if(event!!.keyCode == KEYCODE_BACK) {
//            Toast.makeText(this, "返回", Toast.LENGTH_SHORT).show()
            soundPool.release()  //停止播放时钟声音
        }
        return super.dispatchKeyEvent(event)
    }

    override fun onDestroy() {  //页面结束，清空消息队列
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

}