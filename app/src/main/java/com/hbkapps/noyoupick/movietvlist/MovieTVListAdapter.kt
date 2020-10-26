package com.hbkapps.noyoupick.movietvlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hbkapps.noyoupick.R
import com.hbkapps.noyoupick.model.Movie
import com.hbkapps.noyoupick.model.TV
import kotlinx.android.synthetic.main.movie_list_recycler_item.view.*

class MovieTVListAdapter(private val moviesList: List<Movie>, private val tvList: List<TV>) : RecyclerView.Adapter<MovieTVListAdapter.MovieTvViewHolder>() {

    private var context: Context? = null

    companion object {
        const val TYPE_MOVIE = 1
        const val TYPE_TV = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTvViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.movie_list_recycler_item, parent, false)
        return MovieTvViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieTvViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_MOVIE) {
            holder.setMovieDetails(moviesList[position])
        } else {
            holder.setTvDetails(tvList[position])
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size + tvList.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun getItemViewType(position: Int): Int {
        return if (position > moviesList.size - 1) {
            TYPE_TV
        } else {
            TYPE_MOVIE
        }
    }

    class MovieTvViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.title
        var overview: TextView = itemView.overview
        val poster : ImageView = itemView.poster

        fun setMovieDetails(movie: Movie) {
            title.text = movie.title
            overview.text = movie.overview
            Glide.with(itemView)
                    .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
                    .into(poster)
        }

        fun setTvDetails(tv: TV) {
            title.text = tv.name
            overview.text = tv.overview
            Glide.with(itemView)
                    .load("https://image.tmdb.org/t/p/w342${tv.posterPath}")
                    .into(poster)
        }
    }
}