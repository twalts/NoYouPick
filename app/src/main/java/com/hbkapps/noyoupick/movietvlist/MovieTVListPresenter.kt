package com.hbkapps.noyoupick.movietvlist

import com.hbkapps.noyoupick.Constants
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

    fun getCurMediaItemForTesting (position : Int) : String? {
        return if (tmdbRepository.getChosenMediaType() == Constants.MEDIA_TYPE_MOVIE) {
            tmdbRepository.getMoviesListFromSelection()[position].title
        } else {
            tmdbRepository.getTvListFromSelection()[position].name
        }
    }

    interface MediaListListener {
        fun loadMediaList(movieList: List<Movie>, tvList: List<TV>)
    }
}