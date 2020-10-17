package com.hbkapps.noyoupick.movieslist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hbkapps.noyoupick.R
import com.hbkapps.noyoupick.model.Movie
import com.hbkapps.noyoupick.movieslist.MoviesListAdapter.MovieViewHolder
import kotlinx.android.synthetic.main.movie_list_recycler_item.view.*

class MoviesListAdapter(private val moviesList: List<Movie>) : RecyclerView.Adapter<MovieViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.movie_list_recycler_item, parent, false)
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = moviesList[position]
        holder.title.text = movie.title
        holder.overview.text = movie.overview
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    class MovieViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.title
        var overview: TextView = itemView.overview
    }
}