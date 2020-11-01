package com.hbkapps.noyoupick.movietvlist

import com.hbkapps.noyoupick.model.Media
import com.hbkapps.noyoupick.repository.TmdbRepository
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

    interface MediaListListener {
        fun loadMediaList(mediaList: List<Media>)
    }
}