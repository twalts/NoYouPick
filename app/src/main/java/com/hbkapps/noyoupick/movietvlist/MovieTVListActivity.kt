package com.hbkapps.noyoupick.movietvlist

import android.os.Bundle
import com.hbkapps.noyoupick.BaseActivity
import com.hbkapps.noyoupick.R
import com.hbkapps.noyoupick.model.Movie
import com.hbkapps.noyoupick.model.TV
import com.hbkapps.noyoupick.repository.TmdbRepository
import kotlinx.android.synthetic.main.activity_movie_tv_display.*
import javax.inject.Inject

class MovieTVListActivity : BaseActivity() {

    @Inject
    lateinit var presenter : MovieTVListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_tv_display)
        application.applicationComponent.inject(this)
        presenter.getTVOrMovieListFromRepo(moviesListListener)
    }

    private val moviesListListener : TmdbRepository.MoviesListListener = object : TmdbRepository.MoviesListListener {
        override fun loadMovieList(movieList: List<Movie>) {
            val emptyTvList = listOf<TV>()
            userSelectedMediaRv.adapter = MovieTVListAdapter(movieList, emptyTvList)
        }

        override fun loadTVList(tvList: List<TV>) {
            val emptyMovieList = listOf<Movie>()
            userSelectedMediaRv.adapter = MovieTVListAdapter(emptyMovieList, tvList)
        }

        override fun onFailure() {
            //todo
        }

        override fun startMovieTVListActivity() {}

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right)
        presenter.clearMoviesListFromSelection()
    }
}