package com.hbkapps.noyoupick.landing

import com.hbkapps.noyoupick.repository.TmdbRepository
import javax.inject.Inject

class LandingPresenter @Inject constructor(private val tmdbRepository: TmdbRepository) {

    fun loadMoviesList(callListener: TmdbRepository.MoviesListListener) {
        tmdbRepository.getMoviesList(callListener)
    }

}