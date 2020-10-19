package com.hbkapps.noyoupick.landing

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.hbkapps.noyoupick.BaseActivity
import com.hbkapps.noyoupick.Constants
import com.hbkapps.noyoupick.genreselection.GenreSelectionActivity
import com.hbkapps.noyoupick.R
import com.hbkapps.noyoupick.model.Movie
import com.hbkapps.noyoupick.movieslist.MoviesListAdapter
import com.hbkapps.noyoupick.repository.TmdbRepository
import kotlinx.android.synthetic.main.activity_landing.*
import kotlinx.android.synthetic.main.activity_landing.btnPickBoth
import kotlinx.android.synthetic.main.activity_landing.btnPickMovie
import kotlinx.android.synthetic.main.activity_landing.btnPickTelevision
import javax.inject.Inject

class LandingActivity : BaseActivity() {

    @Inject
    lateinit var presenter: LandingPresenter
    private var mediaType : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        application.applicationComponent.inject(this)
        moviesList.setOnClickListener { presenter.loadMoviesList(moviesListListener) }

        setUpButtons()
    }

    private val moviesListListener: TmdbRepository.MoviesListListener = object : TmdbRepository.MoviesListListener {
        override fun loadMoviesList(moviesList: List<Movie>) {
            moviesListRv.adapter = MoviesListAdapter(moviesList)
        }

        override fun onFailure() {
            //todo
        }

    }

    private fun setUpButtons() {
        btnNext.setOnClickListener {
            if (mediaType != 0) {
                val intent = Intent(this@LandingActivity, GenreSelectionActivity::class.java)
                intent.putExtra("MEDIA_TYPE", mediaType)
                startActivity(intent)
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left)
            }
        }

        btnPickMovie.setOnClickListener {
            if (mediaType != Constants.MEDIA_TYPE_MOVIE) {
                mediaType = Constants.MEDIA_TYPE_MOVIE
                chooseMediaTypeAndHighlightNextButton()
            }
        }

        btnPickTelevision.setOnClickListener {
            if (mediaType != Constants.MEDIA_TYPE_TV) {
                mediaType = Constants.MEDIA_TYPE_TV
                chooseMediaTypeAndHighlightNextButton()
            }
        }

        btnPickBoth.setOnClickListener {
            if (mediaType != Constants.MEDIA_TYPE_BOTH) {
                mediaType = Constants.MEDIA_TYPE_BOTH
                chooseMediaTypeAndHighlightNextButton()
            }
        }
    }

    private fun chooseMediaTypeAndHighlightNextButton() {
        when (mediaType) {
            Constants.MEDIA_TYPE_MOVIE -> {
                clearAllSelections()
                setSelected(btnPickMovie)
            }

            Constants.MEDIA_TYPE_TV -> {
                clearAllSelections()
                setSelected(btnPickTelevision)
            }

            Constants.MEDIA_TYPE_BOTH -> {
                clearAllSelections()
                setSelected(btnPickBoth)
            }
        }
        setSelected(btnNext)
    }

    private fun clearAllSelections() {
        btnPickMovie.apply {
            this.setTextColor(ContextCompat.getColor(this@LandingActivity, R.color.white))
            this.background = ContextCompat.getDrawable(this@LandingActivity,
                R.drawable.button_rectangular_stroke_background
            )
            this.setTypeface(null, Typeface.NORMAL)
        }
        btnPickTelevision.apply {
            this.setTextColor(ContextCompat.getColor(this@LandingActivity, R.color.white))
            this.background = ContextCompat.getDrawable(this@LandingActivity,
                R.drawable.button_rectangular_stroke_background
            )
            this.setTypeface(null, Typeface.NORMAL)
        }

        btnPickBoth.apply {
            this.setTextColor(ContextCompat.getColor(this@LandingActivity, R.color.white))
            this.background = ContextCompat.getDrawable(this@LandingActivity,
                R.drawable.button_rectangular_stroke_background
            )
            this.setTypeface(null, Typeface.NORMAL)
        }
    }

    private fun setSelected(v : TextView) {
        v.setTextColor(ContextCompat.getColor(this, R.color.off_black))
        v.background = ContextCompat.getDrawable(this,
            R.drawable.button_rectangular_filled_background
        )
        v.setTypeface(null, Typeface.BOLD)
    }
}