package com.bytedance.homework.homework4

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.lang.Math.*
import kotlin.math.cos
import kotlin.math.sin

class AutoClockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    //时针
    private var hPaint: Paint = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
        isAntiAlias = true
        strokeWidth = 12F
        strokeJoin = Paint.Join.ROUND
    }

    //分针
    private var mPaint: Paint = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL
        isAntiAlias = true
        strokeWidth = 10F
    }

    //秒针
    private var sPaint: Paint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL
        isAntiAlias = true
        strokeWidth = 6F
    }

    //表盘
    private var wPaint: Paint = Paint().apply {
        color = Color.parseColor("#6a6a6a")
        style = Paint.Style.FILL
        isAntiAlias = true
        strokeWidth = 6F
    }

    //表盘-粗线
    private var wPaintBold: Paint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL
        isAntiAlias = true
        strokeWidth = 8F
        textSize = 100F
        textAlign = Paint.Align.CENTER
    }

    //默认参数
    private var hdeg: Double = 162.5  //时针角度
    private var mdeg: Double = 60.0  //分针角度
    private var sdeg: Double = 270.0  //秒针角度
    private val ox: Float = 500F  //圆心坐标
    private val oy: Float = 1000F  //圆心坐标
    private val r: Float = 500F  //半径

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawClockDial(canvas)
        drawClockHand(canvas)
        drawDigitalClock(canvas)
    }

    //画时钟表盘
    private fun drawClockDial(canvas: Canvas?) {
        //画时间刻度线
        for(deg in 0..354 step 6) {
            val x1: Float = (0.9 * r * cos(deg.toDouble() * PI / 180)).toFloat() + ox
            val y1: Float = (0.9 * r * sin(deg.toDouble() * PI / 180)).toFloat() + oy
            val x2: Float = (r * cos(deg.toDouble() * PI / 180)).toFloat() + ox
            val y2: Float = (r * sin(deg.toDouble() * PI / 180)).toFloat() + oy
            if(deg % 30 == 0) canvas?.drawLine(x1, y1, x2, y2, wPaintBold)
            else canvas?.drawLine(x1, y1, x2, y2, wPaint)
        }

        //画时间数字
        var x = (0.8 * r * cos( 5.5 * PI / 180)).toFloat() + ox
        var y = (0.8 * r * sin( 5.5 * PI / 180)).toFloat() + oy
        canvas?.drawText("3", x, y, wPaintBold)
        x = (0.82 * r * cos( 34 * PI / 180)).toFloat() + ox
        y = (0.82 * r * sin( 34 * PI / 180)).toFloat() + oy
        canvas?.drawText("4", x, y, wPaintBold)
        x = (0.84 * r * cos( 62 * PI / 180)).toFloat() + ox
        y = (0.84 * r * sin( 62 * PI / 180)).toFloat() + oy
        canvas?.drawText("5", x, y, wPaintBold)
        x = (0.85 * r * cos( 90 * PI / 180)).toFloat() + ox
        y = (0.85 * r * sin( 90 * PI / 180)).toFloat() + oy
        canvas?.drawText("6", x, y, wPaintBold)
        x = (0.84 * r * cos( 118 * PI / 180)).toFloat() + ox
        y = (0.84 * r * sin( 118 * PI / 180)).toFloat() + oy
        canvas?.drawText("7", x, y, wPaintBold)
        x = (0.82 * r * cos( 145 * PI / 180)).toFloat() + ox
        y = (0.82 * r * sin( 145 * PI / 180)).toFloat() + oy
        canvas?.drawText("8", x, y, wPaintBold)
        x = (0.8 * r * cos( 174 * PI / 180)).toFloat() + ox
        y = (0.8 * r * sin( 174 * PI / 180)).toFloat() + oy
        canvas?.drawText("9", x, y, wPaintBold)
        x = (0.71 * r * cos( 205 * PI / 180)).toFloat() + ox
        y = (0.71 * r * sin( 205 * PI / 180)).toFloat() + oy
        canvas?.drawText("10", x, y, wPaintBold)
        x = (0.69 * r * cos( 239 * PI / 180)).toFloat() + ox
        y = (0.69 * r * sin( 239 * PI / 180)).toFloat() + oy
        canvas?.drawText("11", x, y, wPaintBold)
        x = (0.69 * r * cos( 270 * PI / 180)).toFloat() + ox
        y = (0.69 * r * sin( 270 * PI / 180)).toFloat() + oy
        canvas?.drawText("12", x, y, wPaintBold)
        x = (0.72 * r * cos( 304 * PI / 180)).toFloat() + ox
        y = (0.72 * r * sin( 304 * PI / 180)).toFloat() + oy
        canvas?.drawText("1", x, y, wPaintBold)
        x = (0.76 * r * cos( 335 * PI / 180)).toFloat() + ox
        y = (0.76 * r * sin( 335 * PI / 180)).toFloat() + oy
        canvas?.drawText("2", x, y, wPaintBold)
    }

    //画时钟指针
    private fun drawClockHand(canvas: Canvas?) {
        //画时针
        var x: Float = ((0.4 * r * cos(hdeg * PI / 180)) + ox).toFloat()
        var y: Float = ((0.4 * r  * sin(hdeg * PI / 180)) + oy).toFloat()
        canvas?.drawLine(ox, oy, x, y, hPaint)
        canvas?.drawCircle(x, y, 6F, hPaint)

        //画分针
        x = ((0.65 * r * cos(mdeg * PI / 180)) + ox).toFloat()
        y = ((0.65 * r * sin(mdeg * PI / 180)) + oy).toFloat()
        canvas?.drawLine(ox, oy, x, y, mPaint)
        canvas?.drawCircle(x, y, 5F, mPaint)

        //画秒针
        x = ((0.8 * r * cos(sdeg * PI / 180)) + ox).toFloat()
        y = ((0.8 * r * sin(sdeg * PI / 180)) + oy).toFloat()
        canvas?.drawLine(ox, oy, x, y, sPaint)
        canvas?.drawCircle(x, y, 3F, sPaint)
        canvas?.drawCircle(ox, oy, 10F, sPaint)
    }

    //画电子时钟
    private fun drawDigitalClock(canvas: Canvas?) {
        var hour = (hdeg / 30 + 3).toInt()
        if(hour > 12) hour -= 12
        var min: Int = (mdeg / 6 + 15).toInt() % 60
        var sec: Int = (sdeg / 6 + 15).toInt() % 60

        //保持时间两位数
        var shour = hour.toString()
        if (hour < 10)  shour = "0" + shour
        var smin = min.toString()
        if (min < 10)  smin = "0" + smin
        var ssec = sec.toString()
        if (sec < 10)  ssec = "0" + ssec
        canvas?.drawText("$shour : $smin : $ssec", 500F, 300F, wPaintBold)
    }

    //每秒刷新各针的角度
    fun setDeg() {
        hdeg += 1.0 / 120
        mdeg += 0.1
        sdeg += 6.0
        if(hdeg >= 360) hdeg -= 360
        if(mdeg >= 360) mdeg -= 360
        if(sdeg >= 360) sdeg -= 360
        this.invalidate()
    }
}