package com.bytedance.homework.homework6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bytedance.homework.R

class TaskListAdapter(private val taskList: List<String>): RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_task_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {    //绑定数据
        val todo = taskList[position]
        holder.task.text = todo
        holder.itemView.setOnClickListener {
            mOnItemClickListener?.onItemClick(holder)  //给每个Item的点击事件设置回调
        }
        holder.itemView.setOnLongClickListener {
            mOnItemLongClickListener?.onItemLongClick(holder)
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return taskList.size  //返回数量
    }

    //借助ViewHolder来循环利用itemView
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val task: TextView = itemView.findViewById(R.id.task)
    }


    private var mOnItemClickListener: OnItemClickListener? = null  //声明一个mItemClickListener接口
    interface OnItemClickListener {  //创建OnItemClickListener接口
        fun onItemClick(holder: ViewHolder)
    }
    fun setOnItemClickListener(listener: OnItemClickListener?) {  //添加setOnItemClickListener方法
        mOnItemClickListener = listener
    }

    private var mOnItemLongClickListener: OnItemLongClickListener? = null  //声明接口
    interface OnItemLongClickListener {  //创建OnItemTouchListener接口
        fun onItemLongClick(holder: ViewHolder)
    }
    fun setOnItemLongClickListener(listener: OnItemLongClickListener?) {  //添加方法
        this.mOnItemLongClickListener = listener
    }
}