package com.socialx.android.SearchNews.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.socialx.android.R
import com.socialx.android.SearchNews.data.News

class MyAdapter(private val newList : ArrayList<News>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val titleImage : ImageView = itemView.findViewById(R.id.news_img)
        val tvHeading : TextView = itemView.findViewById(R.id.title_tv)
        val tvDesc : TextView = itemView.findViewById(R.id.desc_tv)
        val tvAuthor : TextView = itemView.findViewById(R.id.publisher_tv)
        val tvTime : TextView = itemView.findViewById(R.id.time_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = newList[position]
        holder.titleImage.setImageResource(currentItem.titleImage)
        holder.tvHeading.text = currentItem.heading
        holder.tvDesc.text = currentItem.description
        holder.tvAuthor.text = currentItem.author
        holder.tvTime.text = currentItem.time
    }

    override fun getItemCount(): Int {
        return newList.size
    }
}