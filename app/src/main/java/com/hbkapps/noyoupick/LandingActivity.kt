package com.hbkapps.noyoupick

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_landing.*

class LandingActivity : AppCompatActivity() {

    private var mediaType : Int = 0
    private var isFirstSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        setUpButtons()
    }

    private fun setUpButtons() {
        btnSubmitMediaTypeChoice.setOnClickListener {
            if (mediaType !=0) {
                val intent = Intent(this@LandingActivity, GenreSelectionActivity::class.java)
                intent.putExtra("MEDIA_TYPE", mediaType)
                startActivity(intent)
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left)
            }
        }

        btnPickMovie.setOnClickListener {
            if (!isFirstSelected) {
                setSelected(btnSubmitMediaTypeChoice)
                isFirstSelected = true
            }

            if (mediaType != 1) {
                mediaType = 1
                chooseMediaType()
            }
        }

        btnPickTelevision.setOnClickListener {
            if (!isFirstSelected) {
                setSelected(btnSubmitMediaTypeChoice)
                isFirstSelected = true
            }

            if (mediaType != 2) {
                mediaType = 2
                chooseMediaType()
            }
        }

        btnPickBoth.setOnClickListener {
            if (!isFirstSelected) {
                setSelected(btnSubmitMediaTypeChoice)
                isFirstSelected = true
            }

            if (mediaType != 3) {
                mediaType = 3
                chooseMediaType()
            }
        }
    }

    private fun chooseMediaType() {
        //1 - movie, 2 - TV, 3 - both
        when (mediaType) {
            1 -> {
                clearAllSelections()
                setSelected(btnPickMovie)
            }

            2 -> {
                clearAllSelections()
                setSelected(btnPickTelevision)
            }

            3 -> {
                clearAllSelections()
                setSelected(btnPickBoth)
            }
        }
    }

    //only call right before using setSelected()
    private fun clearAllSelections() {
        btnPickMovie.apply {
            this.setTextColor(ContextCompat.getColor(this@LandingActivity, R.color.white))
            this.background = ContextCompat.getDrawable(this@LandingActivity, R.drawable.button_rectangular_stroke_background)
            this.setTypeface(null, Typeface.NORMAL)
        }
        btnPickTelevision.apply {
            this.setTextColor(ContextCompat.getColor(this@LandingActivity, R.color.white))
            this.background = ContextCompat.getDrawable(this@LandingActivity, R.drawable.button_rectangular_stroke_background)
            this.setTypeface(null, Typeface.NORMAL)
        }

        btnPickBoth.apply {
            this.setTextColor(ContextCompat.getColor(this@LandingActivity, R.color.white))
            this.background = ContextCompat.getDrawable(this@LandingActivity, R.drawable.button_rectangular_stroke_background)
            this.setTypeface(null, Typeface.NORMAL)
        }
    }

    private fun setSelected(v : TextView) {
        v.setTextColor(ContextCompat.getColor(this, R.color.off_black))
        v.background = ContextCompat.getDrawable(this, R.drawable.button_rectangular_filled_background)
        v.setTypeface(null, Typeface.BOLD)
    }
}