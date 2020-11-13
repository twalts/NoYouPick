package com.hbkapps.noyoupick.genreselection

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
    private var mockGenreList : ArrayList<GenreItem> = ArrayList()
    private val mockGenre = mock(GenreItem::class.java)

    @Before
    fun setUp() {
        //initialize presenter
        presenter = GenreSelectionPresenter(mockRepository)
        //add a mock GenreItem to mockGenreList
        mockGenreList.add(mockGenre)
        //set mockGenreList as the selectedGenreList in presenter
        presenter.setSelectedGenresList(mockGenreList)
    }

    @Test
    fun onSubmitButtonClicked_Calls_getChosenMediaType_If_List_isNotEmptyOrNull() {
        presenter.onSubmitBtnClicked(mockLoadMediaListener)
        verify(mockRepository).getChosenMediaType()
    }

    @Test
    fun onSubmitButtonClicked_Does_Nothing_If_List_isEmpty() {
        mockGenreList.remove(mockGenre)
        presenter.setSelectedGenresList(mockGenreList)
        presenter.onSubmitBtnClicked(mockLoadMediaListener)
        verifyNoInteractions(mockRepository)
    }

    @After
    fun onTestEnd() {
        presenter.clearGenresList()
    }
}