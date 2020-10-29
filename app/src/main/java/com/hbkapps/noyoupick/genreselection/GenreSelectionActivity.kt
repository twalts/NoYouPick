package com.hbkapps.noyoupick.genreselection

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hbkapps.noyoupick.BaseActivity
import com.hbkapps.noyoupick.R
import com.hbkapps.noyoupick.model.GenreItem
import com.hbkapps.noyoupick.model.Movie
import com.hbkapps.noyoupick.model.TV
import com.hbkapps.noyoupick.movietvlist.MovieTVListActivity
import com.hbkapps.noyoupick.repository.TmdbRepository
import kotlinx.android.synthetic.main.activity_genre_selection.*
import javax.inject.Inject


class GenreSelectionActivity : BaseActivity() {

    @Inject
    lateinit var presenter : GenreSelectionPresenter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressDialog : Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre_selection)
        application.applicationComponent.inject(this)

        setUpRecyclerView(presenter.getGenreList())
        btnSubmitGenreChoice.setOnClickListener {
            presenter.onSubmitBtnClicked(loadMediaListener)
        }
    }

    private val loadMediaListener : TmdbRepository.LoadMediaListener = object : TmdbRepository.LoadMediaListener {
        override fun onMovieListLoaded(movieList: List<Movie>) {
            val intent = Intent(this@GenreSelectionActivity, MovieTVListActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left)
        }

        override fun onTvListLoaded(tvList: List<TV>) {
            val intent = Intent(this@GenreSelectionActivity, MovieTVListActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left)
        }

        override fun onFailure() {
            //todo
        }

        override fun showProgressBar() {
            progressDialog = Dialog(this@GenreSelectionActivity)
            progressDialog.setContentView(R.layout.progress_bar_custom_dialog)
            progressDialog.setCancelable(false)
            progressDialog.show()
        }

        override fun hideProgressBar() {
            progressDialog.dismiss()
        }
    }

    private val genreSelectionInterface: GenreSelectionPresenter.GenreSelectionInterface = object : GenreSelectionPresenter.GenreSelectionInterface {
        override fun setSubmitButtonHighlighted() {
            btnSubmitGenreChoice.setBackgroundColor(
                ContextCompat.getColor(
                    this@GenreSelectionActivity,
                    R.color.unselected_submit_button_background
                )
            )
            btnSubmitGenreChoice.setTextColor(
                ContextCompat.getColor(
                    this@GenreSelectionActivity,
                    R.color.unselected_submit_button_text
                )
            )
        }

        override fun setSubmitButtonUnhighlighted() {
            btnSubmitGenreChoice.setTextColor(
                ContextCompat.getColor(
                    this@GenreSelectionActivity,
                    R.color.off_black
                )
            )
            btnSubmitGenreChoice.background = ContextCompat.getDrawable(
                this@GenreSelectionActivity,
                R.drawable.button_rectangular_filled_background
            )
        }

        override fun getSelectedGenres(): List<GenreItem> {
            return presenter.getSelectedGenresList()
        }
    }

    private fun setUpRecyclerView(genreList: List<GenreItem>) {
        val viewAdapter = GenreSelectionAdapter(genreList, genreSelectionInterface) { genre: GenreItem -> genreClicked(
            genre
        ) }
        recyclerView = rvGenreChoices.apply {
            setHasFixedSize(true)
            adapter = viewAdapter
        }
    }

    private fun genreClicked(genreItem: GenreItem) {
        presenter.addOrRemoveGenreItemFromList(genreItem)
        presenter.setSubmitButtonHighlightedOrUnHighlighted(genreSelectionInterface)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right)
        presenter.clearGenresList()
    }
}