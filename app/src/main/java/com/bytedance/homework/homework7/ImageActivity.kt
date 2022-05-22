package com.bytedance.homework.homework7

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bytedance.homework.R

class ImageActivity : AppCompatActivity() {

    private val imgViewPager2: ViewPager2 by lazy { findViewById(R.id.vp_img) }
    private val imgList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "照片查看"
        setContentView(R.layout.activity_image)
        initImgList()
        imgViewPager2.adapter = ImageAdapter(this, imgList)
        navigateDots(0)  //初始化底部图片导航栏
        imgViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            private var isScrolling = false
            private var currentPosition: Int = 0

            override fun onPageSelected(position: Int) {
                currentPosition = position
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                Log.d("wdw","state = $state")
                if (state == 1) {
                    isScrolling = false
                } else if (state == 2) {
                    isScrolling = true
                } else {
                    if (!isScrolling) {
                        if (currentPosition == 0) {
                            imgViewPager2.currentItem = 7
                        } else {
                            imgViewPager2.currentItem = 0
                        }
                    }
                }
                navigateDots(currentPosition)  //实现底部图片导航栏
            }
        })
    }

    private fun initImgList() : MutableList<String> {
        imgList.add("https://t7.baidu.com/it/u=2621658848,3952322712&fm=193&f=GIF")
        imgList.add("https://tse1-mm.cn.bing.net/th/id/R-C.4290fa077be9db31cf8ed2c5d533f97f?rik=WisWOoIVR5kt9Q&riu=http%3a%2f%2fimg.pconline.com.cn%2fimages%2fupload%2fupc%2ftx%2fphotoblog%2f1305%2f23%2fc9%2f21233806_21233806_1369310564359.jpg&ehk=8GbI6Fdh%2fMCcOIlI7s373Ewm7MKjjEVpFzxfd5W6kvE%3d&risl=&pid=ImgRaw&r=0")
        imgList.add("https://t7.baidu.com/it/u=1856946436,1599379154&fm=193&f=GIF")
        imgList.add("https://bpic.588ku.com/back_origin_min_pic/21/05/13/a942d69d2fc94134bd8eda58f1ea4413.jpg!/fw/750/quality/99/unsharp/true/compress/true")
        imgList.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2Fb6fd9d520826f523488949a42f5f7b35a87e63724f3a4-W31Fzy_fw658&refer=http%3A%2F%2Fhbimg.b0.upaiyun.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1652259537&t=491c873116a6f420d8378b764d29577d")
        imgList.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg95.699pic.com%2Fphoto%2F40142%2F4204.gif_wh860.gif&refer=http%3A%2F%2Fimg95.699pic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1652259537&t=f5f6200bcd870e37d799d98d93b80526")
        imgList.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fhbimg.huabanimg.com%2F8ad1b7265df8a1cdb3068bc942376ec8a8037d0fce6e1-cBistp_fw658&refer=http%3A%2F%2Fhbimg.huabanimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1652259654&t=a01194fd31f99b3de24bc900d2ed4fcf")
        imgList.add("https://pic.netbian.com/uploads/allimg/220308/005128-16466718880e3b.jpg")
        return imgList
    }

    private fun navigateDots(position: Int) {
        when (position) {
            0 -> {
                findViewById<TextView>(R.id.dot_0).setBackgroundResource(R.drawable.style_round_1)
                findViewById<TextView>(R.id.dot_1).setBackgroundResource(R.drawable.style_round)
                findViewById<TextView>(R.id.dot_7).setBackgroundResource(R.drawable.style_round)
            }
            1 -> {
                findViewById<TextView>(R.id.dot_1).setBackgroundResource(R.drawable.style_round_1)
                findViewById<TextView>(R.id.dot_0).setBackgroundResource(R.drawable.style_round)
                findViewById<TextView>(R.id.dot_2).setBackgroundResource(R.drawable.style_round)
            }
            2 -> {
                findViewById<TextView>(R.id.dot_2).setBackgroundResource(R.drawable.style_round_1)
                findViewById<TextView>(R.id.dot_1).setBackgroundResource(R.drawable.style_round)
                findViewById<TextView>(R.id.dot_3).setBackgroundResource(R.drawable.style_round)
            }
            3 -> {
                findViewById<TextView>(R.id.dot_3).setBackgroundResource(R.drawable.style_round_1)
                findViewById<TextView>(R.id.dot_2).setBackgroundResource(R.drawable.style_round)
                findViewById<TextView>(R.id.dot_4).setBackgroundResource(R.drawable.style_round)
            }
            4 -> {
                findViewById<TextView>(R.id.dot_4).setBackgroundResource(R.drawable.style_round_1)
                findViewById<TextView>(R.id.dot_3).setBackgroundResource(R.drawable.style_round)
                findViewById<TextView>(R.id.dot_5).setBackgroundResource(R.drawable.style_round)
            }
            5 -> {
                findViewById<TextView>(R.id.dot_5).setBackgroundResource(R.drawable.style_round_1)
                findViewById<TextView>(R.id.dot_4).setBackgroundResource(R.drawable.style_round)
                findViewById<TextView>(R.id.dot_6).setBackgroundResource(R.drawable.style_round)
            }
            6 -> {
                findViewById<TextView>(R.id.dot_6).setBackgroundResource(R.drawable.style_round_1)
                findViewById<TextView>(R.id.dot_5).setBackgroundResource(R.drawable.style_round)
                findViewById<TextView>(R.id.dot_7).setBackgroundResource(R.drawable.style_round)
            }
            7 -> {
                findViewById<TextView>(R.id.dot_7).setBackgroundResource(R.drawable.style_round_1)
                findViewById<TextView>(R.id.dot_6).setBackgroundResource(R.drawable.style_round)
                findViewById<TextView>(R.id.dot_0).setBackgroundResource(R.drawable.style_round)
            }
        }
    }
}