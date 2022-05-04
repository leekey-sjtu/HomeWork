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
        data.add("1. 正则表达式匹配")
        data.add("2. 合并k个升序链表")
        data.add("3. 买卖股票的最佳时机")
        data.add("4. LeetCoin")
        data.add("5. 校园自行车分配")
        data.add("6. 猜数字")
        data.add("7. 二进制中1的个数")
        data.add("8. 字符串的排列")
        data.add("9. 最小的k个数")
        data.add("10. 最长不含重复字符的子字符串")
        data.add("11. 颠倒二进制位")
        data.add("12. 翻转数位")
        data.add("13. 不含AAA或BBB的字符串")
        data.add("14. 机器人大冒险")
        data.add("15. 游乐园的迷宫")
        data.add("16. 二叉树的最大最大深度")
        data.add("17. 合并链表区间")
        data.add("18. 访问所有节点的最短路径")
        data.add("19. 矩阵中的最常递增路径")
        data.add("20. 删除链表中的节点")
        data.add("21. 两数相加")
        data.add("22. LFU缓存")
        data.add("23. 单词搜索")
        data.add("24. 矩阵对角线遍历")
        data.add("25. 扫雷游戏")

        val adapter = SearchItemAdapter(this)
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

    companion object {

        val detail = listOf<String>(
            "1. 正则表达式匹配\n给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。",
            "2. 合并k个升序链表\n给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字",
            "3. 买卖股票的最佳时机\n给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度",
            "4. LeetCoin\n给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数",
            "5. 校园自行车分配\n给你一个字符串 s，找到 s 中最长的回文子串。",
            "6. 猜数字\n将一个给定字符串 s 根据给定的行数 numRows ，以从上往下、从左到右进行 Z 字形排列",
            "7. 二进制中1的个数\n给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果",
            "8. 字符串的排列\n请你来实现一个 myAtoi(string s) 函数，使其能将字符串转换成一个 32 位有符号整数（类似 C/C++ 中的 atoi 函数）",
            "9. 最小的k个数\n给你一个整数 x ，如果 x 是一个回文整数，返回 true ；否则，返回 false 。",
            "10. 最长不含重复字符的子字符串\n给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。",
            "11. 颠倒二进制位\n给定一个长度为 n 的整数数组height。有n条垂线，第 i 条线的两个端点是(i, 0)和(i, height[i])。找出其中的两条线，使得它们与x轴共同构成的容器可以容纳最多的水。",
            "12. 翻转数位\n罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。",
            "13. 不含AAA或BBB的字符串\n通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV",
            "14. 机器人大冒险\n编写一个函数来查找字符串数组中的最长公共前缀。",
            "15. 游乐园的迷宫\n给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素",
            "16. 二叉树的最大最大深度\n给你一个长度为 n 的整数数组 nums 和 一个目标值 target。",
            "17. 合并链表区间\n给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回",
            "18. 访问所有节点的最短路径\n给你一个由 n 个整数组成的数组 nums ，和一个目标值 target 。",
            "19. 矩阵中的最常递增路径\n给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点",
            "20. 删除链表中的节点\n给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效",
            "21. 两数相加\n将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的",
            "22. LFU缓存\n数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合",
            "23. 单词搜索\n给你一个链表数组，每个链表都已经按升序排列。请你将所有链表合并到一个升序链表中，返回合并后的链表。",
            "24. 矩阵对角线遍历\n给你一个链表，两两交换其中相邻的节点，并返回交换后链表的头节点。",
            "25. 扫雷游戏\n给你链表的头节点 head ，每 k 个节点一组进行翻转，请你返回修改后的链表。"
        )
        

    }
}