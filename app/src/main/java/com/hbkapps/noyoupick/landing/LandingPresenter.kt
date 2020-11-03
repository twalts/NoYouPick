package com.hbkapps.noyoupick.landing

import com.hbkapps.noyoupick.Constants
import com.hbkapps.noyoupick.repository.TmdbRepository
import javax.inject.Inject

class LandingPresenter @Inject constructor(private val tmdbRepository: TmdbRepository) {

    fun onNextButtonClicked(genreListListener: TmdbRepository.GenreListListener) {
        if (tmdbRepository.getChosenMediaType() != Constants.MEDIA_TYPE_NO_SELECTION) {
            tmdbRepository.loadGenreList(genreListListener)
        }
    }

    fun saveMediaTypeSelectionToRepo(mediaTypeSelection: Int) {
        tmdbRepository.setChosenMediaType(mediaTypeSelection)
    }
}

