package com.bytedance.homework.homework5

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.bytedance.homework.R
import com.bytedance.homework5.DictService
import okhttp3.OkHttpClient
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.toString as toString1

class TranslateActivity : AppCompatActivity() {

    private var et_words: EditText? = null
    private var tv_cancel: TextView? = null
    private var btn_translate: Button? = null
    private var tv_ec: TextView? = null
    private var tv_phone: TextView? = null
    private var tv_translate: TextView? = null
    private var tv_sentence: TextView? = null
    private var retrofit = Retrofit.Builder()
        .baseUrl("https://dict.youdao.com/")
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()  //隐藏该页面的标题栏
        setContentView(R.layout.activity_translate)
        et_words = findViewById(R.id.et_words)
        tv_cancel = findViewById(R.id.tv_cancel)
        tv_cancel?.setOnClickListener {
            et_words?.text = null
        }
        tv_ec = findViewById(R.id.tv_ec)
        tv_phone = findViewById(R.id.tv_phone)
        tv_translate = findViewById(R.id.tv_translate)
        tv_sentence = findViewById(R.id.tv_sentence)
        btn_translate = findViewById(R.id.btn_trans)
        clickTranslate()
        btn_translate?.setOnClickListener {
            clickTranslate()
        }
    }

    private fun clickTranslate() {
        if(et_words?.text?.length != 0) {
            val dictService = retrofit.create(DictService::class.java)
            dictService.getInfo(et_words?.text.toString1()).enqueue(object : Callback<DictBean> {
                override fun onResponse(call: Call<DictBean>, response: Response<DictBean>) {
                    Log.d("dict", "success")
                    val dictBean = response.body()
                    ec(dictBean)
                    translate(dictBean)
                    sentence(dictBean)
                }
                override fun onFailure(call: Call<DictBean>, t: Throwable) {
                    Log.d("dict", "falied")
                }
            })
        }
        else {
            Toast.makeText(this@TranslateActivity, "请输入翻译内容！", Toast.LENGTH_SHORT).show()
        }
    }

    private fun ec(dictBean: DictBean?) {
        val word = dictBean?.ec?.word
        val text = word?.get(0)?.trs?.get(0)?.tr?.get(0)?.l?.i?.get(0)
        tv_ec?.text = text
        tv_phone?.text = "英 /" + word?.get(0)?.ukphone + "/    美 /" + word?.get(0)?.usphone + "/"
    }

    private fun translate(dictBean: DictBean?) {
        val webTranslation = dictBean?.web_trans?.web_translation
        if (webTranslation != null) {
            val trans = webTranslation?.get(0)?.trans
            var text = ""
            for (i in 0 until trans?.size!!) {
                text += trans[i].value
                if (i != trans.size - 1) text += "；"
            }
            tv_translate?.text = text
        }
        else{
            Toast.makeText(this@TranslateActivity, "翻译无结果！", Toast.LENGTH_SHORT).show()
            tv_translate?.text = null
        }
    }

    private fun sentence(dictBean: DictBean?) {
        val sentencePair = dictBean?.blng_sents_part?.sentence_pair
        if(sentencePair != null) {
            var text = ""
            for (i in 0 until sentencePair.size) {
                text += sentencePair[i].sentence + "\n" + sentencePair[i].sentence_translation + "\n"
            }
            tv_sentence?.text = text
        }
        else {
            Toast.makeText(this@TranslateActivity, "例句无结果！", Toast.LENGTH_SHORT).show()
            tv_sentence?.text = null
        }
    }

}