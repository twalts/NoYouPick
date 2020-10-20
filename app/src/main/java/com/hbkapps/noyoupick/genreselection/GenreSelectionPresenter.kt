package com.hbkapps.noyoupick.genreselection

import com.hbkapps.noyoupick.model.GenreItem
import com.hbkapps.noyoupick.repository.TmdbRepository
import javax.inject.Inject

class GenreSelectionPresenter @Inject constructor(private val tmdbRepository: TmdbRepository) {

    //  we may want to use this repo/method to replace the hardcoded names and
    //  IDs we set up in GenreSelectionActivity.setUpGenreList()
    fun loadMoviesList(callListener: TmdbRepository.MoviesListListener) {
        tmdbRepository.getMoviesList(callListener)
    }

    private fun isGenreListEmpty(selectedGenres : ArrayList<GenreItem>) = selectedGenres.isNullOrEmpty()

    fun addOrRemoveGenreItemFromList(genreItem: GenreItem, selectedGenres: ArrayList<GenreItem>)
    : ArrayList<GenreItem> {
        if (genreItem.isSelected) {
            selectedGenres.add(genreItem)
        } else {
            selectedGenres.remove(genreItem)
        }
        return selectedGenres
    }

    fun setSubmitButtonHighlightedOrUnHighlighted(selectedGenres: ArrayList<GenreItem>,
                                                  genreSelectionInterface: GenreSelectionInterface) {
        if (!isGenreListEmpty(selectedGenres)) {
            genreSelectionInterface.setSubmitButtonUnhighlighted()
        } else {
            genreSelectionInterface.setSubmitButtonHighlighted()
        }
    }

    fun checkIfUserCanSubmit(selectedGenres: ArrayList<GenreItem>,
                             genreSelectionInterface: GenreSelectionInterface) {
        if (!isGenreListEmpty(selectedGenres)) {
            genreSelectionInterface.displayInfoToastAfterSubmit()
        }
    }

    interface GenreSelectionInterface {
        fun setSubmitButtonHighlighted()
        fun setSubmitButtonUnhighlighted()
        fun displayInfoToastAfterSubmit()
    }
}



