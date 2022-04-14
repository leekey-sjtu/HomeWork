package com.bytedance.homework.homework7

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bytedance.homework.R

class ImageAdapter(val context: Context, private val imgList: MutableList<String>): RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_viewpager_imgview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {  //绑定数据
        Glide.with(context)
            .load(imgList[position])
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
            .placeholder(R.drawable.ic_loading_gif)
            .error(R.drawable.ic_loading_error)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return imgList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {  //ViewHolder就是借助他来做到循环利用itemview
        var imageView: ImageView = itemView.findViewById(R.id.iv_img)
    }

//    private var mOnItemClickListener: OnItemClickListener? = null  //声明一个mItemClickListener接口
//    interface OnItemClickListener {  //创建OnItemClickListener接口
//        fun onItemClick(position: Int)
//    }
//    fun setOnItemClickListener(listener: OnItemClickListener?) {  //添加setOnItemClickListener方法
//        mOnItemClickListener = listener
//    }
}