package com.hbkapps.noyoupick

import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_genre_selection.*

class GenreSelectionActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter : RecyclerView.Adapter<*>
    private lateinit var viewManager : RecyclerView.LayoutManager

    private var mGenreList : ArrayList<String> = ArrayList()
    private var isFirstSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre_selection)

        setUpRecyclerView()

        btnSubmitGenreChoice.setOnClickListener {
            if (isFirstSelected) {
                val mediaType = when (intent.getIntExtra("MEDIA_TYPE", 0)) {
                    1 -> "Movie"
                    2 -> "TV Show"
                    3 -> "Both"
                    else -> "ERROR"
                }

                Toast.makeText(this, "Media type is $mediaType, list of genres is: $mGenreList", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun setUpRecyclerView() {
        val genreList = ArrayList(arrayListOf(*resources.getStringArray(R.array.genre_list)))

        viewManager = LinearLayoutManager(this)
        viewAdapter = UserSelectionViewAdapter(genreList) { genreTV: TextView -> genreClicked(genreTV) }


        recyclerView = rvGenreChoices.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun genreClicked(v : TextView) {
        if (!isFirstSelected) {
            setSelected(btnSubmitGenreChoice)
            isFirstSelected = true
        }

        if (v.typeface == Typeface.DEFAULT_BOLD) {
            setUnSelected(v)
            mGenreList.remove((v.text).toString())
        } else {
            setSelected(v)
            mGenreList.add((v.text).toString())
        }

        if (mGenreList.isEmpty()) {
            btnSubmitGenreChoice.setBackgroundColor(ContextCompat.getColor(this, R.color.unselected_submit_button_background))
            btnSubmitGenreChoice.setTextColor(ContextCompat.getColor(this, R.color.unselected_submit_button_text))
            isFirstSelected = false
        }
    }

    //this function repeats from LandingActivity - should live somewhere other than both activities so we can call both
    private fun setSelected(v : TextView) {
        v.setTextColor(ContextCompat.getColor(this, R.color.off_black))
        v.background = ContextCompat.getDrawable(this, R.drawable.button_rectangular_filled_background)
        v.setTypeface(null, Typeface.BOLD)
    }

    private fun setUnSelected(v : TextView) {
        v.setTextColor(ContextCompat.getColor(this, R.color.white))
        v.background = ContextCompat.getDrawable(this, R.drawable.button_rectangular_stroke_background)
        v.setTypeface(null, Typeface.NORMAL)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right)
    }
}