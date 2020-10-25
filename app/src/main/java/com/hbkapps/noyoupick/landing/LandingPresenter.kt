package com.hbkapps.noyoupick.landing

import com.hbkapps.noyoupick.repository.TmdbRepository
import javax.inject.Inject

class LandingPresenter @Inject constructor(private val tmdbRepository: TmdbRepository) {

    fun onNextButtonClicked(genreListListener: TmdbRepository.GenreListListener) {
        tmdbRepository.loadGenreList(genreListListener)
    }

    fun saveMediaTypeSelectionToRepo(mediaTypeSelection: Int) {
        tmdbRepository.setChosenMediaType(mediaTypeSelection)
    }

}