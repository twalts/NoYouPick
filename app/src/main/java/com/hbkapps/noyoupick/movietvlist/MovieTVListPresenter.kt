package com.hbkapps.noyoupick.movietvlist

import com.hbkapps.noyoupick.Constants
import com.hbkapps.noyoupick.repository.TmdbRepository
import javax.inject.Inject

class MovieTVListPresenter @Inject constructor(private val tmdbRepository: TmdbRepository) {

    fun clearMoviesListFromSelection() {
        tmdbRepository.clearMoviesListFromSelection()
    }

    fun getTVOrMovieListFromRepo(moviesListListener: TmdbRepository.MoviesListListener) {
        if (tmdbRepository.getChosenMediaType() == Constants.MEDIA_TYPE_MOVIE) {
            moviesListListener.loadMovieList(tmdbRepository.getMoviesListFromSelection())
        } else {
            moviesListListener.loadTVList(tmdbRepository.getTvListFromSelection())
        }
    }
}