package com.bytedance.homework.homework3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bytedance.homework.R

class NewsContentFragment(private val title: String, val content: String, val color: Int) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_news_content, container, false)
        view.findViewById<TextView>(R.id.tv_news_title).text = title
        view.findViewById<TextView>(R.id.tv_news_content).text = content
        view.findViewById<LinearLayout>(R.id.ll_fragment).setBackgroundColor(color)
        return view
    }
}