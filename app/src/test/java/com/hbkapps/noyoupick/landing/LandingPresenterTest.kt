package com.hbkapps.noyoupick.landing

import com.hbkapps.noyoupick.Constants
import com.hbkapps.noyoupick.repository.TmdbRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class LandingPresenterTest {

    private val mockRepository = mock(TmdbRepository::class.java)
    private val mockGenreListListener = mock(TmdbRepository.GenreListListener::class.java)
    private lateinit var presenter : LandingPresenter

    @Before
    fun setUp() {
        presenter = LandingPresenter(mockRepository)
    }

    @Test
    fun onNextButtonClicked_loadsGenreList_When_Option_isSelected() {
        `when`(mockRepository.getChosenMediaType()).thenReturn(Constants.MEDIA_TYPE_MOVIE)
        presenter.onNextButtonClicked(mockGenreListListener)
        verify(mockRepository).loadGenreList(mockGenreListListener)
    }
}