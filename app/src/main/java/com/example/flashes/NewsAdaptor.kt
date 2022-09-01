package com.example.flashes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdaptor(private val listener : NewsItemClicked) : RecyclerView.Adapter<NewsViewHolder>() {

    private val news : ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item_view, parent, false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(news[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = news[position]
        holder.titleView.text = currentItem.title
        holder.author.text = currentItem.source
        holder.newsContent.text = currentItem.description
        holder.timeText.text = currentItem.time

        Glide.with(holder.itemView.context).load(currentItem.urlToImage).into(holder.newsImage)
    }

    override fun getItemCount(): Int {
        return news.size
    }

    fun updateNews(updatedNews : ArrayList<News>)
    {
        news.clear()
        news.addAll(updatedNews)

        notifyDataSetChanged()
    }

}

class NewsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    val titleView : TextView = itemView.findViewById(R.id.headline)
    val newsImage : ImageView = itemView.findViewById(R.id.newsImage)
    val newsContent : TextView = itemView.findViewById(R.id.newsContent)
    val author : TextView = itemView.findViewById(R.id.source)
    val timeText : TextView = itemView.findViewById(R.id.time)
}

interface NewsItemClicked{
    fun onItemClicked(item : News)
}