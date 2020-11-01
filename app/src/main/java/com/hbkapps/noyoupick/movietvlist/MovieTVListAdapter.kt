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
import com.hbkapps.noyoupick.model.Media
import com.hbkapps.noyoupick.model.Movie
import com.hbkapps.noyoupick.model.TV
import kotlinx.android.synthetic.main.media_list_cardview_item.view.*

class MovieTVListAdapter(private val mediaList: List<Media>) : RecyclerView.Adapter<MovieTVListAdapter.MovieTvViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTvViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.media_list_cardview_item, parent, false)
        return MovieTvViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieTvViewHolder, position: Int) {
        when (val currMedia = mediaList[position]) {
            is Movie -> holder.setMovieDetails(currMedia)
            is TV -> holder.setTvDetails(currMedia)
        }
    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }


    class MovieTvViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.title
        var overview: TextView = itemView.overview
        private val poster : ImageView = itemView.poster

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