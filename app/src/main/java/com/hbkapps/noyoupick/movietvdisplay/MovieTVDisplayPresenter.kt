package com.hbkapps.noyoupick.movietvdisplay

import com.hbkapps.noyoupick.Constants
import com.hbkapps.noyoupick.model.GenreItem
import com.hbkapps.noyoupick.repository.TmdbRepository
import javax.inject.Inject

class MovieTVDisplayPresenter @Inject constructor(private val tmdbRepository: TmdbRepository) {

    fun loadMoviesList(callListener: TmdbRepository.MoviesListListener) {
        tmdbRepository.getMoviesListFromSelection(callListener, parseGenreSelection())
    }

    fun parseGenreSelection() : String {
        val selectedGenreList = tmdbRepository.getSelectedGenresList()
        return selectedGenreList
            .filter { it.isSelected }
            .joinToString(separator = "|") {
                "${it.id}"
            }
    }

    fun clearMoviesList() {
        tmdbRepository.clearMoviesList()
    }

    fun getMediaType() : String {
        return when (tmdbRepository.getChosenMediaType()) {
            Constants.MEDIA_TYPE_MOVIE -> "Movie"
            Constants.MEDIA_TYPE_TV -> "TV Show"
            Constants.MEDIA_TYPE_BOTH -> "Both"
            else -> "ERROR"
        }
    }
}