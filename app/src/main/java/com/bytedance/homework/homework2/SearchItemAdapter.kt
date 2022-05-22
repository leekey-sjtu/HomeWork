package com.bytedance.homework.homework2

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bytedance.homework.R

//为RecyclerView准备一个适配器，继承RecyclerView.Adapter，并将泛型指定为SearchItemAdapter.SearchItemViewHolder
class SearchItemAdapter(private val mContext: Context) : RecyclerView.Adapter<SearchItemAdapter.SearchItemViewHolder>() {

    private val contentList = mutableListOf<String>()
    private val filteredList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val v = View.inflate(parent.context, R.layout.search_item_layout,null)
        val viewHolder = SearchItemViewHolder(v)

        //对contentlist和filterlist分别注册点击事件
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            if(filteredList != null && filteredList.size != 0) {
                val item = filteredList[position]
                Toast.makeText(parent.context, "You clicked $item", Toast.LENGTH_SHORT).show()
                skipDetailActivity(position)
            }
            else {
                val item = contentList[position]
                Toast.makeText(parent.context, "You clicked $item", Toast.LENGTH_SHORT).show()
                skipDetailActivity(position)
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        holder.bind(filteredList[position])
    }

    override fun getItemCount(): Int = filteredList.size

    fun setContentList(list: List<String>) {
        contentList.clear()
        contentList.addAll(list)
        filteredList.clear()
        filteredList.addAll(list)
        notifyDataSetChanged()
    }

    fun setFilter(keyword: String?) {
        filteredList.clear()
        if (keyword?.isNotEmpty() == true) {
            filteredList.addAll(contentList.filter { it.contains(keyword) })
        } else {
            filteredList.addAll(contentList)
        }
        notifyDataSetChanged()
    }

    class SearchItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tv = view.findViewById<TextView>(R.id.search_item_tv)

        fun bind(text: String) {
            tv.text = text
        }
    }

    private fun skipDetailActivity(position : Int) {
        val intent = Intent(mContext, DetailActivity::class.java)
        intent.putExtra("position", position)
        startActivity(mContext, intent, null)
    }

}