package com.hbkapps.noyoupick.genreselection

import com.hbkapps.noyoupick.Constants
import com.hbkapps.noyoupick.model.GenreItem
import com.hbkapps.noyoupick.repository.TmdbRepository
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class GenreSelectionPresenterTest {

    private val mockRepository = mock(TmdbRepository::class.java)
    private val mockLoadMediaListener = mock(TmdbRepository.LoadMediaListener::class.java)
    private lateinit var presenter: GenreSelectionPresenter
    private val mockGenre = mock(GenreItem::class.java)

    @Before
    fun setUp() {
        presenter = GenreSelectionPresenter(mockRepository)
        presenter.addOrRemoveGenreItemFromList(mockGenre)
    }

    @Test
    fun onSubmitButtonClicked_Calls_getChosenMediaType_If_List_isNotEmptyOrNull() {
        `when`(mockRepository.getChosenMediaType()).thenReturn(Constants.MEDIA_TYPE_MOVIE)
        presenter.onSubmitBtnClicked(mockLoadMediaListener)
        verify(mockRepository).loadMoviesListFromSelection(mockLoadMediaListener, "0")
    }

    @Test
    fun onSubmitButtonClicked_Does_Nothing_If_List_isEmpty() {
        presenter.addOrRemoveGenreItemFromList(mockGenre)
        presenter.onSubmitBtnClicked(mockLoadMediaListener)
        verifyNoInteractions(mockRepository)
    }

    @After
    fun onTestEnd() {
        presenter.clearGenresList()
    }
}