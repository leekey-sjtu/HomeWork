package com.bytedance.homework.homework6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bytedance.homework.R

class TodoListAdapter(private val todoList: List<String>): RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_to_do_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {  //绑定数据
        val todo = todoList[position]
        holder.category.text = todo
        if (position == 3) {
            holder.devider.visibility = View.VISIBLE
        }
        holder.itemView.setOnClickListener {
            mOnItemClickListener?.onItemClick(position)  //给每个Item的点击事件设置回调
        }
        holder.itemView.setOnLongClickListener {
            mOnItemLongClickListener?.onItemLongClick(position)  //给每个Item的长按事件设置回调
            return@setOnLongClickListener true  //这里必须要返回Boolean类型
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {  //ViewHolder就是借助他来做到循环利用itemview
        val category: TextView = itemView.findViewById(R.id.category)
        val devider: View = itemView.findViewById(R.id.v_devider)
    }

    private var mOnItemClickListener: OnItemClickListener? = null  //声明一个mItemClickListener接口
    interface OnItemClickListener {  //创建OnItemClickListener接口
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: OnItemClickListener?) {  //添加setOnItemClickListener方法
        mOnItemClickListener = listener
    }

    private var mOnItemLongClickListener: OnItemLongClickListener? = null  //声明接口
    interface OnItemLongClickListener {  //创建OnItemTouchListener接口
        fun onItemLongClick(position: Int)
    }
    fun setOnItemLongClickListener(listener: OnItemLongClickListener?) {  //添加方法
        this.mOnItemLongClickListener = listener
    }
}