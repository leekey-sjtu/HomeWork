package com.bytedance.homework.homework8

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.hardware.Camera
import android.os.Bundle
import android.os.Environment
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage
import com.bytedance.homework.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception

class Camera1Fragment : Fragment(), SurfaceHolder.Callback {

    private lateinit var mContext: Context
    private var camera: Camera? = null
    private val surfaceView: SurfaceView by lazy { requireView().findViewById(R.id.surfaceView) }
    private val imgPreview: ImageView by lazy { requireView().findViewById(R.id.photo_preview) }
    private val layPreview: LinearLayout by lazy { requireView().findViewById(R.id.lay_preview) }
    private val imgShutter: ImageView by lazy { requireView().findViewById(R.id.iv_shutter_camera) }
    private lateinit var surfaceHolder: SurfaceHolder

    private var pictureCallback = Camera.PictureCallback { data, camera ->  //获取照片中的接口回调
        var fos: FileOutputStream? = null
        val filePath =
            mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath + File.separator + "1.jpg"
        val file = File(filePath)
        try {
            fos = FileOutputStream(file)
            fos.write(data)
            fos.flush()
            val bitmap = BitmapFactory.decodeFile(filePath)
            val rotateBitmap = rotateImage(bitmap, 0)
            layPreview.visibility = View.VISIBLE
            imgPreview.setImageBitmap(rotateBitmap)
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
        this.mContext = requireActivity()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_camera_1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        surfaceHolder = surfaceView.holder
        initCamera()
        surfaceHolder.addCallback(this)
        imgShutter.setOnClickListener {
            camera?.takePicture(null, null, pictureCallback)
        }
    }

    private fun initCamera() {
        camera = Camera.open()

        camera?.let {
            val parameters = it.parameters
            parameters.pictureFormat = ImageFormat.JPEG
            parameters.focusMode = Camera.Parameters.FOCUS_MODE_AUTO
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
        //停止预览效果
        camera?.stopPreview()
        //重新设置预览效果
        try {
            camera?.let {
                it.setPreviewDisplay(holder)
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
//        camera?.stopPreview()
    }

    companion object {
        fun startUI(context: Context) {
            val intent = Intent(context, CameraActivity::class.java)
            context.startActivity(intent)
        }
    }


}