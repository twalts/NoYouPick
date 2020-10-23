package com.hbkapps.noyoupick.medialist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hbkapps.noyoupick.R
import com.hbkapps.noyoupick.model.TV
import kotlinx.android.synthetic.main.movie_list_recycler_item.view.*

class TVListAdapter (private val tvList: List<TV>) : RecyclerView.Adapter<TVListAdapter.TVViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.movie_list_recycler_item, parent, false)
        return TVViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TVViewHolder, position: Int) {
        val tvShow = tvList[position]
        holder.title.text = tvShow.name
        holder.overview.text = tvShow.overview
    }

    override fun getItemCount(): Int {
        return tvList.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    class TVViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.title
        var overview: TextView = itemView.overview
    }
}