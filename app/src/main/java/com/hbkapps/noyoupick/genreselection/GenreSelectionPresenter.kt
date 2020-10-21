package com.hbkapps.noyoupick.genreselection

import com.hbkapps.noyoupick.Constants
import com.hbkapps.noyoupick.model.GenreItem
import com.hbkapps.noyoupick.repository.TmdbRepository
import javax.inject.Inject

class GenreSelectionPresenter @Inject constructor(private val tmdbRepository: TmdbRepository) {

    //  we may want to use this repo/method to replace the hardcoded names and
    //  IDs we set up in GenreSelectionActivity.setUpGenreList()
    fun loadMoviesList(callListener: TmdbRepository.MoviesListListener) {
        tmdbRepository.getPopularMoviesList(callListener)
    }

    fun addOrRemoveGenreItemFromList(genreItem: GenreItem) {
        if (genreItem.isSelected) {
            tmdbRepository.getSelectedGenresList().add(genreItem)
        } else {
            tmdbRepository.getSelectedGenresList().remove(genreItem)
        }
    }

    fun setSubmitButtonHighlightedOrUnHighlighted(genreSelectionInterface: GenreSelectionInterface) {
        if (!tmdbRepository.getSelectedGenresList().isNullOrEmpty()) {
            genreSelectionInterface.setSubmitButtonUnhighlighted()
        } else {
            genreSelectionInterface.setSubmitButtonHighlighted()
        }
    }

    fun onSubmitButtonClicked(genreSelectionInterface: GenreSelectionInterface) {
        if (!tmdbRepository.getSelectedGenresList().isNullOrEmpty()) {
            val mediaTypeName = when (tmdbRepository.getChosenMediaType()) {
                Constants.MEDIA_TYPE_MOVIE -> "Movie"
                Constants.MEDIA_TYPE_TV -> "TV Show"
                Constants.MEDIA_TYPE_BOTH -> "Both"
                else -> "ERROR"
            }
            genreSelectionInterface.startMovieTVDisplayActivity()
        }
    }

    fun clearSelectedGenresList() {
        tmdbRepository.getSelectedGenresList().clear()
    }

    interface GenreSelectionInterface {
        fun setSubmitButtonHighlighted()
        fun setSubmitButtonUnhighlighted()
        fun startMovieTVDisplayActivity()
    }
}



