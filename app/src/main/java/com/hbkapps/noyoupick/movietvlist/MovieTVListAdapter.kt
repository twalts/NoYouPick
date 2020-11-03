package com.hbkapps.noyoupick.movietvlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.bumptech.glide.Glide
import com.hbkapps.noyoupick.R
import com.hbkapps.noyoupick.model.Media
import kotlinx.android.synthetic.main.media_list_cardview_item.view.*

class MovieTVListAdapter(private val mediaList: List<Media>,
                         private val mediaCardListener: MediaCardListener)
    : RecyclerView.Adapter<MovieTVListAdapter.MovieTvViewHolder>() {

    private var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTvViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.media_list_cardview_item, parent, false)
        return MovieTvViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieTvViewHolder, position: Int) {
        holder.setMediaDetails(mediaList[position])

        holder.constraintLayout.setOnClickListener {
            checkExpanded(position, holder)
        }
        checkExpanded(position, holder)
    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    private fun checkExpanded(position: Int, holder: MovieTvViewHolder) {
        if (holder.adapterPosition == position) {
            if (!mediaList[position].isExpanded) {
                TransitionManager.beginDelayedTransition(holder.constraintLayout)
                holder.collapsedConstraintSet.applyTo(holder.constraintLayout)

                holder.director.text = mediaCardListener.getDirectors()
                holder.overview.maxLines = 4

                mediaList[position].isExpanded = true
            } else {
                TransitionManager.beginDelayedTransition(holder.constraintLayout)
                holder.expandedConstraintSet.applyTo(holder.constraintLayout)
                holder.overview.maxLines = 20
                holder.director.text = mediaCardListener.getDirectors()

                mediaList[position].isExpanded = false
                mediaCardListener.onCardExpanded()
            }
        }
    }

    class MovieTvViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.title
        var overview: TextView = itemView.overview
        private val poster : ImageView = itemView.poster
        private val backdrop : ImageView = itemView.backdrop
        var director : TextView = itemView.director

        var constraintLayout : ConstraintLayout = itemView.findViewById(R.id.root)
        val collapsedConstraintSet: ConstraintSet = ConstraintSet()
        val expandedConstraintSet: ConstraintSet = ConstraintSet()

        init {
            collapsedConstraintSet.clone(constraintLayout)
            expandedConstraintSet.clone(constraintLayout.context, R.layout.media_list_cardview_item_expanded)
        }

        fun setMediaDetails(media: Media) {
            title.text = media.getMediaTitle()
            overview.text = media.getMediaOverview()
            Glide.with(itemView)
                    .load("https://image.tmdb.org/t/p/w342${media.getMediaPosterPath()}")
                    .into(poster)
            Glide.with(itemView)
                    .load("https://image.tmdb.org/t/p/w342${media.getMediaBackdropPath()}")
                    .into(backdrop)
        }

    }

    interface MediaCardListener {
        fun onCardExpanded()
        fun getDirectors() : String
    }
}