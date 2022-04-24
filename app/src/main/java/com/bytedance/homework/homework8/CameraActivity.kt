package com.bytedance.homework.homework8

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.hardware.Camera
import android.media.CamcorderProfile
import android.media.MediaRecorder
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.*
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import com.bytedance.homework.R
import com.google.android.material.tabs.TabLayout
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.util.*

class CameraActivity : AppCompatActivity(), SurfaceHolder.Callback {

    private var camera: Camera? = null
    private var mediaRecorder: MediaRecorder? = null
    private lateinit var surfaceHolder: SurfaceHolder
    private val surfaceView: SurfaceView by lazy { findViewById(R.id.surfaceView) }
    private val layCamera: FrameLayout by lazy { findViewById(R.id.lay_camera) }
    private val layPreview: LinearLayout by lazy { findViewById(R.id.lay_preview) }
    private val imagePreview: ImageView by lazy { findViewById(R.id.photo_preview) }
    private val videoPreview: VideoView by lazy { findViewById(R.id.video_preview) }
    private val imgShutterCamera: ImageView by lazy { findViewById(R.id.iv_shutter_camera) }
    private val imgShutterRecord1: ImageView by lazy { findViewById(R.id.iv_shutter_record_1) }
    private val imgShutterRecord2: ImageView by lazy { findViewById(R.id.iv_shutter_record_2) }
    private val tabLayout: TabLayout by lazy { findViewById(R.id.tabLayout_Camera) }
    private var isRecording = false
    private var mp4Path = ""
    private var pictureCallback = Camera.PictureCallback { data, camera ->  //获取照片中的接口回调
        var fos: FileOutputStream? = null
        val filePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath + File.separator + "1.jpg"
        val file = File(filePath)
        try {
            fos = FileOutputStream(file)
            fos.write(data)
            fos.flush()
            val bitmap = BitmapFactory.decodeFile(filePath)
            val rotateBitmap = TransformationUtils.rotateImage(bitmap, 0)
            layCamera.visibility = View.GONE
            layPreview.visibility = View.VISIBLE
            imagePreview.visibility = View.VISIBLE
            videoPreview.visibility = View.GONE
            imagePreview.setImageBitmap(rotateBitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            this.camera?.startPreview()
            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        surfaceHolder = surfaceView.holder
        initCamera()
        surfaceHolder.addCallback(this)
        imgShutterCamera.setOnClickListener {
            val animator1 = ObjectAnimator.ofFloat(imgShutterCamera, "scaleX", 1f, 0.8f, 1f)
            val animator2 = ObjectAnimator.ofFloat(imgShutterCamera, "scaleY", 1f, 0.8f, 1f)
            val animSet = AnimatorSet()
            animSet.duration = 200
            animSet.play(animator1).with(animator2)
            animSet.start()
            takePhotos()
        }
        imgShutterRecord1.setOnClickListener {
            val animator1 = ObjectAnimator.ofFloat(imgShutterRecord1, "scaleX", 1f, 0.5f)
            val animator2 = ObjectAnimator.ofFloat(imgShutterRecord1, "scaleY", 1f, 0.5f)
            val animator3 = ObjectAnimator.ofFloat(imgShutterRecord1, "alpha", 1f, 0f)
            val animator4 = ObjectAnimator.ofFloat(imgShutterRecord2, "scaleX", 0.5f, 1f)
            val animator5 = ObjectAnimator.ofFloat(imgShutterRecord2, "scaleY", 0.5f, 1f)
            val animator6 = ObjectAnimator.ofFloat(imgShutterRecord2, "alpha", 0f, 1f)
            val animSet = AnimatorSet()
            animSet.duration = 200
            animSet.play(animator1).with(animator2).with(animator3).with(animator4).with(animator5).with(animator6)
            animSet.start()
            record()
        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    imgShutterCamera.visibility = View.VISIBLE
                    imgShutterRecord1.visibility = View.GONE
                    imgShutterRecord2.visibility = View.GONE
                } else {
                    imgShutterCamera.visibility = View.GONE
                    imgShutterRecord1.visibility = View.VISIBLE
                    imgShutterRecord2.visibility = View.VISIBLE
                    imgShutterRecord2.alpha = 0f
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    private fun takePhotos() {
        camera?.takePicture(null, null, pictureCallback)
    }

    private fun initCamera() {
        camera = Camera.open()

        camera?.let {
            val parameters = it.parameters
            parameters.pictureFormat = ImageFormat.JPEG
            parameters.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE
            parameters["orientation"] = "portrait"
            parameters["rotation"] = 90
            it.parameters = parameters
            it.setDisplayOrientation(90)
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        try {
            camera?.let {
                it.setPreviewDisplay(holder)
                it.startPreview()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        if (holder.surface == null) {
            return
        }
        camera?.stopPreview()  //停止预览效果
        try {
            camera?.let {
                it.setPreviewDisplay(holder)  //重新设置预览效果
                it.startPreview()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        camera?.let {
            it.stopPreview()
            it.release()
        }
    }

    override fun onResume() {
        super.onResume()
        if (camera == null) {
            initCamera()
        }
        camera?.startPreview()
    }

    override fun onPause() {
        super.onPause()
        camera?.stopPreview()
    }

    private fun record() {
        if (isRecording && mediaRecorder != null) {
//            recordButton.text = "录制"
            val mediaRecorder = this.mediaRecorder ?:return

            mediaRecorder.setOnErrorListener(null)
            mediaRecorder.setOnInfoListener(null)
            mediaRecorder.setPreviewDisplay(null)
            try {
                mediaRecorder.stop()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            mediaRecorder.reset()
            mediaRecorder.release()
            this.mediaRecorder = null
            camera?.lock()
            layCamera.visibility = View.GONE
            layPreview.visibility = View.VISIBLE
            imagePreview.visibility = View.GONE
            videoPreview.visibility = View.VISIBLE
            videoPreview.setVideoPath(mp4Path)
            videoPreview.start()
        } else {
            if (prepareVideoRecorder()) {
//                recordButton.text = "暂停"
                mediaRecorder!!.start()
            }
        }
        isRecording = !isRecording
    }

    private fun prepareVideoRecorder(): Boolean {
        val mediaRecorder = MediaRecorder()
        this.mediaRecorder = mediaRecorder

        // Step 1: Unlock and set camera to MediaRecorder
        camera?.unlock()
        mediaRecorder.setCamera(camera)

        // Step 2: Set sources
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER)
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA)

        // Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH))

        // Step 4: Set output file
        mp4Path = outputMediaPath
        mediaRecorder.setOutputFile(mp4Path)

        // Step 5: Set the preview output
        mediaRecorder.setPreviewDisplay(surfaceHolder.surface)
        mediaRecorder.setOrientationHint(90)

        // Step 6: Prepare configured MediaRecorder
        try {
            mediaRecorder.prepare()
        } catch (e: IllegalStateException) {
            releaseMediaRecorder()
            return false
        } catch (e: IOException) {
            releaseMediaRecorder()
            return false
        }
        return true
    }

    private val outputMediaPath: String
        private get() {
            val mediaStorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val mediaFile = File(mediaStorageDir, "IMG_$timeStamp.mp4")
            if (!mediaFile.exists()) {
                mediaFile.parentFile.mkdirs()
            }
            return mediaFile.absolutePath
        }

    private fun releaseMediaRecorder() {
        mediaRecorder?.let { mediaRecorder->
            mediaRecorder.reset() // clear recorder configuration
            mediaRecorder.release() // release the recorder object
            this.mediaRecorder = null
            camera?.lock() // lock camera for later use
        }
    }
}