package com.hbkapps.noyoupick.movietvlist

import com.hbkapps.noyoupick.model.Media
import com.hbkapps.noyoupick.repository.TmdbRepository
import timber.log.Timber
import javax.inject.Inject

class MovieTVListPresenter @Inject constructor(private val tmdbRepository: TmdbRepository) {

    fun loadMediaListFromRepo(mediaListListener: MediaListListener) {
        mediaListListener.loadMediaList(tmdbRepository.getMediaListFromSelection())
    }

    fun getMediaList(): List<Media> {
        return tmdbRepository.getMediaListFromSelection()
    }

    fun clearMoviesListFromSelection() {
        tmdbRepository.clearMediaListFromSelection()
    }

    fun loadCastAndCrewList(id : String, callListener : TmdbRepository.LoadCastAndCrewListener) {
        tmdbRepository.loadCastAndCrewFromMovie(id, callListener)
    }

    fun parseDirectors() : String {
        val crewList = tmdbRepository.getCrewList()
        return if (crewList != null) {
            val directors = StringBuilder()
            for (crew in crewList) {
                if (crew.job == "Director") {
                    crew.name?.let { directors.append("$it\n") }
                }
            }
            Timber.e("$directors")
            directors.toString()
        } else ""
    }

    interface MediaListListener {
        fun loadMediaList(mediaList: List<Media>)
    }
}