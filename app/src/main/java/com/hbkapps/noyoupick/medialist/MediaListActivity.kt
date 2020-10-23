package com.hbkapps.noyoupick.medialist

import android.os.Bundle
import android.widget.Toast
import com.hbkapps.noyoupick.BaseActivity
import com.hbkapps.noyoupick.R
import com.hbkapps.noyoupick.model.Movie
import com.hbkapps.noyoupick.model.TV
import com.hbkapps.noyoupick.repository.TmdbRepository
import kotlinx.android.synthetic.main.activity_movie_tv_display.*
import javax.inject.Inject

class MediaListActivity : BaseActivity() {

    @Inject
    lateinit var presenter : MediaListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_tv_display)
        application.applicationComponent.inject(this)
        presenter.loadTVOrMoviesList(moviesListListener)

        Toast.makeText(this,
            "Media type is ${presenter.getMediaType()} " +
                    "and selected genre IDs are ${presenter.parseGenreSelection()}",
            Toast.LENGTH_SHORT).show()
    }

    private val moviesListListener: TmdbRepository.MoviesListListener = object : TmdbRepository.MoviesListListener {

        override fun loadMovieList(movieList: List<Movie>) {
            userSelectedMediaRv.adapter = MoviesListAdapter(movieList)
        }

        override fun loadTVList(tvList: List<TV>) {
            userSelectedMediaRv.adapter = TVListAdapter(tvList)
        }

        override fun onFailure() {
            //todo
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right)
        presenter.clearMoviesListFromSelection()
    }
}