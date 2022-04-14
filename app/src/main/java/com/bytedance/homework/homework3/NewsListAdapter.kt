package com.bytedance.homework.homework3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bytedance.homework.R

class NewsListAdapter(val newslist: MutableList<String>) : RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_news_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsTitle = newslist[position]
        holder.newsTitle.text = newsTitle
        holder.itemView.setOnClickListener {
            mOnItemClickListener?.onItemClick(position)  //给每个Item的点击事件设置回调
        }
    }

    override fun getItemCount(): Int {
        return newslist.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {  //ViewHolder就是借助他来做到循环利用itemview
        val newsTitle: TextView = itemView.findViewById(R.id.tv_news_title)
    }

    private var mOnItemClickListener: OnItemClickListener? = null  //声明一个mItemClickListener接口

    interface OnItemClickListener {  //创建OnItemClickListener接口
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {  //添加setOnItemClickListener方法
        mOnItemClickListener = listener
    }
}