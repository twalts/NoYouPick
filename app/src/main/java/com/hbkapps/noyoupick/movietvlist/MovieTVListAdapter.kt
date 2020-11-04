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
import com.hbkapps.noyoupick.model.Cast
import com.hbkapps.noyoupick.model.Crew
import com.hbkapps.noyoupick.model.Media
import com.hbkapps.noyoupick.repository.TmdbRepository
import kotlinx.android.synthetic.main.media_list_cardview_item.view.*

class MovieTVListAdapter(private val mediaList: List<Media>,
                         private val tmdbRepository: TmdbRepository)
    : RecyclerView.Adapter<MovieTVListAdapter.MovieTvViewHolder>() {

    private var context: Context? = null
    private var mediaListExpandedState: BooleanArray = BooleanArray(size = mediaList.size)

    /**Because of the CardStackView library implementation, we cannot use the regular updating methods
     * of RecyclerView.Adapter like notifyDataSetChanged(). So we save the current holder in order to update it after
     * receiving new data. This is reasonable because the user cannot reasonably scroll the view such that
     * the holder would have been recycled and used for a different model**/
    private var currentViewHolder: MovieTvViewHolder? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTvViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.media_list_cardview_item, parent, false)
        return MovieTvViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieTvViewHolder, position: Int) {
        holder.setMediaDetails(mediaList[position])
        holder.setOnClickListener {
            checkExpanded(position, holder)
        }
        if (mediaListExpandedState[position]) {
            holder.expandDetails()
        } else {
            holder.collapseDetails()
        }
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
            val media = mediaList[position]
            if (mediaListExpandedState[position]) {
                holder.collapseDetails()
                mediaListExpandedState[position] = false
            } else {
                if (media.getMediaDirectors() == null && media.getMediaId() != null) {
                    currentViewHolder = holder
                    tmdbRepository.loadCastAndCrewFromMovie(media, loadCastAndCrewListener)
                } else {
                    holder.expandDetails()
                }
                mediaListExpandedState[position] = true
            }
        }
    }


    private val loadCastAndCrewListener : TmdbRepository.LoadCastAndCrewListener = object : TmdbRepository.LoadCastAndCrewListener {
        override fun onCastAndCrewLoaded(media: Media, castList: List<Cast>?, crewList: List<Crew>?) {
            media.crewList = crewList
            media.castList = castList
            currentViewHolder?.director?.text = media.getMediaDirectors()
            currentViewHolder?.expandDetails()
        }

        override fun onFailure(media: Media) {
            media.crewList = emptyList()
            media.castList = emptyList()
            currentViewHolder?.director?.text = media.getMediaDirectors()
            currentViewHolder?.expandDetails()
        }

    }

    class MovieTvViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var title: TextView = itemView.title
        private var overview: TextView = itemView.overview
        private val poster: ImageView = itemView.poster
        private val backdrop: ImageView = itemView.backdrop
        val director: TextView = itemView.director

        private var constraintLayout: ConstraintLayout = itemView.findViewById(R.id.root)
        private val collapsedConstraintSet: ConstraintSet = ConstraintSet()
        private val expandedConstraintSet: ConstraintSet = ConstraintSet()

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
            director.text = media.getMediaDirectors()
        }

        fun collapseDetails() {
            TransitionManager.beginDelayedTransition(constraintLayout)
            collapsedConstraintSet.applyTo(constraintLayout)
            overview.maxLines = 4
        }

        fun expandDetails() {
            TransitionManager.beginDelayedTransition(constraintLayout)
            expandedConstraintSet.applyTo(constraintLayout)
            overview.maxLines = 20
        }

        fun setOnClickListener(onClickListener: View.OnClickListener) {
            constraintLayout.setOnClickListener(onClickListener)
        }
    }

}