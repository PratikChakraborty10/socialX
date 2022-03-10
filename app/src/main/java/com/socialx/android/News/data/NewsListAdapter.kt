package com.socialx.android.News.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.socialx.android.News.NewsActivity
import com.socialx.android.R

class NewsListAdapter(private val listener: NewsActivity): RecyclerView.Adapter<NewsViewHolder>() {

    private var items: ArrayList<PostModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        val viewHolder = NewsViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = items[position]
        holder.titleView.text = currentItem.title
        holder.descView.text = currentItem.desc
        holder.sourceView.text = currentItem.author
        holder.timeView.text = currentItem.publishTime
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.newsImage)
    }

    fun updateNews(updatedNews: ArrayList<PostModel>) {
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class NewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val titleView: TextView = itemView.findViewById(R.id.title_tv)
    val descView: TextView = itemView.findViewById(R.id.desc_tv)
    val sourceView: TextView = itemView.findViewById(R.id.publisher_tv)
    val timeView: TextView = itemView.findViewById(R.id.time_tv)
    val newsImage: ImageView = itemView.findViewById(R.id.news_img)
}