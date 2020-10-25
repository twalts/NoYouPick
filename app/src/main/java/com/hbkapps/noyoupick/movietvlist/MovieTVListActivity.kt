package com.hbkapps.noyoupick.movietvlist

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.hbkapps.noyoupick.BaseActivity
import com.hbkapps.noyoupick.R
import com.hbkapps.noyoupick.model.Movie
import com.hbkapps.noyoupick.model.TV
import kotlinx.android.synthetic.main.activity_movie_tv_display.*
import javax.inject.Inject

class MovieTVListActivity : BaseActivity() {

    @Inject
    lateinit var presenter : MovieTVListPresenter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_tv_display)
        application.applicationComponent.inject(this)
        presenter.loadMediaListFromRepo(mediaListListener)
    }

    private val mediaListListener: MovieTVListPresenter.MediaListListener = object : MovieTVListPresenter.MediaListListener {
        override fun loadMediaList(movieList: List<Movie>, tvList: List<TV>) {
            val viewAdapter = MovieTVListAdapter(movieList, tvList)
            recyclerView = userSelectedMediaRv.apply {
                setHasFixedSize(true)
                adapter = viewAdapter
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right)
        presenter.clearMoviesListFromSelection()
    }
}