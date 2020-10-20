package com.hbkapps.noyoupick.genreselection

import com.hbkapps.noyoupick.Constants
import com.hbkapps.noyoupick.model.GenreItem
import com.hbkapps.noyoupick.repository.TmdbRepository
import javax.inject.Inject

class GenreSelectionPresenter @Inject constructor(private val tmdbRepository: TmdbRepository) {

    private var selectedGenres: ArrayList<GenreItem> = ArrayList()

    //  we may want to use this repo/method to replace the hardcoded names and
    //  IDs we set up in GenreSelectionActivity.setUpGenreList()
    fun loadMoviesList(callListener: TmdbRepository.MoviesListListener) {
        tmdbRepository.getMoviesList(callListener)
    }

    fun addOrRemoveGenreItemFromList(genreItem: GenreItem) {
        if (genreItem.isSelected) {
            selectedGenres.add(genreItem)
        } else {
            selectedGenres.remove(genreItem)
        }
    }

    fun setSubmitButtonHighlightedOrUnHighlighted(genreSelectionInterface: GenreSelectionInterface) {
        if (!selectedGenres.isNullOrEmpty()) {
            genreSelectionInterface.setSubmitButtonUnhighlighted()
        } else {
            genreSelectionInterface.setSubmitButtonHighlighted()
        }
    }

    fun onSubmitButtonClicked(genreSelectionInterface: GenreSelectionInterface,
                              mediaType: Int) {
        if (!selectedGenres.isNullOrEmpty()) {
            val mediaTypeName = when (mediaType) {
                Constants.MEDIA_TYPE_MOVIE -> "Movie"
                Constants.MEDIA_TYPE_TV -> "TV Show"
                Constants.MEDIA_TYPE_BOTH -> "Both"
                else -> "ERROR"
            }
            genreSelectionInterface.displayInfoToastAfterSubmit(mediaTypeName, selectedGenres)
            //passing the selectedGenreList back to Activity temporarily while we still use Toast
        }
    }

    fun getMediaTypeFromRepo() : Int {
        return tmdbRepository.getChosenMediaType()
    }

    fun clearSelectedGenresList() {
        selectedGenres.clear()
    }

    interface GenreSelectionInterface {
        fun setSubmitButtonHighlighted()
        fun setSubmitButtonUnhighlighted()
        fun displayInfoToastAfterSubmit(mediaTypeName : String, selectedGenres: ArrayList<GenreItem>)
    }
}



