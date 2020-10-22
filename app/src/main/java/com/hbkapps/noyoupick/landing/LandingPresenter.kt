package com.hbkapps.noyoupick.landing

import com.hbkapps.noyoupick.Constants
import com.hbkapps.noyoupick.repository.TmdbRepository
import javax.inject.Inject

class LandingPresenter @Inject constructor(private val tmdbRepository: TmdbRepository) {

    fun loadMoviesList(callListener: TmdbRepository.MoviesListListener) {
        tmdbRepository.getPopularMoviesList(callListener)
    }

    fun onNextButtonClicked(landingInterface: LandingInterface) {
        if (tmdbRepository.getChosenMediaType() != Constants.MEDIA_TYPE_NO_SELECTION) {
            landingInterface.startGenreSelectionActivity()
        }
    }

    fun saveMediaTypeSelectionToRepo(mediaTypeSelection: Int) {
        tmdbRepository.setChosenMediaType(mediaTypeSelection)
    }

    interface LandingInterface {
        fun startGenreSelectionActivity()
    }

}