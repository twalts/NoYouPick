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
import kotlinx.android.synthetic.main.activity_landing.*
import kotlinx.android.synthetic.main.activity_landing.btnPickMovie
import kotlinx.android.synthetic.main.activity_landing.btnPickTelevision
import javax.inject.Inject

class LandingActivity : BaseActivity() {

    @Inject
    lateinit var presenter: LandingPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        application.applicationComponent.inject(this)

        setUpButtons()
    }

    private val landingInterface : LandingPresenter.LandingInterface = object : LandingPresenter.LandingInterface {
        override fun startGenreSelectionActivity() {
            val intent = Intent(this@LandingActivity, GenreSelectionActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left)
        }
    }

    private fun setUpButtons() {
        btnNext.setOnClickListener {
            presenter.onNextButtonClicked(landingInterface)
        }

        btnPickMovie.setOnClickListener {
            clearAllSelections()
            setSelected(btnNext)
            setSelected(btnPickMovie)
            presenter.saveMediaTypeSelectionToRepo(Constants.MEDIA_TYPE_MOVIE)
        }

        btnPickTelevision.setOnClickListener {
            clearAllSelections()
            setSelected(btnNext)
            setSelected(btnPickTelevision)
            presenter.saveMediaTypeSelectionToRepo(Constants.MEDIA_TYPE_TV)
        }
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
    }

    private fun setSelected(v : TextView) {
        v.setTextColor(ContextCompat.getColor(this, R.color.off_black))
        v.background = ContextCompat.getDrawable(this,
            R.drawable.button_rectangular_filled_background
        )
        v.setTypeface(null, Typeface.BOLD)
    }
}