package com.bytedance.homework

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bytedance.homework.homework2.RecyclerViewActivity
import com.bytedance.homework.homework3.BilibiliActivity
import com.bytedance.homework.homework4.ClockActivity
import com.bytedance.homework.homework5.TranslateActivity
import com.bytedance.homework.homework6.TodoListActivity
import com.bytedance.homework.homework7.ImgVidActivity
import com.bytedance.homework.homework8.CameraActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)   //设置隐藏状态栏
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR  //切换状态栏字体为黑色
        skipActivity(R.id.btn_2, RecyclerViewActivity::class.java)
        skipActivity(R.id.btn_3, BilibiliActivity::class.java)
        skipActivity(R.id.btn_4, ClockActivity::class.java)
        skipActivity(R.id.btn_5, TranslateActivity::class.java)
        skipActivity(R.id.btn_6, TodoListActivity::class.java)
        skipActivity(R.id.btn_7, ImgVidActivity::class.java)
        findViewById<Button>(R.id.btn_8).setOnClickListener {
            requestCameraPermission()
        }
    }

    private fun skipActivity(btnId: Int, activityClass: Class<*>) {
        findViewById<View>(btnId).setOnClickListener {
            startActivity(Intent(this, activityClass))
        }
    }

    private fun requestCameraPermission() {
        val hasCameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        val hasAudioPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
        if (hasCameraPermission && hasAudioPermission) {
            startActivity(Intent(this, CameraActivity::class.java))
        } else {
            val permissions: MutableList<String> = ArrayList()
            if (!hasCameraPermission) permissions.add(Manifest.permission.CAMERA)
            if (!hasAudioPermission) permissions.add(Manifest.permission.RECORD_AUDIO)
            ActivityCompat.requestPermissions(this, permissions.toTypedArray(), PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var hasPermission = true
        for (grantResult in grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                hasPermission = false
                break
            }
        }
        if (hasPermission) {
            startActivity(Intent(this, CameraActivity::class.java))
        } else {
            Toast.makeText(this, "获取权限失败", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1001
    }
}