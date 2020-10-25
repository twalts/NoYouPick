package com.hbkapps.noyoupick.movietvlist

import com.hbkapps.noyoupick.model.Movie
import com.hbkapps.noyoupick.model.TV
import com.hbkapps.noyoupick.repository.TmdbRepository
import javax.inject.Inject

class MovieTVListPresenter @Inject constructor(private val tmdbRepository: TmdbRepository) {

    fun loadMediaListFromRepo(mediaListListener: MediaListListener) {
        mediaListListener.loadMediaList(tmdbRepository.getMoviesListFromSelection(), tmdbRepository.getTvListFromSelection())
    }

    fun clearMoviesListFromSelection() {
        tmdbRepository.clearMoviesListFromSelection()
    }

    interface MediaListListener {
        fun loadMediaList(movieList: List<Movie>, tvList: List<TV>)
    }
}