package com.hbkapps.noyoupick.medialist

import com.hbkapps.noyoupick.Constants
import com.hbkapps.noyoupick.repository.TmdbRepository
import javax.inject.Inject

class MediaListPresenter @Inject constructor(private val tmdbRepository: TmdbRepository) {

    fun loadTVOrMoviesList(callListener: TmdbRepository.MoviesListListener) {
        if (tmdbRepository.getChosenMediaType() == Constants.MEDIA_TYPE_MOVIE) {
            tmdbRepository.loadMoviesListFromSelection(callListener, parseGenreSelection())
        } else {
            tmdbRepository.loadTVListFromSelection(callListener, parseGenreSelection())
        }
    }

    fun parseGenreSelection() : String {
        val selectedGenreList = tmdbRepository.getSelectedGenresList()
        return selectedGenreList
            .filter { it.isSelected }
            .joinToString(separator = "|") {
                "${it.id}"
            }
    }

    fun clearMoviesListFromSelection() {
        tmdbRepository.clearMoviesListFromSelection()
    }

    fun getMediaType() : String {
        return when (tmdbRepository.getChosenMediaType()) {
            Constants.MEDIA_TYPE_MOVIE -> "Movie"
            Constants.MEDIA_TYPE_TV -> "TV Show"
            else -> "ERROR"
        }
    }
}