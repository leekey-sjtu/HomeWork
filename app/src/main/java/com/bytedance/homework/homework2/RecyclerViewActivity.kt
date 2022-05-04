package com.bytedance.homework.homework2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bytedance.homework.R

class RecyclerViewActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        val tv = findViewById<TextView>(R.id.tv_cancel)
        tv.setOnClickListener(this)

        val rv = findViewById<RecyclerView>(R.id.recycler_view)
        rv.layoutManager = LinearLayoutManager(this)

        val data = mutableListOf<String>()
        data.add("1.正则表达式匹配")
        data.add("2.合并k个升序链表")
        data.add("3.买卖股票的最佳时机")
        data.add("4.LeetCoin")
        data.add("5.校园自行车分配")
        data.add("6.猜数字")
        data.add("7.二进制中1的个数")
        data.add("8.字符串的排列")
        data.add("9.最小的k个数")
        data.add("10.最长不含重复字符的子字符串")
        data.add("11.颠倒二进制位")
        data.add("12.翻转数位")
        data.add("13.不含AAA或BBB的字符串")
        data.add("14.机器人大冒险")
        data.add("15.游乐园的迷宫")
        data.add("16.二叉树的最大最大深度")
        data.add("17.合并链表区间")
        data.add("18.访问所有节点的最短路径")
        data.add("19.矩阵中的最常递增路径")
        data.add("20.删除链表中的节点")
        data.add("21.两数相加")
        data.add("22.LFU缓存")
        data.add("23.单词搜索")
        data.add("24.矩阵对角线遍历")
        data.add("25.扫雷游戏")

        val adapter = SearchItemAdapter()
        adapter.setContentList(data)
        rv.adapter = adapter

        findViewById<EditText>(R.id.words_et).addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                adapter.setFilter(p0.toString())
            }
        })
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.tv_cancel -> {
                val et = findViewById<EditText>(R.id.words_et)
                et.setText("")
                Toast.makeText(this, "已清空", Toast.LENGTH_SHORT).show()
            }
        }
    }
}