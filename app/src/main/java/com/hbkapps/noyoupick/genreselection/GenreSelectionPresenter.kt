package com.hbkapps.noyoupick.genreselection

import com.hbkapps.noyoupick.Constants
import com.hbkapps.noyoupick.model.GenreItem
import com.hbkapps.noyoupick.repository.TmdbRepository
import javax.inject.Inject

class GenreSelectionPresenter @Inject constructor(private val tmdbRepository: TmdbRepository) {

    fun loadGenreList(callListener: TmdbRepository.GenreListListener)  {
        if (tmdbRepository.getChosenMediaType() == Constants.MEDIA_TYPE_MOVIE) {
            tmdbRepository.loadMovieGenreList(callListener)
        } else {
            tmdbRepository.loadTVGenreList(callListener)
        }
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
            genreSelectionInterface.startMovieTVDisplayActivity()
        }
    }

    fun clearSelectedGenresList() {
        for (i in tmdbRepository.getSelectedGenresList()) {
            i.isSelected = false;
        }
        tmdbRepository.getSelectedGenresList().clear()
    }

    interface GenreSelectionInterface {
        fun setSubmitButtonHighlighted()
        fun setSubmitButtonUnhighlighted()
        fun startMovieTVDisplayActivity()
    }
}



