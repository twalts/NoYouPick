package com.hbkapps.noyoupick.genreselection

import com.hbkapps.noyoupick.Constants
import com.hbkapps.noyoupick.model.GenreItem
import com.hbkapps.noyoupick.repository.TmdbRepository
import javax.inject.Inject

class GenreSelectionPresenter @Inject constructor(private val tmdbRepository: TmdbRepository) {

    private var selectedGenreList: ArrayList<GenreItem> = ArrayList()

    fun loadGenreList(callListener: TmdbRepository.GenreListListener) {
        if (tmdbRepository.getChosenMediaType() == Constants.MEDIA_TYPE_MOVIE) {
            tmdbRepository.loadMovieGenreList(callListener)
        } else {
            tmdbRepository.loadTVGenreList(callListener)
        }
    }

    fun loadTVOrMoviesList(callListener: TmdbRepository.MoviesListListener) {
        if (!selectedGenreList.isNullOrEmpty()) {
            if (tmdbRepository.getChosenMediaType() == Constants.MEDIA_TYPE_MOVIE) {
                tmdbRepository.loadMoviesListFromSelection(callListener, parseGenreSelection())
            } else {
                tmdbRepository.loadTVListFromSelection(callListener, parseGenreSelection())
            }
        }
    }

    private fun parseGenreSelection() : String {
        return selectedGenreList
                .joinToString(separator = "|") {
                    "${it.id}"
                }
    }

    fun addOrRemoveGenreItemFromList(genreItem: GenreItem) {
        if (selectedGenreList.contains(genreItem)) {
            selectedGenreList.remove(genreItem)
        } else {
            selectedGenreList.add(genreItem)
        }
    }

    fun checkIfGenreIsSelected(genreItem: GenreItem): Boolean {
        return selectedGenreList.contains(genreItem)
    }

    fun setSubmitButtonHighlightedOrUnHighlighted(genreSelectionInterface: GenreSelectionInterface) {
        if (!selectedGenreList.isNullOrEmpty()) {
            genreSelectionInterface.setSubmitButtonUnhighlighted()
        } else {
            genreSelectionInterface.setSubmitButtonHighlighted()
        }
    }

    fun clearSelectedGenresList() {
        selectedGenreList.clear()
    }

    fun getSelectedGenresList(): List<GenreItem> {
        return selectedGenreList
    }

    interface GenreSelectionInterface {
        fun setSubmitButtonHighlighted()
        fun setSubmitButtonUnhighlighted()
        fun getSelectedGenres(): List<GenreItem>
    }
}



